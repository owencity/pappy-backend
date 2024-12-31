package com.kyu.pappy.utils;

import com.kyu.pappy.model.pagenation.PageResponse;

import java.util.Collections;
import java.util.List;

public class PaginationUtils {

    private PaginationUtils() {

    }

    public static <T>PageResponse <T> toPageResponse(List<T> allData, int page, int size ) {
        int totalElements = allData.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);

        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        if(fromIndex > totalElements) {
            // 잘못된 페이지 번호 일경우 , 빈데이터 반환
            return new PageResponse<>(page, size, totalElements, totalPages, Collections.emptyList());
        }

        List<T> pageContent = allData.subList(fromIndex, toIndex);

        return new PageResponse<>(page, size, totalElements, totalPages, pageContent);
    }
}
