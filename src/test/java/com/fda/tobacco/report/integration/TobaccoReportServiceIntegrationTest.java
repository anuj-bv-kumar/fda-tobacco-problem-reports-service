package com.fda.tobacco.report.integration;

import com.fda.tobacco.report.TobaccoReportServiceApplication;
import com.fda.tobacco.report.entity.ReportRecordEntity;
import com.fda.tobacco.report.model.Product;
import com.fda.tobacco.report.model.ReportRecord;
import com.fda.tobacco.report.model.ReportSearchResponse;
import com.fda.tobacco.report.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Purpose of this class is to test and verify the integration with fda tobacco problem report third party api
 *
 */
@SpringBootTest(classes = TobaccoReportServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TobaccoReportServiceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getUrl(String endpoint) {
        String baseUrl = "http://localhost:" + port + "/api/fda/tobacco";
        return baseUrl + endpoint;
    }

    /**
     * Test to verify if reports can be searched using submitted date.
     */
    @Test
    public void givenReportExists_whenFetchingBySubmittedDate_thenReturnReportSuccessfully() {
        //Given
        String expectedSubmittedDate = "06/21/2019";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getUrl("/reports"))
                .queryParam("date_submitted", expectedSubmittedDate);

        //When
        ResponseEntity<ReportSearchResponse> response = restTemplate.getForEntity(builder.toUriString(), ReportSearchResponse.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReportSearchResponse reportSearchResponse = response.getBody();
        assertThat(reportSearchResponse).isNotNull();
        assertThat(reportSearchResponse.getReports()).isNotNull();
        assertThat(reportSearchResponse.getReports().getFirst().getDateSubmitted()).isEqualTo(expectedSubmittedDate);
    }

    /**
     * Test to execute save report record api and verify if records have been successfully persisted and returned as response
     */
    @Test
    public void givenValidReportRecord_whenSavingReportRecord_thenRecordIsSavedSuccessfully() {
        //Given
        ReportRecord reportRecord = TestUtil.createReportRecord();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ReportRecord> request = new HttpEntity<>(reportRecord, headers);

        //When
        ResponseEntity<ReportRecordEntity> response = restTemplate.postForEntity(getUrl("/reports"), request, ReportRecordEntity.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ReportRecordEntity reportRecordEntity = response.getBody();
        assertThat(reportRecordEntity).isNotNull();
        assertThat(reportRecordEntity.getRecordId()).isNotNull();
        assertThat(reportRecordEntity.getReportId()).isEqualTo(1234);
        assertThat(reportRecordEntity.getProductType()).isEqualTo("Cigarettes");
        assertThat(reportRecordEntity.getDateSubmitted()).isEqualTo("06/21/2019");
        assertThat(reportRecordEntity.getProblemDescription()).isEqualTo("Packaging issues");
    }

    /**
     * Test to fetch and verify the most common product.
     *
     */
    @Test
    public void givenReportsExist_whenFetchingMostCommonProduct_thenReturnMostCommonProduct() {
        //Given
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getUrl("/reports/products/common"));

        //When
        ResponseEntity<Product> response = restTemplate.getForEntity(builder.toUriString(), Product.class);

        //Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isNotNull();
    }
}
