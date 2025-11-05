package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.demo.model.Customer;

@WebMvcTest
public class CustomerServiceTest {

	@MockBean
	private ICustomerService customerService;

	@Test
	public void testGetCustomers() {

		List<Customer> listCustomer = new ArrayList<>();

		listCustomer.add(new Customer("1", "Jitendra", "Kumar", new Date(), "Title1"));
		listCustomer.add(new Customer("2", "Aman", "Kumar", new Date(), "Title2"));
		listCustomer.add(new Customer("3", "Amit", "Kumar", new Date(), "Title3"));
		listCustomer.add(new Customer("4", "Suresh", "Kumar", new Date(), "Title4"));

		Mockito.when(customerService.readCustomer()).thenReturn(listCustomer);

		List<Customer> expected = customerService.readCustomer();

		assertThat(listCustomer).isSameAs(expected);
	}

	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer("1", "Jitendra", "Kumar", new Date(), "title1");
		Customer savedCustomer = new Customer("1", "Jitendra", "Kumar", new Date(), "title1");

		Mockito.when(customerService.createCustomer(customer)).thenReturn(savedCustomer);

		Customer actual = customerService.createCustomer(customer);

		assertThat(actual).isSameAs(savedCustomer);
	}

	@Test
	public void testUpdateCustomer() {
		Customer existingCustomer = new Customer("1", "Jitendra", "Kumar", new Date(), "title1");
		Customer savedCustomer = new Customer("1", "Jackson", "Kumar", new Date(), "title1");

		Mockito.when(customerService.createCustomer(existingCustomer)).thenReturn(savedCustomer);

		Customer actual = customerService.createCustomer(existingCustomer);

		assertThat(actual).isSameAs(savedCustomer);
	}

	@Test
	public void testDeleteCustomer() {
		String id = "1";

		Mockito.when(customerService.deleteCustomer(id)).thenReturn("Customer with id: " + id + " deleted successfully.");
		String deletedRecord = customerService.deleteCustomer(id);
		String expected = "Customer with id: " + id + " deleted successfully.";

		assertEquals(deletedRecord, expected);
	}
}

