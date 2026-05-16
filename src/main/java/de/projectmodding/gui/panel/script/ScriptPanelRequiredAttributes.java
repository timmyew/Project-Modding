package de.projectmodding.gui.panel.script;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.event.LoadAttributesEvent;
import de.projectmodding.core.component.event.event.ReloadAttributesEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.component.query.definition.DefinitionQuery;
import de.projectmodding.core.component.query.definition.ModDefinitionQuery;
import de.projectmodding.core.model.event.LoadAttributesEventModel;
import de.projectmodding.gui.generator.ScriptPanelRequiredAttributeGenerator;
import de.projectmodding.gui.panel.AbstractBasePanel;

import javax.swing.*;
import java.awt.*;

public class ScriptPanelRequiredAttributes extends AbstractBasePanel {
    private final String REQUIRED_ATTRIBUTES = "Required Attributes";
    private LoadAttributesEventModel cachedLoadEventData;

    public ScriptPanelRequiredAttributes(EventSystem eventSystem) {
        super(eventSystem);
        build();
    }

    private void build(){
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), REQUIRED_ATTRIBUTES));
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (event instanceof LoadAttributesEvent loadEvent) {
            this.removeAll();
            cachedLoadEventData = loadEvent.getData();
            loadAttributes(loadEvent.getData());
        }
        else if (event instanceof ReloadAttributesEvent) {
            this.removeAll();
            loadAttributes(cachedLoadEventData);
        }
    }

    private void loadAttributes(LoadAttributesEventModel loadEventData) {
        ScriptPanelRequiredAttributeGenerator.getInstance().generate(this, loadEventData.getDefinitionModel(),
                loadEventData.getScriptBlock(), loadEventData.getComponentManager());
        repaint();
    }

    @Override
    protected void createListener(EventSystem eventSystem) {
        eventSystem.registerEvent(LoadAttributesEvent.class, this);
        eventSystem.registerEvent(ReloadAttributesEvent.class, this);
    }
}
