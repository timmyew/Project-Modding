package de.projectmodding.gui.constant;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatSearchIcon;

import javax.swing.*;

public final class FlatLafIcons {
    private FlatLafIcons(){}

    public static final Icon FILE_CHOOSER_ICON = new FlatSearchIcon();
    public static final Icon PLUS_ICON = new FlatSVGIcon("icons/plus.svg", 16, 16);
    public static final Icon MINUS_ICON = new FlatSVGIcon("icons/minus.svg", 16, 16);
}
