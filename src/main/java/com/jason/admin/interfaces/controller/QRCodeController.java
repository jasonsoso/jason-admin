package com.jason.admin.interfaces.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
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
import com.jason.framework.web.support.ControllerSupport;

@Controller
@RequestMapping(value = "/qrcode")
public class QRCodeController extends ControllerSupport{

	@RequestMapping(value="/contents/{contents}/width/{width}/height/{height}", method=RequestMethod.GET)
	public void qrcode(@PathVariable("contents") String contents,@PathVariable("width") int width,
			@PathVariable("height") int height,HttpServletResponse response) throws IOException{
		ServletOutputStream out = response.getOutputStream();
		//用于设置QR二维码参数
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 设置QR二维码的纠错级别——这里选择最高H级别  
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        // 指定编码格式  
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  
        try {
        	// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数  
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,  
                    BarcodeFormat.QR_CODE, width, height, hints);  
            
            //MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
            
            BufferedImage imageBI = createPhotoAtCenter(MatrixToImageWriter.toBufferedImage(bitMatrix));
            ImageIO.write(imageBI, "png", out) ;  
            
            imageBI.flush();
            out.flush();
        } catch (Exception e) {  
            this.getLogger().error("生成QECode錯誤！", e);
        }finally{
        	out.close();
        }
	}
	
	/**
     * 在二维码中间加入图片
     * 
     * @param bugImg
     * @return
     */
    private BufferedImage createPhotoAtCenter(BufferedImage bufImg) throws Exception {
    	 Image im = ImageIO.read(new File("C:/Users/tanjianna/git/jason-admin/src/main/webapp/resources/bootstrap/ico/logo-40.png"));
         Graphics2D g = bufImg.createGraphics();
         //获取bufImg的中间位置
         int centerX = bufImg.getMinX() + bufImg.getWidth()/2 - bufImg.getWidth()/6/2;
         int centerY = bufImg.getMinY() + bufImg.getHeight()/2 - bufImg.getWidth()/6/2;
         g.drawImage(im,centerX,centerY,bufImg.getWidth()/6,bufImg.getWidth()/6,null);
         g.dispose();
         bufImg.flush();
    	return bufImg;
    }
}

