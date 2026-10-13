package com.nkd.pos.posapi.service;


import java.util.List;

import com.nkd.pos.posapi.entity.ItemsSoldReport;
import com.nkd.pos.posapi.entityReports.AccountantReport;
import com.nkd.pos.posapi.entityReports.DailyPurchasesReport;

public interface EntityReportsService {

 public DailyPurchasesReport getDailyPurchaseReport(String date);
 public AccountantReport getAccountantReport(String startDate, String endDate);
 public List<ItemsSoldReport> getItemsSoldReport(String date);
 public List<ItemsSoldReport> getAccountantReportNew(String date);
}
