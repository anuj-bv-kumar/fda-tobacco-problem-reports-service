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
 * Test and verify TobaccoReportController class using mock
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
    public void whenGetReportsBySubmittedDateWithValidURL_thenReturnStatusOk() throws Exception {

        ReportSearchResponse reportSearchResponse = TestUtil.createReportSearchResponse();
        when(tobaccoReportService.findReportsBySubmittedDate(submittedDate)).thenReturn(reportSearchResponse);

        mockMvc.perform(get("/api/fda/tobacco/reports?date_submitted=" + submittedDate))
                .andExpect(status().isOk());
    }

    @Test
    public void whenGetReportsBySubmittedDateWithoutQueryParam_thenReturnStatusBadRequest() throws Exception {
        ReportSearchResponse reportSearchResponse = TestUtil.createReportSearchResponse();
        when(tobaccoReportService.findReportsBySubmittedDate(submittedDate)).thenReturn(reportSearchResponse);

        mockMvc.perform(get("/api/fda/tobacco/reports"))
                .andExpect(status().is(400));
    }

    @Test
    public void whenFindMostCommonProduct_thenReturnStatusOk() throws Exception {

        when(tobaccoReportService.findMostCommonProduct()).thenReturn(new Product("Cigarette"));

        mockMvc.perform(get("/api/fda/tobacco/reports/products/common"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenSaveReportRecordWithValidParams_thenReturnStatusOk() throws Exception {
        ReportRecord reportRecord = TestUtil.createReportRecord();
        when(tobaccoReportRecordService.saveReportRecord(Mockito.any(ReportRecord.class))).thenReturn(reportRecord);

        mockMvc.perform(post("/api/fda/tobacco/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reportRecord)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenSaveReportRecordWithoutRequestBody_thenReturnStatusBadRequest() throws Exception {
        ReportRecord reportRecord = TestUtil.createReportRecord();
        when(tobaccoReportRecordService.saveReportRecord(Mockito.any(ReportRecord.class))).thenReturn(reportRecord);

        //Don't pass request body
        mockMvc.perform(post("/api/fda/tobacco/reports")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }
}
