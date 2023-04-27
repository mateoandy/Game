package org.example;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable{
    final int GAME_WIDTH = 1200;
    final int GAME_HEIGHT = 600;
    final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    final int BALL_DIAMETER = 20;
    final int PADDLE_WIDTH = 100;
    final int PADDLE_HEIGHT = 20;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Paddle paddle;
    Ball ball;
    final int OBJECT_WIDTH = 25;
    final int OBJECT_HEIGHT = 25;
    Collection<Object> objectCollection = new ArrayList<>();

    GamePanel(){
        newPaddle();
        newBall();
        newObjects();
        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }
    public void newPaddle() {
        paddle = new Paddle(GAME_WIDTH / 2 - PADDLE_WIDTH / 2, GAME_HEIGHT - PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
    }
    public void newBall() {
        ball = new Ball(GAME_WIDTH / 2 - BALL_DIAMETER / 2, GAME_HEIGHT / 2 - BALL_DIAMETER / 2, BALL_DIAMETER, BALL_DIAMETER);
    }
    public void newObjects() {
        int id = 1;
        for(int x = 200; x < 1000; x+=35)
            for(int y = 50; y < 150; y+=35) {
                objectCollection.add(new Object(id, x, y, OBJECT_WIDTH, OBJECT_HEIGHT));
                id++;
            }
    }
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }
    public void draw(Graphics g){
        paddle.draw(g);
        ball.draw(g);
        objectCollection.stream().forEach(x -> x.draw(g));
    }
    public void move(){
        paddle.move();
        ball.move();
    }
    public void checkCollision() {
        if (paddle.x <= 0)
            paddle.x = 0;
        else if (paddle.x + PADDLE_WIDTH >= GAME_WIDTH)
            paddle.x = GAME_WIDTH - PADDLE_WIDTH;

        if (ball.x <= 0)
            ball.setXDirection(-ball.xVelocity);
        else if (ball.x + BALL_DIAMETER >= GAME_WIDTH)
            ball.setXDirection(-ball.xVelocity);
        if (ball.y <= 0)
            ball.setYDirection(-ball.yVelocity);
        else if (ball.y + BALL_DIAMETER >= GAME_HEIGHT) {
            newPaddle();
            newBall();
            newObjects();
        }

        if (ball.intersects(paddle))
            ball.setYDirection(-ball.yVelocity);

        objectCollection = objectCollection.stream().filter(e -> !e.intersects(ball)).collect(Collectors.toCollection(ArrayList::new));
    }
    public boolean checkIfWin(){
        if(objectCollection.isEmpty()) {
            newPaddle();
            newBall();
            newObjects();
            return true;
        }
        else
            return false;
    }

    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if(delta >=1) {
                move();
                checkCollision();
                repaint();
                checkIfWin();
                delta--;
            }
        }
    }
    public class ActionListener extends KeyAdapter{
        public void keyPressed(KeyEvent keyEvent){
                paddle.KeyPressed(keyEvent);
        }
        public void keyReleased(KeyEvent keyEvent){
            paddle.KeyReleased(keyEvent);
        }
    }
}
