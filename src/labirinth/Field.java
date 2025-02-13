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
public class Field {
    private int x, y;
    private FieldType type;
    
    public Field(int x, int y, FieldType type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    public FieldType getType(){
        return type;
    }
    /**
    * draws the field
    * @param gp - Graphics
    */
    public void draw(Graphics gp){
       switch (type) {
           case WALL -> gp.setColor(Color.gray);
           case PATH -> gp.setColor(Color.darkGray);
           case EXIT -> gp.setColor(Color.GREEN);
       } 
       gp.fillRect(x, y, 50, 50);
       gp.drawRect(x, y, 50, 50);
    }
    
    /**
    * gets bounds and creates a new rectangle with x y coordinate and width height set to 50
    * @returns 
    */
    public Rectangle getBounds(){
        return new Rectangle(x, y, 50, 50);
    }
}
