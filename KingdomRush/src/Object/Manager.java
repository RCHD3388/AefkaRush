/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import java.awt.Graphics2D;
import java.util.HashMap;

/**
 *
 * @author richa
 */
public abstract class Manager <T extends Object>{
    HashMap<String, T> objectList;
    public Manager(){
        objectList = new HashMap<>();
    }
    abstract void update();
    abstract void draw(Graphics2D g2);
    abstract void addNewObject();
}
