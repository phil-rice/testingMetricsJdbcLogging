package org.invertthepyramid;

import org.invertthepyramid.domain.Customer;
import org.invertthepyramid.nonfunctionals.ILogger;
import org.invertthepyramid.nonfunctionals.IMetrics;
import org.invertthepyramid.utils.FunctionWithExceptionDecorator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class JdbcFunctionTest {

    JdbcDAOFunctional dao = new JdbcDAOFunctional(mock(ILogger.class), mock(JdbcTemplate.class), mock(IMetrics.class));
    Function<String, List<Customer>> delegate = mock(Function.class);

    @Test
    public void testNonfunctionalsAreMetricsAndLogging() {
        Function<String, List<Customer>> function = dao.nonfunctionals.apply(delegate);
        Assert.assertEquals(Arrays.asList("MetricsDelegator", "LoggerDecorator"), FunctionWithExceptionDecorator.delegateNames(function));
    }

    @Test
    public void testNonfunctionalsWrapsDelegate() {
        Function<String, List<Customer>> function = dao.nonfunctionals.apply(delegate);
        Assert.assertEquals(delegate, FunctionWithExceptionDecorator.getFinalDelegate(function));
    }

    @Test
    public void testFindCustomerByFirstNameLogsAndMetrics() {
        Assert.assertEquals(Arrays.asList("MetricsDelegator", "LoggerDecorator"), FunctionWithExceptionDecorator.delegateNames(dao.findCustomerByFirstName));
    }

    @Test
    public void testFindCustomerByLastNameLogsAndMetrics() {
        Assert.assertEquals(Arrays.asList("MetricsDelegator", "LoggerDecorator"), FunctionWithExceptionDecorator.delegateNames(dao.findCustomerBySecondName));
    }

    @Test
    public void testFindCustomerByFirstNameDelegatesToCustomerOpsAndIsSetupCorrectly() {
        JdbcListGetter function = (JdbcListGetter) FunctionWithExceptionDecorator.getFinalDelegate(dao.findCustomerByFirstName);
        Assert.assertEquals(CustomerOps.findByFirstNameSql, function.sql);
        Assert.assertEquals(CustomerOps.asArray, function.params);
        Assert.assertEquals(CustomerOps.rowMapper, function.rowMapper);
    }
    @Test
    public void testFindCustomerByLastNameDelegatesToCustomerOpsAndIsSetupCorrectly() {
        JdbcListGetter function = (JdbcListGetter) FunctionWithExceptionDecorator.getFinalDelegate(dao.findCustomerBySecondName);
        Assert.assertEquals(CustomerOps.findByLastNameSql, function.sql);
        Assert.assertEquals(CustomerOps.asArray, function.params);
        Assert.assertEquals(CustomerOps.rowMapper, function.rowMapper);
    }


}
