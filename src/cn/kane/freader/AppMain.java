package cn.kane.freader;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;

public class AppMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Charset cs = Charset.forName ("UTF-8");
 	    CharBuffer cb = CharBuffer.allocate (1);
	    cb.put (',');
	    cb.flip ();
		ByteBuffer bb = cs.encode (cb);
		byte b = bb.get();
		
		
		File dataFile = new File("records.dat") ;
		Decoder decoder = new TextRowDecoder(4,b) ;
		FileReader fileReader = FileReader.create(decoder, dataFile);
		Iterator it = fileReader.iterator() ;
	}

}
