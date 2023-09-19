package klouwens; //3/9/23/ 1446 (21)

import java.io.IOException;
import java.io.OutputStream;

public class GUIOutputStream extends OutputStream {

	    private AdresFrame adresFrame;

	    public GUIOutputStream(AdresFrame adresFrame) {
	        this.adresFrame = adresFrame;
	    }
	    
	   
	@Override
	public void write(int b) throws IOException {
		adresFrame.appendConsoleText(String.valueOf((char) b));


	}

}
