/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;
import Object.*;
import PlayerBase.*;

/**
 *
 * @author richa
 */
public class Game {
    protected int score;
    Player player;
    PlayerBase playerbase;
    TowerManager towermanager;
    
    public Game(GamePanel gamepanel, KeyHandler keyhandler){
        player = new Player(gamepanel, keyhandler);
        playerbase = new PlayerBase(gamepanel, player);
        towermanager = new TowerManager(gamepanel, playerbase);
    }
}
