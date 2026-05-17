package de.projectmodding.gui.panel.script;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.event.LoadAttributesEvent;
import de.projectmodding.core.component.event.event.ReloadAttributesEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.component.query.definition.DefinitionQuery;
import de.projectmodding.core.component.query.definition.FileDefinitionModelQuery;
import de.projectmodding.core.model.definition.FileDefinitionModel;
import de.projectmodding.gui.constant.FlatLafIcons;
import de.projectmodding.gui.panel.AbstractBasePanel;

import javax.swing.*;
import java.awt.*;

public class ScriptPanelAttributes extends AbstractBasePanel {

    private final String NORMAL_ATTRIBUTES = "Attributes";

    public ScriptPanelAttributes(Container mainContainer) {
        super(mainContainer);
        build();
    }

    private void build(){
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), NORMAL_ATTRIBUTES));
        buildToolBar();
    }

    private void buildToolBar(){
        JButton addButton = new JButton(FlatLafIcons.PLUS_ICON_X32);
        addButton.addActionListener(e -> {

        });

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(addButton);

        add(toolBar, BorderLayout.NORTH);
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (event instanceof LoadAttributesEvent loadEvent) {
            //FileDefinitionModelQuery.on(loadEvent.getData().getDefinitionModel())
        }
        else if (event instanceof ReloadAttributesEvent) {
        }
    }

    @Override
    protected void createListener(EventSystem eventSystem) {
        eventSystem.registerEvent(LoadAttributesEvent.class, this);
    }
}
