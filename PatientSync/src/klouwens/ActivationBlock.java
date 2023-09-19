package klouwens;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivationBlock {

	private String activationKey;

	private String[] licenses;

	private Date[] Dates;

	public activationBlock(String Key, String license1, String license2, String license3, String license4, String license5, String license6, String license7, String license8, String license9, String license10, String date1, String date2, String date3, String date4, String date5, String date6, String date7, String date8, String date9, String date10 ) 
	{
		SimpleDateFormat form = new SimpleDateFormat("yyMMdd"); 
		this.setActivationKey(Key);
		String[] array = {license1, license2, license3, license4, license5, license6, license7, license8, license9, license10};
		licenses = array;
		
		Date[] array = {form.parse(expiryString)};
	}
	
	public String getActivationKey() {
		return activationKey;
	}

	public void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	public String[] getLicenses() {
		return licenses;
	}

	public void setLicenses(String[] licenses) {
		this.licenses = licenses;
	}

	public Date[] getDates() {
		return Dates;
	}

	public void setDates(Date[] dates) {
		Dates = dates;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
