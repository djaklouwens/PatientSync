package klouwens; //3/9/23/ 1446 (21)

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GUIOutputStream extends OutputStream {

	    private DeveloperFrame developerFrame;
	    private PrintStream defaultOutStream;
	    
	    public GUIOutputStream(DeveloperFrame developerFrame,  PrintStream defaultOutStream) {
	        this.developerFrame = developerFrame;
	        this.defaultOutStream = defaultOutStream;
	    }
	    
	   
	@Override
	public void write(int b) throws IOException {
		String out = String.valueOf((char) b);
		defaultOutStream.print(out);
		developerFrame.appendConsoleText(out);
	}

}
