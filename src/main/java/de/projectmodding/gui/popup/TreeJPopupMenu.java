package de.projectmodding.gui.popup;

import lombok.Setter;

import javax.swing.*;

public class TreeJPopupMenu extends JPopupMenu {
    @Setter
    Boolean doHide = false;

    @Override
    public void setVisible(boolean b){

        if (b){
            if (!doHide)
                super.setVisible(b);
        }
        else {
            super.setVisible(b);
        }
    }
}
