package de.projectmodding.gui.panel;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.component.event.event.EmptyItemListEvent;
import de.projectmodding.core.component.event.event.FillItemListEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.controller.IScriptPanelController;
import de.projectmodding.core.model.mod.files.data.ScriptBlock;
import de.projectmodding.core.util.MathUtils;
import de.projectmodding.gui.constant.FlatLafIcons;
import de.projectmodding.gui.manager.DatatypeComponentManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScriptPanel extends JPanel implements Listener {
    private JList<ScriptBlock> scriptBlockList;
    private final EventSystem eventSystem;
    private final DatatypeComponentManager componentManager;
    private final ScriptPanelHeader scriptPanelHeader = new ScriptPanelHeader();
    private final JPanel requiredAttributePanel = new JPanel();
    private final JPanel customAttributePanel = new JPanel();
    private final IScriptPanelController controller;
    private JPanel attributePanel = null;

    private String currentModVersion = "";
    private String currentModName = "";
    private String currentModFileName = "";


    public ScriptPanel(EventSystem eventSystem, DatatypeComponentManager componentManager, IScriptPanelController controller) {
        super();
        this.controller = controller;
        this.eventSystem = eventSystem;
        this.componentManager = componentManager;
        createComponents();
    }

    private void createComponents() {
        setLayout(new GridBagLayout());
        createItemList();
        createAttributeScrollPane();
        createListener();
    }

    private void createListener() {
        eventSystem.registerEvent(FillItemListEvent.class, this);
        eventSystem.registerEvent(EmptyItemListEvent.class, this);
    }

    private void createItemList() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(createItemListPanel(), constraints);
    }

    private JPanel createItemListPanel() {
        JPanel itemListHeaderPanel = new JPanel(new BorderLayout());
        JToolBar toolBar = new JToolBar();

        toolBar.add(getAddButton());
        toolBar.add(getRemoveButton());
        toolBar.setFloatable(false);


        JLabel titleLabel = new JLabel("Scriptblock List:");
        itemListHeaderPanel.add(titleLabel, BorderLayout.WEST);
        itemListHeaderPanel.add(toolBar, BorderLayout.EAST);

        scriptBlockList = new JList<>();
        scriptBlockList.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                attributePanel.setVisible(scriptBlockList.getSelectedIndex() != -1);
            }
        });
        JScrollPane scrollPane = new JScrollPane(scriptBlockList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(0, 0));

        GridBagConstraints constraintsScrollPane = new GridBagConstraints();
        constraintsScrollPane.fill = GridBagConstraints.BOTH;
        constraintsScrollPane.gridx = 0;
        constraintsScrollPane.gridy = 2;
        constraintsScrollPane.weighty = 1;
        constraintsScrollPane.weightx = 1;

        GridBagConstraints constraintsHeader = new GridBagConstraints();
        constraintsHeader.fill = GridBagConstraints.BOTH;
        constraintsHeader.gridx = 0;
        constraintsHeader.gridy = 1;
        constraintsHeader.weighty = 0;
        constraintsHeader.weightx = 0;

        JPanel itemListPanel = new JPanel(new GridBagLayout());
        itemListPanel.setSize(0, 0);
        itemListPanel.add(scrollPane, constraintsScrollPane);
        itemListPanel.add(itemListHeaderPanel, constraintsHeader);
        itemListPanel.setBorder(BorderFactory.createEtchedBorder());
        return itemListPanel;
    }

    private JButton getRemoveButton() {
        JButton removeButton = new JButton(FlatLafIcons.MINUS_ICON);
        removeButton.addActionListener(e -> {
            int selectedIndex = scriptBlockList.getSelectedIndex();
            if (selectedIndex != -1) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete this Scriptblock?",
                        "Delete?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == JOptionPane.YES_OPTION && scriptBlockList.getModel() instanceof DefaultListModel<ScriptBlock> model) {
                    controller.removeScriptBlock(currentModName, currentModVersion, currentModFileName,
                            model.getElementAt(selectedIndex).getName());
                    model.removeElementAt(scriptBlockList.getSelectedIndex());
                }
            }
        });
        return removeButton;
    }

    private JButton getAddButton() {
        JButton addButton = new JButton(FlatLafIcons.PLUS_ICON);
        addButton.addActionListener(e -> {
            if (scriptBlockList.getModel() instanceof DefaultListModel<ScriptBlock> defaultModel) {
                ScriptBlock newScriptBlock = controller.createScriptBlock(
                        currentModName,
                        currentModVersion,
                        currentModFileName,
                        "NewScriptBlock_".concat(String.valueOf(getUniqueNumber(defaultModel)))
                );
                defaultModel.addElement(newScriptBlock);
            }
        });
        return addButton;
    }

    private int getUniqueNumber(DefaultListModel<ScriptBlock> model) {
        int maxNumber = 0;

        for (int i = 0; i < model.getSize(); i++) {
            String data = model.getElementAt(i).getName();
            int lastDelimiter = data.lastIndexOf('_');
            if (lastDelimiter > -1) {
                String stringNumber = data.substring(lastDelimiter + 1);
                int number = MathUtils.tryParseInt(stringNumber, 0);

                if (number >= maxNumber) {
                    maxNumber = number + 1;
                }
            }
        }

        return maxNumber;
    }

    private JPanel createAttributePanel() {
        attributePanel = new JPanel();
        attributePanel.setLayout(new GridBagLayout());
        attributePanel.setBackground(Color.BLACK);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;
        attributePanel.add(scriptPanelHeader, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        requiredAttributePanel.setBorder(BorderFactory.createEtchedBorder());
        attributePanel.add(requiredAttributePanel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.5;
        customAttributePanel.setBorder(BorderFactory.createEtchedBorder());
        attributePanel.add(customAttributePanel, constraints);
        attributePanel.setVisible(false);
        return attributePanel;
    }

    private void createAttributeScrollPane() {
        JScrollPane scrollPane = new JScrollPane(createAttributePanel());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(0, 0));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.9;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(scrollPane, constraints);
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (isEnabled()) {
            attributePanel.setVisible(false);
            if (event instanceof FillItemListEvent fillItemEvent) {
                DefaultListModel<ScriptBlock> model = new DefaultListModel<>();
                List<ScriptBlock> scriptBlocks = fillItemEvent.getData().getScriptBlocks();

                currentModVersion = fillItemEvent.getData().getModVersion();
                currentModName = fillItemEvent.getData().getModName();
                currentModFileName = fillItemEvent.getData().getFileName();
                model.addAll(scriptBlocks);

                scriptBlockList.setModel(model);
            } else if (event instanceof EmptyItemListEvent && scriptBlockList.getModel() instanceof DefaultListModel<ScriptBlock> model) {
                model.removeAllElements();
            }
        }
    }
}
