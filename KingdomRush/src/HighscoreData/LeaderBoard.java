/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HighscoreData;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author richa
 */
public class LeaderBoard {
    protected final int maxSize = 5;
    protected PriorityQueue<ScoreData> highscoreData;
    public LeaderBoard(){
        highscoreData = new PriorityQueue<>();
    }
    public void addNewData(int value){
        ScoreData newdata = new ScoreData(value);
        if(highscoreData.size() >= maxSize){
            int lastScore = highscoreData.peek().getScore();
            if(value > lastScore){
                highscoreData.poll();
                highscoreData.offer(newdata);
            }
        }else{
            highscoreData.offer(newdata);
        }
    }
    public ArrayList<ScoreData> getData(){
        PriorityQueue<ScoreData> helper = new PriorityQueue<>();
        ArrayList<ScoreData> datas = new ArrayList<>();
        while(!highscoreData.isEmpty()){
            ScoreData temp = highscoreData.poll();
            datas.add(temp);
            helper.add(temp);
        }
        highscoreData = helper;
        return datas;
    }
    public PriorityQueue<ScoreData> getScoreDatas(){
        return highscoreData;
    }
    public void setScoreData(PriorityQueue<ScoreData> scoredata){
        this.highscoreData = scoredata;
    }
}
