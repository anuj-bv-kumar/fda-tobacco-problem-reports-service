package com.fda.tobacco.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Report {

    @JsonProperty("report_id")
    private Integer reportId;

    @JsonProperty("date_submitted")
    private String dateSubmitted;

    @JsonProperty("nonuser_affected")
    private String nonuserAffected;

    @JsonProperty("reported_health_problems")
    private List<String> reportedHealthProblems;

    @JsonProperty("number_tobacco_products")
    private Integer numberTobaccoProducts;

    @JsonProperty("reported_product_problems")
    private List<String> reportedProductProblems;

    @JsonProperty("tobacco_products")
    private List<String> tobaccoProducts;

    @JsonProperty("number_product_problems")
    private Integer numberProductProblems;

}
