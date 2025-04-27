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
public class GameOverUI extends UIBox{
    String text = "Game Over";
    BufferedImage backgroundImage;
    BufferedImage buttonImage;
    Font customFont;
    
    int buttonheight, buttonPosX;
    int buttonwidth, buttonPosY;
    
    public GameOverUI(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        InputStream inputstream = getClass().getResourceAsStream("/assets/font/Merriweather-Regular.ttf");
        try { 
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/pauseBackground/pauseBackground.png"));
            buttonImage = ImageIO.read(getClass().getResourceAsStream("/assets/banner/banner.png"));
        } catch (FontFormatException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        bannerHeight = gamepanel.tileSize * 2;
        bannerWidth = gamepanel.tileSize * 6;
        PositionX = gamepanel.screenWidth / 2- bannerWidth / 2;
        PositionY = gamepanel.arenaScreenRow * gamepanel.tileSize / 2 - bannerHeight / 2;
        
        buttonheight = gamepanel.tileSize;
        buttonwidth = bannerWidth - gamepanel.tileSize * 4;
        buttonPosY = PositionY + bannerHeight + gamepanel.tileSize / 8;
        buttonPosX = PositionX + bannerWidth / 2 - buttonwidth / 2;
    }
    public void draw(Graphics2D g2){
        g2.drawImage(backgroundImage, PositionX, PositionY, bannerWidth, bannerHeight, null);
        g2.setFont(customFont);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 64));
        
        int x = PositionX + bannerWidth / 2 - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2;
        int y = PositionY + bannerHeight / 2 - g2.getFontMetrics().getHeight() / 2 + g2.getFontMetrics().getAscent();
        
        g2.drawString(text, x, y);
        
        g2.drawImage(buttonImage, buttonPosX, buttonPosY, buttonwidth, buttonheight, null);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
        x = buttonPosX + buttonwidth / 2 - (int)g2.getFontMetrics().getStringBounds("EXIT", g2).getWidth() / 2;
        y = buttonPosY + buttonheight / 2 - g2.getFontMetrics().getHeight() / 2 + g2.getFontMetrics().getAscent();
        g2.drawString("EXIT", x, y);
    }
    public boolean isExitButtonPressed(int x, int y){
        if(x >= buttonPosX && x < buttonPosX + buttonwidth){
            if(y >= buttonPosY && y < buttonPosY + buttonheight){
                return true;
            }
        }
        return false;
    }
}
