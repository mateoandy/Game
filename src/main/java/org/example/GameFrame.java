package org.example;

import java.awt.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class GameFrame extends JFrame{
    GamePanel gamePanel;
    File file = new File("src/main/resources/Wallpaper.wav");
    GameFrame(){
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        try{
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
