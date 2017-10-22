package org.smart4j.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/10/22.
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    @Before
    public void init() throws Exception {
//        String file = "sql/customer_init.sql";
//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        String sql;
//        while ((sql = reader.readLine()) != null) {
//            DatabaseHelper.executeUpdate(sql);
//        }
    }

    @Test
    public void getCustomerLlist() {
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2, customerList.size());
    }
    @Test
    public void getCustomerTest() {
        long id = 1;
        Customer customer = customerService.getCustomer(1);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomerMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        fieldMap.put("name", "customer100");
        fieldMap.put("contact", "John");
        fieldMap.put("telephone", "12312345678");
        boolean customer = customerService.createCustomer(fieldMap);
        Assert.assertTrue(customer);
    }

    @Test
    public void updateCustomerMap() {
        long id = 1;
        Map<String, Object> fieldMap = new HashMap<>();

        fieldMap.put("contact", "Eric");

        boolean customer = customerService.updateCustomer(id, fieldMap);
        Assert.assertTrue(customer);
    }

    @Test
    public void deleteCustomerMap() {
        long id = 1;


        boolean customer = customerService.deleteCustomer(id);
        Assert.assertTrue(customer);
    }
}
