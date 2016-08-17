package com.fundynamic.d2tm.game.gui;


public enum BuyIconState {
    Selectable,         // free to select this icon
    Disabled,           // icon is disabled
    Building,           // this icon is being built
    Placeable,          // this icon is finished being built and can now be placed
}
