package de.projectmodding.gui.dataTypeComponent;

import javax.swing.*;
import java.awt.*;

public class StringComponent extends AbstractComponentPanel implements IDataTypeComponent {
    private JLabel label = null;
    private final JTextField textField = new JTextField();


    @Override
    protected void createUIComponents() {
        JPanel dataPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        add(dataPanel, BorderLayout.CENTER);

        label = getDisplayNameLabel();
        dataPanel.add(label);
        dataPanel.add(textField);
    }

    @Override
    public Boolean isDataValid() {
        return null;
    }
}
