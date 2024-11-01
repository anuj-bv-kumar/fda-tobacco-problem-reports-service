package com.fda.tobacco.report.repository;

import com.fda.tobacco.report.entity.ReportRecordEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRecordRepository extends MongoRepository<ReportRecordEntity, String> {
}
