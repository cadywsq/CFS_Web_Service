package edu.cmu.webapp.task8.JSON;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageJSON {
	private String message;
	public MessageJSON() {
		
	}
	public MessageJSON(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
