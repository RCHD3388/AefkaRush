/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;
import ButtonManager.ButtonManager;
import MainPackage.GamePanel;
import PlayerBase.PlayerBase;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author richardwei
 */
public class HomeMenuUI extends UIBox{
    protected PlayerBase playerbase;
    protected BufferedImage buttonLogo;
    protected BufferedImage bannerBackground;
    protected BufferedImage buttonImage;
    protected Font customFont;
    final int margin = 10;
    ArrayList<ButtonManager> buttonmanager = new ArrayList<>();
    
    int buttonSize, buttonPosX, buttonPosY;
    
    public HomeMenuUI(GamePanel gamepanel, PlayerBase playerbase){
        try {
            this.playerbase = playerbase;
            this.gamepanel = gamepanel;
            buttonLogo = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/homebutton.png"));
            bannerBackground = ImageIO.read(getClass().getResourceAsStream("/assets/pausebackground/pauseBackground.png"));
            buttonImage = ImageIO.read(getClass().getResourceAsStream("/assets/banner/banner.png"));
            InputStream inputstream = getClass().getResourceAsStream("/assets/font/Merriweather-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);

            buttonPosX = gamepanel.screenWidth - gamepanel.tileSize - margin;
            buttonPosY = 0 + margin;
            buttonSize = gamepanel.tileSize;
            buttonSize = gamepanel.tileSize;
        } catch (IOException ex) {
            Logger.getLogger(HomeMenuUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FontFormatException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void draw(Graphics2D g2){
        g2.drawImage(buttonLogo, buttonPosX, buttonPosY, buttonSize, buttonSize, null);
    }
    public void drawMenu(Graphics2D g2){
        bannerHeight = gamepanel.tileSize * 6;
        bannerWidth = gamepanel.tileSize * 7;
        PositionY = (gamepanel.arenaScreenRow * gamepanel.tileSize) / 2 - bannerHeight / 2;
        PositionX = gamepanel.screenWidth / 2 - bannerWidth / 2;
        
        g2.setColor(Color.white);
        g2.drawImage(bannerBackground, PositionX, PositionY, bannerWidth, bannerHeight, null);
        
        drawMenuButton(g2);
    }
    public void drawMenuButton(Graphics2D g2){
        int x, y, height, width;
        String text;
        
        width = bannerWidth - gamepanel.tileSize * 2;
        height = gamepanel.tileSize;
        x = PositionX + bannerWidth / 2 - width / 2;
        y = PositionY - height / 2;
        text = "HOME MENU";
        
        g2.drawImage(buttonImage, x, y, width, height, null);
        g2.setFont(customFont);
        g2.setColor(new Color(51, 31, 16));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24));
        x = x + width / 2 - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2;
        y = y + height / 2 - g2.getFontMetrics().getHeight() / 2 + g2.getFontMetrics().getAscent();
        
        g2.drawString(text, x, y);
        
        // DRAW BUTTON SAVE & EXIT, EXIT, CONTINUE
        int margin = gamepanel.tileSize / 4;
        
        height += 10;
        width -= gamepanel.tileSize;
        
        x = PositionX + bannerWidth / 2 - width / 2;
        y = PositionY + bannerHeight / 2 - height / 2 - height - margin;
        
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        
        drawButton(x, y, height, width, "CONTINUE", g2);
        y += margin + height;
        
        drawButton(x, y, height, width, "SAVE & EXIT", g2);
        y += margin + height;
        
        drawButton(x, y, height, width, "FINISH GAME", g2);
    }
    protected void drawButton(int x, int y, int height, int width, String text, Graphics2D g2){
        buttonmanager.add(new ButtonManager(x, y, height, width, playerbase));
        g2.drawImage(buttonImage, x, y, width, height, null);
        x = x + width / 2 - (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth() / 2;
        y = y + height / 2 - g2.getFontMetrics().getHeight() / 2 + g2.getFontMetrics().getAscent();
        
        
        g2.drawString(text, x, y);
    }
    
    public boolean isPressed(int x, int y){
        if(x >= buttonPosX && x < buttonPosX + buttonSize){
            if(y >= buttonPosY && y < buttonPosY + buttonSize){
                if(gamepanel.gameState == gamepanel.playState){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isBannerPressed(int x, int y){
        if(x >= PositionX && x < PositionX + bannerWidth){
            if(y >= PositionY && y < PositionY + bannerHeight){
                return true;
            }
        }
        return false;
    }
    public int isHomeMenuButtonPressed(int x, int y){
        for(int i = 0; i < buttonmanager.size(); i++){
            if(buttonmanager.get(i).isPressed(x, y)){
                return i;
            }
        }
        return -1;
    }
}
