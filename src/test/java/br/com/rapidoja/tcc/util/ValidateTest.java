package br.com.rapidoja.tcc.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Given Validate")
class ValidateTest {

    @Nested
    @DisplayName("When email validate is called")
    class WhenValidateEmail {
        @Test
        @DisplayName("Then should return true when email is valid")
        void thenShouldReturnTrueWhenEmailIsValid() {

            boolean result = Validate.isValidEmail("teste@gmail.com");
            assertTrue(result);
        }

        @Test
        @DisplayName("Then should return false when email is invalid")
        void ThenShouldReturnFalseWhenEmailIsInvalid() {
            boolean result = Validate.isValidEmail("teste.gmail.com");
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("When phone validate is called")
    class WhenValidatePhoneNumber {
        @Test
        @DisplayName("Then should return true when phone is valid")
        void thenShouldReturnTrueWhenPhoneIsValid() {

            boolean result = Validate.isValidPhone("31987654321");
            assertTrue(result);
        }

        @Test
        @DisplayName("Then should return false when phone is invalid")
        void ThenShouldReturnFalseWhenPhoneIsInvalid() {
            boolean result = Validate.isValidPhone("319876X4321");
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("When password validate is called")
    class WhenValidatePassword {
        @Test
        @DisplayName("Then should return true when password is valid")
        void thenShouldReturnTrueWhenPasswordIsValid() {

            boolean result = Validate.isValidPassword("mySecretPassWord");
            assertTrue(result);
        }

        @Test
        @DisplayName("Then should return false when password is invalid")
        void ThenShouldReturnFalseWhenPasswordIsInvalid() {
            boolean result = Validate.isValidPassword("123");
            assertFalse(result);
        }
    }

}