package org.infinite.engines.dialog.dto;

public class Answer {

	private String answer;
	private Long reqLevel;
	private Long reqQuest;
	private Long reqQuestStatus;
	private Long dialogId;
	private String redirectUrl;
	
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Long getReqLevel() {
		return reqLevel;
	}
	public void setReqLevel(Long reqLevel) {
		this.reqLevel = reqLevel;
	}
	public Long getReqQuest() {
		return reqQuest;
	}
	public void setReqQuest(Long reqQuest) {
		this.reqQuest = reqQuest;
	}
	public Long getReqQuestStatus() {
		return reqQuestStatus;
	}
	public void setReqQuestStatus(Long reqQuestStatus) {
		this.reqQuestStatus = reqQuestStatus;
	}
	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}
	public Long getDialogId() {
		return dialogId;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
}
