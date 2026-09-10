package com.ssibssaggi.findex.controller.dto;

public record CursorPageCondition(Long idAfter, String cursor, String sortField, String sortDirection, Integer size) {
}
