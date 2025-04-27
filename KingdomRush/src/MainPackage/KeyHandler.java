/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

import Data.SaveLoad;
import HighscoreData.ScoreData;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.PriorityQueue;

/**
 *
 * @author richa
 */
public class KeyHandler implements KeyListener{
    GamePanel gamepanel;
    SaveLoad saveLoad;
    public boolean up, down, left, right;
    Sound sound = new Sound();
    public KeyHandler(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        saveLoad = new SaveLoad(gamepanel);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        if(gamepanel.gameState == gamepanel.titleState){
            if(code == KeyEvent.VK_UP){
                if(gamepanel.getTitleFrameCurrentMode() - 1 >= 0){
                    gamepanel.setTitleFrameSelectedMode(gamepanel.getTitleFrameCurrentMode() - 1);
                    playSE(2);
                }
            }else if(code == KeyEvent.VK_DOWN){
                if(gamepanel.getTitleFrameCurrentMode() + 1 < 3){
                    gamepanel.setTitleFrameSelectedMode(gamepanel.getTitleFrameCurrentMode() + 1);
                    playSE(2);
                }
            }else if(code == KeyEvent.VK_ENTER){
                int currentMode = gamepanel.getTitleFrameCurrentMode();
                if(currentMode == 0){
                    gamepanel.startNewGame(new Game(gamepanel, this));
                    gamepanel.gameState = gamepanel.playState;
                }else if(currentMode == 1){
                    gamepanel.startNewGame(new Game(gamepanel, this));
                    if(saveLoad.load()){
                        gamepanel.gameState = gamepanel.playState;
                    }
                }else if(currentMode == 2){
                    saveLoad.saveScoreBoard(gamepanel.getLeaderBoard().getScoreDatas());
                    System.exit(0);
                }
            }
        }else{
            // PLAY STATE KEY HANDLER
            if(code == KeyEvent.VK_W){
                up = true;
            }else if(code == KeyEvent.VK_S){
                down = true;
            }else if(code == KeyEvent.VK_A){
                left = true;
            }else if(code == KeyEvent.VK_D){
                right = true;
            }else if(code == KeyEvent.VK_E){
                if(!gamepanel.player.isAttacking()){
                    gamepanel.player.setAttack(gamepanel.enemymanager);
                    playSE(5);
                }
            }else if(code == KeyEvent.VK_SPACE){
                if(gamepanel.gameState == gamepanel.playState){
                    gamepanel.gameState = gamepanel.pauseState;
                }else if(gamepanel.gameState == gamepanel.pauseState){
                    gamepanel.gameState = gamepanel.playState;
                }
            }else if(code == KeyEvent.VK_B){
                gamepanel.towermanager.addNewObject();
                System.out.println(gamepanel.towermanager.getTowers().size());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            up = false;
        }else if(code == KeyEvent.VK_S){
            down = false;
        }else if(code == KeyEvent.VK_A){
            left = false;
        }else if(code == KeyEvent.VK_D){
            right = false;
        }
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
