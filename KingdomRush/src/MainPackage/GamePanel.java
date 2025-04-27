/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;
import Data.SaveLoad;
import HighscoreData.LeaderBoard;
import HighscoreData.ScoreData;
import PlayerBase.PlayerBase;
import Maps.MapsManager;
import Object.EnemyManager;
import Object.Player;
import Object.TowerManager;
import PlayerBase.Shop;
import UI.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
/**
 *
 * @author richa
 */
public class GamePanel extends JPanel implements Runnable{
    final int originalTile = 20;
    final int scale = 4;
    public final int tileSize = originalTile * scale;
    
    public final int arenaScreenCol = 16;
    public final int arenaScreenRow = 9;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    
    public final int screenWidth = tileSize * maxScreenCol; // 1280 px
    public final int screenHeight = tileSize * maxScreenRow; // 720 px
    
    // FPS
    int fps = 60;
    // GAME SYSTEM
    SaveLoad saveload = new SaveLoad(this);
    Graphics2D g2;
    Player player;
    EnemyManager enemymanager;
    TowerManager towermanager;
    MapsManager mapsmanager = new MapsManager(this);
    //  KEY AND MOUSE HANDLER
    KeyHandler keyhandler = new KeyHandler(this);
    MouseHandler mousehandler = new MouseHandler(this);
    // UI 
    PauseUI pauseUI = new PauseUI(this);
    TitleUI titleUI = new TitleUI(this);
    StartButtonUI startButtonUI = new StartButtonUI(this);
    GameOverUI gameoverUI = new GameOverUI(this);
    HomeMenuUI homemenuUI;
    InformationBoxUI informationBoxUI;
    LeaderBoard leaderboard = new LeaderBoard();
    Shop shop;
    // SOUND 
    Sound sound = new Sound();
    
    PlayerBase playerbase;
    
    Thread gameThread;
    
    // GAME MECHANICS
    public int gameState;
    public int homeState = 3;
    public int pauseState = 2;
    public int playState = 1;
    public int titleState = 0;
    public boolean shopStateOn = false;
    public boolean onStage = false;
    public boolean gameOver = false;
    public boolean mouseTarget = false;
    
    // MOUSE POSITION HANDLER
    protected int mouseX, mouseY;
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.WHITE);
        this.setDoubleBuffered(true);
        
        // add keyhandler to this panel & focusable to focused to receive keyinputs
        this.addKeyListener(keyhandler);
        this.addMouseListener(mousehandler);
        this.addMouseMotionListener(mousehandler);
        this.setFocusable(true);
        load();
        
        playMusic(0);
    }
    // setup default game
    public void load(){
        leaderboard.setScoreData(saveload.loadScoreBoard());
    }
    public void setupDefault(){
        shop = new Shop(this, playerbase);
        informationBoxUI = new InformationBoxUI(this, player, playerbase);
        homemenuUI = new HomeMenuUI(this, playerbase);
        
        enemymanager = new EnemyManager(this, playerbase);
        
        gameState = titleState;
    }
    public void startNewGame(Game game){
        this.player = game.player;
        this.playerbase = game.playerbase;
        this.towermanager = game.towermanager;
        setupDefault();
        
        stopMusic();
        playMusic(7);
    }
    
    // gamethread & run method
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run(){
        long drawInterval = 1000 / fps; // interval 1 seconds 60 frames (1000 milisceonds / 60)
        long nextDrawTime = System.currentTimeMillis()+ drawInterval;
                
        while(gameThread != null){
            update();
            repaint();
            
            // SET FPS
            try {
                long remainingTime = nextDrawTime - System.currentTimeMillis(); //
                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep(remainingTime);
                nextDrawTime += drawInterval; // set nextdrawtime plus drawinterval
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // UPDATE
    public void playStateUpdate(){
        player.update();
        playerbase.update();
        if(onStage == true){
            enemymanager.update();
        }
    }
    public void update(){
        if(gameState == playState && gameOver == false){
            playStateUpdate();
            towermanager.update();
        }
    }
    
    // PAINT COMPONENT
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D)g;
        // draw components
        if(gameState == titleState){
            titleUI.draw(g2);
        }else{
            mapsmanager.draw(g2);
            if(mouseTarget == true){
                drawMouseTarget();
            }
            towermanager.draw(g2);
            player.draw(g2);
            if(onStage == true){
                enemymanager.draw(g2);
            }
            homemenuUI.draw(g2);
            playerbase.draw(g2);
            informationBoxUI.draw(g2);
            if(onStage == false){
                startButtonUI.draw(g2);
            }
            if(shopStateOn == true){
                shop.draw(g2);
            }
            if(gameState == pauseState){
                pauseUI.draw(g2);
            }else if(gameState == homeState){
                homemenuUI.drawMenu(g2);
            }
            if(gameOver == true ){
                gameoverUI.draw(g2);
                stopMusic();
            }
            g2.dispose();
        }
    }
    public void drawMouseTarget(){
        try {
            g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/assets/enemy/target.png")),
                    mouseX - tileSize / 2, mouseY - tileSize / 2, tileSize, tileSize, null);
        } catch (IOException ex) {
            Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void targetingArea(int x, int y){
        if(x >= 0 && x <= arenaScreenCol * tileSize){
            if(y >= 0 && y <= arenaScreenRow * tileSize){
                mouseTarget = false;
                playerbase.useItem(x, y);
            }
        }
    }
    // MAPS GETTER
    public boolean isValidMapMove(int x, int y){
        if(mapsmanager.getMapsString(x, y).equals("1")){
            return true;
        }
        return false;
    }
    
    // GETTER SETTER - TITLEFRAME NUMBER
    public void setTitleFrameSelectedMode(int value){
        titleUI.setNum(value);
    }
    public int getTitleFrameCurrentMode(){
        return titleUI.getNum();
    }
    
    // GETTER SETTER - HOMEMENU & UPGRADEBUTTON
    public boolean isHomeMenuPressed(int x, int y){
        return homemenuUI.isPressed(x,y);
    }
    public boolean isUpgradeButtonPressed(int x, int y){
        return informationBoxUI.isUpgradeButtonPressed(x, y);
    }
    public boolean shopBoxPressed(int x, int y){
        return shop.boxPressed(x,y);
    }
    // GETTER SETTER 
    public EnemyManager getEnemyManager(){
        return enemymanager;
    }
    public PlayerBase getPlayerBase(){
        return playerbase;
    }
    public TowerManager getTowerManager(){
        return towermanager;
    }
    public int getPlayerCenterX(){
        return player.getCenterPosX();
    }
    public int getPlayerCenterY(){
        return player.getCenterPosY();
    }
    
    // RESET STAGE CONDITION
    public void resetCondition(){
        gameState = titleState;
        shopStateOn = false;
        onStage = false;
        gameOver = false;
        
        stopMusic();
        playMusic(0);
    }
    public LeaderBoard getLeaderBoard(){
        return leaderboard;
    }
    
    // music
    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    
    public void stopMusic(){
        sound.stop();
    }
    
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
