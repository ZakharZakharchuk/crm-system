package org.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.config.security.AuthenticationRequest;
import org.example.data.entity.User;
import org.example.exceptions.UserProcessingException;
import org.example.repository.UserRepository;
import org.example.utils.UnitTestExpectedEntitySupplier;
import org.example.utils.UnitTestMockedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest implements UnitTestMockedData {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User userWithoutFullName;
    private AuthenticationRequest authenticationRequest;
    private User user;
    private String username;

    @BeforeEach
    public void setUp() {
        userWithoutFullName = UnitTestExpectedEntitySupplier.createUserWithoutUsername();
        user = UnitTestExpectedEntitySupplier.createUser();
        username = userWithoutFullName.getFirstname() + " " + userWithoutFullName.getLastname();
        authenticationRequest = UnitTestExpectedEntitySupplier.createAuthenticationRequest();
    }

    @Test
    public void testGenerateFullName_NoMatchingFullNames_ReturnsConcatenatedName() {
        when(userRepository.countByFullNameStartingWith(username)).thenReturn(0L);

        String actualFullName = userService.generateUsername(userWithoutFullName);

        assertEquals(username, actualFullName);

        verify(userRepository).countByFullNameStartingWith(username);
    }

    @Test
    public void testGenerateFullName_MatchingFullNameExists_ReturnsExistingFullName() {
        when(userRepository.countByFullNameStartingWith(username)).thenReturn(1L);
        when(userRepository.findById(EXIST_ID)).thenReturn(Optional.of(user));

        String actualFullName = userService.generateUsername(userWithoutFullName);

        assertEquals(username, actualFullName);

        verify(userRepository).countByFullNameStartingWith(username);
        verify(userRepository).findById(EXIST_ID);
    }

    @Test
    public void testGenerateFullName_MatchingFullNameExists_ReturnsModifiedFullName() {
        when(userRepository.countByFullNameStartingWith(username)).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        String actualFullName = userService.generateUsername(userWithoutFullName);

        assertEquals(username + "1", actualFullName);

        verify(userRepository).countByFullNameStartingWith(username);
        verify(userRepository).findById(EXIST_ID);
    }

    @Test
    public void testChangePassword() {
        when(userRepository.existsByEmail(authenticationRequest.getEmail())).thenReturn(true);
        doNothing().when(userRepository).updatePasswordByEmail(authenticationRequest.getEmail(),
              authenticationRequest.getPassword());

        userService.changePassword(authenticationRequest);

        verify(userRepository).existsByEmail(authenticationRequest.getEmail());
        verify(userRepository).updatePasswordByEmail(authenticationRequest.getEmail(),
              authenticationRequest.getPassword());
    }

    @Test
    public void changePasswordShouldThrowUserProcessingException() {
        String wrongEmail = "Wrong email";
        authenticationRequest.setEmail(wrongEmail);
        when(userRepository.existsByEmail(authenticationRequest.getEmail())).thenReturn(false);

        Exception exception = assertThrows(UserProcessingException.class,
              () -> userService.changePassword(authenticationRequest));

        String expectedMessage = UserService.USER_EXCEPTION;
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);

        verify(userRepository).existsByEmail(wrongEmail);
    }
}
