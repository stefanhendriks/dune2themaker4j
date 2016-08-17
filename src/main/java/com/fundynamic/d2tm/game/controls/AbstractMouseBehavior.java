package com.fundynamic.d2tm.game.controls;


import com.fundynamic.d2tm.game.behaviors.Selectable;
import com.fundynamic.d2tm.game.entities.Entity;
import com.fundynamic.d2tm.game.entities.Predicate;
import com.fundynamic.d2tm.game.map.Cell;
import com.fundynamic.d2tm.math.Vector2D;
import org.newdawn.slick.Graphics;

import java.util.Set;

public abstract class AbstractMouseBehavior implements MouseBehavior {
    protected final Mouse mouse;

    public AbstractMouseBehavior(Mouse mouse) {
        this.mouse = mouse;
    }

    public abstract void leftClicked();

    public abstract void rightClicked();

    public abstract void mouseMovedToCell(Cell cell);

    public void render(Graphics g) {
        // DO NOTHING
    }

    public void draggedToCoordinates(Vector2D coordinates) {
        // DO NOTHING
    }

    public void leftButtonReleased() {
        // DO NOTHING
    }

    protected void deselectCurrentlySelectedEntity() {

        // For all selected entities of this player...
        Set<Entity> entities = mouse.getEntityRepository().filter(
                Predicate.builder().
                        forPlayer(mouse.getControllingPlayer()).
                        isSelected().
                        build());

        // ... deselect them ...
        for (Entity entity : entities) {
            ((Selectable) entity).deselect();
        }

        // TODO: This can probably be removed some day?
        // ... and deselect the one we had stored in our mouse state as well ...
        Entity lastSelectedEntity = mouse.getLastSelectedEntity();
        if (lastSelectedEntity != null && lastSelectedEntity.isSelectable()) {
            ((Selectable) lastSelectedEntity).deselect();
        }

        mouse.setMouseImage(Mouse.MouseImages.NORMAL, 0, 0);
    }
}
