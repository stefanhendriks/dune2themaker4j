package com.fundynamic.d2tm.game.gui;


import com.fundynamic.d2tm.game.behaviors.Renderable;
import com.fundynamic.d2tm.game.behaviors.Updateable;
import com.fundynamic.d2tm.game.entities.EntityData;
import com.fundynamic.d2tm.game.entities.Rectangle;
import com.fundynamic.d2tm.game.rendering.RenderQueue;
import com.fundynamic.d2tm.math.Vector2D;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Represents an icon that can be built.
 */
public class BuyIcon implements Renderable, Updateable {

    private Rectangle rectangle;
    private BuyIconState state;
    private boolean mouseHovers;
    private float buildingProgress;

    private Image image;

    public BuyIcon(int x, int y, Image image) {
        rectangle = new Rectangle(x, y, x + image.getWidth(), y + image.getHeight());
        this.image = image;
        buildingProgress = 0f;
    }

    public boolean isVectorWithin(Vector2D vec) {
        return rectangle.isVectorWithin(vec);
    }

    @Override
    public void update(float deltaInSeconds) {
        if (state == BuyIconState.Building) {
            // 0.2f (20%) per second
            buildingProgress += EntityData.getRelativeSpeed(0.2f, deltaInSeconds);
            if (buildingProgress >= 1.0f) {
                buildingProgress = 1.0f;
                // Done building the thing
                state = BuyIconState.Placeable;
            }
        }
    }

    @Override
    public void render(Graphics graphics, int x, int y) {
        graphics.drawImage(image, rectangle.getTopLeftXAsInt(), rectangle.getTopLeftYAsInt());

        if (state == BuyIconState.Building) {
            // busy building
            graphics.setColor(Color.white);

            String progressString = "" + (int)(buildingProgress * 100) + "%";

            // TODO: Create GraphicsHelper? (to deal with our Rectangle class, etc?)
            graphics.setColor(Color.black);
            graphics.drawString(progressString, rectangle.getTopLeftXAsInt() + 1, rectangle.getTopLeftYAsInt() + 1);
            graphics.setColor(Color.white);
            graphics.drawString(progressString, rectangle.getTopLeftXAsInt(), rectangle.getTopLeftYAsInt());
        }

        if (state == BuyIconState.Placeable) {
            String placeString = "Place it";

            // TODO: Create GraphicsHelper? (to deal with our Rectangle class, etc?)
            graphics.setColor(Color.black);
            graphics.drawString(placeString, rectangle.getTopLeftXAsInt() + 1, rectangle.getTopLeftYAsInt() + 1);
            graphics.setColor(Color.red);
            graphics.drawString(placeString, rectangle.getTopLeftXAsInt(), rectangle.getTopLeftYAsInt());
        }

        if (state == BuyIconState.Disabled) {
            // when we are building stuff and it is not this icon, darken it
            graphics.setColor(new Color(0,0,0,128));

            // TODO: Create GraphicsHelper? (to deal with our Rectangle class, etc?)
            graphics.fillRect(
                    rectangle.getTopLeftXAsInt(),
                    rectangle.getTopLeftYAsInt(),
                    rectangle.getWidthAsInt(),
                    rectangle.getHeightAsInt()
            );
        }


        if (mouseHovers) {
            graphics.setColor(Color.blue);
            // TODO: Create GraphicsHelper?
            graphics.drawRect(
                    rectangle.getTopLeftXAsInt(),
                    rectangle.getTopLeftYAsInt(),
                    rectangle.getWidthAsInt(),
                    rectangle.getHeightAsInt()
            );
        }
    }

    public void build() {
        if (this.state != BuyIconState.Building) {
            this.state = BuyIconState.Building;
            buildingProgress = 0f;
        }
    }

    @Override
    public void enrichRenderQueue(RenderQueue renderQueue) {
        // do nothing
    }

    public void setMouseHovers(boolean mouseHovers) {
        this.mouseHovers = mouseHovers;
    }

    public boolean isMouseHovers() {
        return mouseHovers;
    }

    public void disable() {
        this.state = BuyIconState.Disabled;
    }

    public void enable() {
        this.state = BuyIconState.Selectable;
    }
}
