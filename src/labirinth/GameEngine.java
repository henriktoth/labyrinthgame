package labirinth;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

enum FieldType{
    WALL, PATH, EXIT;
}
public class GameEngine extends JPanel implements KeyListener {
    private JLabel timerLabel;
    private JLabel levelLabel;
    private int timerCount = 0;
    
    private Player player;
    private Dragon dragon;
    private Field[][] level;

    private String playerName;
    private int currentLevel = 1;
    private boolean isGameOver = false;

    public GameEngine() {
        
        setBackground(Color.BLACK);
        setFocusable(true); 
        addKeyListener(this);
        
        timerLabel = new JLabel("Time: 0");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBounds(10, 10, 100, 30);
        add(timerLabel);

        levelLabel = new JLabel("Level: " + currentLevel);
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setBounds(120, 10, 100, 30);
        add(levelLabel);
        
        loadLevelFromFile("src/labirinth/1.txt");
        
        this.player = new Player(50, 500, 50, 50);
        this.dragon = new Dragon(level, 50, 50, this);

        Timer tickTimer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
                isGameOver = dragon.checkCollisionWithPlayer();
                if(isGameOver){
                    dragon.stopMoving();
                    JOptionPane.showMessageDialog(GameEngine.this, "Game Over");
                    System.exit(0);
                }
            }
        });
        tickTimer.start();

        Timer gameTime = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerCount++;
                timerLabel.setText("Time: " + timerCount);
            }
        });
        gameTime.start();
    }
    
    public Player getPlayer(){
        return this.player;
    }
    
    public boolean getIsGameOver(){
        return this.isGameOver;
    }
    
    /**
    * Requests players name in an external window.
    * @returns String
    */

    /**
    * Loads level from a txt file.
    * @param filename - The txt filename
    */
    private void loadLevelFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> lines = br.lines().collect(Collectors.toList());
            level = new Field[lines.size()][];
            for (int row = 0; row < lines.size(); row++) {
                String line = lines.get(row);
                String[] cells = line.split(" ");
                Field[] rowFields = new Field[cells.length];
                for (int col = 0; col < cells.length; col++) {
                    FieldType type = switch (cells[col]) {
                        case "W" -> FieldType.WALL;
                        case "P" -> FieldType.PATH;
                        case "E" -> FieldType.EXIT;
                        default -> throw new IllegalArgumentException("Unknown cell type");
                    };
                    rowFields[col] = new Field(col * 50, row * 50, type);
                }
                level[row] = rowFields;
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load level file!", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    /**
    * sets level counter +1, loads next level, sets dragon random position, sets player to starting pos.
    */
    public void advanceLevel() {
        currentLevel++;
        isGameOver = false;
        loadLevelFromFile("src/labirinth/" + currentLevel + ".txt");
        dragon.setLevel(level);
        player.x = 0;
        player.y = 500;
        dragon.spawnAtRandomPath();
        levelLabel.setText("Level: " + currentLevel);
    }
    
    /**
    * sets level counter 0, loads level 1, sets dragon random position, sets player starting pos.
    */
    public void restartGame() {
        currentLevel = 1;
        timerCount = 0;
        timerLabel.setText("Time: " + timerCount);
        isGameOver = false;
        loadLevelFromFile("src/labirinth/1.txt");
        dragon.setLevel(level);
        player.x = 50;
        player.y = 500;
        dragon.spawnAtRandomPath();
        levelLabel.setText("Level: " + currentLevel);
    }
    
    /**
    * paints the level, the player and the dragon
    * @param g - Graphics
    */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int playerRow = player.y / 50;
        int playerCol = player.x / 50;
        
        for (int row = 0; row < level.length; row++) {
            for (int col = 0; col < level[0].length; col++) {
                if (Math.abs(row - playerRow) <= 3 && Math.abs(col - playerCol) <= 3) {
                    level[row][col].draw(g);
                } else {
                    g.setColor(Color.BLACK);
                    g.fillRect(col * 50, row * 50, 50, 50);
                }
            }
        }
        player.draw(g); 
        dragon.draw(g);
    }
    
    /**
    * listens for wasd keys, to move player if it can move. Listenes for collision with dragon.
    * @param e - Key event
    */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int dx = 0, dy = 0;
        switch (keyCode) {
            case KeyEvent.VK_W -> dy = -50;
            case KeyEvent.VK_S -> dy = 50;
            case KeyEvent.VK_A -> dx = -50;
            case KeyEvent.VK_D -> dx = 50;
        }
        Rectangle nextPosition = new Rectangle(player.x + dx, player.y + dy, 50, 50);
        boolean canMove = true;
        
        for (Field[] row : level) {
            for (Field field : row) {
                if (field.getBounds().intersects(nextPosition)) {
                    if (field.getType() == FieldType.WALL) {
                        canMove = false;
                    } 
                    else if (field.getType() == FieldType.EXIT) {
                        if (currentLevel == 10) {
                            isGameOver = true;
                            dragon.stopMoving();
                            JOptionPane.showMessageDialog(this, "You win");
                            System.exit(0);
                        } else {
                            advanceLevel();
                        }
                    }
                }
            }
        }
        if (canMove){
            player.move(dx, dy);
            isGameOver = dragon.checkCollisionWithPlayer();
        }
        if (isGameOver){
            setFocusable(false);
            dragon.stopMoving();
            JOptionPane.showMessageDialog(this, "Game Over");
            System.exit(0);
        }
        else{
            repaint();
        }
    
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}
