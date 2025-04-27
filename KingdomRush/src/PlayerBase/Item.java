/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PlayerBase;

import MainPackage.Sound;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author richa
 */
public class Item {
    protected BufferedImage itemLogo; 
    protected BufferedImage[] animation;
    protected String itemName;
    protected int level;
    protected int cooldown;
    protected int curCD;
    protected int damage;
    protected int upgradeCost;
    protected int areaSize;
    protected int animationX, animationY;
    
    // COOLDOWN TIME
    protected int curTime;
    
    // BUTTON POSITION
    protected int x, y;
    protected int height, width;
    
    Sound sound = new Sound();
    
    public Item(String logoPath, String animationPath, String name, int damage, int upgradeCost, int areaSize){
        this.itemName = name;
        this.damage = damage;
        this.level = 1;
        this.upgradeCost = upgradeCost;
        this.curTime = 1;
        this.areaSize = areaSize;
        animation = new BufferedImage[10];
        
        try {
            // SET ITEM LOGO
            itemLogo = ImageIO.read(getClass().getResourceAsStream(logoPath));
            
            // SET ITEM ANIMATION
            for(int x = 0; x < 10; x++){
                String path = animationPath + String.valueOf((x+1)) + ".png";
                animation[x] = ImageIO.read(getClass().getResourceAsStream(path));
            }
        } catch (IOException ex) {
            Logger.getLogger(Item.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BufferedImage getLogo(){
        return itemLogo;
    }
    
    public void setButtonPosition(int x, int y, int height, int width){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    public int getDamage(){
        return level * damage;
    }
    public int getUpgradeCost(){
        return level * upgradeCost;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int getLevel(){
        return level;
    }
    public int getAreaSize(){
        return areaSize;
    }
    public int getAnimationStartPos(char type){
        if(type == 'x'){
            return animationX;
        }else{
            return animationY;
        }
    }
    public void setDefCD(){
        curCD = cooldown;
    }
    
    public BufferedImage getAnimation(int index){
        return animation[index];
    }
    public void levelUp(PlayerBase playerbase){
        if(playerbase.getCoin() < getUpgradeCost()){
            System.out.println("not enough coin");
        }else{
            playerbase.setCoin(playerbase.getCoin() - getUpgradeCost());
            level += 1;
        }
    }
    
    
    public boolean isPressed(int targetx, int targety){
        if(targetx >= x && targetx <= x + width){
            if(targety >= y && targety <= y + height){
                return true;
            }
        }
        return false;
    }
    public void setAnimationCoord(int x, int y){
        animationX = x;
        animationY = y;
        
        playSE(9);
    }
    
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
