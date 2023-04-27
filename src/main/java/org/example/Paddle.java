package org.example;

import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {
    int xVelocity;
    final int speed = 10;
    Paddle(int x, int y, int paddleWidth, int paddleHeight){
        super(x, y, paddleWidth, paddleHeight);
    }
    public void KeyPressed(KeyEvent keyEvent){
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                setXDirection(-speed);
                move();
                break;
            case KeyEvent.VK_D:
                setXDirection(speed);
                move();
                break;
        }
    }
    public void KeyReleased(KeyEvent keyEvent){
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                setXDirection(0);
                move();
                break;
            case KeyEvent.VK_D:
                setXDirection(0);
                move();
                break;
        }
    }
    public void setXDirection(int XDirection){
        xVelocity = XDirection;
    }
    public void move(){
        x += xVelocity;
    }
    public void draw(Graphics g){
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}
