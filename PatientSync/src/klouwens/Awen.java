package klouwens;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

public class Awen 
{	
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
		} catch (FileNotFoundException e) {
			System.err.println("Error while parsing AW list. Document can not be found");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			return postalCodes;
	}
}
