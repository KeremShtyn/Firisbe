package com.example.firisbe.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;


@Data
@AllArgsConstructor
public class FirisbePageable<T> {

    private Long totalElements;
    private Integer totalPages;
    private Pageable pageable;
    private List<T> contents;

}
