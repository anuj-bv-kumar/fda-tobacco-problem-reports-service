package com.fda.tobacco.report.service.impl;

import com.fda.tobacco.report.exception.ProductNotFoundException;
import com.fda.tobacco.report.model.ProductSearchResponse;
import com.fda.tobacco.report.model.Product;
import com.fda.tobacco.report.model.ReportSearchResponse;
import com.fda.tobacco.report.service.TobaccoReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TobaccoReportServiceImpl implements TobaccoReportService {

    static final String COMMON_PRODUCT_SEARCH_QUERY = "?count=tobacco_products.exact&limit=1";
    static final String FIND_REPORTS_BY_SUBMITTED_DATE_SEARCH_QUERY = "?search=date_submitted:";

    static final String INPUT_DATE_PATTERN = "MM/dd/yyyy";
    static final String OUTPUT_DATE_PATTERN = "yyyyMMdd";

    private final WebClient webClient;

    /**
     * Fetch the most common product from fda tobacco problem reports
     *
     * @return Product {@link Product}
     * @throws ProductNotFoundException
     */
    @Override
    public Product findMostCommonProduct() throws ProductNotFoundException {

        ProductSearchResponse response = webClient.get()
                .uri(COMMON_PRODUCT_SEARCH_QUERY)
                .retrieve()
                .bodyToMono(ProductSearchResponse.class)
                .block();

        if (response == null || response.getProducts().isEmpty()) {
            throw new ProductNotFoundException("Most common product not found !!");
        }
        //Response will have only single result due to limit=1 search criteria
        return response.getProducts().getFirst();
    }

    /**
     * Find the tobacco problem reports by submitted date.
     *
     * @param submittedDate, expected format MM/dd/yyyy
     * @return ReportSearchResponse
     */
    @Override
    public ReportSearchResponse findReportsBySubmittedDate(String submittedDate) {

        // Define the formatter for the input format
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(INPUT_DATE_PATTERN);

        // Parse the input submittedDate to LocalDate
        LocalDate parsedDate = LocalDate.parse(submittedDate, inputFormatter);

        // Define the formatter for the output format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(OUTPUT_DATE_PATTERN);
        // Format the submittedDate to the desired output format
        String formattedDate = parsedDate.format(outputFormatter);
        return webClient.get()
                .uri(FIND_REPORTS_BY_SUBMITTED_DATE_SEARCH_QUERY + formattedDate)
                .retrieve()
                .bodyToMono(ReportSearchResponse.class)
                .block();
    }
}
