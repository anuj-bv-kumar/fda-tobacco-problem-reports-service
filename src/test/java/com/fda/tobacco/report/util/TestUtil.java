package com.fda.tobacco.report.util;

import com.fda.tobacco.report.entity.ReportRecordEntity;
import com.fda.tobacco.report.model.Report;
import com.fda.tobacco.report.model.ReportRecord;
import com.fda.tobacco.report.model.ReportSearchResponse;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static ReportRecord createReportRecord() {
        ReportRecord reportRecord = new ReportRecord();
        reportRecord.setReportId(1234);
        reportRecord.setDateSubmitted("06/21/2019");
        reportRecord.setProductType("Cigarettes");
        reportRecord.setProblemDescription("Packaging issues");
        return reportRecord;
    }

    public static ReportSearchResponse createReportSearchResponse() {
        List<Report> reports = new ArrayList<>();
        Report report = new Report();
        report.setReportId(12);
        report.setDateSubmitted("06/21/2019");
        report.setNonuserAffected("No");
        report.setNumberProductProblems(1);
        return new ReportSearchResponse(reports);
    }

    public static ReportRecordEntity createReportRecordEntity() {
        ReportRecordEntity reportRecordEntity = new ReportRecordEntity();
        reportRecordEntity.setReportId(1234);
        reportRecordEntity.setDateSubmitted("06/21/2019");
        reportRecordEntity.setProductType("Cigarettes");
        reportRecordEntity.setProblemDescription("Packaging issues");
        return reportRecordEntity;
    }
}
