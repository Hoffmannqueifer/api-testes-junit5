package com.dicasdeumdev.api.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.dicasdeumdev.api.domain.User;
import com.dicasdeumdev.api.domain.dto.UserDTO;
import com.dicasdeumdev.api.repositories.UserRepository;
import com.dicasdeumdev.api.services.exceptions.DataIntegratyViolationException;
import com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
class UserServiceImplTest {

    private static final int INDEX = 0;
	private static final String USUÁRIO_NÃO_ENCONTRADO = "Usuário não encontrado";
	public static final int ID = 1;
    public static final String NAME = "hoff";
    public static final String EMAIL = "hoff@gmail.com";
    public static final String PASSWORD = "1122";
    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;

    private User user;

    private UserDTO userDTO;

    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);
        User response = service.findById(ID);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(User.class, response.getClass());
        Assertions.assertEquals(ID, response.getId());
        Assertions.assertEquals(NAME, response.getName());
        Assertions.assertEquals(EMAIL, response.getEmail());
    }
    
    @Test
    void whenFindByIdThenReturnAnObjecttNotFoundException() {
    	when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(USUÁRIO_NÃO_ENCONTRADO));
    	
    	try {
    		service.findById(ID);
    	}catch(Exception ex) {
    		assertEquals(ObjectNotFoundException.class, ex.getClass());
    		assertEquals(USUÁRIO_NÃO_ENCONTRADO, ex.getMessage());
    	}
    }

    @Test
    void whenfindAllThenReturnAnListOfUsers() {
    	when(repository.findAll()).thenReturn(List.of(user));
    	List<User> response = service.findAll();
    	
    	Assertions.assertNotNull(response);
    	Assertions.assertEquals(1, response.size());
    	Assertions.assertEquals(User.class, response.get(INDEX).getClass());
    	Assertions.assertEquals(ID, response.get(INDEX).getId());
    	Assertions.assertEquals(NAME, response.get(INDEX).getName());
    	Assertions.assertEquals(EMAIL, response.get(INDEX).getEmail());
    	Assertions.assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    void whenCreateThenReturnSucess() {
    	when(repository.save(Mockito.any())).thenReturn(user);
    	
    	User response = service.create(userDTO);
    	Assertions.assertNotNull(response);
    	Assertions.assertEquals(ID, response.getId());
    	Assertions.assertEquals(NAME, response.getName());
    	Assertions.assertEquals(EMAIL, response.getEmail());
    	Assertions.assertEquals(PASSWORD, response.getPassword());
    }
    
    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
    	when(repository.findByEmail(Mockito.anyString())).thenReturn(optionalUser);
    	
    	try {
    		optionalUser.get().setId(2);
    		service.create(userDTO);
    	}catch(Exception ex) {
    		Assertions.assertEquals(DataIntegratyViolationException.class, ex.getClass());
    		Assertions.assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
    	}
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}