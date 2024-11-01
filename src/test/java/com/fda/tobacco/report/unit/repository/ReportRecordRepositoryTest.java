package com.fda.tobacco.report.unit.repository;


import com.fda.tobacco.report.entity.ReportRecordEntity;
import com.fda.tobacco.report.repository.ReportRecordRepository;
import com.fda.tobacco.report.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Purpose of this class is to test and verify report record persistence in embedded mongo db (in memory)
 */
@DataMongoTest
public class ReportRecordRepositoryTest {

    @Autowired
    ReportRecordRepository reportRecordRepository;

    @BeforeEach
    public void setUp() {
        reportRecordRepository.deleteAll();
    }

    @Test
    public void givenReportRecord_whenSaved_thenAbleToRetrieveSuccessfully() {
        //Given
        ReportRecordEntity reportRecord = TestUtil.createReportRecordEntity();

        //When
        ReportRecordEntity savedRecord = reportRecordRepository.save(reportRecord);

        //Then
        assertNotNull(savedRecord);
        assertThat(savedRecord.getRecordId()).isNotNull();
        assertThat(savedRecord.getReportId()).isEqualTo(1234);
        assertThat(savedRecord.getProductType()).isEqualTo("Cigarettes");
        assertThat(savedRecord.getDateSubmitted()).isEqualTo("06/21/2019");
        assertThat(savedRecord.getProblemDescription()).isEqualTo("Packaging issues");
    }


}