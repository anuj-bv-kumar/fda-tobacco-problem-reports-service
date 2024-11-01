package com.fda.tobacco.report.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fda.tobacco.report.model.Product;
import com.fda.tobacco.report.model.ReportRecord;
import com.fda.tobacco.report.model.ReportSearchResponse;
import com.fda.tobacco.report.service.TobaccoReportRecordService;
import com.fda.tobacco.report.service.TobaccoReportService;
import com.fda.tobacco.report.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test and verify TobaccoReportController class api's using BDD approach
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TobaccoReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TobaccoReportService tobaccoReportService;

    @MockBean
    private TobaccoReportRecordService tobaccoReportRecordService;

    private static final String submittedDate = "06/21/2019";

    @Test
    public void givenValidSubmittedDate_whenGetReportsBySubmittedDate_thenStatusIsOk() throws Exception {
        //Given
        ReportSearchResponse reportSearchResponse = TestUtil.createReportSearchResponse();
        when(tobaccoReportService.findReportsBySubmittedDate(submittedDate)).thenReturn(reportSearchResponse);

        //When
        mockMvc.perform(get("/api/fda/tobacco/reports?date_submitted=" + submittedDate))
        //then
        .andExpect(status().isOk());
    }

    @Test
    public void givenNoQueryParam_whenGetReportsBySubmittedDate_thenStatusIsBadRequest() throws Exception {
        //Given
        ReportSearchResponse reportSearchResponse = TestUtil.createReportSearchResponse();
        when(tobaccoReportService.findReportsBySubmittedDate(submittedDate)).thenReturn(reportSearchResponse);

        //When
        mockMvc.perform(get("/api/fda/tobacco/reports"))
        //then
        .andExpect(status().is(400));
    }

    @Test
    public void givenReportsExist_whenFindMostCommonProduct_thenStatusIsOk() throws Exception {
        //Given
        when(tobaccoReportService.findMostCommonProduct()).thenReturn(new Product("Cigarette"));

        //When
        mockMvc.perform(get("/api/fda/tobacco/reports/products/common"))
        //Then
        .andExpect(status().isOk());
    }

    @Test
    public void givenValidReportRecord_whenSaveReportRecord_thenStatusIsOk() throws Exception {
        //Given
        ReportRecord reportRecord = TestUtil.createReportRecord();
        when(tobaccoReportRecordService.saveReportRecord(Mockito.any(ReportRecord.class))).thenReturn(reportRecord);

        //When
        mockMvc.perform(post("/api/fda/tobacco/reports")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(reportRecord)))
        //Then
        .andExpect(status().isOk());
    }

    @Test
    public void givenNoRequestBody_whenSaveReportRecord_thenStatusIsBadRequest() throws Exception {
        //Given
        ReportRecord reportRecord = TestUtil.createReportRecord();
        when(tobaccoReportRecordService.saveReportRecord(Mockito.any(ReportRecord.class))).thenReturn(reportRecord);

        //When
        mockMvc.perform(post("/api/fda/tobacco/reports")
        .contentType(MediaType.APPLICATION_JSON))
        //Then
        .andExpect(status().is(400));
    }
}
