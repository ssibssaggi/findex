package com.ssibssaggi.findex.common.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        String nestCursor,
        String nextIdAfter,
        Integer size,
        Integer totalElements,
        Boolean hasNext
) {
}
