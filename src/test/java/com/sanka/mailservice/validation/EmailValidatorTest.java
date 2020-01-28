package com.sanka.mailservice.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmailValidatorTest {

    @Mock
    private ConstraintValidatorContext mockContext;

    private EmailValidator validator;

    @BeforeEach
    void setup() {
        validator = new EmailValidator();
    }

    @ParameterizedTest
    @DisplayName("GIVEN emails don't conform to RFC-822 address spec EXPECT false")
    @ValueSource(strings = {
            "!!!@$%^",
            "@yahoo.com",
            "test@#$@#$",
            "username@yahoo..com",
            "username@yahoo.com."}
            )
    void invalidEmails(final String input) {
        assertThat(validator.isValid(input, mockContext)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("GIVEN emails do conform to RFC-822 address EXPECT true")
    @ValueSource(strings = {
            "test@test",
            "_@yahoo.com",
            ".test@test.com.au",
            "blah@-yahoo.com"})
    void validEmails(final String input) {
        assertThat(validator.isValid(input, mockContext)).isTrue();
    }

    @Test
    @DisplayName("GIVEN exception scenarios EXPECT false")
    void nullEmail() {
        assertThat(validator.isValid(null, mockContext)).isFalse();
        assertThat(validator.isValid("", null)).isFalse();
    }

}