/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package labirinth;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author henriktoth0517
 */
public class LabirinthGUI {
    private JFrame frame;
    private GameEngine gameArea;
    
    public LabirinthGUI(){        
        frame = new JFrame("Labirinth");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        gameArea = new GameEngine();
        frame.getContentPane().add(gameArea);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("Start New Game");
        newGameItem.addActionListener(e ->{
            gameArea.restartGame();
        });
        gameMenu.add(newGameItem);
        
        menuBar.add(gameMenu);
        frame.setJMenuBar(menuBar);
        
        frame.setPreferredSize(new Dimension(800, 650));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
    
}
