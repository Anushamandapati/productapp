package com.capgemini.productapp;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.capgemini.productapp.controller.ProductController;
import com.capgemini.productapp.entity.Product;
import com.capgemini.productapp.service.ProductService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductControllerTest
{
	@Mock
	public ProductService productService;
	@InjectMocks
	private ProductController productController;
	private MockMvc mockMvc;

	@Before
	public void setUp() 
	{
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}
	@Test
	public void testAddProductWhichAddsProductObject() throws Exception
	{
		when(productService.addProduct(Mockito.isA(Product.class)))
		.thenReturn(new Product(1234,"Motopower","Mobile",16000.0));
		mockMvc.perform(post("/product")
				 	.contentType(MediaType.APPLICATION_JSON)
				 	.content("{\"productId\": \"1234\", \"productName\": \"Motopower\",\"productCategory\":\"Mobile\",\"productPrice\": \"16000.0\"}")
				 	.accept(MediaType.APPLICATION_JSON))
				 	.andExpect(status().isOk())
				 	.andExpect(jsonPath("$.productId").exists())
				 	.andExpect(jsonPath("$.productName").exists())
				 	.andExpect(jsonPath("$.productCategory").exists())
				 	.andExpect(jsonPath("$.productPrice").exists())
				 	.andDo(print());	
	}
	@Test
	public void testFindProductbyId()throws Exception
	{
		when(productService.findProductById(1234)).thenReturn(new Product(1234, "Motopower", "Mobile", 16000.0));
		mockMvc.perform(get("/products/1234"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.productId").exists())
        .andExpect(jsonPath("$.productName").exists())
        .andExpect(jsonPath("$.productId").value(1234))
        .andExpect(jsonPath("$.productName").value("Motopower"))
        .andDo(print());		              
	}
	@Test
	public void testUpdateProduct() throws Exception
	{
		String content="{\"productId\":1234,\"productName\":\"Mac\",\"productCategory\":\"mobile\",\"productPrice\":17000.0}";
		when(productService.updateProduct(Mockito.isA(Product.class))).thenReturn(new Product(1234, "Mac", "mobile", 17000.0));
		when(productService.findProductById(Mockito.isA(Integer.class))).thenReturn(new Product(1234, "Motopower", "Mobile", 16000.0));
		mockMvc.perform(put("/product")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.productId").exists())
		.andExpect(jsonPath("$.productName").exists())
		.andExpect(jsonPath("$.productId").value(1234))
		.andExpect(jsonPath("$.productName").value("Mac"));
	}
	@Test
	public void testDeleteProduct() throws Exception
	{
		when(productService.findProductById(1234)).thenReturn(new Product(1234, "Mac", "Mobile", 16000.0));
		mockMvc.perform(delete("/products/1234")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}
	
}
