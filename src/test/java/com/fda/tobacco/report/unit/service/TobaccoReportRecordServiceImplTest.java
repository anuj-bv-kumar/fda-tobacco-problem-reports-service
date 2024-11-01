package com.fda.tobacco.report.unit.service;

import com.fda.tobacco.report.entity.ReportRecordEntity;
import com.fda.tobacco.report.model.ReportRecord;
import com.fda.tobacco.report.repository.ReportRecordRepository;
import com.fda.tobacco.report.service.TobaccoReportRecordService;
import com.fda.tobacco.report.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class to unit test and verify save record implementation logic in service class using mock.
 */
@SpringBootTest
public class TobaccoReportRecordServiceImplTest {
    @Autowired
    private TobaccoReportRecordService tobaccoReportRecordService;

    @MockBean
    private ReportRecordRepository reportRepository;

    @Test
    public void testSaveReportRecord() {

        //Prepare test data
        ReportRecordEntity reportRecordEntity = TestUtil.createReportRecordEntity();
        ReportRecord reportRecord = TestUtil.createReportRecord();

        //Mock
        when(reportRepository.save(reportRecordEntity)).thenReturn(reportRecordEntity);

        //Invoke
        ReportRecord reportRecordResponse = tobaccoReportRecordService.saveReportRecord(reportRecord);

        //Assert
        assertThat(reportRecordResponse).isNotNull();
        assertThat(reportRecordResponse.getReportId()).isEqualTo(1234);
        assertThat(reportRecordResponse.getDateSubmitted()).isEqualTo("06/21/2019");
        assertThat(reportRecordResponse.getProductType()).isEqualTo("Cigarettes");
        assertThat(reportRecordResponse.getProblemDescription()).isEqualTo("Packaging issues");

    }
}
