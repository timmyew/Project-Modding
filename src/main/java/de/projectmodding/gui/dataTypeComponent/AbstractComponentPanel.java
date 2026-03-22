package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.exception.NoAttributeAssignedException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.gui.constant.FlatLafIcons;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractComponentPanel extends JPanel implements IDataTypeComponent {
    protected AttributeModel attribute;
    private final JButton removeButton = new JButton(FlatLafIcons.CLOSE_ICON_X24);
    private final JButton questionButton = new JButton(FlatLafIcons.QUESTION_ICON_X24);

    private boolean isUICreated = false;

    public void setAttribute(AttributeModel attributeModel){
        this.attribute = attributeModel;

        if (!isUICreated){
            setLayout(new BorderLayout(5, 5));
            createToolbar();
            createUIComponents();
            isUICreated = true;
        }
    }


    public void showComponent(){
        if (attribute == null && isUICreated)
            setVisible(true);
        else
            throw new NoAttributeAssignedException(String.format("Panel can't show, because the attribute is not assigned. Classname: %s", getClass().getName()));
    }

    public void hideComponentAndReleaseAttribute(){
        attribute = null;
        setVisible(false);
    }

    public boolean isActive(){
        return isUICreated && isVisible() && attribute != null;
    }

    protected abstract void createUIComponents();

    private void createToolbar(){
        JToolBar toolBar = new JToolBar();
        toolBar.add(removeButton);
        toolBar.add(questionButton);
        toolBar.setFloatable(false);

        add(toolBar, BorderLayout.NORTH);
    }

    private String getDisplayName(){
        return attribute.getDisplayName() != null && !attribute.getDisplayName().isBlank() ? attribute.getDisplayName() : attribute.getKeyName();
    }

    protected JLabel getDisplayNameLabel(){
        return new JLabel(getDisplayName());
    }
}
