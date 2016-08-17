package com.fundynamic.d2tm.game.controls;


import com.fundynamic.d2tm.game.gui.BuyStuffGuiElement;
import com.fundynamic.d2tm.game.map.Cell;
import com.fundynamic.d2tm.math.Vector2D;


public class GuiMouse extends AbstractMouseBehavior {

    private BuyStuffGuiElement buyStuffGuiElement;

    public GuiMouse(Mouse mouse, BuyStuffGuiElement buyStuffGuiElement) {
        super(mouse);
        this.buyStuffGuiElement = buyStuffGuiElement;
        mouse.setMouseImage(Mouse.MouseImages.NORMAL, 0, 0);
    }

    @Override
    public void leftClicked() {
        if (buyStuffGuiElement.isVectorWithin(mouse.getPosition())) {
            buyStuffGuiElement.leftClicked();
        }
    }

    @Override
    public void rightClicked() {
        if (buyStuffGuiElement.isVectorWithin(mouse.getPosition())) {
            buyStuffGuiElement.rightClicked();
        } else {
            deselectCurrentlySelectedEntity();
            mouse.setMouseBehavior(new NormalMouse(mouse));
            mouse.setLastSelectedEntity(null);
        }
    }

    @Override
    public void mouseMovedToCell(Cell cell) {

    }

    @Override
    public void draggedToCoordinates(Vector2D viewportCoordinates) {
    }

    @Override
    public String toString() {
        return "GuiMouse";
    }
}
