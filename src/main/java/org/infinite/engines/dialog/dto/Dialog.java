package org.infinite.engines.dialog.dto;

import java.util.ArrayList;

public class Dialog {

	private Long id;
	private String sentence;
	private ArrayList<Answer> answers;
	
	
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	
	
}
