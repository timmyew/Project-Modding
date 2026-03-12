package de.projectmodding.gui.panel;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.component.event.event.EmptyItemListEvent;
import de.projectmodding.core.component.event.event.FillItemListEvent;
import de.projectmodding.core.component.event.system.EventSystem;

import javax.swing.*;
import java.awt.*;

public class ScriptPanel extends JPanel implements Listener {
    private JList<String> listData;
    private final EventSystem eventSystem;

    public ScriptPanel(EventSystem eventSystem){
        super();
        this.eventSystem = eventSystem;
        createComponents();
    }

    private void createComponents()
    {
        setLayout(new GridBagLayout());
        createItemList();
        createAttributePanel();
        createListener();
    }

    private void createListener(){
        eventSystem.registerEvent(FillItemListEvent.class, this);
        eventSystem.registerEvent(EmptyItemListEvent.class, this);
    }

    private void createItemList()
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(createItemListPanel(), constraints);
    }

    private JPanel createItemListPanel()
    {
        JLabel titleLabel = new JLabel("Item List");
        listData = new JList<>();
        JScrollPane scrollPane = new JScrollPane(listData);
        scrollPane.setPreferredSize(new Dimension(0, 0));

        GridBagConstraints constraintsScrollPane = new GridBagConstraints();
        constraintsScrollPane.fill = GridBagConstraints.BOTH;
        constraintsScrollPane.gridx = 0;
        constraintsScrollPane.gridy = 1;
        constraintsScrollPane.weighty = 1;
        constraintsScrollPane.weightx = 1;

        GridBagConstraints constraintsTitle = new GridBagConstraints();
        constraintsTitle.fill = GridBagConstraints.BOTH;
        constraintsTitle.gridx = 0;
        constraintsTitle.gridy = 0;
        constraintsTitle.weighty = 0;
        constraintsTitle.weightx = 0;

        JPanel itemListPanel = new JPanel(new GridBagLayout());
        itemListPanel.setSize(0, 0);
        itemListPanel.add(scrollPane, constraintsScrollPane);
        itemListPanel.add(titleLabel, constraintsTitle);
        itemListPanel.setBorder(BorderFactory.createEtchedBorder());
        return itemListPanel;
    }

    private void createAttributePanel()
    {
        JPanel panel = new JPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 0.9;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        add(panel, constraints);
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (isEnabled()){
            if (event instanceof FillItemListEvent fillItemEvent) {
                DefaultListModel<String> model = new DefaultListModel<>();
                model.addAll(fillItemEvent.getData());
                listData.setModel(model);
            }
            else if (event instanceof EmptyItemListEvent && listData.getModel() instanceof DefaultListModel<String> model) {
                model.removeAllElements();
            }
        }
    }
}
