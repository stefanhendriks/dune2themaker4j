package com.fundynamic.d2tm.game.gui;


import com.fundynamic.d2tm.game.behaviors.Renderable;
import com.fundynamic.d2tm.game.behaviors.Updateable;
import com.fundynamic.d2tm.game.controls.Mouse;
import com.fundynamic.d2tm.game.controls.PlacingStructureMouse;
import com.fundynamic.d2tm.game.entities.EntityRepository;
import com.fundynamic.d2tm.game.rendering.RenderQueue;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class BuyIconList extends ArrayList<BuyIcon> implements Renderable, Updateable {

    private Mouse mouse;

    private BuyIcon busyWithBuildingIcon;

    private EntityRepository entityRepository;

    public BuyIconList(Mouse mouse, EntityRepository entityRepository) {
        this.mouse = mouse;
        this.entityRepository = entityRepository;
    }

    public void leftClicked() {
        if (isBusyBuilding()) {
            // clicked on the icon that is building things
            if (busyWithBuildingIcon.isMouseHovers()) {
                // for now place random thing
                mouse.setMouseBehavior(new PlacingStructureMouse(mouse, entityRepository));

                // TODO: When placed, mark as done
                // TODO: Hide gui element
            }

            // to rightclicked thing?
            // TODO: check if hovering over the busyWithBuildingIcon
            // TODO: and if so, we can cancel building, etc?
            return;
        }

        for (BuyIcon buyIcon : this) {
            if (buyIcon.isMouseHovers()) {
                busyWithBuildingIcon = buyIcon;
                break;
            }
        }

        if (busyWithBuildingIcon != null) {
            busyWithBuildingIcon.build();
            // set all other icons to disabled
            for (BuyIcon buyIcon : this) {
                if (busyWithBuildingIcon == buyIcon) {
                    continue;
                }
                buyIcon.disable();
            }
        }
    }

    public boolean isBusyBuilding() {
        return busyWithBuildingIcon != null;
    }

    public void stopActiveIconBeingBuild() {
        for (BuyIcon buyIcon : this) {
            if (buyIcon.isMouseHovers() && buyIcon == busyWithBuildingIcon) {
                busyWithBuildingIcon = null;
            }
        }

        if (busyWithBuildingIcon == null) {
            for (BuyIcon buyIcon : this) {
                buyIcon.enable();
            }
        }
    }

    @Override
    public void render(Graphics graphics, int x, int y) {
        for (BuyIcon buyIcon : this) {
            // render icon
            buyIcon.render(graphics, x, y);
        }
    }

    @Override
    public void enrichRenderQueue(RenderQueue renderQueue) {
        // nothing to do here
    }

    public void update(float deltaInSeconds) {
        for (BuyIcon buyIcon : this) {
            // tell it if the mouse is hovering over it or not
            buyIcon.setMouseHovers(buyIcon.isVectorWithin(mouse.getPosition()));

            // call its update method
            buyIcon.update(deltaInSeconds);
        }
    }
}
