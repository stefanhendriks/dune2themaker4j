package com.fundynamic.d2tm.game.gui;

import com.fundynamic.d2tm.game.behaviors.Renderable;
import com.fundynamic.d2tm.game.behaviors.Updateable;
import com.fundynamic.d2tm.game.controls.Mouse;
import com.fundynamic.d2tm.game.entities.Rectangle;
import com.fundynamic.d2tm.game.rendering.RenderQueue;
import com.fundynamic.d2tm.graphics.ImageRepository;
import com.fundynamic.d2tm.math.Vector2D;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import java.util.ArrayList;
import java.util.List;

public class BuyStuffGuiElement implements Renderable, Updateable {

    private Rectangle rectangle;

    private BuyIconList buyIconList = new BuyIconList();

    private final int widthOfIcon;
    private final int heightOfIcon;

    private Mouse mouse;

    public BuyStuffGuiElement(int x, int y, Mouse mouse) {
        this.mouse = mouse;
        // TODO: Do this properly, because a getInstance is a very ugly way to get this dependency here.
        ImageRepository instance = ImageRepository.getInstance();
        List<Image> images = new ArrayList<>();
        images.add(instance.loadAndCache("ui/icons/icon_1slab.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_4slab.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_barracks.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_heavyfactory.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_hightech.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_ix.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_lightfactory.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_palace.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_radar.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_refinery.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_repair.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_rturret.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_silo.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_starport.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_turret.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_wall.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_windtrap.bmp"));
        images.add(instance.loadAndCache("ui/icons/icon_wor.bmp"));

        // 3 icons in a row. We assume the dimensions of the first one
        widthOfIcon = images.get(0).getWidth();
        heightOfIcon = images.get(0).getHeight();

        // the height of this window is:
        // 26 pixels for the 'top' (ie, the title of the thing, Const Yard, etc)
        // then we add the height + 1 pixel room (in between the icons)
        int rows = 1 + (int) Math.floor(images.size() / 4);

        int heightOfWindow = 26 + (rows * heightOfIcon) + rows + 1;
        int widthOfWindow =  1 + (4 * widthOfIcon) + 3 + 1; // 1 pixel left border + 4 icons + 3 times in between a pixel + 1 pixel for border at the right

        rectangle = new Rectangle(x, y, x + widthOfWindow, y + heightOfWindow);

        System.out.println("The gui window dimensions are: " + rectangle);


        // Window content
        int imageX = getX() + 1; // 1 pixel is the border
        int imageY = getY() + 26; // start beneath the 'title bar'

        int horizontalIconCounter = 0;
        for (int i = 0; i < images.size(); i++) {
            Image image = images.get(i);

            BuyIcon buyIcon = new BuyIcon(imageX, imageY, image);
            buyIconList.add(buyIcon);
            horizontalIconCounter++;

            imageX += widthOfIcon;

            if (horizontalIconCounter > 3) {
                horizontalIconCounter = 0;

                // row is full
                imageY += heightOfIcon;

                // as long as expect a full new row to be rendered, add another extra pixel so we keep
                // a pixel space between icons
                if ((i + 1) < images.size()) { // 1 icon is enough for a row
                    imageY++;
                }
                imageX = getX() + 1; // reset X again
            } else {
                imageX++; // and one pixel in between
            }
        }
    }

    public int getX() {
        return rectangle.getTopLeftXAsInt();
    }

    public int getY() {
        return rectangle.getTopLeftYAsInt();
    }

    public int getMostRightXCoordinate() {
        return rectangle.getBottomRight().getXAsInt();
    }

    public int getMostDownYCoordinate() {
        return rectangle.getBottomRight().getYAsInt();
    }

    private int getHeightAsInt() {
        return rectangle.getHeightAsInt();
    }

    private int getWidthAsInt() {
        return rectangle.getWidthAsInt();
    }

    public boolean isVectorWithin(Vector2D vec) {
        return rectangle.isVectorWithin(vec);
    }

    @Override
    public void render(Graphics graphics, int x, int y) {
        graphics.setColor(Color.black);
        graphics.fillRect(getX(), getY(), getWidthAsInt(), getHeightAsInt());

        graphics.setColor(Color.darkGray);
        graphics.drawRect(getX(), getY(), getWidthAsInt(), getHeightAsInt());

        // title
        graphics.setAntiAlias(false);
        graphics.setColor(Color.white);
        graphics.drawString("Construction Yard", getX() + 1, getY() + 2);

        graphics.setColor(Color.yellow);
        graphics.drawLine(getX(), getY() + 24, getMostRightXCoordinate(), getY() + 24);

        buyIconList.render(graphics, x, y);

    }

    @Override
    public void enrichRenderQueue(RenderQueue renderQueue) {
        // nothing to do here
    }

    @Override
    public void update(float deltaInSeconds) {
        for (BuyIcon buyIcon : buyIconList) {
            buyIcon.setMouseHovers(buyIcon.isVectorWithin(mouse.getPosition()));
            buyIcon.update(deltaInSeconds);
        }
    }

    public void leftClicked() {
        buyIconList.buildActiveIcon();
    }

    public void rightClicked() {
        buyIconList.stopActiveIconBeingBuild();
    }
}