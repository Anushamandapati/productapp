package com.capgemini.productapp;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.repository.ProductRepository;
import com.capgemini.productapp.service.impl.ProductServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest
{
	private MockMvc mockMvc;
	
	Product product = new Product(1, "dell", "laptop", 100000);
	@Mock
	private ProductRepository productRepository;
	@InjectMocks
	private ProductServiceImpl productServiceImpl;
	
	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productServiceImpl).build();
	}
	
	@Test
	public void testAddProductService() {
		System.out.println("product before add method  called" +product);
		
		when(productRepository.save(product)).thenReturn(product);
		Product result = productServiceImpl.addProduct(product);
		assertEquals(result, product);
		
		System.out.println("product after add method  called" +result);
	}
	@Test
	public void testUpdateProductService() {
		System.out.println("product before update method called" +product);
		
		when(productRepository.save(product)).thenReturn(product);
		Product result = productServiceImpl.updateProduct(product);
		
	}
	
	

}
