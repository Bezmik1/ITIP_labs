package university.spring_lab3_notifications;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AssertionsTest {

    @Test
    void shouldCheckAssertions() {
        String value = "Spring";
        
        assertEquals("Spring", value);
        assertNotNull(value);
        assertTrue(value.startsWith("Sp"));
        assertFalse(value.isEmpty());
    }
}