package com.ssibssaggi.findex.common.dto;

import java.util.List;
import java.util.function.Function;

public record CursorPageResult<T>(
        List<T> content,
        PageMeta pageMeta
) {
    public static <T> CursorPageResult<T> of(List<T> content, PageMeta pageMeta) {
        return new CursorPageResult<>(content, pageMeta);
    }

    public <R> CursorPageResult<R> map(Function<T, R> mapper) {
        List<R> mapperContent = content.stream().map(mapper).toList();
        return new CursorPageResult<>(mapperContent, this.pageMeta);
    }
}
