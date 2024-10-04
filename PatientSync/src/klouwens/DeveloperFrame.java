package klouwens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Style;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

public class DeveloperFrame {
	
	JFrame frame;
	JTextPane console;
	StyledDocument doc;
	Style textStyle;
    Style errorStyle;

	public DeveloperFrame()
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
		
	// Initialize JFrame		
		frame = new JFrame();
		frame.setBounds(900, 100, 750, 530);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PatientSync Console");
		frame.getContentPane().setLayout(new BorderLayout());
	//	frame.setIconImage(Toolkit.getDefaultToolkit().getImage(AdresFrame.class.getResource("/resources/PatientSync V6 png.png")));
	//TODO Fix image	
		console = new JTextPane();

		console.setBackground(Color.DARK_GRAY.darker());
		console.setEditable(false);
		console.setFont(new Font(Font.DIALOG_INPUT , Font.PLAIN, 16));
		frame.getContentPane().add(console);
		doc = console.getStyledDocument();
		textStyle = console.addStyle("normal", null);
        StyleConstants.setForeground(textStyle, Color.white);
		
        errorStyle = console.addStyle("error", null);
        StyleConstants.setForeground(errorStyle, Color.red);
        
        JScrollPane scrollPane = new JScrollPane(console);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	}
	public void appendConsoleText(String text)
	{
		try {
			doc.insertString(doc.getLength(), text, textStyle );
		} 
		catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void appendConsoleError(String error)
	{
		try {
			doc.insertString(doc.getLength(), error, errorStyle );
		
		
		} 
		catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
