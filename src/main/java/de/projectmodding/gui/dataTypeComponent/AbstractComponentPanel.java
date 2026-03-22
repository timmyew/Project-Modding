package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.component.factory.DialogFactory;
import de.projectmodding.core.exception.NoAttributeAssignedException;
import de.projectmodding.core.model.AttributeModel;
import de.projectmodding.gui.constant.FlatLafIcons;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractComponentPanel extends JPanel implements IDataTypeComponent {
    @Getter
    protected AttributeModel attribute;
    protected final JButton removeButton = new JButton(FlatLafIcons.CLOSE_ICON_X24);
    protected final JButton questionButton = new JButton(FlatLafIcons.QUESTION_ICON_X24);
    protected final JPanel dataPanel = new JPanel();

    private String descriptionText = "";

    private boolean isUICreated = false;

    public void setAttribute(AttributeModel attributeModel){
        this.attribute = attributeModel;
        descriptionText = attribute.getDescription();

        if (!isUICreated){
            setLayout(new BorderLayout(5, 5));
            createHeaderPanel();
            createDataPanel();
            createUIComponents();
            isUICreated = true;
        }
    }

    public JPanel getPanel(){
        return this;
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

    private void createDataPanel(){
        add(dataPanel, BorderLayout.CENTER);
    }

    private void createHeaderPanel(){
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout(5, 5));
        headerPanel.add(removeButton, BorderLayout.WEST);
        headerPanel.add(questionButton, BorderLayout.EAST);

        questionButton.addActionListener(action -> {
            JOptionPane.showMessageDialog(this, descriptionText, "Description",  JOptionPane.INFORMATION_MESSAGE);
        });

        add(headerPanel, BorderLayout.NORTH);
    }
}
