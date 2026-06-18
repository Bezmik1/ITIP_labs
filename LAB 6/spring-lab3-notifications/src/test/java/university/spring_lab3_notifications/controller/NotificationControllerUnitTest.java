package university.spring_lab3_notifications.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import university.spring_lab3_notifications.model.dto.NotificationDto;
import university.spring_lab3_notifications.service.NotificationService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest - поднимает только веб-слой (контроллеры)
// Не загружает всю базу данных и другие сервисы
@WebMvcTest(NotificationController.class)
// @AutoConfigureMockMvc(addFilters = false) - отключает фильтры безопасности (JWT, CSRF)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    // @Autowired - Spring сам создаст и внедрит MockMvc
    @Autowired
    private MockMvc mockMvc;

    // @MockBean - создает мок-объект в контексте Spring
    @MockBean
    private NotificationService notificationService;

    // ========== ТЕСТ 1: GET /notifications/all ==========
    @Test
    void shouldReturnAllNotifications() throws Exception {
        // Подготовка данных
        NotificationDto dto = new NotificationDto();
        dto.setTitle("Уведомление 1");
        List<NotificationDto> notifications = Arrays.asList(dto);
        
        // Настройка мока
        when(notificationService.getAllNotifications()).thenReturn(notifications);
        
        // Выполнение HTTP GET запроса и проверка
        mockMvc.perform(get("/notifications/all"))
                .andExpect(status().isOk())                          // Ожидаем статус 200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Ожидаем JSON
                .andExpect(jsonPath("$.length()").value(1));        // Ожидаем массив из 1 элемента
        
        // Проверка, что метод сервиса был вызван
        verify(notificationService, times(1)).getAllNotifications();
    }

    // ========== ТЕСТ 2: DELETE /notifications/{id} ==========
    @Test
    void shouldDeleteNotification() throws Exception {
        Long notificationId = 1L;
        
        // Настройка мока для void метода
        doNothing().when(notificationService).deleteNotification(notificationId);
        
        // Выполнение HTTP DELETE запроса
        mockMvc.perform(delete("/notifications/" + notificationId))
                .andExpect(status().isOk())                         // Ожидаем статус 200
                .andExpect(content().string("Уведомление удалено")); // Ожидаем текст ответа
        
        // Проверка, что метод сервиса был вызван
        verify(notificationService, times(1)).deleteNotification(notificationId);
    }
}