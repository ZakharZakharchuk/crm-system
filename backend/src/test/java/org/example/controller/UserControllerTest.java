package org.example.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.config.security.AuthenticationRequest;
import org.example.service.UserService;
import org.example.utils.UnitTestExpectedEntitySupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private final static String USER_ENDPOINT = "/users";
    private final static String CHANGE_PASSWORD_ENDPOINT="/new-password";

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private AuthenticationRequest authenticationRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
              .standaloneSetup(userController)
              .build();
        authenticationRequest = UnitTestExpectedEntitySupplier.createAuthenticationRequest();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void changePasswordShouldReturnNoContent() throws Exception {
        doNothing().when(userService).changePassword(authenticationRequest);

        mockMvc
              .perform(post(USER_ENDPOINT + CHANGE_PASSWORD_ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest)))
              .andExpect(status().isNoContent());

        verify(userService).changePassword(authenticationRequest);
    }
}
