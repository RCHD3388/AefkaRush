/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import HighscoreData.ScoreData;
import Object.Tower;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author richa
 */
public class DataStorage implements Serializable{
    // PLAYER BASE
    int stage;
    int gold;
    int life;
    
    // PLAYER ITEM AND BUILD
    int[] item = new int[3];
    int[] build = new int[3];
    
    // LEADERBOARD - PRIORITY QUEUE
    ArrayList<ScoreData> scoredata = new ArrayList<>();
    
    // TOWER DATA
    ArrayList<String> towerdata = new ArrayList<>();
    
    // TOWER GETTER SETTER
    public void addTowerData(String command){
        towerdata.add(command);
    }
}
