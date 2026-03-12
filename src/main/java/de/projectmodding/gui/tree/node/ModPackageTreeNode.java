package de.projectmodding.gui.tree.node;

import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.mod.files.BaseFile;
import lombok.Getter;

import javax.swing.tree.DefaultMutableTreeNode;

public class ModPackageTreeNode extends DefaultMutableTreeNode {
    @Getter
    private BaseFile baseFile;

    @Getter
    private ModDataKey key;

    @Getter
    private String version;

    @Getter
    private String modName;

    private final Boolean canAddFiles;

    public ModPackageTreeNode(BaseFile baseFile) {
        this.baseFile = baseFile;
        canAddFiles = false;
    }

    public ModPackageTreeNode(String folderName){
        canAddFiles = false;
        super(folderName);
    }

    public ModPackageTreeNode(String folderName, ModDataKey key, String version, String modName){
        this.key = key;
        this.version = version;
        this.modName = modName;
        canAddFiles = true;
        super(folderName);
    }

    public Boolean canAddFiles() {
        return canAddFiles;
    }

    @Override
    public String toString() {
        if (baseFile != null && baseFile.getFileName() != null && !baseFile.getFileName().isBlank())
            return baseFile.getFileName();
        else
            return this.userObject == null ? "" : this.userObject.toString();
    }
}
