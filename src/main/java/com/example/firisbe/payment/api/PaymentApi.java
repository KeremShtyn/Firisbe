package com.example.firisbe.payment.api;


import com.example.firisbe.payment.dto.PaymentDTO;
import com.example.firisbe.util.response.FirisbeResponse;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static com.example.firisbe.util.api.FirisbeAPIEnpoint.PAYMENT_PATH;

@CrossOrigin
@RequestMapping(PAYMENT_PATH)
public interface PaymentApi {

    @PostMapping
    public FirisbeResponse<Object> save(@RequestBody PaymentDTO paymentDTO, @RequestHeader String userId);

    @PutMapping
    public FirisbeResponse<Object> update(@RequestBody PaymentDTO paymentDTO, @RequestHeader String userId);

    @GetMapping
    public FirisbeResponse<Object> getPaymentsPageByPage(@RequestHeader Map<String, Object> header, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "modifyDate:DESC", required = false) String[] sortBy, String userId);

    @GetMapping
    public FirisbeResponse<Object> getAllPayments(@RequestHeader Map<String, Object> header, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(value = "sort", defaultValue = "modifyDate:DESC", required = false) String[] sortBy, @RequestParam(defaultValue = "0")LocalDateTime startDate, @RequestParam(defaultValue = "0") LocalDateTime endDate);

    @GetMapping("/search")
    public FirisbeResponse<Object> searchPayment(@RequestHeader Map<String, Object> header, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size);

}
