package ButtonManager;

import PlayerBase.PlayerBase;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author richa
 */
public class ButtonManager {
    protected int posx, posy, height, width;
    protected String name;
    protected PlayerBase playerbase;

    public ButtonManager(int x, int y, int height, int width, PlayerBase playerbase) {
        this.posx = x;
        this.posy = y;
        this.height = height;
        this.width = width;
        this.playerbase = playerbase;
    }
    public boolean isPressed(int x, int y){
        if(x >= posx && x <= posx + width){
            if(y >= posy && y <= posy + height){
                upItemLevel();
                return true;
            }
        }
        return false;
    }
    public void upItemLevel(){}
}
