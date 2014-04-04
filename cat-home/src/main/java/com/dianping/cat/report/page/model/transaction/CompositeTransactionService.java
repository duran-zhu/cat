package com.dianping.cat.report.page.model.transaction;

import java.util.List;

import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;

import com.dianping.cat.consumer.transaction.TransactionAnalyzer;
import com.dianping.cat.consumer.transaction.TransactionReportMerger;
import com.dianping.cat.consumer.transaction.model.entity.TransactionReport;
import com.dianping.cat.report.page.model.spi.internal.BaseCompositeModelService;
import com.dianping.cat.report.page.model.spi.internal.BaseRemoteModelService;
import com.dianping.cat.service.ModelRequest;
import com.dianping.cat.service.ModelResponse;

public class CompositeTransactionService extends BaseCompositeModelService<TransactionReport> implements LogEnabled{
	private Logger m_logger;

	@Override
	public void enableLogging(Logger logger) {
		m_logger = logger;
	}
	
	public CompositeTransactionService() {
		super(TransactionAnalyzer.ID);
	}

	@Override
	protected BaseRemoteModelService<TransactionReport> createRemoteService() {
		return new RemoteTransactionService();
	}

	@Override
	protected TransactionReport merge(ModelRequest request, List<ModelResponse<TransactionReport>> responses) {
		m_logger.info("reponse size:" + responses.size());
		if (responses.size() == 0) {
			return null;
		}
		TransactionReportMerger merger = new TransactionReportMerger(new TransactionReport(request.getDomain()));

		for (ModelResponse<TransactionReport> response : responses) {
			if (response != null) {
				TransactionReport model = response.getModel();
				if (model != null) {
					m_logger.info("model accept");
					model.accept(merger);
				}
			}
		}
		return merger.getTransactionReport();
	}
}
