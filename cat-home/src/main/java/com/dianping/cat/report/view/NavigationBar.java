package com.dianping.cat.report.view;

import org.unidal.web.mvc.Page;

import com.dianping.cat.report.ReportPage;
import com.dianping.cat.system.SystemPage;

public class NavigationBar {
	public Page[] getSystemPages() {
		return new Page[] {

		SystemPage.ALARM,

		SystemPage.CONFIG,

		SystemPage.LOGIN

		};
	}

	public Page[] getVisiblePages() {
		return new Page[] {

		ReportPage.HOME,

		ReportPage.METRIC,
		
		ReportPage.USERMONITOR,

		ReportPage.TRANSACTION,

		ReportPage.EVENT,

		ReportPage.PROBLEM,

		ReportPage.HEARTBEAT,

		ReportPage.CROSS,

		ReportPage.CACHE,

		ReportPage.MATRIX,

		ReportPage.DEPENDENCY,

		ReportPage.NETWORK,

		ReportPage.ALTERATION,

		ReportPage.STATE,

		ReportPage.STATISTICS,
		
		ReportPage.LOGVIEW,

		};
	}
}
