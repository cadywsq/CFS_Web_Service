package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;

import edu.cmu.webapp.task8.formbean.CreateCustomerFormBean;

public class CreateCustomerBeanParam {
	
    private @FormParam("firstname") String firstName;
    private @FormParam("lastname") String lastName;
    private @FormParam("username") String username;
    private @FormParam("password") String password;
    private @FormParam("addr_line1") String addrLine1;
    private @FormParam("addr_line2") String addrLine2;
    private @FormParam("city") String city;
    private @FormParam("state") String state;
    private @FormParam("zip") String zip;
    private CreateCustomerFormBean createCustomerFormbean;
    
    public CreateCustomerBeanParam(){
    	createCustomerFormbean = new CreateCustomerFormBean();
    }
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public CreateCustomerFormBean getCustomerFormbean() {
		createCustomerFormbean.setFirstName(this.firstName);
		createCustomerFormbean.setLastName(this.lastName);
		createCustomerFormbean.setUsername(this.username);
		createCustomerFormbean.setPassword(this.password);
		createCustomerFormbean.setAddrLine1(this.addrLine1);
		createCustomerFormbean.setAddrLine2(this.addrLine2);
		createCustomerFormbean.setCity(this.city);
		createCustomerFormbean.setState(this.state);
		createCustomerFormbean.setZip(this.zip);
		createCustomerFormbean.setCash(0);
		return createCustomerFormbean;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddrLine1() {
		return addrLine1;
	}
	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}
	public String getAddrLine2() {
		return addrLine2;
	}
	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
}
