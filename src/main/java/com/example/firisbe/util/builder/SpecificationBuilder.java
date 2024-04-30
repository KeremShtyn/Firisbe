package com.example.firisbe.util.builder;

import com.example.firisbe.payment.entity.PaymentEntity;
import com.example.firisbe.util.SearchCriteria;
import com.example.firisbe.util.SearchOperation;
import com.example.firisbe.util.specification.PaymentSpecification;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpecificationBuilder {
    private final List<SearchCriteria> params;

    public SpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final SpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final SpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }


    public Specification<PaymentEntity> buildAirline() {
        if (params.size() == 0 || Objects.isNull(params.get(0).getValue()) || params.get(0).getValue().toString().trim().length() == 0) {
            return null;
        }


        Specification<PaymentEntity> result = new PaymentSpecification(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);

            SearchOperation dataOption = criteria.getDataOption() == null ? SearchOperation.ALL : SearchOperation.valueOf(criteria.getDataOption());
            result = dataOption == SearchOperation.ALL
                    ? Specification.where(result).and(new PaymentSpecification(criteria))
                    : Specification.where(result).or(new PaymentSpecification(criteria));
        }

        return result;
    }
}






