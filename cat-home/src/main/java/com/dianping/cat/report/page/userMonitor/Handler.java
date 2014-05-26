package com.dianping.cat.report.page.userMonitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.unidal.lookup.annotation.Inject;
import org.unidal.web.mvc.PageHandler;
import org.unidal.web.mvc.annotation.InboundActionMeta;
import org.unidal.web.mvc.annotation.OutboundActionMeta;
import org.unidal.web.mvc.annotation.PayloadMeta;

import com.alibaba.cobar.parser.util.Pair;
import com.dianping.cat.Monitor;
import com.dianping.cat.config.UrlPatternConfigManager;
import com.dianping.cat.configuration.url.pattern.entity.PatternItem;
import com.dianping.cat.helper.TimeUtil;
import com.dianping.cat.report.ReportPage;
import com.dianping.cat.report.page.LineChart;
import com.dianping.cat.report.page.PayloadNormalizer;
import com.dianping.cat.report.page.PieChart;
import com.dianping.cat.report.page.userMonitor.graph.UserMonitorGraphCreator;

public class Handler implements PageHandler<Context> {
	@Inject
	private JspViewer m_jspViewer;

	@Inject
	private UrlPatternConfigManager m_patternManager;

	@Inject
	private CityManager m_cityManager;

	@Inject
	private PayloadNormalizer m_normalizePayload;

	@Inject
	private UserMonitorGraphCreator m_graphCreator;

	@Override
	@PayloadMeta(Payload.class)
	@InboundActionMeta(name = "userMonitor")
	public void handleInbound(Context ctx) throws ServletException, IOException {
		// display only, no action here
	}

	@Override
	@OutboundActionMeta(name = "userMonitor")
	public void handleOutbound(Context ctx) throws ServletException, IOException {
		Model model = new Model(ctx);
		Payload payload = ctx.getPayload();

		normalize(model, payload);
		Collection<PatternItem> rules = m_patternManager.queryUrlPatternRules();

		long start = payload.getHistoryStartDate().getTime();
		long end = payload.getHistoryEndDate().getTime();

		start = start - start % TimeUtil.ONE_HOUR;
		end = end - end % TimeUtil.ONE_HOUR;

		Date startDate = new Date(start);
		Date endDate = new Date(end);
		String type = payload.getType();
		String channel = payload.getChannel();
		String city = payload.getCity();
		Map<String, String> pars = new LinkedHashMap<String, String>();
		String url = payload.getUrl();

		if (url == null && rules.size() > 0) {
			url = new ArrayList<PatternItem>(rules).get(0).getName();
		}

		pars.put("type", type);
		pars.put("channel", channel);
		pars.put("city", city);

		if (url != null) {
			if (Monitor.TYPE_INFO.equals(type)) {
				Pair<Map<String, LineChart>, List<PieChart>> charts = m_graphCreator.queryBaseInfo(startDate, endDate, url,
				      pars);
				Map<String, LineChart> lineCharts = charts.getKey();
				List<PieChart> pieCharts = charts.getValue();

				model.setLineCharts(lineCharts);
				model.setPieCharts(pieCharts);
			} else {
				Pair<LineChart, PieChart> pair = m_graphCreator.queryErrorInfo(startDate, endDate, url, pars);

				model.setLineChart(pair.getKey());
				model.setPieChart(pair.getValue());
			}
		}
		model.setStart(startDate);
		model.setEnd(endDate);
		model.setPattermItems(rules);
		model.setAction(Action.VIEW);
		model.setPage(ReportPage.USERMONITOR);
		model.setCityInfo(m_cityManager.getCityInfo());

		if (!ctx.isProcessStopped()) {
			m_jspViewer.view(ctx, model);
		}
	}

	private void normalize(Model model, Payload payload) {
		model.setPage(ReportPage.USERMONITOR);

		m_normalizePayload.normalize(model, payload);
	}
}
