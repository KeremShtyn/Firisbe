package com.example.firisbe.util.response;


import com.example.firisbe.util.UserUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class FirisbeGenerator {

    private FirisbeGenerator() {
    }

    public static <T> FirisbeResponse<T> generateSignResponse(T payload, Object... parametersWithOrderVersionReferenceIdExtras) {

        FirisbeResponse<T> tFirisbeResponse;

        if (payload instanceof Collection) {
            tFirisbeResponse = new FirisbeResponse.SignResponseBuilder<T>()
                    .withPayload(payload)
                    .build();
        } else if (payload instanceof Map) {
            Map<String, Object> resultMap = (Map) payload;
            tFirisbeResponse = new FirisbeResponse.SignResponseBuilder<T>()
                    .withPayload((T) resultMap)
                    .build();

        } else {
            tFirisbeResponse = new FirisbeResponse.SignResponseBuilder<T>()
                    .withPayload(payload)
                    .build();
        }


        if (parametersWithOrderVersionReferenceIdExtras.length > 0) {
            for (Object object : parametersWithOrderVersionReferenceIdExtras) {
             if (object instanceof String && object == parametersWithOrderVersionReferenceIdExtras[0]) {
                    tFirisbeResponse.setVersion((String) object);
                } else if (object instanceof String && object == parametersWithOrderVersionReferenceIdExtras[1]) {
                    tFirisbeResponse.setReferenceId((String) object);
                }
            }
        } else {
            tFirisbeResponse.setVersion(ResponseContants.SIGN_RESPONSE_VERSION);
            tFirisbeResponse.setReferenceId(ResponseContants.SIGN_RESPONSE_REFERENCE + UserUtil.formatLocalDateTimeAsString(LocalDateTime.now(), ResponseContants.SIGN_KEY_DATE_TIME_FORMAT));
        }

        return tFirisbeResponse;
    }

    public static <T, S> Map<String, T> generatePageMap(List<T> objectResultList, Page<S> page) {
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put(ResponseContants.PAGE_MAP_PARAMETER_RESULT, objectResultList);
        pageMap.put(ResponseContants.PAGE_MAP_PARAMETER_PAGE, page.getPageable());
        return (Map<String, T>) pageMap;
    }

    public static Sort generateSorts(String[] sortBy) {
        return Sort.by(
                Arrays.stream(sortBy)
                        .map(sort -> sort.split(";", 2))
                        .map(array ->
                                new Sort.Order(generateDirection(array[1]), array[0])
                        ).collect(Collectors.toList())
        );
    }

    private static Sort.Direction generateDirection(String sortDirection) {
        if (sortDirection.equalsIgnoreCase("DESC")) {
            return Sort.Direction.DESC;
        } else {
            return Sort.Direction.ASC;
        }
    }


    public static Map<String, String> populateSort(String[] sortBy) {
        if (Objects.isNull(sortBy)) {
            return null;
        }
        Map<String, String> sort = new HashMap<>();
        try {
            Arrays.asList(sortBy).forEach(f -> {
                String[] sorted = f.split(":");
                sort.put(sorted[0], sorted[1]);
            });
        } catch (Exception e) {
            //TODO : handle exception
        }
        return sort;
    }
}
