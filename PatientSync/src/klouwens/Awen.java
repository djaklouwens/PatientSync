package klouwens;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class Awen 
{

	/*	public static String[] getAWen() 
		{
			String[] awData = new String[]{"\t2903VE\t", "\t2903SC\t", "\t2906TA\t", "\t2906TB\t", "\t2906TC\t", "\t2903VB\t", "\t2903SB\t", "\t2903SJ\t", "\t2903VM\t", "\t2906VG\t", "\t2906TE\t", "\t2903VG\t", "\t2905VT\t", "\t2903VD\t", "\t2903VH\t", "\t2903VP\t", "\t2905VH\t", "\t2905VV\t", "\t2902EN\t", "\t2903BC\t", "\t2903SX\t", "\t2903SN\t", "\t2902CS\t", "\t2904EP\t", "\t2905VM\t", "\t2907WK\t", "\t2903VA\t", "\t2906TJ\t", "\t2903TK\t", "\t2903SE\t", "\t2905VS\t", "\t2903VL\t", "\t2902EM\t", "\t2906TN\t", "\t2906VA\t", "\t2906TD\t", "\t2906VE\t", "\t2905TG\t", "\t2903VN\t", "\t2903VK\t", "\t2906VH\t", "\t2905VK\t", "\t2905TH\t", "\t2906VD\t", "\t2903TP\t", "\t2902CP\t", "\t2907WH\t", "\t2903TD\t", "\t2903SV\t", "\t2905VW\t", "\t2906VB\t", "\t2906VL\t", "\t2902EC\t", "\t2903VC\t", "\t2903BB\t", "\t2903BP\t", "\t2907WN\t", "\t2907WG\t", "\t2905VB\t", "\t2903BT\t", "\t2902CT\t", "\t2906VJ\t", "\t2906VC\t", "\t2903VJ\t", "\t2902EL\t", "\t2902EP\t", "\t2906TM\t", "\t2906BE\t", "\t2906BS\t", "\t2903TA\t", "\t2903SH\t", "\t2903SM\t", "\t2902EA\t", "\t2902EB\t", "\t2902EK\t", "\t2905VD\t", "\t2905VN\t", "\t2905VP\t", "\t2905TP\t", "\t2905VL\t", "\t2907KB\t", "\t2904XS\t", "\t2907NK\t", "\t2904TH\t", "\t2905VE\t", "\t2906AJ\t", "\t2906TG\t", "\t2902EJ\t", "\t2905VG\t", "\t2905TK\t", "\t2902EH\t", "\t2905VC\t", "\t2904EL\t", "\t2907DC\t", "\t2902EG\t", "\t2905XJ\t", "\t2905TB\t", "\t2906TK\t", "\t2904TA\t", "\t2907RG\t", "\t2905TJ\t", "\t2907DB\t", "\t2905BT\t", "\t2906BB\t", "\t2906BM\t", "\t2905VR\t", "\t2904EE\t", "\t2905VA\t", "\t2903TH\t", "\t2907RD\t", "\t2904EH\t", "\t2907RM\t", "\t2906BA\t", "\t2906BC\t", "\t2907ZE\t", "\t2903TE\t", "\t2907NJ\t", "\t2905TW\t", "\t2905XR\t", "\t2906BH\t", "\t2905TA\t", "\t2903ST\t", "\t2907KG\t", "\t2907NC\t", "\t2907WP\t", "\t2905TL\t", "\t2903AK\t", "\t2902ED\t", "\t2904XV\t", "\t2906TL\t", "\t2903SK\t", "\t2905TN\t", "\t2905TV\t", "\t2903CA\t", "\t2905TE\t", "\t2905TR\t", "\t2907WL\t", "\t2905VJ\t", "\t2907KC\t", "\t2907RL\t", "\t2902CR\t", "\t2906BD\t", "\t2903CC\t", "\t2905TC\t", "\t2907JB\t", "\t2904ER\t", "\t2904ET\t", "\t2906EK\t", "\t2905XE\t", "\t2902EE\t", "\t2907KA\t", "\t2903SG\t", "\t2903SL\t", "\t2903TB\t", "\t2903BS\t", "\t2903TL\t", "\t2907NG\t", "\t2904RG\t", "\t2904RH\t", "\t2907RH\t", "\t2907JC\t", "\t2905TD\t", "\t2904EG\t", "\t2903TR\t", "\t2905TM\t", "\t2906BL\t", "\t2906BN\t", "\t2904ED\t", "\t2904EJ\t", "\t2904RE\t", "\t2903TC\t", "\t2904EV\t", "\t2903CB\t", "\t2903AH\t"};
			return awData;
		}
		*/
	
	public static List<String> getAWen() 
	{
		InputStream AWfile = Awen.class.getResourceAsStream("/resources/AWen.xlsx");
		List<String> postalCodes = new ArrayList<>();
		try (Workbook workbook = WorkbookFactory.create(AWfile))
	    {
			Sheet sheet = workbook.getSheetAt(1); // Assuming the data is in the second sheet
	        // Start iterating from the second row (index 1) since data starts from row 2
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) 
	        {
	        	Row row = sheet.getRow(i);
	        	if (row != null) 
	        	{
	            	Cell postalCodeCell = row.getCell(0); // Assuming postal code is in the A column
	                Cell endDateCell = row.getCell(3); // Assuming end date is in the D column
	                if (postalCodeCell != null && endDateCell == null)  //
	                {
	                	String postalCode = postalCodeCell.getStringCellValue();
	                	postalCodes.add(postalCode);
	                }
	            }
	        }
	    } catch (EncryptedDocumentException e) {
	    	System.err.println("Error while parsing AW list. Document is Encrypted");
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			System.err.println("Invalid format; not readable Excel file");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.err.println("Error while parsing AW list. Document can not be found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			return postalCodes;
	}
}
