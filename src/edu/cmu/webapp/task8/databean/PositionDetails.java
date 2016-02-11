package edu.cmu.webapp.task8.databean;

public class PositionDetails {
    private PositionBean position;
    private String fundName;
    private String fundSymbol;
    public PositionBean getPosition() {
        return position;
    }
    public void setPosition(PositionBean position) {
        this.position = position;
    }
    public String getFundName() {
        return fundName;
    }
    public void setFundName(String fundName) {
        this.fundName = fundName;
    }
    public String getFundSymbol() {
        return fundSymbol;
    }
    public void setFundSymbol(String fundSymbol) {
        this.fundSymbol = fundSymbol;
    }
}
