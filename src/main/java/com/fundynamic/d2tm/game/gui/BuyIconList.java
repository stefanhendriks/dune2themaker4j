package com.fundynamic.d2tm.game.gui;


import com.fundynamic.d2tm.game.behaviors.Renderable;
import com.fundynamic.d2tm.game.rendering.RenderQueue;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;

public class BuyIconList extends ArrayList<BuyIcon> implements Renderable {

    private BuyIcon busyWithBuildingIcon;

    public void buildActiveIcon() {
        if (isBuildingIcon()) {
            // TODO: check if hovering over the busyWithBuildingIcon
            // and if so, we can cancel building, etc?
            // or when it is finished building, we can get into 'place this thing mode' ?
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

    public boolean isBuildingIcon() {
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

    }
}
