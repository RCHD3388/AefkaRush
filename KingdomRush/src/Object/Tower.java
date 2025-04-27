/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import MainPackage.GamePanel;
import MainPackage.Sound;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author richa
 */
public class Tower extends Object implements Serializable{
    // STATUS
    protected Integer level;
    protected final Integer maxLevel = 3;
    protected Integer upgradeCost;
    protected Integer damage;
    // SPECIAL SHOOT
    protected Integer specialShootCooldown;
    protected Integer specialShootCounter;
    protected Boolean isSpecialShootAvailable;
    protected Boolean usingSpecialShoot;
    // HOT CONDITION & RANGE
    protected Boolean shootable;
    protected Integer maxRange = 5 * 60;
    //COOLDOWN SHOOT
    protected Integer cooldownShoot = 60;
    protected Integer currCooldown;
    // SHOT DIRECTION
    protected String shootDirection = "4";
    // MAGAZINE
    protected ArrayList<Projectile> magazine;
    // TOWER ANIMATION
    protected Integer animationCooldown = 10;
    protected Integer currAnimationCD;
    protected final Integer maxAnimationIndex = 11;
    protected Integer currAnimationIndex;
    
    Sound sound = new Sound();
    
    public Tower(int x, int y, int height, int width){
        setupNewTower(x,y,height,width);
        setupDefault();
    }
    public Tower(int x, int y, int height, int width, int level){
        setupNewTower(x, y, height, width);
        setupDefault();
        this.level = level;
    }
    public void setupDefault(){
        magazine = new ArrayList<>();
        shootable = false;
        currAnimationCD = 1;
        currAnimationIndex = 0;
        currCooldown = 1;
        level = 1;
        upgradeCost = 500;
        damage = 50;
        
        specialShootCooldown = 5;
        specialShootCounter = 1;
        isSpecialShootAvailable = false;
        usingSpecialShoot = false;
    }
    public void setupNewTower(int x, int y, int height, int width){
        posX = x;
        posY = y;
        this.height = height;
        this.width = width;
    }
    
    public void update(EnemyManager enemymanager) {
        // UPDATE ANIMATION 
        currAnimationCD++;
        if(currAnimationCD == animationCooldown){
            currAnimationIndex++;
            if(currAnimationIndex == maxAnimationIndex){
                currAnimationIndex = 0;
            }
            currAnimationCD = 1;
        }
        // ADD NEW MAGAZINE 
        checkShootableArea(enemymanager);
        if(shootable){
            currCooldown++;
            if(currCooldown >= cooldownShoot){
                if(isSpecialShootAvailable){
                    specialShootCounter++;
                    if(specialShootCounter >= specialShootCooldown){
                        usingSpecialShoot = true;
                        specialShootCounter = 1;
                    }
                }
                addMagazine(shootDirection);
                currCooldown = 1;
            }
        }
        // UPDATE MAGAZINE POSITION
        for(int x = 0; x < magazine.size(); x++){
            Projectile currMagazine = magazine.get(x);
            currMagazine.update();
            while(magazine.size() > x && (currMagazine.outOfRange(maxRange) || currMagazine.isHitEnemy(enemymanager))){
                magazine.remove(x);
            }
        }
    }
    public void checkShootableArea(EnemyManager EManager){
        if(EManager.checkAttackedArea(posX-maxRange, posY, maxRange, height, 0)){
            shootable = true;
            shootDirection = "3";
        }else if(EManager.checkAttackedArea(posX, posY-maxRange, width, maxRange, 0)){
            shootable = true;
            shootDirection = "1";
        }else if(EManager.checkAttackedArea(posX, posY, maxRange, height, 0)){
            shootable = true;
            shootDirection = "4";
        }else if(EManager.checkAttackedArea(posX, posY, width, maxRange, 0)){
            shootable = true;
            shootDirection = "2";
        }else{
            shootable = false;
        }
    }
    public void addMagazine(String code){
        int x,y,h,w;
        x = posX + width / 2;
        y = posY + height / 4;
        if(code.equals("1") || code.equals("2")){
            w = 40;
            h = 60;
        }else{
            w = 60;
            h = 40;
        }
        BufferedImage magazineImage = null;
        try {
            if(usingSpecialShoot){
                magazineImage = ImageIO.read(getClass().getResource("/assets/tower/specialmagazine_"+code+".png"));
            }else{
                magazineImage = ImageIO.read(getClass().getResource("/assets/tower/magazine"+code+".png"));
            }
        } catch (IOException ex) { ex.printStackTrace(); }
        
        Projectile newmagazine;
        if(usingSpecialShoot == true){
            newmagazine = new Projectile(x, y, w, h, magazineImage, Integer.parseInt(code), damage*2);
            usingSpecialShoot = false;
        }else{
            newmagazine = new Projectile(x, y, w, h, magazineImage, Integer.parseInt(code), damage);
        }
        magazine.add(newmagazine);
        
        playSE(6);
    }
    
    public void draw(Graphics2D g2){
        g2.drawImage(getImage(), posX, posY - height / 3 - 10, width, height, null);
        // draw magazine
        for(int x = 0; x < magazine.size(); x++){
            magazine.get(x).draw(g2);
        }
    }
    public BufferedImage getImage(){
        if(level == 1){
            return TowerManager.images1.get(currAnimationIndex);
        }else if(level == 2){
            return TowerManager.images2.get(currAnimationIndex);
        }else {
            return TowerManager.images3.get(currAnimationIndex);
        }
    }
    
    public boolean isMaxLevel(){
        return (level == maxLevel);
    }
    public void upgrade(){
        this.level++;
        this.cooldownShoot -= 10;
        this.damage += 30;
        this.upgradeCost += 500;
        if(level > 1){
            isSpecialShootAvailable = true;
        }
    }
    
    // GETTER SETTER

    public int getPosX() {
        return posX;
    }
    public void setPosX(int posX) {
        this.posX = posX;
    }
    public int getPosY() {
        return posY;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getAnimationIndex(){
        return currAnimationIndex;
    }
    public int getLevel(){
        return level;
    }
    public int getUpgradeCost(){
        return upgradeCost;
    }
    
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
