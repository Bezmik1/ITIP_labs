package university.spring_lab3_notifications.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import university.spring_lab3_notifications.model.dto.RegisterRequest;
import university.spring_lab3_notifications.model.entity.User;
import university.spring_lab3_notifications.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldRegisterUser() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Иван Петров");
        request.setEmail("ivan@test.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail("ivan@test.com")).thenReturn(false);
        when(passwordEncoder.encode("123456")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertDoesNotThrow(() -> authService.register(request));

        // ========== ЗАДАНИЕ №6: verify с times(1) ==========
        verify(userRepository, times(1)).existsByEmail("ivan@test.com");
        verify(passwordEncoder, times(1)).encode("123456");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@test.com");

        when(userRepository.existsByEmail("existing@test.com")).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> authService.register(request));
        
        // ========== ЗАДАНИЕ №6: verify с times(1) ==========
        verify(userRepository, times(1)).existsByEmail("existing@test.com");
        verify(userRepository, never()).save(any(User.class));
    }
}