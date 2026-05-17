package de.projectmodding.gui.panel;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.component.event.system.EventSystem;
import lombok.Getter;

import javax.swing.*;

public abstract class AbstractBasePanel extends JPanel implements Listener {

    protected final Container container;

    public AbstractBasePanel(Container mainContainer) {
        super();
        this.container = mainContainer;
        createListener(container.resolve(EventSystem.class));
    }

    protected abstract void createListener(EventSystem eventSystem);
}
