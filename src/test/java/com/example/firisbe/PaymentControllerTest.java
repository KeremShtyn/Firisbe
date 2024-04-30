package com.example.firisbe;


import com.example.firisbe.payment.dto.PaymentDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FirisbeApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yml")
@Tag("PaymentControllerTest")
public class PaymentControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();


    private MockMvc mockMvc;

    public PaymentControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }


    @Test
    @WithMockUser("spring")
    public void get_SetPayment_OK() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(100);
        paymentDTO.setUserCreditCardNumber("1000000");

        mockMvc.perform(post("/api/v1/payment")
                        .content(objectMapper.writeValueAsString(paymentDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("spring")
    public void get_SetPayment_BAD() throws Exception {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setAmount(0);
        paymentDTO.setUserCreditCardNumber("1000000");

        mockMvc.perform(post("/api/v1/payment")
                        .content(objectMapper.writeValueAsString(paymentDTO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
