package MainPackage;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author kelun
 */
public class Sound {
    Clip clip;
    URL soundURL []= new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/assets/sound/background_sound.wav");
        soundURL[1] = getClass().getResource("/assets/sound/hit_sound.wav");
        soundURL[2] = getClass().getResource("/assets/sound/pindah_sound.wav");
        soundURL[3] = getClass().getResource("/assets/sound/build_sound.wav");
        soundURL[4] = getClass().getResource("/assets/sound/buy_sound.wav");
        soundURL[5] = getClass().getResource("/assets/sound/atk_sound.wav");
        soundURL[6] = getClass().getResource("/assets/sound/tower_sound.wav");
        soundURL[7] = getClass().getResource("/assets/sound/game_sound.wav");
        soundURL[8] = getClass().getResource("/assets/sound/dead_sound.wav");
        soundURL[9] = getClass().getResource("/assets/sound/explosion_sound.wav");
        soundURL[10] = getClass().getResource("/assets/sound/mouse_sound.wav");
        soundURL[11] = getClass().getResource("/assets/sound/upgrade_sound.wav");
        
    }
   
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e){
            
        }
    }
    
    public void play(){
        clip.start();
    }
    
    public void loop(){
        clip.loop(clip.LOOP_CONTINUOUSLY);
    }
    
    public void stop(){
        clip.stop();
    }
}
