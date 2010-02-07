package org.infinite.engines.dialog.dto;

public class Answer {

	private String answer;
	private long reqLevel;
	private boolean strict;
	private long reqQuest;
	private long reqQuestStatus;
	private long dialogId;
	private String redirectUrl;
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getReqLevel() {
		return reqLevel;
	}
	public void setReqLevel(long reqLevel) {
		this.reqLevel = reqLevel;
	}
	public long getReqQuest() {
		return reqQuest;
	}
	public void setReqQuest(long reqQuest) {
		this.reqQuest = reqQuest;
	}
	public long getReqQuestStatus() {
		return reqQuestStatus;
	}
	public void setReqQuestStatus(long reqQuestStatus) {
		this.reqQuestStatus = reqQuestStatus;
	}
	public void setDialogId(long dialogId) {
		this.dialogId = dialogId;
	}
	public long getDialogId() {
		return dialogId;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setStrict(boolean strict) {
		this.strict = strict;
	}
	public boolean isStrict() {
		return strict;
	}
	
}
