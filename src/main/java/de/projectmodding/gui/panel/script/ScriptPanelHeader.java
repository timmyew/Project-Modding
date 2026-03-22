package de.projectmodding.gui.panel.script;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.event.LoadAttributesEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.gui.panel.AbstractBasePanel;

import javax.swing.*;
import java.awt.*;

public class ScriptPanelHeader extends AbstractBasePanel {
    private final String SCRIPT_NAME = "Script Name:";
    private final String SCRIPT_MODULE_NAME = "Script Module:";
    private final String BUTTON_SAVE = "Save";
    private final String SCRIPT_ATTRIBUTES = "Script Attributes:";

    private final int MIN_TEXT_FIELD_LENGTH = 25;

    JButton saveButton = new JButton(BUTTON_SAVE);

    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();

    JLabel labelScriptName = new JLabel(SCRIPT_NAME);
    JTextField textFieldScriptName = new JTextField(MIN_TEXT_FIELD_LENGTH);
    JPanel panelScriptName = new JPanel();

    JLabel labelModuleName = new JLabel(SCRIPT_MODULE_NAME);
    JTextField textFieldModuleName = new JTextField(MIN_TEXT_FIELD_LENGTH);
    JPanel panelModuleName = new JPanel();

    public ScriptPanelHeader(EventSystem eventSystem) {
        super(eventSystem);
        build();
    }

    private void build(){
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), SCRIPT_ATTRIBUTES));
        createModuleNamePanel();
        createScriptNamePanel();

        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(panelScriptName);
        leftPanel.add(panelModuleName);

        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 5, 0, 5);
        rightPanel.add(saveButton, constraints);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
    }

    private void createScriptNamePanel(){
        panelScriptName.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        panelScriptName.add(labelScriptName, constraints);

        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        panelScriptName.add(textFieldScriptName, constraints);
    }

    private void createModuleNamePanel(){
        panelModuleName.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        panelModuleName.add(labelModuleName, constraints);

        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        panelModuleName.add(textFieldModuleName, constraints);
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (event instanceof LoadAttributesEvent loadEvent) {
            textFieldScriptName.setText(loadEvent.getData().getScriptBlock().getName());
            textFieldModuleName.setText(loadEvent.getData().getModuleName());
        }
    }

    @Override
    protected void createListener(EventSystem eventSystem) {
        eventSystem.registerEvent(LoadAttributesEvent.class, this);
    }
}
