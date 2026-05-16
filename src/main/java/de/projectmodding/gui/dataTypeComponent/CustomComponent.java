package de.projectmodding.gui.dataTypeComponent;

import de.projectmodding.core.constant.definition.AttributeConstants;
import de.projectmodding.core.util.MathUtils;
import de.projectmodding.gui.constant.UiResourcesConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomComponent extends AbstractComponentPanel implements IDataTypeComponent {
    private JLabel label;
    private JComboBox<String> comboBox;
    private final List<ActionListener> actionListeners = new ArrayList<>();

    private final int WIDTH = 220;
    private final int HEIGHT = 95;

    @Override
    protected void initAttribute() {
        comboBox.setModel(new DefaultComboBoxModel<>(attribute.getInternalData().split(AttributeConstants.GLOBAL_DELIMITER)));

        if (!attribute.getValue().isEmpty()) {
            comboBox.setSelectedItem(attribute.getValue());
        }

        addDefaultActionListener();
        initActionListeners();
    }

    private void addDefaultActionListener(){
        addActionListener(e -> {
            attribute.setValue(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
            System.out.println("Value of " + attribute.getKeyName() + " is " + attribute.getValue());
        });
    }

    @Override
    protected void createUIComponents() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(BorderFactory.createLineBorder(UiResourcesConstants.BORDER_COLOR, 5));

        label = new JLabel(attribute.getDisplayName());
        comboBox = new JComboBox<>();
        comboBox.setName(attribute.getCustomType());

        dataPanel.setLayout(new BorderLayout());

        dataPanel.add(label, BorderLayout.NORTH);
        dataPanel.add(comboBox, BorderLayout.CENTER);
    }

    @Override
    public Boolean isDataValid() {
        return comboBox.getSelectedItem() != null;
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    @Override
    public void removeActionListener() {
        actionListeners.clear();
        Arrays.stream(comboBox.getActionListeners()).toList().forEach(comboBox::removeActionListener);
    }

    private void initActionListeners() {
        actionListeners.forEach(actionListener -> comboBox.addActionListener(actionListener));
    }
}
