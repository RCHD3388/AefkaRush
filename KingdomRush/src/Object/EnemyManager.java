package Object;

import MainPackage.GamePanel;
import PlayerBase.PlayerBase;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author richa
 */
public class EnemyManager extends Manager<Enemy>{
    public int numsOfEnemy;
    protected int enemyCounter; // total enemy perstage 
    protected int index = 0;
    protected PlayerBase playerbase;
    protected GamePanel gamepanel;
    protected int spawnCounter;
    protected int spawnCD; //  spawn cooldown 
    
    protected final int enemyLevelUpCounter = 3;
    
    public EnemyManager(GamePanel gamepanel, PlayerBase playerbase){
        this.gamepanel = gamepanel;
        this.playerbase = playerbase;
        this.numsOfEnemy = 4;
        this.enemyCounter = numsOfEnemy;
        this.spawnCD = 80;
        this.spawnCounter = 1;
    }
    public int getEnemyLevel(){
        int level = playerbase.getStageNumber() / enemyLevelUpCounter + 1;
        return level;
    }
    public void addNewObject(){
        if(enemyCounter % 2 == 0){
            objectList.put(String.valueOf(index), new Necremencer(gamepanel, getEnemyLevel()));
        }else{
            objectList.put(String.valueOf(index), new Nightborne(gamepanel, getEnemyLevel()));
        }
        index++;
    }
    public void update(){
        if(enemyCounter >= 1){
            spawnCounter++;
            if(spawnCounter >= spawnCD){
                addNewObject();
                enemyCounter--;
                spawnCounter = 1;
            }
        }
        ArrayList<String> keysaver = new ArrayList<>();
        for (Map.Entry<String, Enemy> entry : objectList.entrySet()) {
            Enemy enemy = entry.getValue();
            enemy.update();
            if(enemy.posX + enemy.width <= 0){
                playerbase.minLive();
                keysaver.add(entry.getKey());
            }
        }
        for(int x = 0; x < keysaver.size(); x++){
            objectList.remove(keysaver.get(x));
        }
        if(objectList.size() == 0){
            isEnemyCounterEnd();
        }
    }
    public void draw(Graphics2D g2){
        for (Map.Entry<String, Enemy> entry : objectList.entrySet()) {
            entry.getValue().draw(g2);
        }
    }
    public boolean checkAttackedArea(int x, int y, int width, int height, int damage){
        boolean isDamaged = false;
        ArrayList<String> deadEnemyNumber = new ArrayList<>();
        for (Map.Entry<String, Enemy> entry : objectList.entrySet()) {
            Enemy enemy = entry.getValue();
            if(enemy.isAttacked(x, y, width, height, damage)){
                isDamaged = true;
            }
            if(enemy.isDead()){
                deadEnemyNumber.add(entry.getKey());
            }
        }
        for(int i = 0; i < deadEnemyNumber.size(); i++){
            objectList.remove(deadEnemyNumber.get(i));
        }
        if(objectList.isEmpty()){
            isEnemyCounterEnd();
        }
        return isDamaged;
    }
    public void isEnemyCounterEnd(){
        if(enemyCounter == 0 && objectList.isEmpty()){
            setUpNewStage();
        }
    }
    public void generateNumsOfEnemy(){
        numsOfEnemy = 4 + gamepanel.getPlayerBase().getStageNumber() * 2;
        enemyCounter = numsOfEnemy;
    }
    public void setUpNewStage(){
        playerbase.setNextStage();
        generateNumsOfEnemy();
        spawnCounter = 1;
        index = 0;
    }
}