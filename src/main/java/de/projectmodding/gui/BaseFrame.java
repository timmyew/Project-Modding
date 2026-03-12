package de.projectmodding.gui;

import de.projectmodding.core.component.event.Listener;
import de.projectmodding.core.component.event.system.EventSystem;

import javax.swing.*;
import java.awt.*;

public abstract class BaseFrame extends JFrame implements Listener {

    private final GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    protected final EventSystem eventSystem;

    protected BaseFrame(EventSystem eventSystem) {
        super();
        this.eventSystem = eventSystem;
    }

    protected void initForm(String title, JPanel contentPanel, int heightPercent, int widthPercent, int closeAction) {
        setTitle(title);
        setDefaultCloseOperation(closeAction);
        setContentPane(contentPanel);
        rescaleWindowSize(heightPercent, widthPercent);
        setVisible(true);
        registerEvents();
    }

    private void rescaleWindowSize(int heightPercent, int widthPercent) {
        this.setSize(
                graphicsDevice.getDisplayMode().getWidth() / 100 * widthPercent,
                graphicsDevice.getDisplayMode().getHeight() / 100 * heightPercent
        );
        setLocationRelativeTo(null);
    }

    public abstract void showForm();

    protected abstract void registerEvents();
}
