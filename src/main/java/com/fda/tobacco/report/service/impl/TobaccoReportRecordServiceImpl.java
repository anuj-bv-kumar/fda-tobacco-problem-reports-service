package com.fda.tobacco.report.service.impl;

import com.fda.tobacco.report.entity.ReportRecordEntity;
import com.fda.tobacco.report.mapper.ReportRecordMapper;
import com.fda.tobacco.report.model.ReportRecord;
import com.fda.tobacco.report.repository.ReportRecordRepository;
import com.fda.tobacco.report.service.TobaccoReportRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TobaccoReportRecordServiceImpl implements TobaccoReportRecordService {

    private final ReportRecordRepository reportRepository;

    private final ReportRecordMapper reportRecordMapper;

    /**
     * Persist a new ReportRecord to the database.
     *
     * @param reportRecord the {@link ReportRecord} object containing report data
     *                     to be saved.
     * @return the saved {@link ReportRecord} object with the updated database
     * values (such as an auto-generated record id).
     */
    @Override
    public ReportRecord saveReportRecord(ReportRecord reportRecord) {
        ReportRecordEntity reportRecordEntity = reportRecordMapper.mapReportRecordToReportRecordEntity(reportRecord);
        reportRecordEntity = reportRepository.save(reportRecordEntity);
        return reportRecordMapper.mapReportRecordEntityToReportRecord(reportRecordEntity);
    }

}
