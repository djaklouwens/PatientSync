package klouwens; //19/9 23 13.49 f2

import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintStream;

public class AdresCheckApp {

	public static PrintStream defaultOut;
	public static void main(String[] args) throws IOException
	{
		defaultOut = System.out;
	//	UserPreferences.insertActivationKey("valid");	//valid
	//	UserPreferences.insertActivationKey("null");	//invalid
	//	UserPreferences.insertLicense("EQ\\XTG");		//valid
	//	UserPreferences.insertLicense("DP\\XTG");		//invalid
	//	UserPreferences.insertLicense("null");			//invalid (empty)
		
// Start AdresFrame 	
		EventQueue.invokeLater(new Runnable() {
			public void run() 
			{
				
				try { AdresFrame window = new AdresFrame();
				//	System.setOut(new PrintStream(new GUIOutputStream(window), true));
				//	System.setErr(new PrintStream(new GUIOutputStream(window), true));
					window.frame.setVisible(true);
			        System.out.println("PatientSync v0.4.4");
			        System.out.println("All rights reserved. Copyright 2022-2023.");
			        System.out.println("	");
			        System.out.println("Activation Key:	" + UserPreferences.getActivationKey());
			        System.out.println("License:	" + UserPreferences.getLicense());
			      
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
	
}

