package com.fda.tobacco.report.mapper;

import com.fda.tobacco.report.entity.ReportRecordEntity;
import com.fda.tobacco.report.model.ReportRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportRecordMapper {
    ReportRecord mapReportRecordEntityToReportRecord(ReportRecordEntity reportRecordEntity);

    ReportRecordEntity mapReportRecordToReportRecordEntity(ReportRecord reportRecord);

}
