package br.com.rapidoja.tcc.util;

import br.com.rapidoja.tcc.model.OrderStatus;
import br.com.rapidoja.tcc.model.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Given Formatter")
class FormatterTest {

    @Nested
    @DisplayName("When format profile is called")
    class WhenFormatProfile {
        @Test
        @DisplayName("Then should return formatted profile")
        void thenShouldReturnFormattedProfile() {
            String result = Formatter.formatProfile(Profile.ADMIN);
            assertEquals(Profile.ADMIN.getProfileName(), result);
        }
    }

    @Nested
    @DisplayName("When format order stauts is called")
    class WhenFormatOrderStatus {
        @Test
        @DisplayName("Then should return formatted order status")
        void thenShouldReturnFormattedOrderStatus() {
            String result = Formatter.formatOrderStatus(OrderStatus.PENDING);
            assertEquals(OrderStatus.PENDING.getStatusName(), result);
        }
    }
}