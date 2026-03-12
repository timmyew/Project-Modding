package de.projectmodding.gui.tree.renderer;

import de.projectmodding.gui.tree.node.ModPackageTreeNode;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class ModPackageTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus){
        Component result = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof ModPackageTreeNode packageNode) {
            if (packageNode.getBaseFile() == null && packageNode.isLeaf())
                this.setIcon(closedIcon);
        }
        return result;
    }
}
