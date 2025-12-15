package com.carrental.carrental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchDto {
    private String q;
    private String category;
    private Boolean available;
    private Integer yearFrom;
    private Integer yearTo;
    private Double budget;
}
