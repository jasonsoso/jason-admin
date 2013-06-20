package com.jason.admin.interfaces.controller;

import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;




@Controller
@RequestMapping(value = "/qrcode")
public class QRCodeController {

	@RequestMapping(value="/contents/{contents}/width/{width}/height/{height}", method=RequestMethod.GET)
	public void qrcode(@PathVariable("contents") String contents,@PathVariable("width") int width,
			@PathVariable("height") int height,HttpServletResponse response) throws IOException{
		ServletOutputStream out = response.getOutputStream();
		//生成二维码
		Hashtable<Object, Object> hints = new Hashtable<Object, Object>();
        // 指定纠错等级  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
        // 指定编码格式  
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");  
        try {  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,  
                    BarcodeFormat.QR_CODE, width, height, hints);  
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);

            out.flush();
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	out.close();
        }
	}

}

