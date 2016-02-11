package edu.cmu.webapp.task8.databean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ValueFormatter {
	public static String round(double value, int places) {
		if (places < 0) return "N/A";
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		NumberFormat formatter;
		switch (places) {
			case 2:
				formatter = new DecimalFormat("#,##0.00");
				break;
			case 3:
				formatter = new DecimalFormat("#,##0.000");
				break;
			case 1:
				formatter = new DecimalFormat("#,##0.0");
				break;
			default:
				formatter = new DecimalFormat("#,##0.00");
				break;
		}
		double retval = Double.parseDouble(bd.toString());
		return formatter.format(retval);
	}
	
	public static void main(String[] args) {
		ValueFormatter cg = new ValueFormatter();
		System.out.println(cg.round(2.3430412, 2));
		System.out.println(cg.round(1231232.3455012, 3));
		System.out.println(cg.round(2.3454912, 2));
		System.out.println(cg.round(2.3454912, 3));
		System.out.println(cg.round(2.3500001, 3));
		System.out.println(cg.round(2.3750412, 2));
		System.out.println(cg.round(2.3750412, 3));
	}
}
