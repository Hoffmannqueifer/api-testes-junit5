package com.dicasdeumdev.api.resources.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.dicasdeumdev.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class ResourceExceptionHandlerTest {

	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	@InjectMocks
	private ResourceExceptionHandler exceptionHandler;
	
	@BeforeEach
	void setUP() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
		ResponseEntity<StandardError> response = exceptionHandler.objectNotFound(
				new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO), new MockHttpServletRequest());
		
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getBody());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		Assertions.assertEquals(ResponseEntity.class, response.getClass());
		Assertions.assertEquals(StandardError.class, response.getBody().getClass());
		Assertions.assertEquals(USUARIO_NAO_ENCONTRADO, response.getBody().getError());
		Assertions.assertEquals(404, response.getBody().getStatus());
		
	}
}
