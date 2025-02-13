/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labirinth;
import java.awt.Graphics;
import java.awt.Color;
/**
 *
 * @author henriktoth0517
 */
public class Player extends Sprite {
    
    public Player(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
    * draws the sprite
    * @param g - Graphics
    */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
    
    /**
    * draws the sprite
    * @param i - position for x coordinate
    * @param j - position for y coordinate
    */
    public void setPosition(int i, int j) {
        x = i;
        y = j;
    }
}
