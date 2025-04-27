/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import MainPackage.GamePanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author richa
 */
public class Necremencer extends Enemy{
    

    public Necremencer(GamePanel gamepanel, int level) {
        super(gamepanel, level, 10, 170, 2);
        
        try {
            for(int x = 1; x <= 12; x++){
                animation.add(ImageIO.read(getClass().getResourceAsStream("/assets/enemy/necremencer/necremencer (" + String.valueOf(x) + ").png")));
            }
        } catch (IOException ex) {
            Logger.getLogger(Necremencer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void update(){
        spriteCounter++;
        if(spriteCounter > 10){
            spriteCounter = 0;
            spriteIndex++;
            if(spriteIndex >= animation.size()){
                spriteIndex = 0;
            }   
        }
        currMoveCooldown++;
        if(currMoveCooldown == moveCooldown){
            move();
            updatePosition();
            currMoveCooldown = 0;
        }
    }
    
    @Override
    public void draw(Graphics2D g2){   
        g2.drawImage(animation.get(spriteIndex), posX - 40, posY - 80, width*2, height*2, null);
        
        drawHealthBar(g2);
    }
}
