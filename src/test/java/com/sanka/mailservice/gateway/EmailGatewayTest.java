package com.sanka.mailservice.gateway;

import com.sanka.mailservice.exception.ServiceDownException;
import com.sanka.mailservice.model.Email;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailGatewayTest {

    @Mock
    private EmailClient mockClientPrimary;

    @Mock
    private EmailClient mockClientSecondary;

    private EmailGateway emailGateway;

    @BeforeEach
    void setup() {
        emailGateway = new EmailGateway(mockClientPrimary, mockClientSecondary);
    }

    @Test
    @DisplayName("GIVEN the primary client throws an exception THEN call the secondary client")
    void testFailoverSuccess() {
        // Given
        val emailMock = mock(Email.class);
        doThrow(RestClientException.class).when(mockClientPrimary).sendEmail(emailMock);

        // When
        emailGateway.sendEmail(emailMock);

        // Then
        verify(mockClientSecondary, times(1)).sendEmail(emailMock);
    }

    @Test
    @DisplayName("Given both clients throw an exception EXPECT ServiceDownException")
    void testFailoverFailure() {
        // Given
        val emailMock = mock(Email.class);
        doThrow(RestClientException.class).when(mockClientPrimary).sendEmail(emailMock);
        doThrow(RestClientException.class).when(mockClientSecondary).sendEmail(emailMock);

        // Then
        assertThrows(ServiceDownException.class, () -> emailGateway.sendEmail(emailMock));
    }

}