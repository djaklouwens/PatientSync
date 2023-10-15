package klouwens; //3 9 22.01 (9d)

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JInternalFrame;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import org.jdesktop.swingx.prompt.PromptSupport;
import org.jdesktop.swingx.prompt.PromptSupport.FocusBehavior;

public class AdresFrame {
	// GUI Items
		JFrame frame;
		JLabel errorLabel;
		JLabel infoLabel;
		JComboBox<String> comboBox;
		ResourceBundle trans;
		JButton btnStart;
		JFileChooser fileChooser;
		JProgressBar progressBar;
		JLabel progressLabel;
		JLabel fileLabel;
		private JButton btnNewButton;
		private JCheckBox chckbxNewCheckBox;
		JFormattedTextField validationTextField;

	public AdresFrame() throws ParseException
	{
//Initialize Look & Feel	
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
// Initialize Translations
		trans = getTrans();
		System.out.println(new Locale(UserPreferences.getLocale().toString()));
	
// Initialize JFrame		
		frame = new JFrame();
		frame.setBounds(100, 100, 743, 527);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PatientSync");
		frame.getContentPane().setLayout(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(AdresFrame.class.getResource("/resources/PatientSync V6 png.png")));
		
	

// Initialize Labels
		errorLabel = new JLabel(""); // initialize error label with empty text
		errorLabel.setBounds(20, 42, 697, 40);
        errorLabel.setForeground(Color.RED); // set text color to red
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(errorLabel); // add to frame

        infoLabel = new JLabel(""); // initialize progress label with empty text
        infoLabel.setBounds(10, 11, 707, 20);
        infoLabel.setForeground(Color.BLACK); // set text color to black
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(infoLabel); // add to frame
        infoLabel.setText(trans.getString("welcome.message"));
        
        fileLabel = new JLabel("");
        fileLabel.setBounds(121, 440, 400, 14);
        progressLabel = new JLabel("");
        progressLabel.setBounds(330, 460, 200, 14);
        
        validationTextField = new JFormattedTextField();
        validationTextField.setBounds(270, 93, 200, 20);
        validationTextField.setColumns(0);
        
        if (UserPreferences.getActivationKey().equals("null"))
        {
        	errorLabel.setText(trans.getString("error.activationkey"));
    		System.out.print("No activation key");
            
            PromptSupport.setPrompt(trans.getString("enter.activationkey"), validationTextField);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, validationTextField);
           
            validationTextField.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    UserPreferences.insertActivationKey(validationTextField.getText());
        			progressLabel.setText(trans.getString("success.activationkey"));
        			errorLabel.setText(trans.getString("prompt.restart"));
                }});
            
            frame.getContentPane().add(validationTextField);
        }
        else if (UserPreferences.getLicense().equals("null"))
        {
        	errorLabel.setText(trans.getString("error.license.invalid"));
            frame.getContentPane().add(validationTextField);
            PromptSupport.setPrompt(trans.getString("enter.license"), validationTextField);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, validationTextField);
            validationApp();
        }

        else if (Decrypt.validateDate() == true)  //TODO Deal with parse exception here! they happen when license is already invalid before startup.
       {
    	  
    	   mainApp();
       }
        											//14/10/23
       else if (Decrypt.validateDate() == false) //TODO Same here, but we can also just prevent that from happening by trying to parse before storing in UserPrefs.
        {											//TBH i think this is the way. Just create a parse tryer in the decrypt class and run that first. Im gonna fix other things first now tho
        	errorLabel.setText(trans.getString("error.license.expired"));
            frame.getContentPane().add(validationTextField);
            PromptSupport.setPrompt(trans.getString("enter.license"), validationTextField);
            PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, validationTextField);
        	validationApp();
        }
	}
	
	public void disableLocaleBox() {
		comboBox.setEnabled(false);
	}
	public ResourceBundle getTrans() {
	Locale.setDefault(new Locale(UserPreferences.getLocale()));
	return ResourceBundle.getBundle("resources/translations", Locale.getDefault());
	}
	public void updateMain() 
	{
		   infoLabel.setText(trans.getString("welcome.message"));
		   btnStart.setText(trans.getString("start.sync"));
		   fileChooser.setDialogTitle(trans.getString("choose.input.file"));
		   fileChooser.setFileFilter(new FileNameExtensionFilter(trans.getString("csv.excel"), "csv", "xlsx"));

	}

    public void validationApp()
    {
        validationTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputText = validationTextField.getText();
              
               // Activation Key Reset
                if(inputText.equals("null"))
                {
                	UserPreferences.insertActivationKey("null");
                	infoLabel.setText("Activation Key has been reset");
    				errorLabel.setText(trans.getString("prompt.restart"));
                }
                
                //License
                else if (inputText.length() == 6)
                {
                	UserPreferences.insertLicense(inputText);
    						try {
    							if (Decrypt.validateDate() == true)
    							 {
    								SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    								infoLabel.setText(trans.getString("success.license") + " " + sdf.format(Decrypt.decryptDate(inputText)));
    								errorLabel.setText(trans.getString("prompt.restart"));
    							 }
    						} catch (ParseException e1) {
    							errorLabel.setText(trans.getString("error.license.invalid") + " (ParseException)");
    							UserPreferences.insertLicense("null");							
    						}
    						try {
    							if (Decrypt.validateDate() == false)
    							 {
    								errorLabel.setText(trans.getString("error.license.expired"));							 
    							 }
    						} catch (ParseException e1) {
    							errorLabel.setText(trans.getString("error.license.invalid")  + " (ParseException)");
    							UserPreferences.insertLicense("null");
    						}		
                }
                else {
                	//TODO invalid input length error
                }
            
            }});}
