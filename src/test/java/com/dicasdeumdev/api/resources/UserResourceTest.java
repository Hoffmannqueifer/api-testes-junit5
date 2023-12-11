package com.dicasdeumdev.api.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;

import com.dicasdeumdev.api.domain.User;
import com.dicasdeumdev.api.domain.dto.UserDTO;
import com.dicasdeumdev.api.services.impl.UserServiceImpl;

@SpringBootTest
public class UserResourceTest {
	
	public static final int ID = 1;
    public static final String NAME = "hoff";
    public static final String EMAIL = "hoff@gmail.com";
    public static final String PASSWORD = "1122";
    
    private User user;
    private UserDTO userDTO;

	@InjectMocks
	private UserResource resource;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private UserServiceImpl service;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}
	
	private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
	
	@Test
	void whenFindByIDThenReturnSuccess() {
		Mockito.when(service.findById(Mockito.anyInt())).thenReturn(user);
		Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<UserDTO> response = resource.findById(ID);
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(UserDTO.class, response.getBody().getClass());
		
		Assertions.assertEquals(ID, response.getBody().getId());
        Assertions.assertEquals(NAME, response.getBody().getName());
        Assertions.assertEquals(EMAIL, response.getBody().getEmail());
        Assertions.assertEquals(PASSWORD, response.getBody().getPassword());
		
	}
}
