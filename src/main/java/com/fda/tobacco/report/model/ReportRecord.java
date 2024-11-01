package com.fda.tobacco.report.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReportRecord {

    @JsonProperty("record_id")
    private String recordId;

    @NotBlank
    @JsonProperty("report_id")
    private Integer reportId;

    @NotBlank
    @JsonProperty("date_submitted")
    private String dateSubmitted;

    @NotBlank
    @JsonProperty("product_type")
    private String productType;

    @NotBlank
    @JsonProperty("problem_description")
    private String problemDescription;

}
