package university.spring_lab3_notifications.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import university.spring_lab3_notifications.model.dto.NotificationDto;
import university.spring_lab3_notifications.model.entity.Notification;
import university.spring_lab3_notifications.model.entity.User;
import university.spring_lab3_notifications.model.enums.NotificationChannel;
import university.spring_lab3_notifications.model.enums.NotificationStatus;
import university.spring_lab3_notifications.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void shouldGetNotificationById() {
        Long notificationId = 1L;
        
        User user = new User();
        user.setId(1L);
        
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setTitle("Тестовое уведомление");
        notification.setMessage("Текст сообщения");
        notification.setChannel(NotificationChannel.EMAIL);
        notification.setStatus(NotificationStatus.CREATED);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRecipient(user);
        
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        
        NotificationDto result = notificationService.getNotificationById(notificationId);
        
        assertNotNull(result);
        assertEquals("Тестовое уведомление", result.getTitle());
        
        // ========== ЗАДАНИЕ №6: verify с times(1) ==========
        verify(notificationRepository, times(1)).findById(notificationId);
    }

    @Test
    void shouldThrowExceptionWhenNotificationNotFound() {
        Long notificationId = 99L;
        
        when(notificationRepository.findById(notificationId)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () ->
            notificationService.getNotificationById(notificationId));
        
        // ========== ЗАДАНИЕ №6: verify с times(1) ==========
        verify(notificationRepository, times(1)).findById(notificationId);
    }
}