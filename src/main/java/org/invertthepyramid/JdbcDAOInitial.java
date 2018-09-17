package org.invertthepyramid;

import org.invertthepyramid.domain.Customer;
import org.invertthepyramid.nonfunctionals.ILogger;
import org.invertthepyramid.nonfunctionals.IMetrics;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcDAOInitial {
    final static String successfulMetricName = "database.success";
    final static String failedMetricName = "database.failure";
    final static String findByFirstNameSql = "SELECT id, first_name, last_name FROM customers WHERE first_name = ?";
    final static String findByLastNameSql = "SELECT id, first_name, last_name FROM customers WHERE last_name = ?";

    private final ILogger log; // configured by dependency injection
    private JdbcTemplate jdbc;
    private final IMetrics metrics; // our interface that decouples us from the metrics library we are using

    public JdbcDAOInitial(ILogger log, JdbcTemplate jdbc, IMetrics metrics) {
        this.log = log;
        this.jdbc = jdbc;
        this.metrics = metrics;
    }

    public List<Customer> findCustomerByFirstName(String firstName) {
        log.info("Querying for customer records where first_name = '" + firstName + "':");
        try {
            List<Customer> customers = jdbc.query(findByFirstNameSql, new Object[]{firstName},
                    (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
            );
            customers.forEach(customer -> log.info("Loaded: " + customer.toString()));
            metrics.mark(successfulMetricName, 1);
            return customers;
        } catch (RuntimeException e) {
            metrics.mark(failedMetricName, 1);
            log.error("Failed to load customers with first name = " + firstName, e);
            throw e;
        }
    }

    public List<Customer> findCustomerByLastName(String lastName) {
        log.info("Querying for customer records where last_name = '" + lastName + "':");
        try {
            List<Customer> customers = jdbc.query(findByLastNameSql, new Object[]{lastName},
                    (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
            );
            customers.forEach(customer -> log.info("Loaded: " + customer.toString()));
            metrics.mark(successfulMetricName, 1);
            return customers;
        } catch (RuntimeException e) {
            metrics.mark(failedMetricName, 1);
            log.error("Failed to load customers with last name = " + lastName, e);
            throw e;
        }
    }
}