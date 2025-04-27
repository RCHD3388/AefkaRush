/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import MainPackage.GamePanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author richa
 */
public abstract class Enemy extends Character{
    protected int level;
    protected int characterMaxHp, characterHp; // HP
    protected int boxX, boxY, boxSize;
    protected ArrayList<BufferedImage> animation = new ArrayList<>();
    String direction;
    int spriteCounter;
    int spriteIndex;
    int moveCooldown = 3;
    int currMoveCooldown = 1;
    
    public Enemy(GamePanel gamepanel, int level, int attack, int hp, int speed){
        this.gamepanel = gamepanel;
        this.posX = gamepanel.screenWidth - gamepanel.tileSize;
        this.posY = gamepanel.tileSize * 5;
        this.height = gamepanel.tileSize;
        this.width = gamepanel.tileSize;
        setCollisionBox();
        this.direction = "left";
        this.level = level;
        
        spriteIndex = 0;
        spriteCounter = 1;
        
//        this.attackDamage = attack + (level * 2);
        this.characterSpeed = speed;
        
        this.characterMaxHp = hp + (level * 50);
        this.characterHp = this.characterMaxHp;
    }
    public void setCollisionBox(){
        int centerX = posX + width/2;
        int centerY = posY + height/2;
        boxSize = gamepanel.tileSize / 2;
        boxX = centerX - boxSize / 2;
        boxY = centerY - boxSize / 2;
    }
    public abstract void update();
    public void updatePosition(){
        int x = posX / gamepanel.tileSize;
        int y = posY / gamepanel.tileSize;
        if(x >= 0 && y >= 0 && x <= gamepanel.maxScreenCol && y <= gamepanel.maxScreenRow){
            if(direction == "left"){
                if(!gamepanel.isValidMapMove(x, y)){
                    posX = (posX / gamepanel.tileSize + 1) * gamepanel.tileSize;
                    if(gamepanel.isValidMapMove(x+1, y-1)){
                        direction = "up";
                    }else{
                        direction = "down";
                    }
                }
            }else if(direction == "up"){
                if(!gamepanel.isValidMapMove(x, y)){
                    posY = (posY / gamepanel.tileSize + 1) * gamepanel.tileSize;
                    if(gamepanel.isValidMapMove(x-1, y+1)){
                        direction = "left";
                    }else{
                        direction = "right";
                    }
                }
            }else if(direction == "down"){
                if(!gamepanel.isValidMapMove(x, y + 1)){
                    posY = (posY / gamepanel.tileSize) * gamepanel.tileSize;
                    if(gamepanel.isValidMapMove(x-1, y)){
                        direction = "left";
                    }else{
                        direction = "right";
                    }
                }
            }
        }
    }
    public void move(){
        if(direction.equals("up")){
            posY -= characterSpeed;
            boxY -= characterSpeed;
        }else if(direction.equals("left")){
            posX -= characterSpeed;
            boxX -= characterSpeed;
        }else if(direction.equals("down")){
            posY += characterSpeed;
            boxY += characterSpeed;
        }else if(direction.equals("right")){
            posX += characterSpeed;
            boxX += characterSpeed;
        }
    }
    public abstract void draw(Graphics2D g2);
    public void drawHealthBar(Graphics2D g2){
        int hp_width = width - 40;
        int hp_height = 6;
        int hp_y = posY - hp_height;
        int hp_x = posX + 20;
        
        g2.setColor(Color.white);
        g2.fillRect(hp_x, hp_y, hp_width, hp_height);
        
        g2.setColor(Color.red);
        g2.fillRect(hp_x, hp_y, characterHp * hp_width / characterMaxHp, hp_height);
        
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.black);
        g2.drawRect(hp_x, hp_y, hp_width, hp_height);
    }
    
    public boolean isDead(){
        if(characterHp == 0){
            return true;
        }
        return false;
    }
    public boolean isAttacked(int x, int y, int width, int height, int damage){
        if(boxX >= x && boxX <= x + width && boxY >= y && boxY <= y + height || 
            boxX + boxSize >= x && boxX + boxSize <= x + width && boxY + boxSize >= y && boxY + boxSize <= y + height ||
            boxX >= x && boxX <= x + width && boxY + boxSize >= y && boxY + boxSize <= y + height || 
            boxX + boxSize >= x && boxX + boxSize <= x + width && boxY >= y && boxY <= y + height){
                Damaged(damage);
                return true;
        }
        return false;
    }
    public void Damaged(int damage){
        if(characterHp - damage < 0){
            characterHp = 0;
        }else{
            characterHp -= damage;
        }
    }
}
