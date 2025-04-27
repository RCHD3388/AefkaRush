/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author richa
 */
public class Projectile extends Object{
    protected int speed = 6;
    protected int directionCode;
    protected int damage;
    protected int stepNumber;
    protected BufferedImage image;
    
    public Projectile(int x, int y, int width, int height, BufferedImage image, int direction, int damage){
        this.posX = x - width / 2;
        this.posY = y - height / 2;
        this.width = width;
        this.height = height;
        this.image = image;
        this.directionCode = direction;
        this.damage = damage;
        stepNumber = 0;
    }
    public void update(){
        if(directionCode == 1){
            posY -= speed;
        }else if(directionCode == 2){
            posY += speed;
        }else if(directionCode == 3){
            posX -= speed;
        }else if(directionCode == 4){
            posX += speed;
        }
        stepNumber++;
    }
    public void draw(Graphics2D g2){
//        g2.drawRect(posX, posY, width, height);
        g2.drawImage(image, posX, posY, width, height, null);
    }
    public boolean outOfRange(int range){
        if(stepNumber * speed >= range){
            return true;
        }
        return false;
    }
    public boolean isHitEnemy(EnemyManager EManager){
        if(EManager.checkAttackedArea(posX, posY, width, height, damage)){
            return true;
        }
        return false;
    }
}
