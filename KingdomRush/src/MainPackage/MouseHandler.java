/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import Data.SaveLoad;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.*;

/**
 *
 * @author richardwei
 */
public class MouseHandler implements MouseListener, MouseMotionListener{
    GamePanel gamepanel;
    SaveLoad saveLoad;
    Sound sound = new Sound();
    
    public MouseHandler(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        saveLoad = new SaveLoad(gamepanel);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mousePositionX = e.getX();
        int mousePositionY = e.getY();
        playSE(10);
        
        if(gamepanel.gameState == gamepanel.playState){
            if(gamepanel.gameOver == true){
                if(gamepanel.gameoverUI.isExitButtonPressed(mousePositionX, mousePositionY)){
                    gamepanel.getLeaderBoard().addNewData(gamepanel.playerbase.generateScore());
                    setNewGame();
                }
            }else if(gamepanel.shopStateOn == false){
                if(gamepanel.onStage == false){
                    if(gamepanel.startButtonUI.isPressed(mousePositionX, mousePositionY)){
                        gamepanel.onStage = true;
                    }
                }else{
                    if(gamepanel.playerbase.isReadyUseItem()){
                        int indexitem = gamepanel.playerbase.isItemClicked(mousePositionX, mousePositionY);
                        if(indexitem != -1){
                            System.out.println("item click");
                            gamepanel.playerbase.setOnUseItemIndex(indexitem);
                            gamepanel.mouseTarget = true;
                        }
                    }
                }
                if(gamepanel.mouseTarget == true){
                    gamepanel.targetingArea(mousePositionX, mousePositionY);
                }
                if(gamepanel.isHomeMenuPressed(mousePositionX, mousePositionY)){
                    gamepanel.gameState = gamepanel.homeState;
                }else if(gamepanel.isUpgradeButtonPressed(mousePositionX, mousePositionY)){
                    gamepanel.shopStateOn = true;
                }
                gamepanel.towermanager.isTowerPressed(mousePositionX, mousePositionY);
            }else{
                if(!gamepanel.shopBoxPressed(mousePositionX, mousePositionY)){
                    gamepanel.shopStateOn = false;
                }else{
                    gamepanel.shop.pressedButton(mousePositionX, mousePositionY);
                }
            }
        }else if(gamepanel.gameState == gamepanel.homeState){
            if(!gamepanel.homemenuUI.isBannerPressed(mousePositionX, mousePositionY)){
                gamepanel.gameState = gamepanel.playState;
            }else{
                int index = gamepanel.homemenuUI.isHomeMenuButtonPressed(mousePositionX, mousePositionY);
                if(index == 0){
                    gamepanel.gameState = gamepanel.playState;
                }else if(index == 1){
                    saveLoad.save();
                    setNewGame();
                }else if(index == 2){
                    gamepanel.getLeaderBoard().addNewData(gamepanel.playerbase.generateScore());
                    setNewGame();
                }
            }
        }else if(gamepanel.gameState == gamepanel.titleState){
            if(!gamepanel.titleUI.isOnLeaderboard()){
                gamepanel.titleUI.isLeaderboardButtonPressed(mousePositionX, mousePositionY);
            }else{
                gamepanel.titleUI.isExitButtonzpressed(mousePositionX, mousePositionY);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    public void setNewGame(){
        gamepanel.resetCondition();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamepanel.mouseX = e.getX();
        gamepanel.mouseY = e.getY();
    }
    
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
