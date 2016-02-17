package edu.cmu.webapp.task8.JSON;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class LoginJSON {
	private String message;
	private List<Menu> menu;
	public LoginJSON() {
		
	}
	public LoginJSON(String message) {
		this.message = message;
		this.menu = new ArrayList<>();
	}
	public LoginJSON(String message, List<Menu> menu) {
		this.message = message;
		this.menu = menu;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Menu> getMenu() {
		return menu;
	}
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

}
