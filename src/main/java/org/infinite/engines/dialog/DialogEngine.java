package org.infinite.engines.dialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.infinite.engines.dialog.dto.Answer;
import org.infinite.engines.dialog.dto.Dialog;
import org.infinite.objects.Character;
import org.infinite.util.GenericUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class DialogEngine {

	public static final String PARSE_START 	= "PARSE(";
	public static final String PARSE_END 	= ")";

	public static final String DIALOG			="dialog";
	public static final String DIALOG_ID		="id";
	public static final String DIALOG_SENTENCE	="sentence";
	public static final String DIALOG_ANSWERS	="answers";

	public static final String ANSWER			="answer";
	public static final String ANSWER_REQ_LV	="reqLevel";
	public static final String ANSWER_STRICT	="strict";
	public static final String ANSWER_REQ_QID	="reqQuest";
	public static final String ANSWER_REQ_QSTAT	="reqQuestStatus";
	public static final String ANSWER_DIALOGID	="dialogId";
	public static final String ANSWER_URL		="redirectUrl";



	public List<Dialog> getDialogData(String filename) throws IOException {
		return getDialogData( DialogEngine.class.getResourceAsStream(filename));
	}

	public List<Dialog> getDialogData(InputStream is) throws IOException {

		ArrayList<Dialog> out = new ArrayList<Dialog>();

		JSONObject obj = (JSONObject)JSONValue.parse( new InputStreamReader(is) );

		JSONArray dialogs = (JSONArray) obj.get(DIALOG);

		for(int i=0;i < dialogs.size();i++){

			Dialog dialog = new Dialog();
			JSONObject jsonDialog =  (JSONObject) dialogs.get(i);

			dialog.setId( (Long)jsonDialog.get(DIALOG_ID) );
			dialog.setSentence( (String)jsonDialog.get(DIALOG_SENTENCE) );

			JSONArray jsonAllAnswers = (JSONArray) jsonDialog.get(DIALOG_ANSWERS);
			ArrayList<Answer> answers = new ArrayList<Answer>();
			for(int j=0; j<jsonAllAnswers.size();j++){

				Answer answer = new Answer();

				JSONObject jsonAnswer =  (JSONObject) jsonAllAnswers.get(j);

				answer.setAnswer(	(String) jsonAnswer.get(ANSWER) );
				answer.setReqLevel(	(Long)jsonAnswer.get(ANSWER_REQ_LV) );
				answer.setStrict( (Boolean)jsonAnswer.get(ANSWER_STRICT));
				answer.setReqQuest(	(Long)jsonAnswer.get(ANSWER_REQ_QID) );
				answer.setReqQuestStatus( 	(Long)jsonAnswer.get(ANSWER_REQ_QSTAT) );
				answer.setDialogId(			(Long)jsonAnswer.get(ANSWER_DIALOGID) );
				answer.setRedirectUrl(		(String) jsonAnswer.get(ANSWER_URL) );

				answers.add(answer);
			}
			dialog.setAnswers(answers);
			out.add(dialog);
		}

		return out;
	}


	public Dialog selectDialog(long id,List<Dialog> dialogs){
		
		Dialog out = null;
		for(Dialog d : dialogs){
			if(d.getId()==id){
				out = d;
				break;
			}
		}
		return out;
	}
	
	public List<Answer> getAnswers(Dialog d, Character c){
		
		ArrayList<Answer> answers = new ArrayList<Answer>();
		
		for (Answer a : d.getAnswers()) {
			
			if( c.getLevel() < a.getReqLevel() ){
				continue;
			}
			
			//if it's a strict check it must be exactly the same
			if( a.isStrict() && c.getLevel()!= a.getReqLevel()){
				continue;
			}
			
			if( a.getReqQuest()>0 && !c.isPlayerOnQuest( a.getReqQuest(), a.getReqQuestStatus()) ){
				continue;
			}
			answers.add(a);
		}
	
		return answers;
	}
	
	public String evalCst(String input) throws Exception{

		String myinput = input;
		
		if( myinput.startsWith(PARSE_START) ){

			myinput = myinput.substring(PARSE_START.length());
			
			if(myinput.indexOf(PARSE_END)==-1)
				throw new Exception("Sintax error, PARSE_START without PARESE_END");
			
			String append = myinput.substring( myinput.indexOf(PARSE_END)+1 );
			myinput = myinput.substring(0, myinput.indexOf(PARSE_END) );
			
			ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");
			js.put("myinputjs", myinput);

			try {
				myinput = (String) js.eval("eval(myinputjs);");
			} catch (ScriptException e) {
				GenericUtil.err("Rinho error:", e);
			}

			myinput = myinput + append;
		}
		return myinput;
	}


}
