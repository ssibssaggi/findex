package com.ssibssaggi.findex.common.dto;

import java.util.List;
import java.util.function.Function;

import lombok.Builder;

@Builder
public record PageResponse<T>(
        List<T> content,
        String nextCursor,
        Long nextIdAfter,
        Integer size,
        Long totalElements,
        Boolean hasNext
) {

    public <R> PageResponse<R> map(Function<T, R> mapper) {
        List<R> mappedContent = this.content.stream()
                .map(mapper)
                .toList();

        return new PageResponse<>(
                mappedContent,
                this.nextCursor,
                this.nextIdAfter,
                this.size,
                this.totalElements,
                this.hasNext
        );
    }

    public static <T> PageResponse<T> of(
            List<T> content,
            String nestCursor,
            Long nextIdAfter,
            Long totalElements,
            Integer size,
            Boolean hasNext
    ) {
        return PageResponse.<T>builder()
                .content(content)
                .nextCursor(nestCursor)
                .nextIdAfter(nextIdAfter)
                .size(size)
                .totalElements(totalElements)
                .hasNext(hasNext)
                .build();
    }
}
