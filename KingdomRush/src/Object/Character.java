/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import MainPackage.GamePanel;

public abstract class Character extends Object{
    protected GamePanel gamepanel;
    protected int characterSpeed;
    
    // CHARACTER STATUS
    protected boolean alive;
    protected int attackDamage; // ATTACK
}
