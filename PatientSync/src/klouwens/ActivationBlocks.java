package klouwens; //20/4/23 17.26 (66)

import java.text.ParseException;
import java.util.Date;

public class ActivationBlocks {

	private ActivationBlock[] ActivationBlocks;


	public void fillActivationBlocks() throws ParseException
	{
		ActivationBlocks = getActivationBlocks();
	}
	
	public static ActivationBlock[] getActivationBlocks() throws ParseException {
		
		ActivationBlock[] blocks = {	new ActivationBlock("zeXf", "498625", "707327", "374588", "071795", "851316", "619358", "762316", "132129", "595868", "621511", "240101", "240701", "250101", "250701", "260101", "260701", "270101", "270701", "280101", "280701"), 
										new ActivationBlock("GeLL", "594285", "361297", "191415", "310438", "277390", "603585", "041299", "463435", "448555", "176885", "240101", "240701", "250101", "250701", "260101", "260701", "270101", "270701", "280101", "280701"),
										new ActivationBlock("tcMt", "586862", "682486", "443010", "801711", "800926", "469076", "757626", "443681", "771469", "797558", "240101", "240701", "250101", "250701", "260101", "260701", "270101", "270701", "280101", "280701"),
						/*test*/		new ActivationBlock("test", "586862", "682486", "443010", "801711", "800926", "469076", "757626", "443681", "771469", "797558", "240101", "240701", "250101", "250701", "260101", "260701", "270101", "270701", "280101", "220701")};
		return blocks;
	}
	

	
	public int validateBlocks() throws ParseException
	// 0 = Activation invalid
	// 1 = Activation valid		License invalid
	// 2 = Activation valid		License valid
	{		
			
	int validLicense = 0;  
	for (ActivationBlock block : ActivationBlocks)
    {	
		
		System.out.println(block.getActivationKey());
		if (block.getActivationKey().equals(UserPreferences.getActivationKey()))
			{
			System.out.println("Valid ActivationKey");
			validLicense = 1;
    		
    		for (int i = 0 ; i < block.getLicenses().length; i++)
    		{
    			System.out.println(block.getLicense(i));
    			if (block.getLicense(i).equals(UserPreferences.getLicense()))
    			{
    				System.out.println("Valid License");
    				int difference = new Date().compareTo(block.getDate(i));
    				
    				if (difference <= 0)
    				{
    					validLicense = 2;
    				}
    			}
    		}
			}
    }
	return validLicense;
	}
}
