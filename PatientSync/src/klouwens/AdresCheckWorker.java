package klouwens; //19/9/14.54

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class AdresCheckWorker extends SwingWorker<Void, Integer> {
    private File[] inputfiles;
    private static JFrame frame;
    private static JLabel errorLabel;
    private static JLabel infoLabel;
    private static JProgressBar progressBar;
    private static JLabel progressLabel;
    private static JLabel fileLabel;
    
    Locale locale = Locale.getDefault();
	ResourceBundle trans = ResourceBundle.getBundle("resources/translations", locale);
 
    
 // Counters
 	public static int combinedtot = 0;
 	public static int tot = 0;
 	public static int faulty = 0;
 	public static int noBSN = 0;
 	public static int noAdres = 0;
 	public static int awMed = 0;
 	public static int awSBVZ = 0;
 	public static int max = 0;
 	

 	

    public AdresCheckWorker(File[] inputfiles, JFrame frame, JLabel errorLabel, JLabel infoLabel,JProgressBar progressBar, JLabel progressLabel, JLabel fileLabel) {
        this.inputfiles = inputfiles;
        AdresCheckWorker.frame = frame;
        AdresCheckWorker.errorLabel = errorLabel;
        AdresCheckWorker.infoLabel = infoLabel;
        AdresCheckWorker.progressBar = progressBar;
        AdresCheckWorker.progressLabel = progressLabel;
        AdresCheckWorker.fileLabel = fileLabel;
    }

    @Override
    protected Void doInBackground() 
    { try {
    	System.out.println("Starting Sync >");
    	System.out.println("User ID:		" + UserPreferences.getUserID());
    	
    	// Init Translations    	
        Locale locale = Locale.getDefault();
		ResourceBundle trans = ResourceBundle.getBundle("resources/translations", locale);
		System.out.println("Locale:			" + locale);
		
// Validate Files
		if (validateFile(inputfiles) == false)
		{	// Error invalid file type
			infoLabel.setText(trans.getString("sync.aborted"));
			errorLabel.setText(trans.getString("error.filetype"));
			System.out.println("ERROR:		File validation failed: Input file is not .csv");
			return null;	
		}
		
// Init Time & Date
		Instant start = Instant.now();
		SimpleDateFormat form = new SimpleDateFormat("yyMMdd");   
		String date = form.format(new Date());	
		
		
// Init Selenium
		infoLabel.setText(trans.getString("connect.svbz"));
		System.out.println("	");
		System.out.println("Start Selenium Initialization");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--window-size=550,350");
		options.addArguments("--disable-extensions");
		options.addArguments("--app=https://raadplegen.sbv-z.nl");
		options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		options.addArguments("--disable-gpu");

		
		System.out.println("Start Selenium Driver");
		//Crash here can indicate a problem with the driver
    	ChromeDriver driver = new ChromeDriver(options);
		frame.addWindowListener(new java.awt.event.WindowAdapter() 
			{ 
			public void windowClosing(java.awt.event.WindowEvent e) 
				{
				try 
					{
					driver.quit();
					}
				finally
				{
					System.exit(0);
				}
				
			}
		});

    	infoLabel.setText(trans.getString("waiting.login"));
    	System.out.println("Selenium Initialization 3/3");
    	System.out.println("Waiting for user login");
    	driver.get("https://uzipas.raadplegen.sbv-z.nl/opvragen-persoonsgegevens");
    	driver.get("https://raadplegen.sbv-z.nl/auth/uzipas/inloggen?return=%2Fopvragen-persoonsgegevens");
    	System.out.println("User login sucessful");
 
	

    	
    	
    	
    	if (! UserPreferences.getUserID().equals("DEV"))
    		{
    		driver.manage().window().setPosition(new Point(-32000,-32000));
    		driver.manage().window().minimize();
    		}
    	driver.manage().window().setSize(new Dimension(550,1080));
    	new Actions(driver).click(new WebDriverWait(driver, Duration.ofMillis(2000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"persoonsgegevens-opvragen\"]/a")))).build().perform();
    	System.out.println("Connected to SBV-Z");
    	
// Importing AW data 
    	System.out.println("Importing AW data");
    	infoLabel.setText(trans.getString("init.aw"));
    	List<String> aWen = Awen.getAWen();
    	
// Make patients array from input 
		System.out.println("Files inputed:		" + inputfiles.length);
		int file = 0;
    	for(File inputfile : inputfiles) 
    	{	if (inputfile.canRead()) 
	    {	
    		file ++;
    		rid = 0;
    		tot = 0;
    		faulty = 0;
	    	noBSN = 0;
	    	noAdres = 0;
	    	awMed = 0;
	    	awSBVZ = 0;
	    	max = 0;

// Add fileLabel    	
	    	if (inputfiles.length > 1)
	    	{
	    		fileLabel.setText(trans.getString("file") + inputfile.getName() + " (" + file + "/" + inputfiles.length + ")");
	    	}	
	    	else
	    	{
	    		fileLabel.setText(trans.getString("file") + inputfile.getName());
	    	}
	    	
	    	

// Import Patients Array and Create Export location
	    	infoLabel.setText(trans.getString("init.patients"));
			ArrayList<Patient> patients = new ArrayList<Patient>();
			File exportFile;
			
	
			if (inputfile.getName().endsWith(".csv"))
				{	
					System.out.println("File type:		.csv");
					exportFile = new File(inputfile.getParentFile(), inputfile.getName().substring(0, inputfile.getName().length() - 4) + "_outputdata_" + date + ".xlsx");
					patients = importCSVPatientsToArray(inputfile);
				}
				
			else 
				{
					System.out.print("Error: invalid file type"); errorLabel.setText("Error: invalid file type"); return null;	// TODO is this unnecessary? we already validated
				}
			
			if (patients.isEmpty())
				{
				System.out.println("ERROR: 		No patients succesfully imported");
				fileLabel.setText(trans.getString("file") + inputfile.getName() + trans.getString("error.patients"));
				return null;
				}
			
// Init Apache
			FileOutputStream out = new FileOutputStream(exportFile);
	    	XSSFWorkbook wb = new XSSFWorkbook();
	    	wb.getProperties().getCoreProperties().setCreator("PatientSync");
	    	XSSFSheet ss = wb.createSheet("Nieuwe Patientengegevens");
	    	generateHeaders(ss);
	    	System.out.println("Sheet with Headers created");
		
// Find Postcode SVBZ
	    	System.out.println("Start importing patients from SVB-Z");
	    	System.out.println("Showing faulty patients:");
		for(Patient pt : patients)
		{
			infoLabel.setText(trans.getString("syncing"));
			publish(tot);
			tot = tot+1 ;
			if (pt.getBsn().isEmpty())
			{
				noBSN = noBSN + 1;
				System.out.println(tot + "GEEN BSN MEDICOM, Geboortedatum: " + pt.getGeboortedatum());
			}
				
			else 
			{	
				importDataSVBZ(driver, pt);	
				if(pt.getPostcodeSVBZ().replace("\\s+","").isEmpty())
				{
					noAdres ++;
					exportFaultyPatient(ss, pt, tot);
				}
				
				else if(!pt.getPostcodeSVBZ().equalsIgnoreCase(pt.getPostcodeMedicom()))
				{
					faulty = faulty + 1;
			
// Compare against AW
				
					
						
					for(String aw : aWen)
					{
						if(aw.replaceAll("\\s+","").equalsIgnoreCase(pt.getPostcodeMedicom()))
						{
							pt.setMedicomOW(true);
							awMed++;
						}
						if(aw.replaceAll("\\s+","").equalsIgnoreCase(pt.getPostcodeSVBZ())) 
						{
							pt.setSvbzOW(true);
							awSBVZ++;
						}	
					}
					
					if(pt.isMedicomOW() && pt.isSvbzOW())
					{
						awMed--;
						awSBVZ--;
					}

					printFaultyPatient(pt, tot);
					exportFaultyPatient(ss, pt, tot);
					
				}
			}
		}
		
		System.out.println("Done importing patients from SVB-Z, shutting down driver");
		infoLabel.setText(trans.getString("sync.completed"));
		 driver.quit();	
		 publish(tot);
// Print final counters
		exportStatistics(ss);
		System.out.println("Totaal: "  + tot);
		System.out.println("Foute Postcodes: "  + faulty);
		System.out.println("Zonder BSN: "  + noBSN);
		System.out.println("Van OW -> Norm: "  + awMed);
		System.out.println("Van Norm -> OW: "  + awSBVZ);
				
		wb.write(out);
		wb.close();
    	
    	combinedtot = combinedtot + tot;	
    	}
    	
    	System.out.println(combinedtot);
    	
    	Instant end = Instant.now();
    	Duration interval = Duration.between(start, end);
    	System.out.println("Execution time in Minutes: " + interval.toMinutes());
    	
    	}
    	
	}
catch (Exception e)
	{
		System.err.println("An error occurred while syncing patients. This can be caused by: Selenium, ChromeDriver, Inputfile, or dataparsing  ");
		System.out.println("Printing Stack Trace:");
		e.printStackTrace();
		errorLabel.setText(trans.getString("error.sync"));;
		infoLabel.setText(trans.getString("sync.aborted"));
	}
    
    return null;
    
    }

	public static ArrayList<Patient> importCSVPatientsToArray(File inputFile) throws IOException 
	{
		ArrayList<Patient> patients;
		patients = new ArrayList<Patient>();
						
		FileReader file = new FileReader(inputFile);
		BufferedReader reader = new BufferedReader(file);
		
		String line = reader.readLine();
		line = reader.readLine();
		System.out.println("Headers:	" + line);
		if(checkCSVFormat(line))
		{
			System.out.println("Correct Headers:	TRUE");
			while ((line = reader.readLine()) != null) 
			{
			patients.add(readCSVPatient(line));
			max ++;
			}
			
		}
		System.out.println("Patients imported:	" + max);
		System.out.println("Patient import finished");
		reader.close();
	    return patients;
    
	   
	}
	
 	public static int bsnColumn;
 	public static int gebdColumn;
 	public static int adrsColumn;
 	public static int pstcColumn;
 	public static int wvnmColumn;
 	public static int mnsnColumn;
	

    public static boolean checkCSVFormat(String line) {
        Locale locale = Locale.getDefault();
        ResourceBundle trans = ResourceBundle.getBundle("resources/translations", locale);
       
 
//Check the presence column headers and return false when absent.   
        int numericChars = 0;
        for (int i = 0; i < line.length(); i++) 
        {
        	if (Character.isDigit(line.charAt(i)))
            {   
                numericChars++;
            }
        }

        System.out.println("Digits in header:	" + numericChars);
        if (numericChars > 2 * line.length() / 5 ) {
            // Most characters are numeric, indicating no headers present
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.headers"));
            System.out.println("ERROR:	No Headers");
            return false;  
        }

 //Check the order of column headers and return false when wrong. 
       
        String[] fields = line.split(";");
        
        determineCSVColumns(fields);
        
        if (bsnColumn == -1) {
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.bsn"));
            return false;
        }
        
        if (UserPreferences.getGebBox() && gebdColumn == -1) {
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.gebd"));
            return false;
        }

        if (UserPreferences.getOldAdresBox() && adrsColumn == -1) {
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.adrs"));
            return false;
        }

        if (pstcColumn == -1) {
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.pstc"));
            return false;
        }

        if (UserPreferences.getWoonverbandBox() && wvnmColumn == -1) {
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.wvnm"));
            return false;
        }
        
        if (UserPreferences.getMensnummerBox() && mnsnColumn == -1) {
            infoLabel.setText(trans.getString("sync.aborted"));
            errorLabel.setText(trans.getString("error.format.mnsn"));
            return false;
        }
        System.out.println("BSN: " + bsnColumn + " GEBD: " + gebdColumn + " ADRS: " + adrsColumn + " PSTC: " + pstcColumn +" WVNS: " +
     	wvnmColumn +" MNSN: " +
     	mnsnColumn );
        return true;
    }

    public static void determineCSVColumns(String[] fields) 
    {
    	bsnColumn = -1;
     	gebdColumn = -1;
     	adrsColumn = -1;
     	pstcColumn = -1;
     	wvnmColumn = -1;
     	mnsnColumn = -1; 
    	
    	for (int i = 0; i < fields.length; ++i)
         {
    		if (fields[i].equalsIgnoreCase("BSN"))
         	{
         		bsnColumn = i;
         	}
         	
         	if (fields[i].equalsIgnoreCase("Geboortedatum"))
         	{
         		gebdColumn = i;
         	}
         	
         	if (fields[i].equalsIgnoreCase("Adres"))
         	{
         		adrsColumn = i;
         	}
         	
         	if (fields[i].equalsIgnoreCase("Postcode"))
         	{
         		pstcColumn = i;
         	}
         	if (fields[i].equalsIgnoreCase("Woonverbandnummer"))
         	{
         		wvnmColumn = i;
         	}
         	if (fields[i].equalsIgnoreCase("Mensnummer")) //TODO Check hoe medicom dit exporteerd (mensnummer / mens-nummer / etc)
         	{
         		mnsnColumn = i;
         	}
         		
         }
    }
    
	public static Patient readCSVPatient(String line)
	{

		Patient pt = new Patient();
	    String[] fields = line.split(";",-1);
	    pt.setBsn(fields[bsnColumn].replaceAll("\\s+",""));

	    pt.setPostcodeMedicom(fields[pstcColumn].replaceAll("\\s+",""));
	    
	   if (gebdColumn != -1) {
		    pt.setGeboortedatum(fields[gebdColumn].replaceAll("\\s+",""));
	   }
	   if (adrsColumn != -1) {
		   pt.setAdresMedicom(fields[adrsColumn]); 
	   }

	   	if (wvnmColumn != -1) {
		    pt.setWoonverband(fields[wvnmColumn].replaceAll("\\s+",""));
		   }

	   	if (mnsnColumn != -1) {
		    pt.setMensnummer(fields[mnsnColumn].replaceAll("\\s+",""));
		   }

	   	return pt;  
	}
	
	
	public static void importDataSVBZ(ChromeDriver driver, Patient pt)
	{
		WebElement searchBox = new WebDriverWait(driver,Duration.ofSeconds(40)).until(ExpectedConditions.elementToBeClickable(By.id("bsn")));
		searchBox.sendKeys(pt.getBsn());
				
	//	WebElement searchButton = driver.findElement(By.xpath("//button"));
		
	//	WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"content\"]/app-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens/sbvz-bsn/form/fieldset/div/div/button"));
	//	WebElement searchButton = new WebDriverWait(driver,Duration.ofSeconds(40)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/app-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens/sbvz-bsn/form/fieldset/div/div/button")));
	//	searchButton.click();
		new Actions(driver).click(new WebDriverWait(driver, Duration.ofMillis(2000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/app-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens/sbvz-bsn/form/fieldset/div/div/button")))).build().perform();
				
		WebElement postcodeBox = new WebDriverWait(driver,Duration.ofSeconds(40)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"verblijfplaatsDataTable\"]/table/tbody/tr[7]/td")));
		postcodeBox = driver.findElement(By.xpath("//*[@id=\"verblijfplaatsDataTable\"]/table/tbody/tr[7]/td"));
		String pc = postcodeBox.getText();
		pt.setPostcodeSVBZ(pc);
		
		WebElement straatBox = driver.findElement(By.xpath("//*[@id=\"verblijfplaatsDataTable\"]/table/tbody/tr[2]/td"));
		WebElement nummerBox = driver.findElement(By.xpath("//*[@id=\"verblijfplaatsDataTable\"]/table/tbody/tr[3]/td"));
		WebElement stadBox = driver.findElement(By.xpath("//*[@id=\"verblijfplaatsDataTable\"]/table/tbody/tr[8]/td"));
		WebElement opschortingBox = driver.findElement(By.xpath("//*[@id=\"inschrijvingResultaat\"]/table/tbody/tr[1]/td"));
		WebElement onderzoekBox = driver.findElement(By.xpath("//*[@id=\"persoonsResultaat\"]/table/tbody/tr[11]/td"));
		WebElement geheimBox = driver.findElement(By.xpath("//*[@id=\"inschrijvingResultaat\"]/table/tbody/tr[2]/td"));

		pt.setStraat(straatBox.getText());
		pt.setNummer(nummerBox.getText());
		pt.setStad(" " + stadBox.getText());
		pt.setOpschorting(opschortingBox.getText());
		pt.setOnderzoek(onderzoekBox.getText());
		
		String geheim = geheimBox.getText();
		if(!geheim.equalsIgnoreCase("Geen beperking")) { 
			pt.setGeheim(geheim);
		}
		
		
		if(pt.getStad().equalsIgnoreCase(" Capelle aan den IJssel"))
		{
			pt.setStad("");
		}
				
	//	WebElement anderBSN = new WebDriverWait(driver, Duration.ofSeconds(40)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/app-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens-resultaat/div/a")));
		new Actions(driver).click(new WebDriverWait(driver, Duration.ofMillis(2000)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"content\"]/app-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens-resultaat/div/a")))).build().perform();
	//	anderBSN.click();
	//	new Actions(driver).moveToElement(new WebDriverWait(driver, Duration.ofMillis(20)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/app-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens/sbvz-opvragen-persoonsgegevens-resultaat/div/a")))).click().build().perform();
	}
			
	public static void printFaultyPatient(Patient pt, int tot)
	{
		System.out.println(tot + " BSN: " + pt.getBsn() + ", GD: " + pt.getGeboortedatum() + ", WV: "+ pt.getWoonverband() + ";			Correcte adres: " + pt.getStraat() + " " + pt.getNummer() + " "+ pt.getPostcodeSVBZ() + "			"+ pt.isMedicomOW() + " / " + pt.isSvbzOW()   );
	}
					
	public static int cid;
	public static int rid;
			
	public static void generateHeaders(XSSFSheet ss)
	{
		cid = 0;
		XSSFRow row = ss.createRow(rid++);
		fillNextCell(row, "#");
		if (UserPreferences.getBSNBox())
		{
				fillNextCell(row, "BSN");
		}
		
		if (UserPreferences.getMensnummerBox())
		{
				fillNextCell(row, "Mens Nummer");
		}
	
		if (UserPreferences.getGebBox())
		{
				fillNextCell(row, "Geb. Datum");
		}
		
		if (UserPreferences.getWoonverbandBox())
		{
				fillNextCell(row, "Woonverband #");
		}
		
		if (UserPreferences.getOldAdresBox())
		{
			fillNextCell(row, "Medicom Adres");
			cid = cid + 2;
		}
		
		if (UserPreferences.getNewAdresBox())
		{
			fillNextCell(row, "SBV-Z Adres");
			cid = cid + 3;
		}
		
		cid++;
		fillNextCell(row, "Medicom");
		fillNextCell(row, "SBV-Z");
	}
			
	public static void exportFaultyPatient(XSSFSheet ss, Patient pt, int tot )
	{
		cid = 0;
		XSSFRow row = ss.createRow(rid++);
				
		fillNextCell(row, String.valueOf(tot));
		if (UserPreferences.getBSNBox())
		{
			fillNextCell(row, pt.getBsn());
		}
		
		if (UserPreferences.getMensnummerBox())
		{
			fillNextCell(row, pt.getMensnummer());
		}
		
		if (UserPreferences.getGebBox())
		{
			fillNextCell(row, pt.getGeboortedatum());
		}
		
		if (UserPreferences.getWoonverbandBox())
		{
			fillNextCell(row, pt.getWoonverband());
		}

		if (UserPreferences.getOldAdresBox())
		{
			fillNextCell(row, pt.getAdresMedicom());
			cid++;
			fillNextCell(row, pt.getPostcodeMedicom());
			
		}
		
		if (UserPreferences.getNewAdresBox())
		{
			fillNextCell(row, pt.getStraat() );
			fillNextCell(row, pt.getNummer() );
			fillNextCell(row, pt.getPostcodeSVBZ());
			fillNextCell(row, pt.getStad());
		}
		cid++;
		if(pt.isMedicomOW()) {
			fillNextCell(row, "OW" );
		}
		else {
			fillNextCell(row, "" );
		}
		if(pt.isSvbzOW()) {
			fillNextCell(row, "OW" );
		}
		else {
			fillNextCell(row, "" );
		}
		cid++;
		fillNextCell(row, UserPreferences.getUserID() + "/ Vlgns SVB-Z woont pt nu op: " + pt.getStraat() + " " + pt.getNummer() + " " + pt.getPostcodeSVBZ() + pt.getStad() + ". Oude adres: " + pt.getAdresMedicom() + " " + pt.getPostcodeMedicom());
		//TODO ^ Goede text		
		fillNextCell(row, pt.getOnderzoek());
		fillNextCell(row, pt.getOpschorting());
		fillNextCell(row, pt.getGeheim());

	}

	public static void fillNextCell(XSSFRow row, String data)
	{
		Cell cell = row.createCell(cid++);
		cell.setCellValue(data);
	}
	
	public static void exportStatistics(XSSFSheet ss)
	{
		cid = 1;
		rid++;
		
		XSSFRow row = ss.createRow(rid++);
		fillNextCell(row, "Totale Patienten:");
		fillNextCell(row, String.valueOf(tot));
		
		cid = 1;
		row = ss.createRow(rid++);
		fillNextCell(row, "Geen BSN:");
		fillNextCell(row, String.valueOf(noBSN));
	
		cid = 1;	
		row = ss.createRow(rid++);
		fillNextCell(row, "Geen Adress:");
		fillNextCell(row, String.valueOf(noAdres));
	
		cid = 1;
		row = ss.createRow(rid++);
		fillNextCell(row, "Aan te passen:");
		fillNextCell(row, String.valueOf(faulty));
		
		cid = 1;
		row = ss.createRow(rid++);
		fillNextCell(row, "Waarvan:");
		fillNextCell(row, "OW -> Niet OW");
		fillNextCell(row, String.valueOf(awMed));
		
		cid = 2;
		row = ss.createRow(rid++);
		fillNextCell(row, "Niet OW -> OW");
		fillNextCell(row, String.valueOf(awSBVZ));
	}
	
	
	public static boolean validateFile(File[] inputFiles) {
	    for (File file : inputFiles) {
	        String fileName = file.getName();
	        if (!fileName.endsWith(".csv")) {
	        	return false;
	        }
	    }
	    return true;
	}
  
    
  
    
    
    
    @Override
	protected void process(List<Integer> chunks) {
    	int progress = chunks.get(chunks.size() - 1);
        progressLabel.setText(trans.getString("progressbar.progress") + " " + progress + " " + trans.getString("progressbar.outof") + " " + max + " " + trans.getString("progressbar.patients"));
        progressBar.setMaximum(max);
        progressBar.setValue(progress);
            
    }
    
}