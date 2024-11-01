package com.fda.tobacco.report.unit.service;

import com.fda.tobacco.report.exception.ProductNotFoundException;
import com.fda.tobacco.report.model.Product;
import com.fda.tobacco.report.model.Report;
import com.fda.tobacco.report.model.ReportSearchResponse;
import com.fda.tobacco.report.service.TobaccoReportService;
import com.fda.tobacco.report.service.impl.TobaccoReportServiceImpl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Test class to unit test and verify api's implementation logic in service class using mockWebServer.
 */
@SpringBootTest
public class TobaccoReportServiceImplTest {
    @Autowired
    private TobaccoReportService tobaccoReportService;

    public static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s",
                mockWebServer.getPort());
        tobaccoReportService = new TobaccoReportServiceImpl(WebClient.create(baseUrl));
    }

    @Test
    public void givenReportsExist_whenFindMostCommonProduct_thenReturnMostCommonProduct() throws ProductNotFoundException {
        //Given - a mock response
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"results\":[{\"term\":\"cigarette\",\"count\":810}]}"));

        //When
        Product mostCommonProduct = tobaccoReportService.findMostCommonProduct();

        //Then
        assertThat(mostCommonProduct).isNotNull();
        assertThat(mostCommonProduct.getName()).isEqualTo("cigarette");

    }

    @Test
    public void givenReportsWithSpecificDate_whenSearchingByDate_thenReportsAreReturned() {
        //Given - a mock response
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("{\"results\":[{\"date_submitted\":\"06/21/2019\",\"nonuser_affected\":\"No\",\"reported_health_problems\":[\"No information provided\"],\"number_tobacco_products\":1,\"report_id\":884,\"number_health_problems\":0,\"reported_product_problems\":[\"Other\"],\"tobacco_products\":[\"Cigarette\"],\"number_product_problems\":1}]}"));

        //When
        ReportSearchResponse response = tobaccoReportService.findReportsBySubmittedDate("06/21/2019");

        //Then
        assertThat(response).isNotNull();
        assertThat(response.getReports().size()).isEqualTo(1);
        Report report = response.getReports().getFirst();
        assertThat(report.getDateSubmitted()).isEqualTo("06/21/2019");
        assertThat(report.getReportId()).isEqualTo(884);
        assertThat(report.getNonuserAffected()).isEqualTo("No");
        assertThat(report.getNumberTobaccoProducts()).isEqualTo(1);
        assertThat(report.getNumberProductProblems()).isEqualTo(1);
    }
}
