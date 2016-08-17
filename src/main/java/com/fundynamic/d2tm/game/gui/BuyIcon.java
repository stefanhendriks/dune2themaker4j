package com.fundynamic.d2tm.game.gui;


import com.fundynamic.d2tm.game.behaviors.Renderable;
import com.fundynamic.d2tm.game.behaviors.Updateable;
import com.fundynamic.d2tm.game.entities.Rectangle;
import com.fundynamic.d2tm.game.rendering.RenderQueue;
import com.fundynamic.d2tm.math.Vector2D;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Represents an icon that can be built.
 */
public class BuyIcon implements Renderable, Updateable {

    private Rectangle rectangle;

    private Image image;

    public BuyIcon(int x, int y, Image image) {
        rectangle = new Rectangle(x, y, x + image.getWidth(), y + image.getHeight());
        this.image = image;
    }

    public boolean isVectorWithin(Vector2D vec) {
        return rectangle.isVectorWithin(vec);
    }

    @Override
    public void update(float deltaInSeconds) {

    }

    @Override
    public void render(Graphics graphics, int x, int y) {
        graphics.drawImage(image, rectangle.getTopLeftXAsInt(), rectangle.getTopLeftYAsInt());
    }

    @Override
    public void enrichRenderQueue(RenderQueue renderQueue) {
        // do nothing
    }
}
