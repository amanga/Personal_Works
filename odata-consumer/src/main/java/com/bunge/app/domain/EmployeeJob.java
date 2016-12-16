package com.bunge.app.domain;

import java.util.Calendar;

public class EmployeeJob {

	private String userId;
	private Calendar endDate;
	private Calendar startDate;
	private String eventReason;
	private String event;
	private String emplStatus;
	private String source;
	private String company;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEmplStatus() {
		return emplStatus;
	}

	public void setEmplStatus(String emplStatus) {
		this.emplStatus = emplStatus;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "EmployeeJob [userId=" + userId + ", endDate=" + endDate
				+ ", startDate=" + startDate + ", eventReason=" + eventReason
				+ ", event=" + event + ", emplStatus=" + emplStatus
				+ ", source=" + source + ", company=" + company + "]";
	}

}
