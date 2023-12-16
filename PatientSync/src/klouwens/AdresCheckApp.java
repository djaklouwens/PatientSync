package klouwens; //19/9 23 13.49 f2

import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintStream;

public class AdresCheckApp {

	public static PrintStream defaultOut;
	public static void main(String[] args) throws IOException
	{
	//	UserPreferences.insertActivationKey("valid");	//valid
	//	UserPreferences.insertActivationKey("null");	//invalid
	//	UserPreferences.insertLicense("EQ\\XTG");		//valid
	//	UserPreferences.insertLicense("DP\\XTG");		//invalid
	//	UserPreferences.insertLicense("null");			//invalid (empty)
		
// Start AdresFrame 	
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				
				try 
				{	
					if (UserPreferences.getUserID().equals("DEV"))
					{
						DeveloperFrame developerFrame = new DeveloperFrame();
						System.setErr(new PrintStream(new GUIErrorStream(developerFrame, System.err), true));
						System.setOut(new PrintStream(new GUIOutputStream(developerFrame, System.out), true));
						developerFrame.frame.setVisible(true);
					}
				
					System.out.println("PatientSync v0.6.1");
					System.out.println("All rights reserved. Copyright 2022-2023.");
					System.out.println("	");
					System.out.println("Activation Key:	" + UserPreferences.getActivationKey());
					System.out.println("License:		" + UserPreferences.getLicense());
					
					AdresFrame adresFrame = new AdresFrame();
					adresFrame.frame.setVisible(true);
				} 
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
		
	
}

