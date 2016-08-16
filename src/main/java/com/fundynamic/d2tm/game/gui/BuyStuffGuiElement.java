package com.fundynamic.d2tm.game.gui;

import com.fundynamic.d2tm.game.entities.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class BuyStuffGuiElement extends Rectangle {

    public BuyStuffGuiElement(int x, int y) {
        this(x, y, 320, 200);
    }

    public BuyStuffGuiElement(int x, int y, int width, int height) {
        super(x, y, x + width, y + height);
    }

    public int getX() {
        return topLeft.getXAsInt();
    }

    public int getY() {
        return topLeft.getYAsInt();
    }

    public int getMostRightXCoordinate() {
        return getX() + getWidthAsInt();
    }

    public int getMostDownYCoordinate() {
        return getY() + getHeightAsInt();
    }

    public void draw(Graphics graphics) {
        graphics.setColor(Color.gray);
        graphics.fillRect(getX(), topLeft.getYAsInt(), getWidthAsInt(), getHeightAsInt());

        graphics.setColor(Color.darkGray);
        graphics.drawRect(getX(), topLeft.getYAsInt(), getWidthAsInt(), getHeightAsInt());

        // title
        graphics.setAntiAlias(false);
        graphics.setColor(Color.white);
        graphics.drawString("Construction Yard", getX() + 1, topLeft.getYAsInt() + 2);

        graphics.setColor(Color.yellow);
        graphics.drawLine(getX(), getY() + 24, getMostRightXCoordinate(), getY() + 24);

        // Window content
    }
}
