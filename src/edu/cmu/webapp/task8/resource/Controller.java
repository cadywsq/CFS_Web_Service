package edu.cmu.webapp.task8.resource;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;

public class Controller{
	static AbstractDAOFactory dao;
	public static void databasePrecheck() {
		dao = AbstractDAOFactory.getDAOFactory();
        dao.databasePrecheck();
	}
}
