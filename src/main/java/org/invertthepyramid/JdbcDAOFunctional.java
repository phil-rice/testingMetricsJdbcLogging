package org.invertthepyramid;

import org.invertthepyramid.domain.Customer;
import org.invertthepyramid.nonfunctionals.*;
import org.invertthepyramid.utils.FunctionDelegator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.function.Function;

import static org.invertthepyramid.CustomerOps.*;

public class JdbcDAOFunctional {
    final static String successfulMetricName = "database.success";
    final static String failedMetricName = "database.failure";

    final FunctionDelegator<String, List<Customer>> nonfunctionals;

    public Function<String, List<Customer>> findCustomerByFirstName;
    public Function<String, List<Customer>> findCustomerBySecondName;

    public JdbcDAOFunctional(ILogger log, JdbcTemplate jdbc, IMetrics metrics) {
        this.nonfunctionals = delegate -> new NonFunctionalBuilder<>(delegate).
                withLogger(log, CustomerOps::displayCustomer, CustomerOps::displayFailedLoad).
                withMetrics(metrics, successfulMetricName, failedMetricName).build();

        findCustomerByFirstName = nonfunctionals.apply(findCustomerByFirstNameFromJdbc(jdbc));
        findCustomerBySecondName = nonfunctionals.apply(findCustomerByLastNameFromJdbc(jdbc));
    }
}

interface CustomerOps {
    final static RowMapper<Customer> rowMapper = (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"));

    static String displayCustomer(String name, List<Customer> customers) { return customers.toString(); }

    static String displayFailedLoad(String name, RuntimeException e) { return "Failed to load: " + name; }

    final static String findByFirstNameSql = "SELECT id, first_name, last_name FROM customers WHERE first_name = ?";
    final static String findByLastNameSql = "SELECT id, first_name, last_name FROM customers WHERE last_name = ?";
    static Function<String, Object[]> asArray = string -> new Object[]{string};

    static Function<String, List<Customer>> findCustomerByFirstNameFromJdbc(JdbcTemplate jdbc) { return new JdbcListGetter<>(jdbc, findByFirstNameSql, asArray, rowMapper); }

    static Function<String, List<Customer>> findCustomerByLastNameFromJdbc(JdbcTemplate jdbc) { return new JdbcListGetter<>(jdbc, findByLastNameSql, asArray, rowMapper); }

}
