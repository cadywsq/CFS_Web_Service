package edu.cmu.webapp.task8.JSON;

public class Menu {
	private String link;
	private String function;
	public Menu() {
	}
	public Menu(String link, String function) {
		this.link = link;
		this.function = function;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
}
