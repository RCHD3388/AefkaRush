/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

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
public class StartButtonUI extends UIBox{
    protected BufferedImage banner;
    
    public StartButtonUI(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        try {
            banner = ImageIO.read(getClass().getResourceAsStream("/assets/banner/banner.png"));
        } catch (IOException ex) {
            Logger.getLogger(StartButtonUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bannerHeight = gamepanel.tileSize;
        bannerWidth = gamepanel.tileSize * 3;
        PositionX = gamepanel.screenWidth / 2 - bannerWidth / 2;
        PositionY = gamepanel.tileSize / 8;
    }
    public void draw(Graphics2D g2){
        g2.drawImage(banner, PositionX, PositionY, bannerWidth, bannerHeight, null);
        InputStream inputstream = getClass().getResourceAsStream("/assets/font/Merriweather-Regular.ttf");
        try { 
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
            
            g2.setFont(customFont);
            g2.setColor(Color.black);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
            
            int x = PositionX + bannerWidth / 2 - (int)g2.getFontMetrics().getStringBounds("Start Stage", g2).getWidth() / 2;
            int y = PositionY + g2.getFontMetrics().getAscent() + bannerHeight / 2 - g2.getFontMetrics().getHeight() / 2;
            
            g2.drawString("Start Stage", x, y);
        } catch (FontFormatException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isPressed(int x, int y){
        if(x >= PositionX && x <= PositionX + bannerWidth){
            if(y >= PositionY && y <= PositionY + bannerHeight){
                return true;
            }
        }
        return false;
    }
}
