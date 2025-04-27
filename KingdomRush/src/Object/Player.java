/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;
import MainPackage.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import java.util.*;

/**
 *
 * @author richa
 */
public class Player extends Character{
    private Random rand = new Random();
    protected int attackSpeed;
    protected int critDamage;
    // POSITION, IMAGE, DIRECTION
    KeyHandler keyhandler;
    protected ArrayList<BufferedImage> walkright = new ArrayList<>();
    protected ArrayList<BufferedImage> walkleft = new ArrayList<>();
    protected ArrayList<BufferedImage> idle = new ArrayList<>();
    protected ArrayList<BufferedImage> attack = new ArrayList<>();
    protected String direction;
    
    // ANIMATION INDEX & COUNTER
    protected int spriteIndex = 0;
    protected int spriteCounter = 0;
    
    protected ArrayList<PlayerBuild> builds = new ArrayList<>();
    
    protected boolean attacking;
  
    public Player(GamePanel gamepanel, KeyHandler keyhandler){
        this.gamepanel = gamepanel;
        this.keyhandler = keyhandler;
        alive = false;
        
        this.attackDamage = 25;
        this.attackSpeed = 10;
        this.critDamage = 150;
        setupDefaultPlayer();
        getPlayerImage();
    }
    private void setupDefaultPlayer(){
        attacking = false;
        posX = gamepanel.screenWidth / 2 - gamepanel.tileSize / 2;
        posY = gamepanel.screenHeight / 2 - gamepanel.tileSize / 2;
        height = gamepanel.tileSize;
        width = gamepanel.tileSize;
        characterSpeed = 4; 
        
        builds.add(new PlayerBuild("ATCK", 10, "/assets/effect/logo/sword.png", this));
        builds.add(new PlayerBuild("ASPD", 15, "/assets/effect/logo/food.png", this));
        builds.add(new PlayerBuild("CRIT", 10, "/assets/effect/logo/crit.png", this));
    }
    public void setBuildLevel(int index, int level){
        builds.get(index).setLevel(level);
    }
    public void getPlayerImage() {
        // defautl direction
        direction = "left";
        
        try{
            for(int x = 0; x < 6; x++){
                walkright.add(ImageIO.read(getClass().getResourceAsStream("/assets/player/walk"+(x+1)+".png")));
                walkleft.add(ImageIO.read(getClass().getResourceAsStream("/assets/player/walkleft"+(x+1)+".png")));
                idle.add(ImageIO.read(getClass().getResourceAsStream("/assets/player/idle"+(x+1)+".png")));
                attack.add(ImageIO.read(getClass().getResourceAsStream("/assets/player/attack"+(x+1)+".png")));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    // PLAYER MOVEMENT
    public void moveUp(){
        direction = "up";
        if(posY-characterSpeed >= 0){
            posY -= characterSpeed;
        }
    }
    public void moveDown(){
        direction = "down";
        if(posY+characterSpeed <= gamepanel.arenaScreenRow * gamepanel.tileSize - gamepanel.tileSize){ 
            posY += characterSpeed;
        }
    }
    public void moveLeft(){
        direction = "left";
        if(posX-characterSpeed >= 0){
            posX -= characterSpeed;
        }
    }
    public void moveRight(){
        direction = "right";
        if(posX+characterSpeed <= gamepanel.arenaScreenCol * gamepanel.tileSize - (gamepanel.tileSize * 2)){ // check if < 1280 (maps dimension) - 80 (tilesize)
            posX += characterSpeed;
        }
    }
    
    // UPDATE DRAW
    public void update(){
        if(keyhandler.up == true){
            moveUp();
        }else if(keyhandler.down == true){
            moveDown();
        }else if(keyhandler.left == true){
            moveLeft();
        }else if(keyhandler.right == true){
            moveRight();
        }else{
            direction = "idle";
        }
        
        spriteCounter++;
        if(attacking == false){
            if(spriteCounter > 5){
                spriteCounter = 0;
                spriteIndex++;
                if(spriteIndex == 5){
                    spriteIndex = 0;
                }
            }
        }else{
            if(spriteCounter > attackSpeed){
                spriteCounter = 0;
                spriteIndex++;
                if(spriteIndex == 5){
                    spriteIndex = 0;
                    attacking = false;
                }
            }
        }
    }
    
    // DRAW
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        
        if(attacking == true){
            image = attack.get(spriteIndex);
        }else{
            if(direction.equals("up")){
                image = walkright.get(spriteIndex);
            }else if(direction.equals("down")){
                image = walkright.get(spriteIndex);
            }else if(direction.equals("left")){
                image = walkleft.get(spriteIndex);
            }else if(direction.equals("right")){
                image = walkright.get(spriteIndex);
            }else if(direction.equals("idle")){
                image = idle.get(spriteIndex);
            }
        }
        g2.drawImage(image, posX, posY, width, height, null);
        
    }
    
    // GETTER SETTER
    public boolean isAttacking(){
        return attacking;
    }
    public void setAttack(EnemyManager enemymanager){
        attacking = true;
        spriteIndex = 0;
        if(rand.nextInt(100) < 30){
            enemymanager.checkAttackedArea(posX, posY, width, height, attackDamage * critDamage / 100);
        }else{
            enemymanager.checkAttackedArea(posX, posY, width, height, attackDamage);
        }
    }
    public ArrayList<BufferedImage> getIdleAnimation(){
        return idle;
    }
    public BufferedImage getIdleImage(){
        return idle.get(6);
    }
    public ArrayList<PlayerBuild> getBuilds(){
        return builds;
    }
    
    public String getStatusByString(String value){
        String result = value;
        if(value.equals("ATCK")){
            result += " " + String.valueOf(attackDamage);
        }else if(value.equals("ASPD")){
            result += " " + String.valueOf(attackSpeed);
        }else{
            result += " " + String.valueOf(critDamage);
        }
        
        return result;
    }
    
    // GETTER SETTER
    public int getAttackSpeed(){
        return attackSpeed;
    }
    public int getCritDamage(){
        return critDamage;
    }
    public int getAttack(){
        return attackDamage;
    }
    public void setAttackSpeed(int value){
        attackSpeed = value;
    }
    public void setCritDamage(int value){
        critDamage = value;
    }
    public void setAttack(int value){
        attackDamage = value;
    }
    public int getCenterPosX(){
        return posX + width/2;
    }
    public int getCenterPosY(){
        return posY + height/2;
    }
}
