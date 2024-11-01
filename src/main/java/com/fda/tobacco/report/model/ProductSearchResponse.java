package com.fda.tobacco.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ProductSearchResponse {
    @JsonProperty("results")
    private List<Product> products;
}
