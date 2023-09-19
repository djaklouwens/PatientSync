package klouwens; //3 9 22.01 (9d)

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
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

public class AdresFrame {

	JFrame frame;
	JLabel errorLabel;
	JLabel progressLabel;
	JComboBox<String> comboBox;
	ResourceBundle trans;
	JButton btnStart;
	JFileChooser fileChooser;

	/**
	 * Launch the application.
	 * @throws ParseException 
	 */
	public AdresFrame() throws ParseException
	{
		initialize();

	}






	/**
	 * Initialize the contents of the frame.
	 * @throws ParseException 
	 */
	private void initialize() throws ParseException {
		


			try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setBounds(100, 100, 743, 527);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PatientSync");
		frame.getContentPane().setLayout(null);

		
		trans = getTrans();
		System.out.println(new Locale(UserPreferences.getLocale().toString()));

		errorLabel = new JLabel(""); // initialize error label with empty text
		errorLabel.setBounds(20, 42, 697, 40);
        errorLabel.setForeground(Color.RED); // set text color to red
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(errorLabel); // add to frame

        progressLabel = new JLabel(""); // initialize progress label with empty text
        progressLabel.setBounds(10, 11, 707, 20);
        progressLabel.setForeground(Color.BLACK); // set text color to black
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(progressLabel); // add to frame
        progressLabel.setText(trans.getString("welcome.message"));
        
        ActivationBlocks blocks = new ActivationBlocks();
        blocks.fillActivationBlocks();
        
        JFormattedTextField formattedTextField = new JFormattedTextField();
        formattedTextField.setBounds(270, 93, 200, 20);
        formattedTextField.setColumns(0);
        formattedTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // get the text the user entered
                String inputText = formattedTextField.getText();
                System.out.print(inputText.length());
               
				mainApp();
               
               // Activation KEy 
                if (inputText.length() == 4)
                {
                	UserPreferences.insertActivationKey(inputText);
                	 try {
						if (blocks.validateBlocks() == 1)
						 {
							progressLabel.setText(trans.getString("succes.activationkey"));
							errorLabel.setText(trans.getString("prompt.restart"));
						 }
						if (blocks.validateBlocks() == 2)
						 {
							progressLabel.setText(trans.getString("succes.activationkey"));
							errorLabel.setText(trans.getString("prompt.restart"));
							 
						 }
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	 
				}
                
                
                else if (inputText.length() == 6)
                {
                	UserPreferences.insertLicense(inputText);
                	 try {
 						if (blocks.validateBlocks() == 1)
 						 {
 							progressLabel.setText(trans.getString("succes.license"));
 							errorLabel.setText(trans.getString("prompt.restart"));
 						 }
 						if (blocks.validateBlocks() == 2)
 						 {
 							progressLabel.setText(trans.getString("succes.license"));
 							errorLabel.setText(trans.getString("prompt.restart"));
 							 
 						 }
 					} catch (ParseException e1) {
 						// TODO Auto-generated catch block
 						e1.printStackTrace();
 					}
                }

            }
        });

        
     
       
        if (blocks.validateBlocks() == 2) {
        	
        	mainApp();
		
	}

	else if (blocks.validateBlocks() == 1){
		errorLabel.setText(trans.getString("error.license"));
        frame.getContentPane().add(formattedTextField);
        PromptSupport.setPrompt(trans.getString("enter.license"), formattedTextField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, formattedTextField);
        // add logic to read out the licensekey

	}
        
	else if (blocks.validateBlocks() == 0){
		errorLabel.setText(trans.getString("error.activationkey"));
		System.out.print("geeen activatie");
        frame.getContentPane().add(formattedTextField);
        PromptSupport.setPrompt(trans.getString("enter.activationkey"), formattedTextField);
        PromptSupport.setFocusBehavior(PromptSupport.FocusBehavior.SHOW_PROMPT, formattedTextField);
        // add logic to read out the actiation
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
		   progressLabel.setText(trans.getString("welcome.message"));
		   btnStart.setText(trans.getString("start.sync"));
		   fileChooser.setDialogTitle(trans.getString("choose.input.file"));
		   fileChooser.setFileFilter(new FileNameExtensionFilter(trans.getString("csv.excel"), "csv", "xlsx"));

	}


	public void mainApp()
	{
    	
	fileChooser = new JFileChooser();
	fileChooser.setBounds(25, 11, 582, 399);
	fileChooser.setDialogTitle(trans.getString("choose.input.file"));
	fileChooser.setMultiSelectionEnabled(true);
	fileChooser.setFileFilter(new FileNameExtensionFilter(trans.getString("csv.excel"), "csv", "xlsx"));
	
	btnStart = new JButton(trans.getString("start.sync"));
	btnStart.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				disableLocaleBox();
				progressLabel.setText(trans.getString("starting.sync"));
				errorLabel.setText("");
				File[] inputfiles = fileChooser.getSelectedFiles();

				// Create a new AdresCheckWorker and execute it
			    AdresCheckWorker worker = new AdresCheckWorker(inputfiles, errorLabel, progressLabel);
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



   
    
    JProgressBar progressBar = new JProgressBar();
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
        
