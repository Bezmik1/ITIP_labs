package university.spring_lab3_notifications;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class SpyExampleTest {

    // ========== ЗАДАНИЕ №7: Пример использования spy() ==========
    @Test
    void shouldUseSpyOnList() {
        // 1. СОЗДАЕМ РЕАЛЬНЫЙ ОБЪЕКТ
        List<String> realList = new ArrayList<>();
        
        // 2. СОЗДАЕМ SPY (частичный mock) на основе реального объекта
        List<String> spyList = spy(realList);
        
        // 3. ВЫЗЫВАЕМ МЕТОДЫ НА SPY (они работают по-настоящему)
        spyList.add("Spring");
        spyList.add("Boot");
        
        // 4. ПРОВЕРЯЕМ, ЧТО ЭЛЕМЕНТЫ ДОБАВИЛИСЬ (реальный метод size работает)
        assertEquals(2, spyList.size());
        
        // 5. ПРОВЕРЯЕМ, ЧТО МЕТОД add БЫЛ ВЫЗВАН (как на mock)
        verify(spyList).add("Spring");
        verify(spyList).add("Boot");
        
        // 6. ПОЛУЧАЕМ ЭЛЕМЕНТЫ (реальный метод get работает)
        assertEquals("Spring", spyList.get(0));
        assertEquals("Boot", spyList.get(1));
    }
}