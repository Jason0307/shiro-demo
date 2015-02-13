/**
 * 
 */
package org.zhubao.qrcode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * @author jason.zhu
 * @date   2015-2-13
 * @email jasonzhu@augmentum.com.cn
 */
public class QrCodeTest {

	public static void main(String[] args) {
		
		 ByteArrayOutputStream out = QRCode.from("Hello World").to(ImageType.PNG).stream();
		 
	        try {
	            FileOutputStream fout = new FileOutputStream(new File(
	                    "D:/QR_Code.JPG"));
	 
	            fout.write(out.toByteArray());
	 
	            fout.flush();
	            fout.close();
	 
	        } catch (FileNotFoundException e) {
	        	System.out.println(e);
	        } catch (IOException e) {
	        	System.out.println(e);
	        }
	}
}
