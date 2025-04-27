/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import MainPackage.GamePanel;
import MainPackage.Sound;
import Maps.MapsModel;
import PlayerBase.PlayerBase;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import javax.imageio.ImageIO;

/**
 *
 * @author richa
 */
public class TowerManager extends Manager<Tower>{
    protected GamePanel gamepanel;
    protected PlayerBase playerbase;
    protected int towerPrice;
    protected static ArrayList<BufferedImage> images1;
    protected static ArrayList<BufferedImage> images2;
    protected static ArrayList<BufferedImage> images3;
    
    Sound sound = new Sound();
    
    public TowerManager(GamePanel gamepanel, PlayerBase playerbase){
        this.gamepanel = gamepanel;
        this.playerbase = playerbase;
        setupTowerImages();
        towerPrice = 150;
    }
    public void setupTowerImages(){
        images1 = new ArrayList<>();
        images2 = new ArrayList<>();
        images3 = new ArrayList<>();
        try{
            for(int x = 0; x < 11; x++){
                images1.add(ImageIO.read(getClass().getResource("/assets/tower/towerlevel1/tower" + String.valueOf(x+1) + ".png")));
                images2.add(ImageIO.read(getClass().getResource("/assets/tower/towerlevel2/tower" + String.valueOf(x+1) + ".png")));
                images3.add(ImageIO.read(getClass().getResource("/assets/tower/towerlevel3/tower" + String.valueOf(x+1) + ".png")));
            }
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void addNewObject(){
        int x = gamepanel.getPlayerCenterX() / gamepanel.tileSize;
        int y = gamepanel.getPlayerCenterY() / gamepanel.tileSize;
        String newkey = String.valueOf(x) + "$" + String.valueOf(y);
        if(MapsModel.checkModel(x, y, "14") && !objectList.containsKey(newkey)){
            if(playerbase.getCoin() >= towerPrice){
                x = x * gamepanel.tileSize;
                y = y * gamepanel.tileSize;
                objectList.put(newkey, new Tower(x, y, gamepanel.tileSize + gamepanel.tileSize / 2, gamepanel.tileSize));
                playerbase.setCoin(playerbase.getCoin() - towerPrice);
                playSE(3);
            }else{
                System.out.println("not enough coin");
            }
        }else{
            System.out.println("position not valid");
        }
    }
    
    public void update(){
        if(objectList.size() > 0){
            for(Map.Entry<String, Tower> entry : objectList.entrySet()){
                Tower temp = entry.getValue();
                temp.update(gamepanel.getEnemyManager());
            }
        }
    }
    public void draw(Graphics2D g2){
        if(objectList.size() > 0){
            for(Map.Entry<String, Tower> entry : objectList.entrySet()){
                Tower temp = entry.getValue();
                temp.draw(g2);
            }
        }
    }
    public void isTowerPressed(int x, int y){
        String key = String.valueOf(x/gamepanel.tileSize) + "$" + String.valueOf(y/gamepanel.tileSize);
        if(objectList.containsKey(key)){
            Tower temp = objectList.get(key);
            if(!temp.isMaxLevel()){
                if(playerbase.getCoin() >= temp.getUpgradeCost()){
                    playerbase.setCoin(playerbase.getCoin() - temp.getUpgradeCost());
                    temp.upgrade();
                }else{
                    System.out.println("not enough coin");
                }
            }
        }
    }
    
    //GETTER SETTER
    public HashMap<String, Tower> getTowers(){
        return objectList;
    }
    public void setTowers(String command){
        String[] splitvalue = command.split("\\$");
        int x = Integer.parseInt(splitvalue[0]);
        int y = Integer.parseInt(splitvalue[1]);
        int posX = x * gamepanel.tileSize;
        int posY = y * gamepanel.tileSize;
        int lvl = Integer.parseInt(splitvalue[2]);
        objectList.put((String.valueOf(x) + "$" + String.valueOf(y)), new Tower(posX, posY, 120, 80, lvl));
    }
    
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
