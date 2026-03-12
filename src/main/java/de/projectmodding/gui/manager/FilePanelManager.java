package de.projectmodding.gui.manager;

import de.projectmodding.core.model.mod.files.BaseFile;

import javax.swing.*;
import java.util.HashMap;

public final class FilePanelManager {
    HashMap<Class<? extends BaseFile>, JPanel> panels = new HashMap<>();

    public FilePanelManager(){

    }

    public void registerPanel(Class<? extends BaseFile> modelClass, JPanel panel) {
        if (!panels.containsKey(modelClass))
            panels.put(modelClass, panel);
    }

    public JPanel getPanel(Class<? extends BaseFile> modelClass) {
        return panels.get(modelClass);
    }
}
