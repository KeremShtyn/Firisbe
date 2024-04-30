package com.example.firisbe.payment.controller;

import com.example.firisbe.payment.api.PaymentApi;
import com.example.firisbe.payment.domain.Payment;
import com.example.firisbe.payment.dto.PaymentDTO;
import com.example.firisbe.payment.dto.SearchDTO;
import com.example.firisbe.payment.mapper.PaymentDTOMapper;
import com.example.firisbe.payment.service.PaymentService;
import com.example.firisbe.util.FirisbePageable;
import com.example.firisbe.util.SearchCriteria;
import com.example.firisbe.util.builder.SpecificationBuilder;
import com.example.firisbe.util.response.FirisbeGenerator;
import com.example.firisbe.util.response.FirisbeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class PaymentController implements PaymentApi {

    private PaymentService paymentService;

    private PaymentDTOMapper paymentDTOMapper;

    public static final String SEARCHCRITERIALIST = "searchcriterialist";

    private ObjectMapper objectMapper;

    public PaymentController(PaymentService paymentService, PaymentDTOMapper paymentDTOMapper, ObjectMapper objectMapper) {
        this.paymentService = paymentService;
        this.paymentDTOMapper = paymentDTOMapper;
        this.objectMapper = objectMapper;
    }

    @Autowired
    public PaymentController(PaymentService paymentService, PaymentDTOMapper paymentDTOMapper) {
        this.paymentService = paymentService;
        this.paymentDTOMapper = paymentDTOMapper;
    }

    @Override
    public FirisbeResponse<Object> save(PaymentDTO paymentDTO, String userId) {
        Payment payment = paymentDTOMapper.toDomain(paymentDTO);
        payment = paymentService.createPayment(payment, userId);
        return FirisbeGenerator.generateSignResponse(paymentDTOMapper.toDTO(payment));
    }

    @Override
    public FirisbeResponse<Object> update(PaymentDTO paymentDTO, String userId) {
        return save(paymentDTO, userId);
    }

    @Override
    public FirisbeResponse<Object> getPaymentsPageByPage(Map<String, Object> header, int page, int size, String[] sortBy, String userId) {
        FirisbePageable<Payment> paymentPage = paymentService.findPageByUser(page, size, userId);
        return FirisbeGenerator.generateSignResponse(paymentPage);
    }

    @Override
    public FirisbeResponse<Object> getAllPayments(Map<String, Object> header, int page, int size, String[] sortBy, LocalDateTime startDate, LocalDateTime endDate) {
        FirisbePageable<Payment> paymentPage = paymentService.getPaymentsByCreatedDate(page, size, startDate, endDate);
        return FirisbeGenerator.generateSignResponse(paymentPage);
    }

    @Override
    public FirisbeResponse<Object> searchPayment(Map<String, Object> header, int pageNum, int pageSize) {
        List<SearchCriteria> criteriaList = convertHeader(header);

        SearchDTO searchDTO = objectMapper.convertValue(header, SearchDTO.class);
        searchDTO.setSearchCriteriaList(criteriaList);

        SpecificationBuilder builder = new SpecificationBuilder();
        criteriaList = searchDTO.getSearchCriteriaList();
        if (criteriaList != null) {
            criteriaList.forEach(x -> {
                x.setDataOption(searchDTO.getDataOption());
                builder.with(x);
            });

        }

        Pageable page = PageRequest.of(pageNum, pageSize, Sort.by("id").ascending());
        FirisbePageable<Payment> paymentPage = paymentService.findBySearchCriteria(builder.buildAirline(), page);
        paymentPage = new FirisbePageable<>(paymentPage.getTotalElements(), paymentPage.getTotalPages(), paymentPage.getPageable(), paymentPage.getContents());


        return FirisbeGenerator.generateSignResponse(paymentPage);
    }

    private List<SearchCriteria> convertHeader(Map<String, Object> header) {
        String searchCriteriaList = (String) header.get(SEARCHCRITERIALIST);
        if (StringUtils.isEmpty(searchCriteriaList)) {
            return List.of();
        }

        SearchCriteria[] searchCriterias = null;
        try {
            searchCriterias = objectMapper.convertValue(new JSONParser(searchCriteriaList).parse(), SearchCriteria[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        header.remove(SEARCHCRITERIALIST);
        return new ArrayList<SearchCriteria>(Arrays.asList(searchCriterias));
    }
}
