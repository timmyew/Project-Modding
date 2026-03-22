package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.gui.constant.UiResourcesConstants;

import javax.swing.*;
import java.awt.*;

public class CustomComponent extends AbstractComponentPanel implements IDataTypeComponent {
    private JLabel scriptTypeLabel;
    private JComboBox<String> scriptTypeComboBox;

    private final int WIDTH = 220;
    private final int HEIGHT = 95;

    @Override
    protected void createUIComponents() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createLineBorder(UiResourcesConstants.BORDER_COLOR, 5));

        removeButton.setEnabled(!attribute.getRequired());

        scriptTypeLabel = new JLabel(attribute.getDisplayName());
        scriptTypeComboBox = new JComboBox<>();
        scriptTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"Test1", "Test2", "Test3"}));

        dataPanel.setLayout(new BorderLayout());

        dataPanel.add(scriptTypeLabel, BorderLayout.NORTH);
        dataPanel.add(scriptTypeComboBox, BorderLayout.CENTER);
    }

    @Override
    public Boolean isDataValid() {
        return null;
    }
}
