package com.fsd.workoutportal.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fsd.workoutportal.model.User;
import com.fsd.workoutportal.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {
	Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void registerUserTest() throws Exception {
		log.info("Start: registerUserTest");
		User mockUser = new User();
		mockUser.setName("testuser");
		mockUser.setPassword("pass");
		
		Mockito.when(userService.addUser(Mockito.any(User.class))).thenReturn(mockUser);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/user")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"test\",\"password\": \"pass\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		//System.out.println(result.getResponse().getContentAsString());
		String expected = "{status: SUCCESS, message: null}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		//assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		log.info("Exit: registerUserTest");
	}
	
	@Test
	public void loginUserTest() throws Exception {
		log.info("Start: loginUserTest");
		User mockUser = new User();
		mockUser.setName("testuser");
		mockUser.setPassword("pass");
		
		Mockito.when(userService.login(Mockito.any(User.class))).thenReturn(mockUser);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/user/login")
				.accept(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"testuser\",\"password\": \"pass\"}")
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		log.info("Exit: loginUserTest");
	}
	

}
