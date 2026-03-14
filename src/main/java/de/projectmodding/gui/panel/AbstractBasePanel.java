package de.projectmodding.gui.panel;

import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.component.event.system.EventSystem;
import lombok.Getter;

import javax.swing.*;

public abstract class AbstractBasePanel extends JPanel implements Listener {

    @Getter
    private final EventSystem eventSystem;

    public AbstractBasePanel(EventSystem eventSystem) {
        super();
        this.eventSystem = eventSystem;
        createListener(eventSystem);
    }

    protected abstract void createListener(EventSystem eventSystem);
}
