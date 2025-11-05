package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.model.ResponseDefObject;
import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import com.example.demo.utility.Util;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICustomerService customerService;

    @Test
    public void testGetCustomers() throws Exception {

        List<Customer> listCustomer = new ArrayList<>();

        listCustomer.add(new Customer("1", "Jitendra", "Kumar", new Date(), "Title1"));
        listCustomer.add(new Customer("2", "Aman", "Kumar", new Date(), "Title2"));
        listCustomer.add(new Customer("3", "Amit", "Kumar", new Date(), "Title3"));
        listCustomer.add(new Customer("4", "Suresh", "Kumar", new Date(), "Title4"));

        Mockito.when(customerService.readCustomer()).thenReturn(listCustomer);

        String url = "/getcustomers";

        MvcResult mvcResult = mockMvc.perform(get(url)).andExpect(status().isAccepted()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonObjectResponse = new JSONObject(actualJsonResponse);
        String actual = jsonObjectResponse.getString("data");

        String exptectedJsonResponse = objectMapper
                .writeValueAsString(new ResponseEntity<>(
                        new ResponseDefObject<>(HttpStatus.CREATED.value(), Util.SUCCESS, listCustomer), HttpStatus.ACCEPTED));

        JSONObject jsonObject = new JSONObject(exptectedJsonResponse);
        System.out.println(exptectedJsonResponse);
        String getBody = jsonObject.getString("body");
        JSONObject jsonObjectBody = new JSONObject(getBody);
        String expected = jsonObjectBody.getString("data");

        assertThat(actual).isEqualToIgnoringWhitespace(expected);
    }

    @Test
    public void testCreateCustomer() throws Exception {
        Customer customer = new Customer("1", "Jitendra", "Kumar", new Date(), "title1");
        Customer savedCustomer = new Customer("1", "Jitendra", "Kumar", new Date(), "title1");

        // For create, customer should not exist
        Mockito.when(customerService.isCustomerExist(customer.getCustomerId())).thenReturn(false);
        Mockito.when(customerService.createCustomer(Mockito.any(Customer.class))).thenReturn(savedCustomer);

        String url = "/savecustomer";

        mockMvc.perform(post(url).contentType("application/json").content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        Customer existingCustomer = new Customer("1", "Jitendra", "Kumar", new Date(), "title1");
        Customer savedCustomer = new Customer("1", "Jackson", "Kumar", new Date(), "title1");

        // For update, customer must exist
        Mockito.when(customerService.isCustomerExist(existingCustomer.getCustomerId())).thenReturn(true);
        Mockito.when(customerService.createCustomer(Mockito.any(Customer.class))).thenReturn(savedCustomer);

        String url = "/updatecustomer";

        mockMvc.perform(
                post(url).contentType("application/json").content(objectMapper.writeValueAsString(existingCustomer)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testDeleteCustomer() throws Exception {
        String id = "1";

        Mockito.when(customerService.isCustomerExist(id)).thenReturn(true);
        Mockito.when(customerService.deleteCustomer(id)).thenReturn("Customer with id: " + id + " deleted successfully.");

        String url = "/deletecustomer/" + id;

        mockMvc.perform(delete(url)).andExpect(status().isAccepted());

        Mockito.verify(customerService, times(1)).deleteCustomer(id);
    }
}

