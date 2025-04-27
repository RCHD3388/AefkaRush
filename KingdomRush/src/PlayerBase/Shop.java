/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlayerBase;

import ButtonManager.*;
import MainPackage.GamePanel;
import Object.PlayerBuild;
import UI.InformationBoxUI;
import java.awt.BasicStroke;
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
 * @author richa
 */
public class Shop {
    GamePanel gamepanel;
    PlayerBase playerbase;
    
    protected int positionX, positionY;
    protected int bannerHeight, bannerWidth;
    protected final int borderSize = 8;
    protected Font customFont;
    protected Color fontColor;
    protected BufferedImage shopBannerBackground;
    ArrayList<ItemsButtonManager> itemManager = new ArrayList<>();
    ArrayList<BuildButtonManager> buildManager = new ArrayList<>();
    
    //ITEMS AND BUILD
    ArrayList<Item> items;
    ArrayList<PlayerBuild> builds;
    
    public Shop(GamePanel gamepanel, PlayerBase playerbase){
        this.gamepanel = gamepanel;
        this.playerbase = playerbase;
        items = playerbase.getAllitems();
        builds = playerbase.player.getBuilds();
        
        // SETUP BANNER HEIGHT, WIDTH & POSITION X Y
        bannerHeight = gamepanel.screenHeight / 2;
        bannerWidth = bannerHeight + gamepanel.tileSize / 2;
        positionX = gamepanel.screenWidth / 2 - bannerWidth / 2;
        positionY = gamepanel.screenHeight / 2 - bannerHeight / 2;
        
        // SETUP FONT
        InputStream inputstream = getClass().getResourceAsStream("/assets/font/Merriweather-Regular.ttf");
        
        try { 
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
            fontColor = new Color(0,0,0);
            shopBannerBackground = ImageIO.read(getClass().getResourceAsStream("/assets/pauseBackground/pauseBackground.png"));
        } catch (FontFormatException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(shopBannerBackground, positionX, positionY, bannerWidth, bannerHeight, null);
        // DRAW HEADER
        drawHeader(g2);
        // DRAWCONTENT
        drawContent(g2);
    }
    public void drawHeader(Graphics2D g2){
        BufferedImage image = null;
        
        try {
            image  = ImageIO.read(getClass().getResourceAsStream("/assets/banner/banner.png"));
        } catch (IOException ex) {
            Logger.getLogger(Shop.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // SETUP WIDTH HEIGHT X Y 
        int width = bannerWidth - gamepanel.tileSize;
        int height = gamepanel.tileSize;
        int y = positionY - height / 2;
        int x = positionX + bannerWidth / 2 - width / 2;
        
        g2.drawImage(image, x, y, width, height, null);
        
        String headerText = "UPGRADE ITEMS & BUILD";
        g2.setFont(customFont);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20));
        y = y + height / 2 - g2.getFontMetrics().getHeight() / 2 + g2.getFontMetrics().getAscent();
        x = x + width / 2 - (int)g2.getFontMetrics().getStringBounds(headerText, g2).getWidth() / 2;
        g2.drawString(headerText, x, y);
    }
    public void drawContent(Graphics2D g2){
        String buttonText;
        int x,y,height,width, buttonHeight, buttonWidth, buttonX = 0, buttonY = 0;
        int boxSize = 60;
        int marginx = 42;
        int marginy = 16;
        int borderSize = 2;
        
        // SETUP AND DRAW CHARACTER SKILL UPGRADE BOX
        height = gamepanel.tileSize;
        width = gamepanel.tileSize;
        x = positionX + bannerWidth / 2 - width / 2 - width - marginx;
        y = positionY + borderSize + gamepanel.tileSize / 4 * 3;
        
        buttonWidth = width + width / 4;
        buttonHeight = 30;
        g2.setFont(customFont);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16));
        
        for(int i = 0; i < items.size(); i++){
            g2.setStroke(new BasicStroke(borderSize));
            g2.drawImage(items.get(i).getLogo(), x, y, width, height, null);
            g2.setColor(Color.white);
            g2.drawRect(x, y, width, height);
            
            // DRAW UPGRADE TEXT BUTTON
            buttonY = y + height + marginy / 2;
            buttonX = (x + (width / 2)) - buttonWidth / 2;
            
            g2.setColor(new Color(51, 31, 16));
            g2.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(buttonX, buttonY, buttonWidth, buttonHeight);
            
            itemManager.add(new ItemsButtonManager(buttonX, buttonY, buttonHeight, buttonWidth, items.get(i), playerbase));
            buttonText = ("UP " + String.valueOf(items.get(i).getUpgradeCost()));
            
            g2.setColor(Color.white);
            buttonY = buttonY + (buttonHeight / 2 - g2.getFontMetrics().getHeight() / 2) + g2.getFontMetrics().getAscent();
            buttonX = buttonX + buttonWidth / 2 - (int)g2.getFontMetrics().getStringBounds(buttonText, g2).getWidth() / 2;
            g2.drawString(buttonText, buttonX, buttonY);
            
            x += width + marginx;
        }
        
        // BUILDS
        x = positionX + bannerWidth / 2 - width / 2 - width - marginx;
        y = buttonY + buttonHeight + marginy;
        for(int i = 0; i < builds.size(); i++){
            g2.setStroke(new BasicStroke(borderSize));
            g2.drawImage(builds.get(i).getImage(), x, y, width, height, null);
            g2.setColor(Color.white);
            g2.drawRect(x, y, width, height);
            
            buttonY = y + height + marginy / 2;
            buttonX = (x + (width / 2)) - buttonWidth / 2;
            buttonText = ("UP " + String.valueOf(builds.get(i).getUpgradeCost()));
            
            g2.setColor(new Color(51, 31, 16));
            g2.fillRect(buttonX, buttonY, buttonWidth, buttonHeight);
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(buttonX, buttonY, buttonWidth, buttonHeight);
            buildManager.add(new BuildButtonManager(buttonX, buttonY, buttonHeight, buttonWidth, builds.get(i), playerbase));
            g2.setColor(Color.white);
            buttonY = buttonY + (buttonHeight / 2 - g2.getFontMetrics().getHeight() / 2) + g2.getFontMetrics().getAscent();
            buttonX = buttonX + buttonWidth / 2 - (int)g2.getFontMetrics().getStringBounds(buttonText, g2).getWidth() / 2;
            g2.drawString(buttonText, buttonX, buttonY);
            x += width + marginx;
        }
    }
    
    public boolean boxPressed(int x, int y){
        if(x > positionX && x < positionX + bannerWidth){
            if(y > positionY && y < positionY + bannerHeight){
                return true;
            }
        }
        return false;
    }
    
    public void pressedButton(int x, int y){
        for(int i = 0; i < 3; i++){
            itemManager.get(i).isPressed(x, y);
            buildManager.get(i).isPressed(x, y);
        }
    }
}
