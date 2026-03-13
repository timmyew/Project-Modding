package de.projectmodding.gui.panel;

import javax.swing.*;
import java.awt.*;

public class ScriptPanelHeader extends JPanel {
    private final int MIN_TEXT_FIELD_LENGTH = 25;

    JButton saveButton = new JButton("Save");

    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();

    JLabel labelScriptName = new JLabel("Script Name:");
    JTextField textFieldScriptName = new JTextField(MIN_TEXT_FIELD_LENGTH);
    JPanel panelScriptName = new JPanel();

    JLabel labelModuleName = new JLabel("Module Name:");
    JTextField textFieldModuleName = new JTextField(MIN_TEXT_FIELD_LENGTH);
    JPanel panelModuleName = new JPanel();

    public ScriptPanelHeader(){
        super();
        build();
    }

    private void build(){
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEtchedBorder());
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
}
