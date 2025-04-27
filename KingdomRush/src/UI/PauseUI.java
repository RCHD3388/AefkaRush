/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;
import MainPackage.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
/**
 *
 * @author richardwei
 */
public class PauseUI extends UIBox{
    Graphics2D g2;
    protected BufferedImage image;
    protected Font customFont;
    protected Color fontColor;
    
    public PauseUI(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        setupBanner();
    }
    public void setupBanner(){
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/assets/pausebackground/pauseBackground.png"));
            InputStream inputstream = getClass().getResourceAsStream("/assets/font/LuckiestGuy-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
        }catch(IOException e){
            e.printStackTrace();
        }catch(FontFormatException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(customFont);
        fontColor = new Color(255,255,204);
        
        // SET BANNER WIDTH, HEIGHT & START POSITION
        bannerWidth = 7 * gamepanel.tileSize;
        bannerHeight = 2 * gamepanel.tileSize;
        
        PositionX = gamepanel.screenWidth / 2 - bannerWidth / 2;
        PositionY = gamepanel.screenHeight / 2 - bannerHeight / 2;
        // =========================================
        
        g2.drawImage(image, PositionX, PositionY, bannerWidth, bannerHeight, gamepanel);
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 96F));
        String text = "PAUSE";
        
        // SET TEXT POSITION
        int textPosX = getXforCenteredText(text);
        int textPosY = getYforCenteredText(text);
        
        g2.setColor(fontColor);
        g2.drawString(text, textPosX, textPosY);
    }
    
    public int getXforCenteredText(String text){
        // GET FONT LENGTH
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        // GENERATE X POSITION FOR CENTERED TEXT
        int x = gamepanel.screenWidth/2 - length/2;
        return x;
    }
    public int getYforCenteredText(String text){
        // GET FONT LENGTH
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getHeight(); 
        // GENERATE X POSITION FOR CENTERED TEXT
        int y = gamepanel.screenHeight/2 + 26;
        return y;
    }
}
