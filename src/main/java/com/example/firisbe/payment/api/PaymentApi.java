package com.example.firisbe.payment.api;


import com.example.firisbe.payment.dto.PaymentDTO;
import com.example.firisbe.util.response.FirisbeResponse;
import org.springframework.web.bind.annotation.*;

import static com.example.firisbe.util.api.FirisbeAPIEnpoint.PAYMENT_PATH;

@CrossOrigin
@RequestMapping(PAYMENT_PATH)
public interface PaymentApi {

    @PostMapping
    public FirisbeResponse<Object> save(@RequestBody PaymentDTO paymentDTO);

    @PutMapping
    public FirisbeResponse<Object> update(@RequestBody PaymentDTO paymentDTO);



}
