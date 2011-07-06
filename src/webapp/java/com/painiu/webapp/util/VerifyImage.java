/**
 * @(#)VerifyImage.java Dec 13, 2009
 * 
 * Copyright 2008 Painiu. All rights reserved.
 */
package com.painiu.webapp.util;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * <p>
 * <a href="VerifyImage.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author Zhang Songfu
 * @version $Id: VerifyImage.java 36 2010-06-01 02:14:52Z zhangsf $
 */
public class VerifyImage {

	//~ Static fields/initializers =============================================

	//~ Instance fields ========================================================

	//~ Constructors ===========================================================

	//~ Methods ================================================================

	public String GetImage(OutputStream outputStream)
    {
        int width = 60, height = 20;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(250,250,250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Courier New",Font.BOLD,18));
        g.setColor(new Color(30,30,30));
        Random random = new Random();
        for (int i = 0; i < 6; i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(20);
            int yl = random.nextInt(20);
            g.drawLine(x,y,x+xl,y+yl);
        }
        String sRand = "";
        for (int i = 0; i < 4; i++){
            String rand=String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(0,0,0));
            g.drawString(rand,13*i+6,16);
        }
        g.dispose();
        try
        {
            ImageIO.write(image, "JPEG", outputStream);
            outputStream.flush();
            return sRand;
        } catch (IOException e)
        {
            e.printStackTrace();
            return "fail";
        }
    }
    
	public Color getRandColor(int fc, int bc)
    {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

}
