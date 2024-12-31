package com.kyu.pappy.model.pagenation;

import java.util.List;

public record PageResponse<T> (
        int currentPage,
        int pageSize,
        long totalElements,
        int totalPage,
        List<T> content
) {
}
