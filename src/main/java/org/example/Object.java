package org.example;

import java.awt.*;

public class Object extends Rectangle {
    int id;
    Object(int id, int x, int y, int objectWidth, int objectHeight){
        super(x, y, objectWidth, objectHeight);
        this.id = id;
    }
    public void draw(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
