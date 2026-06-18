package university.spring_lab3_notifications.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import university.spring_lab3_notifications.model.dto.UserDto;
import university.spring_lab3_notifications.model.dto.UserMapper;
import university.spring_lab3_notifications.model.entity.User;
import university.spring_lab3_notifications.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldGetUserById() {
        Long userId = 1L;
        
        User user = new User();
        user.setId(userId);
        user.setName("Иван Петров");
        user.setEmail("ivan@test.com");
        
        UserDto expectedDto = UserDto.builder()
                .name("Иван Петров")
                .email("ivan@test.com")
                .build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(expectedDto);
        
        UserDto result = userService.getUserById(userId);
        
        assertNotNull(result);
        assertEquals("Иван Петров", result.getName());
        
        // ========== ЗАДАНИЕ №6: verify с times(1) ==========
        // Проверяем, что метод findById был вызван ровно 1 раз с аргументом userId
        verify(userRepository, times(1)).findById(userId);
        
        // Проверяем, что метод toResponse был вызван ровно 1 раз с аргументом user
        verify(userMapper, times(1)).toResponse(user);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);
        
        userService.deleteUser(userId);
        
        // ========== ЗАДАНИЕ №6: verify с times(1) ==========
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }
}