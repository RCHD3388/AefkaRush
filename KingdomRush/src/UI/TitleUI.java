/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;
import HighscoreData.HighscoreBanner;
import MainPackage.GamePanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 *
 * @author richardwei
 */
public class TitleUI {
    private int comNum; // MENU NUMBER
    protected GamePanel gamepanel;
    protected Graphics2D g2;
    protected Font customFont;
    protected Color fontColor;
    protected BufferedImage backgroundImage;
    protected BufferedImage bannerBackground;
    protected int bannerWidth,bannerHeight,screenX,screenY;
    
    protected HighscoreBanner HBanner;
    protected boolean onLeaderboard;
    
    public TitleUI (GamePanel gamepanel){
        this.gamepanel = gamepanel;
        HBanner = new HighscoreBanner(gamepanel);
        setupDefault();
    }
    public void setupDefault(){
        comNum = 0;
        bannerWidth = gamepanel.tileSize * 10;
        bannerHeight = gamepanel.tileSize * 6;
        screenX = gamepanel.screenWidth / 2 - bannerWidth / 2;
        screenY = gamepanel.screenHeight / 2 - bannerHeight / 2 - gamepanel.tileSize / 2;
        onLeaderboard = false;
        
        HBanner.setLeaderboardButton(screenX + bannerWidth - gamepanel.tileSize * 2, screenY + bannerHeight - gamepanel.tileSize * 2, gamepanel.tileSize);
        HBanner.setBackButton(screenX + gamepanel.tileSize, screenY + gamepanel.tileSize, gamepanel.tileSize);
        // SET FONT
        try{
            InputStream inputstream = getClass().getResourceAsStream("/assets/font/LuckiestGuy-Regular.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, inputstream);
        }catch(FontFormatException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        // ==================================================================================================
        
        // SET IMAGE
        try{
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/assets/titleframebackground/titleFrameBackground.png"));
            bannerBackground = ImageIO.read(getClass().getResourceAsStream("/assets/pausebackground/pauseBackground.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;
        g2.setFont(customFont);
        fontColor = new Color(255,255,204);
        
        g2.drawImage(backgroundImage, 0, 0, gamepanel.screenWidth, gamepanel.screenHeight, null);
        // ===============================
        g2.drawImage(bannerBackground, screenX, screenY, bannerWidth, bannerHeight, null);
        
        if(onLeaderboard == false){
            drawTitle(bannerHeight, bannerWidth, screenX, screenY);
            HBanner.drawLeaderboardButton(g2);
        }else{
            HBanner.drawExitButton(g2);
            HBanner.drawScoreBoard(g2);
        }
    }
    public void drawTitle(int bannerHeigth, int bannerWidth, int screenX, int screenY){
        
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 96F));
        String text = "AEFKA RUSH";
        
        // SET POSITION
        int positionX = getXforCenteredText(text);
        int positionY = screenY + gamepanel.tileSize + g2.getFontMetrics().getAscent();
        
        // SET COLOR
        g2.setColor(fontColor);
        
        // DRAW TITLE
        g2.drawString(text, positionX, positionY);
        
        // DRAW MENU TEXT
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN + Font.ITALIC, 48F));
        text = "NEW GAME";
        positionX = getXforCenteredText(text);
        positionY += gamepanel.tileSize; // add position y 
        g2.drawString(text, positionX, positionY);
        drawSideArrow(0,positionX,positionY);
        
        text = "LOAD GAME";
        positionX = getXforCenteredText(text);
        positionY += gamepanel.tileSize; // add position y 
        g2.drawString(text, positionX, positionY);
        drawSideArrow(1,positionX,positionY);
        
        text = "EXIT GAME";
        positionX = getXforCenteredText(text);
        positionY += gamepanel.tileSize; // add position y 
        g2.drawString(text, positionX, positionY);        
        drawSideArrow(2,positionX,positionY);
    }
    public void drawSideArrow(int index, int x, int y){
        if(comNum == index){
            g2.setColor(fontColor);
            g2.drawString(">", x - gamepanel.tileSize, y); // set text start X position - tilesize
        }
    }
    
    public int getXforCenteredText(String text){
        // GET FONT LENGTH
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        // GENERATE X POSITION FOR CENTERED TEXT
        int x = gamepanel.screenWidth/2 - length/2;
        return x;
    }
    // CHECKER
    public void isLeaderboardButtonPressed(int x, int y){
        if(HBanner.isButtonPressed(x,y)){
            onLeaderboard = true;
        }
    }
    public void isExitButtonzpressed(int x, int y){
        if(HBanner.isExitButtonPressed(x, y)){
            onLeaderboard = false;
        }
    }
    public boolean isOnLeaderboard(){
        return onLeaderboard;
    }
    
    // GETTER SETTER - comNum;
    public HighscoreBanner getHighscoreBanner(){
        return HBanner;
    }
    public void setNum(int value){
        this.comNum = value;
    }
    public int getNum(){
        return this.comNum;
    }
}