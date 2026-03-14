package de.projectmodding.gui.panel.script;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.gui.panel.AbstractBasePanel;

import javax.swing.*;
import java.awt.*;

public class ScriptPanelRequiredAttributes extends AbstractBasePanel {
    private final String REQUIRED_ATTRIBUTES = "Required Attributes";

    public ScriptPanelRequiredAttributes(EventSystem eventSystem) {
        super(eventSystem);
        build();
    }

    private void build(){
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), REQUIRED_ATTRIBUTES));
    }

    @Override
    public <T> void onEvent(Event<T> event) {

    }

    @Override
    protected void createListener(EventSystem eventSystem) {

    }
}
