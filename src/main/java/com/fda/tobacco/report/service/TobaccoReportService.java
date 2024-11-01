package com.fda.tobacco.report.service;

import com.fda.tobacco.report.exception.ProductNotFoundException;
import com.fda.tobacco.report.model.Product;
import com.fda.tobacco.report.model.ReportSearchResponse;

public interface TobaccoReportService {

    Product findMostCommonProduct() throws ProductNotFoundException;

    ReportSearchResponse findReportsBySubmittedDate(String date);
}
