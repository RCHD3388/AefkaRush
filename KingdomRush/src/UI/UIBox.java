/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

import MainPackage.GamePanel;
import java.awt.Graphics2D;

/**
 *
 * @author richa
 */
public abstract class UIBox {
    protected GamePanel gamepanel;
    protected int bannerHeight;
    protected int bannerWidth;
    protected int PositionX;
    protected int PositionY;
    
    public abstract void draw(Graphics2D g2);
}
