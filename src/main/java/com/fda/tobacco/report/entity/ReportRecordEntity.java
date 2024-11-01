package com.fda.tobacco.report.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "report")
public class ReportRecordEntity {

    @Id
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