// Start Normal Behavior
	
	public void mainApp(){
		
// Init FileChooser
    System.out.println("Main App");
	fileChooser = new JFileChooser();
	fileChooser.setBounds(25, 11, 582, 399);
	fileChooser.setDialogTitle(trans.getString("choose.input.file"));
	fileChooser.setMultiSelectionEnabled(true);
	fileChooser.setFileFilter(new FileNameExtensionFilter(trans.getString("csv.excel"), "csv", "xlsx"));
	
	
    JCheckBox mensnummerBox = new JCheckBox("Mensnummer");
    mensnummerBox.setSelected(UserPreferences.getMensnummerBox());
    mensnummerBox.setBounds(10, 89, 97, 23);
    frame.getContentPane().add(mensnummerBox);
    mensnummerBox.setName("mensnummerbox");
		
	JCheckBox bsnBox = new JCheckBox("BSN");
	bsnBox.setSelected(UserPreferences.getBSNBox());
	bsnBox.setBounds(10, 115, 97, 23);
	frame.getContentPane().add(bsnBox);
	bsnBox.setName("bsnbox");

	JCheckBox gebBox = new JCheckBox("Geb. datum");
	gebBox.setSelected(UserPreferences.getGebBox());
	gebBox.setBounds(109, 89, 97, 23);
	frame.getContentPane().add(gebBox);
	gebBox.setName("gebbox");
 
	JCheckBox woonverbandBox = new JCheckBox("Woonverband");
	woonverbandBox.setSelected(UserPreferences.getWoonverbandBox());
	woonverbandBox.setBounds(109, 115, 97, 23);
	frame.getContentPane().add(woonverbandBox);
	woonverbandBox.setName("woonverbandbox");

	JCheckBox oldAdresBox = new JCheckBox("Oude Adres");
	oldAdresBox.setSelected(UserPreferences.getOldAdresBox());
	oldAdresBox.setBounds(208, 89, 97, 23);
	frame.getContentPane().add(oldAdresBox);
	oldAdresBox.setName("oldadresbox");

	JCheckBox newAdresBox = new JCheckBox("Nieuwe Adres");
	newAdresBox.setSelected(UserPreferences.getNewAdresBox());
	newAdresBox.setBounds(208, 115, 97, 23);
	frame.getContentPane().add(newAdresBox);
	newAdresBox.setName("newadresbox");
 
	JLabel UserIDLabel = new JLabel("User ID:");
	UserIDLabel.setBounds(20, 227, 46, 14);
	frame.getContentPane().add(UserIDLabel);
 
	JFormattedTextField userIDTextField = new JFormattedTextField();
	userIDTextField.setBounds(20, 242, 74, 20);
	frame.getContentPane().add(userIDTextField);
	PromptSupport.setPrompt(UserPreferences.getUserID(), userIDTextField);
	PromptSupport.setFocusBehavior(FocusBehavior.SHOW_PROMPT, userIDTextField);
	userIDTextField.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String inputText = userIDTextField.getText();
			UserPreferences.setUserID(inputText);
	}});      
	
	ItemListener checkboxListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
		JCheckBox checkbox = (JCheckBox)e.getItem();
		String boolState = e.getStateChange() == 1 ? "true" : "false";
		UserPreferences.setCheckBox(checkbox.getName(), boolState);
		 }};
		 
	mensnummerBox.addItemListener(checkboxListener);
	bsnBox.addItemListener(checkboxListener);
	gebBox.addItemListener(checkboxListener);
	woonverbandBox.addItemListener(checkboxListener);
	oldAdresBox.addItemListener(checkboxListener);
	newAdresBox.addItemListener(checkboxListener);
	
	btnStart = new JButton(trans.getString("start.sync"));
	btnStart.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				disableLocaleBox();
				infoLabel.setText(trans.getString("starting.sync"));
				errorLabel.setText("");
				File[] inputfiles = fileChooser.getSelectedFiles();

				// Create a new AdresCheckWorker and execute it
			    AdresCheckWorker worker = new AdresCheckWorker(inputfiles, errorLabel, infoLabel, progressBar, progressLabel, fileLabel);
			    worker.execute();

			    

			}

			else
			{
				// Error message no file selected
			}
		}
	});
	btnStart.setBounds(25, 454, 89, 24);
	frame.getContentPane().add(btnStart);
	


   
    
    progressBar = new JProgressBar();
    progressBar.setBounds(121, 457, 465, 22);
    frame.getContentPane().add(progressBar);
    
    comboBox = new JComboBox<String>(new String[]{"Nederlands", "English"}); // add language options to the combobox
    comboBox.setBounds(597, 457, 120, 22);
    frame.getContentPane().add(comboBox);
    

    if (UserPreferences.getLocale().equals("en")) {
        comboBox.setSelectedItem("English");

    }
    else if (UserPreferences.getLocale().equals("nl")) {
        comboBox.setSelectedItem("Nederlands");

    }

    comboBox.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String language = (String) comboBox.getSelectedItem();
            if (language.equals("English")) {
                UserPreferences.setLocale("en");
            } 
            else if (language.equals("Nederlands")) {
                UserPreferences.setLocale("nl");
            } 
            trans = getTrans();
            updateMain();
            
        }
    });
	}
}
        
