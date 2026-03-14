package de.projectmodding.gui.constant;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatSearchIcon;

import javax.swing.*;

public final class FlatLafIcons {
    private FlatLafIcons(){}

    public static final Icon FILE_CHOOSER_ICON = new FlatSearchIcon();
    public static final Icon PLUS_ICON_X16 = new FlatSVGIcon("icons/plus.svg", 16, 16);
    public static final Icon PLUS_ICON_X32 = new FlatSVGIcon("icons/plus.svg", 32, 32);
    public static final Icon MINUS_ICON_X16 = new FlatSVGIcon("icons/minus.svg", 16, 16);
    public static final Icon CLOSE_ICON_X24 = new FlatSVGIcon("icons/close.svg", 24, 24);
    public static final Icon QUESTION_ICON_X24 = new FlatSVGIcon("icons/question.svg", 24, 24);
}
