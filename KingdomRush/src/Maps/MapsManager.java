/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Maps;

import MainPackage.GamePanel;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author richardwei
 */
public class MapsManager {
    String[][] mapsmodel;
    GamePanel gamepanel;
    Tile[] tileStyle; // save all tile style
    
    public MapsManager(GamePanel gamepanel){
        this.gamepanel = gamepanel;
        tileStyle = new Tile[15];
        getTileImage();
        mapsmodel = MapsModel.model; // get MapsModel 
    }
    public void getTileImage(){
        try{
            tileStyle[0] = new Tile();
            tileStyle[1] = new Tile();
            tileStyle[2] = new Tile();
            tileStyle[3] = new Tile();
            tileStyle[4] = new Tile();
            tileStyle[5] = new Tile();
            tileStyle[6] = new Tile();
            tileStyle[7] = new Tile();
            tileStyle[8] = new Tile();
            tileStyle[9] = new Tile();
            tileStyle[10] = new Tile();
            tileStyle[11] = new Tile();
            tileStyle[12] = new Tile();
            tileStyle[13] = new Tile();
            tileStyle[14] = new Tile();
            tileStyle[0].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/grass.png"));
            tileStyle[1].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/sand.png"));
            tileStyle[2].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/cornerTopLeft.png"));
            tileStyle[3].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/top.png"));
            tileStyle[4].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/cornerTopRight.png"));
            tileStyle[5].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/right.png"));
            tileStyle[6].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/cornerBottomRight.png"));
            tileStyle[7].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/bottom.png"));
            tileStyle[8].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/cornerBottomLeft.png"));
            tileStyle[9].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/left.png"));
            tileStyle[10].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/angle1.png"));
            tileStyle[11].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/angle2.png"));
            tileStyle[12].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/angle3.png"));
            tileStyle[13].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/angle4.png"));
            tileStyle[14].image = ImageIO.read(getClass().getResourceAsStream("/assets/mapstile/buildgrass.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        int tileSize = gamepanel.tileSize;
        for(int y = 0; y < gamepanel.arenaScreenRow; y++){
            for(int x = 0; x < gamepanel.arenaScreenCol; x++){
                int indexTileStyle = Integer.parseInt(mapsmodel[y][x]);
                g2.drawImage(tileStyle[indexTileStyle].image, x*tileSize, y*tileSize, tileSize, tileSize, gamepanel);
            }                       
        }
    }
    public String getMapsString(int x, int y){
        return mapsmodel[y][x];
    }
}
