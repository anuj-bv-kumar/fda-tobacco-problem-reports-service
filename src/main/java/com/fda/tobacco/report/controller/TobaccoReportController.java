package com.fda.tobacco.report.controller;

import com.fda.tobacco.report.exception.ProductNotFoundException;
import com.fda.tobacco.report.model.Product;
import com.fda.tobacco.report.model.ReportRecord;
import com.fda.tobacco.report.model.ReportSearchResponse;
import com.fda.tobacco.report.service.TobaccoReportRecordService;
import com.fda.tobacco.report.service.TobaccoReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "tobacco-report-controller")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/fda/tobacco")
public class TobaccoReportController {

    private final TobaccoReportService tobaccoReportService;
    private final TobaccoReportRecordService tobaccoReportRecordService;

    @Operation(summary = "Fetch the most common product")
    @GetMapping(value = "/reports/products/common")
    public ResponseEntity<Product> findMostCommonProduct() throws ProductNotFoundException {
        Product mostCommonProduct = tobaccoReportService.findMostCommonProduct();
        return ResponseEntity.ok(mostCommonProduct);
    }

    @Operation(summary = "Fetch report by submitted date")
    @GetMapping("/reports")
    public ResponseEntity<ReportSearchResponse> findReportsBySubmittedDate(
            @RequestParam("date_submitted")
            @Parameter(description = "Expected date format MM/dd/yyyy", example = "06/21/2019") String submittedDate) {
        ReportSearchResponse response = tobaccoReportService.findReportsBySubmittedDate(submittedDate);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Save report record",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReportRecord.class),
                            examples = @ExampleObject(
                                    value = "{ \"report_id\": \"1234\", \"date_submitted\": \"06/21/2019\", \"product_type\": \"Cigarettes\", \"problem_description\": \"packaging\" }"
                            )
                    )
            )
    )
    @PostMapping("/reports")
    public ReportRecord saveReportRecord(
            @RequestBody ReportRecord reportRecord
    ) {
        return tobaccoReportRecordService.saveReportRecord(reportRecord);
    }
}
