package com.fundynamic.d2tm.game.controls;

import com.fundynamic.d2tm.game.map.Cell;
import com.fundynamic.d2tm.game.structures.ConstructionYard;

/**
 * This class represents the state of the mouse. This is not about coordinates (we can get these via Slick listeners)
 * but about what structure is selected, the type of mouse being rendered (ie moving? attacking? placing structure? or
 * 'normal' ?)
 */
public class Mouse {

    private Cell hoverCell;
    private ConstructionYard selectedStructure;

    public Mouse(Cell hoverCell) {
        this.hoverCell = hoverCell;
    }

    public void setHoverCell(Cell cell) {
        if (cell == null) throw new IllegalArgumentException("argument cell may not be null");
        this.hoverCell = cell;
    }

    public Cell getHoverCell() {
        return hoverCell;
    }

    /**
     * When a structure is bound to a cell, this method makes sure that is the only selected structure.
     * If there is no structure bound to the cell then this automatically deselects the structure.
     */
    public void selectStructure() {
        selectedStructure = hoverCell.getConstructionYard(); // TODO: change "construction Yard" into "Structure"
        // TODO: tell structure it is being selected? (for perhaps other listeners GUI related!?)
        // ie: hoverCell.selectStructureOnCell();
        //       -- structure.select(); ...
    }

    public boolean hasStructureSelected(ConstructionYard structureToCheck) {
        if (structureToCheck == null) return false;
        if (this.selectedStructure == null) return false;
        return this.selectedStructure == structureToCheck;
    }

    public ConstructionYard getSelectedStructure() {
        return selectedStructure;
    }
}
