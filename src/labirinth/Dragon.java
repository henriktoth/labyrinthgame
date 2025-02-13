/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labirinth;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author henriktoth0517
 */
public class Dragon extends Sprite{
    private Field[][] level;
    private Random random;
    private GameEngine gameEngine;
    Timer timer = new Timer(true);
    private int dx = 0;
    private int dy = 0;
    
    public Dragon(Field[][] level, int width, int height, GameEngine gameEngine){
        super(0, 0, width, height);
        this.level = level;
        this.random = new Random();
        this.gameEngine = gameEngine;
        spawnAtRandomPath();
        chooseNewDirection();
        startMoving();
    }
    public void setLevel(Field[][] level){
        this.level = level;
    }
    /**
    * spawns the dragon at a random point in the map (only if it is a path)
    */
    public void spawnAtRandomPath(){
        while(true){
            int row = random.nextInt(level.length);
            int col = random.nextInt(level[0].length);
            if (level[row][col].getType() == FieldType.PATH){
                int playerRow = gameEngine.getPlayer().y / 50;
                int playerCol = gameEngine.getPlayer().x / 50;
                if (Math.abs(row - playerRow) >= 4 || Math.abs(col - playerCol) >= 4){
                    this.x = col * 50;
                    this.y = row * 50;
                    break;
                }
            }
        }
    }
    /**
    * in case dragon cannot advance, it chooses a different direction.
    */
    private void chooseNewDirection() {
        int[][] directions = {{-50, 0}, {50, 0}, {0, -50}, {0, 50}};
        while (true) {
            int[] direction = directions[random.nextInt(directions.length)];
            int newX = x + direction[0];
            int newY = y + direction[1];

            Rectangle nextPosition = new Rectangle(newX, newY, width, height);
            boolean canMove = true;

            for (Field[] row : level) {
                for (Field cell : row) {
                    if (cell.getBounds().intersects(nextPosition) && cell.getType() == FieldType.WALL) {
                        canMove = false;
                    }
                }
            }

            if (canMove) {
                dx = direction[0];
                dy = direction[1];
                break;
            }
        }
    }
    
    /**
    * every 250ms the dragon moves.
    */
    private void startMoving(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameEngine.getIsGameOver() == false){
                    moveDragon();
                }
            }
        }, 250, 250);
    }
    public void stopMoving() {
        timer.cancel();
    }
    /**
     * Stops the dragon from moving.
     * moves the dragon from position to (dx,dy) set by the dragon objects parameters.
     */
    private void moveDragon() {
        int newX = x + dx;
        int newY = y + dy;

        Rectangle nextPosition = new Rectangle(newX, newY, width, height);
        boolean canMove = true;

        for (Field[] row : level) {
            for (Field cell : row) {
                if (cell.getBounds().intersects(nextPosition) && cell.getType() == FieldType.WALL ||
                    cell.getBounds().intersects(nextPosition) && cell.getType() == FieldType.EXIT)
                {
                    canMove = false;
                }
            }
        }

        if (canMove) {
            x = newX;
            y = newY;
        } else {
            chooseNewDirection();
        }
    }
    
    /**
    * 
    * checks if the dragon is colliding with the player
    * @returns boolean
    */
    public boolean checkCollisionWithPlayer(){
        int dx = Math.abs(x - gameEngine.getPlayer().x);
        int dy = Math.abs(y - gameEngine.getPlayer().y);
        if (dx == 50 && dy == 0 || dx == 0 && dy == 50) {
            return true;
            
        }
        return false;
    }
    /**
    * draws the dragon
    * @param g - Graphics
    */
    @Override
    public void draw(Graphics g) {
        int playerRow = gameEngine.getPlayer().y / 50;
        int playerCol = gameEngine.getPlayer().x / 50;
        int dragonRow = y / 50;
        int dragonCol = x / 50;

        if (Math.abs(playerRow - dragonRow) <= 3 && Math.abs(playerCol - dragonCol) <= 3) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, width, height);
        }
    }
}
