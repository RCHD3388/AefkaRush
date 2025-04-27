/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import MainPackage.GamePanel;
import Object.Player;
import Object.PlayerBuild;
import PlayerBase.Item;
import PlayerBase.PlayerBase;
import java.awt.*;
import static java.awt.SystemColor.text;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.spec.NamedParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author richa
 */
public class InformationBoxUI extends UIBox{
    protected int borderSize;
    protected Color backgroundColor;
    protected Color borderColor;
    protected Font customFont;
    protected Color fontColor;
    protected ArrayList<BufferedImage> animationCharacter = new ArrayList<>();
    protected ArrayList<PlayerBuild> builds = new ArrayList<>();
    protected ArrayList<Item> items = new ArrayList<>();
    protected Player player;
    protected PlayerBase playerbase;
    protected int animationIndex = 1;
    protected int animationCounter = 1;
    // SHOP BUTTON 
    protected int shopButtonX, shopButtonY, shopButtonHeight, shopButtonWidth; 
    
    protected int margin;
    
    public InformationBoxUI(GamePanel gamepanel, Player player, PlayerBase playerbase){
        this.player = player;
        this.builds = player.getBuilds();
        this.gamepanel = gamepanel;
        this.margin = gamepanel.tileSize;
        this.playerbase = playerbase;
        
        // SETUP BANNER HEIGHT, WIDTH, START POSITION & BORDER SIZE
        bannerHeight = gamepanel.tileSize;
        bannerWidth = gamepanel.screenWidth;
        PositionX = 0;
        PositionY = gamepanel.screenHeight - gamepanel.tileSize;
        borderSize = 6;
        // ==========================================
        // SET COLOR BACKGROUND & BORDER
        backgroundColor = new Color(51, 31, 16);
        borderColor = new Color(139, 69, 19);
        
        // SET FONT
        InputStream inputstream = getClass().getResourceAsStream("/assets/font/Merriweather-Regular.ttf");
        try { 
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
            fontColor = new Color(254,254,204);
        } catch (FontFormatException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InformationBoxUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void draw(Graphics2D g2){
        String stage = playerbase.getStage();
        ArrayList<Item> items= playerbase.getAllitems();
        g2.setColor(backgroundColor);
        g2.fillRect(PositionX, PositionY, bannerWidth, bannerHeight);
        
        g2.setStroke(new BasicStroke(borderSize));
        g2.setColor(borderColor);
        g2.drawRect(PositionX, PositionY, bannerWidth, bannerHeight);
        
        int x, y, textLength, height, width;
        
        // DRAW STAGE TEXT
        g2.setFont(customFont);
        g2.setColor(new Color(51, 31, 16));
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30));
        
        textLength = (int)g2.getFontMetrics().getStringBounds(stage, g2).getWidth();
        x = bannerWidth / 2 - textLength / 2;
        y = gamepanel.arenaScreenRow * gamepanel.tileSize - g2.getFontMetrics().getHeight() / 2;
        // y = Arena_height + bannerheight/2 - textheight/2;
        g2.drawString(stage, x, y);
        
        // === DRAW ITEMS ===
            height = bannerHeight - 2 * borderSize - 6;
            width = height;
            x = x + textLength + margin / 2;
            y = gamepanel.arenaScreenRow * gamepanel.tileSize + bannerHeight / 2 - height / 2;

            for(int i = 0; i < items.size(); i++){
                g2.drawImage(items.get(i).getLogo(), x, y, width, height, null);
                items.get(i).setButtonPosition(x, y, height, width);
                g2.setColor(new Color(0,0,0,100));
                g2.fillRect(x, y, width, playerbase.getCurUseItemCD() * height / playerbase.getUseItemCD());
                
                g2.setColor(fontColor);
                x += width + 2; // MARGIN 2 PX
                String itemText = "LV " + String.valueOf(items.get(i).getLevel()); // SET ITEM INFORMATION TEXT

                // === DRAW ITEM INFORMATION TEXT (LEVEL INFORMATION) === 
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16));
                // info arg : itemtext, x , y+fontascent
                g2.drawString(itemText, x, y + g2.getFontMetrics().getAscent());

