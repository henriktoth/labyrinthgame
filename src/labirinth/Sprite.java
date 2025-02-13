/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labirinth;


import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
/**
 *
 * @author henriktoth0517
 */
public class Sprite {
    protected int x, y, width, height;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
    * moves the sprite from x y coordinate to x+dx and y+dy
    * @param dx - x coordinate difference
    * @param dy - y coordinate difference
    */
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    /**
    * draws the sprite
    * @param g - Graphics
    */
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, y, width, height);
    }
    
    /**
    * gets bounds and creates a new rectangle with x y coordinate and width height.
    * @returns Rectangle
    */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
