package org.invertthepyramid;

import org.invertthepyramid.domain.Customer;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerOpsTest {

    Customer customer1 = new Customer(1, "john", "smith");
    Customer customer2 = new Customer(2, "jane", "doe");
    List<Customer> customers = Arrays.asList(customer1, customer2);

    @Test
    public void testDisplayCustomerJustDoesToString() {
        assertEquals("[]", CustomerOps.displayCustomer("irrelevant", Arrays.asList()));
        assertEquals(customers.toString(), CustomerOps.displayCustomer("irrelevant", customers));
    }

    @Test
    public void testDisplayLoadFailedString() {
        assertEquals("Failed to load: <name>", CustomerOps.displayFailedLoad("<name>", new RuntimeException("some message")));
    }

    @Test
    public void testAsArrayJustListsTheNameIntoAnArray() {
        Object[] actual = CustomerOps.asArray.apply("<name>");
        assertEquals(1, actual.length);
        assertEquals("<name>", actual[0]);
    }

    @Test
    public void testRowMapperMakesACharacter() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong("id")).thenReturn(123l);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Smith");
        assertEquals(new Customer(123, "John", "Smith"), CustomerOps.rowMapper.mapRow(resultSet, 999));
        verify(resultSet, times(0)).next();
    }


}
