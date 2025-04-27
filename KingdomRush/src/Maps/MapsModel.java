/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Maps;

/**
 *
 * @author richa
 */
public class MapsModel {
    protected static final String[][] model = {
        {"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" },
        {"0" ,"0" ,"0" ,"0" ,"14","14","14","14","14","14","14","14","0" ,"0" ,"0" ,"0" },
        {"0" ,"0" ,"0" ,"2" ,"3" ,"3" ,"3" ,"3" ,"3" ,"3" ,"3" ,"3" ,"4" ,"0" ,"0" ,"0" },
        {"14","14","14","9" ,"1" ,"1" ,"1" ,"1" ,"1" ,"1" ,"1" ,"1" ,"5" ,"14","14","14"},
        {"3" ,"3" ,"3" ,"12","1" ,"10","7" ,"7" ,"7" ,"7" ,"11","1" ,"13","3" ,"3" ,"3" },
        {"1" ,"1" ,"1" ,"1" ,"1" ,"5" ,"14","14","14","14","9" ,"1" ,"1" ,"1" ,"1" ,"1" },
        {"7" ,"7" ,"7" ,"7" ,"7" ,"6" ,"0" ,"0" ,"0" ,"0" ,"8" ,"7" ,"7" ,"7" ,"7" ,"7" },
        {"14","14","14","14","14","0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"14","14","14","14","14"},
        {"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" ,"0" }
    };
    public static boolean checkModel(int x, int y, String exp){
        if(model[y][x].equals(exp)){
            return true;
        }
        return false;
    }
}
