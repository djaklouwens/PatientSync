package klouwens; //3/9/23/ 1446 (21)

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GUIErrorStream extends OutputStream {

	    private DeveloperFrame developerFrame;
	    private PrintStream defaultErrorStream;

	    public GUIErrorStream(DeveloperFrame developerFrame, PrintStream defaultErrorStream) {
	        this.developerFrame = developerFrame;
	        this.defaultErrorStream = defaultErrorStream;
	    }
	    
	   
	@Override
	public void write(int b) throws IOException {
		String out = String.valueOf((char) b);
		defaultErrorStream.print(out);
		developerFrame.appendConsoleError(out);
		
		

	


	}

}
