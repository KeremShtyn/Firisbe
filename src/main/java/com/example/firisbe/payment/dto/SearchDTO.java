package com.example.firisbe.payment.dto;

import com.example.firisbe.util.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {

    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;

}
