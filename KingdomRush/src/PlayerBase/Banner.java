/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlayerBase;

import MainPackage.GamePanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author richa
 */
public class Banner {
    protected int positionX;
    protected int positionY;
    protected int bannerHeight;
    protected int bannerWidth;
    protected BufferedImage image;
    protected Font customFont;
    protected Color fontColor;
    
    public Banner(String imagePath, int width, int height, int x, int y){
        bannerWidth = width;
        bannerHeight = height;
        this.positionX = x;
        this.positionY = y;
        
        InputStream inputstream = getClass().getResourceAsStream("/assets/font/LuckiestGuy-Regular.ttf");
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
            fontColor = new Color(254,254,204);
        } catch (FontFormatException ex) {
            Logger.getLogger(Banner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Banner.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        }catch(IOException e){
            e.printStackTrace();
        }   
    }
    
    public void draw(Graphics2D g2, String text){
        g2.drawImage(image, positionX, positionY, bannerWidth, bannerHeight, null);
        
        g2.setFont(customFont);
        g2.setColor(fontColor);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 36F));
        int x = positionX + bannerWidth;
        int y = g2.getFontMetrics().getAscent() + bannerHeight / 2 - g2.getFontMetrics().getHeight() / 2 + positionY;
        g2.drawString(text, x, y);
        
    }
}
