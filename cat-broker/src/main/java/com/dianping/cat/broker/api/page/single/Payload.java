package com.dianping.cat.broker.api.page.single;

import com.dianping.cat.broker.api.ApiPage;
import com.dianping.cat.broker.api.page.batch.Action;

import org.unidal.web.mvc.ActionContext;
import org.unidal.web.mvc.ActionPayload;
import org.unidal.web.mvc.payload.annotation.FieldMeta;

public class Payload implements ActionPayload<ApiPage, Action> {
   private ApiPage m_page;

	@FieldMeta("op")
	private Action m_action;

	@FieldMeta("ts")
	private long m_timestamp;

	@FieldMeta("tu")
	private String m_targetUrl;

	@FieldMeta("v")
	private String m_version;

	@FieldMeta("d")
	private double m_duration;

	@FieldMeta("hs")
	private String m_httpStatus;

	@FieldMeta("ec")
	private String m_errorCode;

   @Override
   public Action getAction() {
      return m_action;
   }

   public double getDuration() {
   	return m_duration;
   }

	public String getErrorCode() {
   	return m_errorCode;
   }

	public String getHttpStatus() {
   	return m_httpStatus;
   }

	@Override
   public ApiPage getPage() {
      return m_page;
   }

	public String getTargetUrl() {
   	return m_targetUrl;
   }

	public long getTimestamp() {
   	return m_timestamp;
   }

	public String getVersion() {
   	return m_version;
   }

	public void setAction(String action) {
      m_action = Action.getByName(action, Action.VIEW);
   }

	public void setDuration(double duration) {
   	m_duration = duration;
   }
	
	public void setErrorCode(String errorCode) {
   	m_errorCode = errorCode;
   }

	public void setHttpStatus(String httpStatus) {
   	m_httpStatus = httpStatus;
   }

	@Override
   public void setPage(String page) {
      m_page = ApiPage.getByName(page, ApiPage.SINGLE);
   }

	public void setTargetUrl(String targetUrl) {
   	m_targetUrl = targetUrl;
   }

	public void setTimestamp(long timestamp) {
   	m_timestamp = timestamp;
   }

   public void setVersion(String version) {
   	m_version = version;
   }

   @Override
   public void validate(ActionContext<?> ctx) {
      if (m_action == null) {
         m_action = Action.VIEW;
      }
   }
}
