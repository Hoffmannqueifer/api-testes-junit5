package com.dicasdeumdev.api.resources;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dicasdeumdev.api.domain.User;
import com.dicasdeumdev.api.domain.dto.UserDTO;
import com.dicasdeumdev.api.services.impl.UserServiceImpl;

@SpringBootTest
public class UserResourceTest {
	
	private static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
	private static final int INDEX = 0;
	private static final String USUÁRIO_NÃO_ENCONTRADO = "Usuário não encontrado";
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
	
	@Test
	void whenFindAllThenReturnAListOfUserDTO() {
		Mockito.when(service.findAll()).thenReturn(List.of(user));
		Mockito.when(mapper.map(Mockito.any(), Mockito.any())).thenReturn(userDTO);
		
		ResponseEntity<List<UserDTO>> response = resource.findAll();
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(ArrayList.class, response.getBody().getClass());
		Assertions.assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());
		
		Assertions.assertEquals(ID, response.getBody().get(INDEX).getId());
    	Assertions.assertEquals(NAME, response.getBody().get(INDEX).getName());
    	Assertions.assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
    	Assertions.assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
		
	}
}