                x += (int)g2.getFontMetrics().getStringBounds(itemText, g2).getWidth() + 10;
            }
        
        // === DRAW BUILD ===
            x = margin * 2 + margin / 2;
            for (int i = 0; i < builds.size(); i++) {
                g2.drawImage(builds.get(i).getImage(), x, y, width, height, null);
                
                x += width + 2;
                String buildText = "LV " + builds.get(i).getLevel();
                g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16));
                g2.drawString(buildText, x, y + g2.getFontMetrics().getAscent());
                
                buildText = player.getStatusByString(builds.get(i).getName());
                g2.drawString(buildText, x, (y + g2.getFontMetrics().getAscent() + g2.getFontMetrics().getHeight()));
                
                x += (int)g2.getFontMetrics().getStringBounds(buildText, g2).getWidth() + 10;
            }
        
        drawShopButton(g2);
        drawPlayerInformation(g2, player);
    }
    
    public void drawShopButton(Graphics2D g2){
        int button_margin = 4;
        String buttonText = "Upgrade Here";
//        BufferedImage buttonImage = ImageIO.read(getClass().getResourceAsStream("/assets/"));
        int textX, textY;
        
        shopButtonWidth = gamepanel.tileSize * 2;
        shopButtonX = bannerWidth - borderSize - shopButtonWidth - button_margin * 2;
        
        shopButtonHeight = bannerHeight - borderSize * 2 - button_margin * 4;
        shopButtonY = PositionY + bannerHeight / 2 - shopButtonHeight / 2;
        
        g2.setColor(new Color(222,139,43));
        g2.fillRect(shopButtonX, shopButtonY, shopButtonWidth, shopButtonHeight);
        
        g2.setStroke(new BasicStroke(4));
        g2.setColor(new Color(154,62,51));
        g2.drawRect(shopButtonX, shopButtonY, shopButtonWidth, shopButtonHeight);
        
        g2.setFont(customFont);
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18));
        
        textX = (int)g2.getFontMetrics().getStringBounds(buttonText, g2).getWidth();
        textX = shopButtonX + shopButtonWidth / 2 - textX / 2;
        
        textY = g2.getFontMetrics().getHeight();
        textY = shopButtonY + shopButtonHeight / 2 - textY / 2 + g2.getFontMetrics().getAscent();
        g2.drawString(buttonText, textX, textY);
    }
    
    public void drawPlayerInformation(Graphics2D g2, Player player){
        animationCharacter = player.getIdleAnimation();
        int x, y, height, width;
        int boxmargin = 2;
        height = bannerHeight - boxmargin * 2 - borderSize * 2;
        width = height;
        x = borderSize + boxmargin;
        y = gamepanel.arenaScreenRow * gamepanel.tileSize + borderSize + boxmargin;
        
        g2.setColor(new Color(222,139,43));
        g2.fillRect(x, y, width, height);

        int index = getAnimationIndex(); 
        
        y -= boxmargin * 4;
        x += boxmargin * 4;
        g2.drawImage(animationCharacter.get(index), x, y, width, height, null);
        
        // HEALTH BAR AND SHIELD
    }
    
    public boolean isUpgradeButtonPressed(int x, int y){
        if(x > shopButtonX && x < shopButtonX + shopButtonWidth){
            if(y > shopButtonY && y < shopButtonY + shopButtonHeight){
                return true;
            }
        }
        return false;
    }
    public int getAnimationIndex(){
        if(animationCounter > 10){
            if(animationIndex >= animationCharacter.size() - 1){
                animationIndex = 0;
            }else{
                animationIndex++;
            }
            animationCounter = 1;
        }else{
            animationCounter++;
        }
        
        return animationIndex;
    }
}
