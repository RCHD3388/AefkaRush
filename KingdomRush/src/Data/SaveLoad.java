/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import HighscoreData.ScoreData;
import MainPackage.GamePanel;
import Object.Tower;
import PlayerBase.PlayerBase;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author richa
 */
public class SaveLoad {
    GamePanel gamepanel;
    DataStorage data;
    
    public SaveLoad(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        data = new DataStorage();
    }
    public void save(){
        
        // TOWER SAVE
        HashMap<String, Tower> towerlist = gamepanel.getTowerManager().getTowers();
        for (Map.Entry<String, Tower> en : towerlist.entrySet()) {
            Object key = en.getKey();
            Tower val = en.getValue();
            data.addTowerData(key + "$" + val.getLevel());
        }
        // PLAYERBASE & PLAYER ITEM AND BUILD
        PlayerBase playerbase = gamepanel.getPlayerBase();
        data.gold = playerbase.getCoin();
        data.life = playerbase.getLife();
        data.stage = playerbase.getStageNumber();
        for(int x = 0; x < 3; x++){
            data.build[x] = playerbase.getPlayer().getBuilds().get(x).getLevel();
            System.out.print(data.build[x] + "build ");
            data.item[x] = playerbase.getAllitems().get(x).getLevel();
            System.out.print(data.item[x] + "item ");
        }
        
        try {
            FileOutputStream fileOut = new FileOutputStream("SaveFile.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
            System.out.println("Saved");
        } catch (Exception e) {
            System.out.println("save failed");
            e.printStackTrace();
        }
    }
    public void saveScoreBoard(PriorityQueue<ScoreData> newscoredata){
        ArrayList<ScoreData> helper = new ArrayList<>();
        while(!newscoredata.isEmpty()){
            helper.add(newscoredata.poll());
        }
        data.scoredata = helper;
        try {
            FileOutputStream fileOut = new FileOutputStream("score.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(data);
            out.close();
            fileOut.close();
            System.out.println("Saved");
        } catch (Exception e) {
            System.out.println("save failed");
            e.printStackTrace();
        }
    }
    public boolean load(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("SaveFile.dat")));
            data = (DataStorage)in.readObject();
            in.close();
            System.out.println("Load Successful");
        }catch(Exception e){
            System.out.println("belum terdapat simpanan apapun");
            return false;
        }
        
        // TOWER DATA
        ArrayList<String> towertemp = data.towerdata;
        for(int x = 0; x < towertemp.size(); x++){
            gamepanel.getTowerManager().setTowers(towertemp.get(x));
        }
        
        // PLAYERBASE & PLAYER ITEM & BUILD
        PlayerBase playerbase = gamepanel.getPlayerBase();
        playerbase.setCoin(data.gold);
        playerbase.setLife(data.life);
        playerbase.setStage(data.stage);
        gamepanel.getEnemyManager().generateNumsOfEnemy();
        for(int x = 0; x < 3; x++){
            playerbase.setItemLevel(x, data.item[x]);
            playerbase.getPlayer().setBuildLevel(x, data.build[x]);
        }
        return true;
    }
    public PriorityQueue<ScoreData> loadScoreBoard(){
        try{
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("score.dat")));
            data = (DataStorage)in.readObject();
            in.close();
            System.out.println("Load Successful");
        }catch(Exception e){
            System.out.println("belum terdapat simpanan apapun");
        }
        System.out.println(data.scoredata.size());
        PriorityQueue<ScoreData> helper = new PriorityQueue<>();
        for(int x = 0; x < data.scoredata.size(); x++){
            helper.offer(data.scoredata.get(x));
        }
        ScoreData.setStaticNumber(helper.size() + 1);
        return helper;
    }
}
