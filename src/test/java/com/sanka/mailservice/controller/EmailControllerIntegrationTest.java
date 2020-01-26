package com.sanka.mailservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanka.mailservice.model.EmailRequest;
import com.sanka.mailservice.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/***
 *  This class is the slice test for the REST controller layer.
 *
 * @author moonlander70
 */
@WebMvcTest
class EmailControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private EmailService emailService;


    @Test
    @DisplayName("GIVEN 'from' does not exist EXPECT a 400 bad request with Validation Error msg")
    void test_noFrom() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder().build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0].param", equalTo("from")))
                .andExpect(jsonPath("$.details[0].msg", equalTo("'from' cannot be blank or null")));
    }

    // NOTE: In Production code, I would test this email validation with several more cases
    // But given this is an assignment and also given, the Email annotation is from javax, I haven't done so
    @Test
    @DisplayName("GIVEN 'from' is not in the email format EXPECT a 400 bad request with email validation error")
    void from_notEmail() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test.test.com")
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0].param", equalTo("from")))
                .andExpect(jsonPath("$.details[0].msg", equalTo("must be a well-formed email address")));

    }

    @Test
    @DisplayName("GIVEN the 'to' has invalid email formats EXPECT a 400 Bad Request with email validation error")
    void to_notEmail() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .to(Set.of("test.test.com","test@test.com"))
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0].param", equalTo("to[]")))
                .andExpect(jsonPath("$.details[0].msg", equalTo("must be a well-formed email address")));

    }

    // you don't need a 'to' for sending an email, as you can use Cc or Bcc. Service layer
    // will decide the validation
    @Test
    @DisplayName("GIVEN 'from' exists and 'to' has no values THEN call the service layer")
    void to_noValues() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .to(Set.of())
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON));

        verify(emailService, times(1)).sendEmail(emailRequest);
    }

    @Test
    @DisplayName("GIVEN the 'cc' has invalid email formats EXPECT a 400 Bad Request with email validation error")
    void cc_notEmail() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .cc(Set.of("test.test.com","test@test.com"))
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0].param", equalTo("cc[]")))
                .andExpect(jsonPath("$.details[0].msg", equalTo("must be a well-formed email address")));

    }

    @Test
    @DisplayName("GIVEN 'from' exists and 'cc' has no values THEN call the service layer")
    void cc_noValues() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .cc(Set.of())
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON));

        verify(emailService, times(1)).sendEmail(emailRequest);
    }


    @Test
    @DisplayName("GIVEN the 'bcc' has invalid email formats EXPECT a 400 Bad Request with email validation error")
    void bcc_notEmail() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .bcc(Set.of("test.test.com","test@test.com"))
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details[0].param", equalTo("bcc[]")))
                .andExpect(jsonPath("$.details[0].msg", equalTo("must be a well-formed email address")));

    }

    @Test
    @DisplayName("GIVEN 'from' exists and 'bcc' has no values THEN call the service layer")
    void bcc_noValues() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .bcc(Set.of())
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON));

        verify(emailService, times(1)).sendEmail(emailRequest);
    }

    @Test
    @DisplayName("GIVEN 'from' exists and 'subject' exists THEN call the service layer")
    void subject_noValue() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .subject("Test Subject")
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON));

        verify(emailService, times(1)).sendEmail(emailRequest);
    }

    // ASSERTION: it is possible to send emails which don't have a message body.
    @Test
    @DisplayName("GIVEN no 'message' exists THEN call the service layer")
    void message_noValue() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .to(Set.of("test@test.com"))
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON));

        verify(emailService, times(1)).sendEmail(emailRequest);

    }

    @Test
    @DisplayName("GIVEN 'message' exists THEN call the service layer")
    void message_hasValue() throws Exception {
        final EmailRequest emailRequest = EmailRequest.builder()
                .from("test@test.com")
                .to(Set.of("test@test.com"))
                .message("Hi, this is a test message")
                .build();

        mockMvc.perform(post("/emails")
                .content(mapper.writeValueAsString(emailRequest))
                .contentType(MediaType.APPLICATION_JSON));

        verify(emailService, times(1)).sendEmail(emailRequest);
    }

}
