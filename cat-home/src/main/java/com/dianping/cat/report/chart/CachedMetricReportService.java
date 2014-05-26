package com.dianping.cat.report.chart;

import java.util.Date;
import java.util.Map;

import com.dianping.cat.consumer.metric.model.entity.MetricReport;

public interface CachedMetricReportService {

	public MetricReport queryMetricReport(String product, Date date);

	public MetricReport queryUserMonitorReport(String product, Map<String, String> properties, Date date);

}
