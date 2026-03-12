package de.projectmodding.gui;

import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.event.EmptyItemListEvent;
import de.projectmodding.core.component.event.event.FillItemListEvent;
import de.projectmodding.core.component.event.event.ModCreationEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.component.loader.DefinitionLoader;
import de.projectmodding.core.controller.IMainController;
import de.projectmodding.core.enums.ModDataKey;
import de.projectmodding.core.model.definition.DefinitionVersionMap;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.core.model.mod.files.BaseFile;
import de.projectmodding.gui.manager.FilePanelManager;
import de.projectmodding.gui.panel.ScriptPanel;
import de.projectmodding.gui.popup.TreeJPopupMenu;
import de.projectmodding.gui.tree.node.ModPackageTreeNode;
import de.projectmodding.gui.tree.renderer.ModPackageTreeCellRenderer;
import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;

public final class MainForm extends BaseFrame {
    private final int HEIGHT_PERCENTAGE = 70;
    private final int WIDTH_PERCENTAGE = 47;
    private final int MINIMUM_WIDTH = 1200;
    private final int MINIMUM_HEIGHT = 800;
    private final DefinitionLoader definitionLoader = DefinitionLoader.getInstance();

    @Getter
    private final NewPackageForm newPackageForm = new NewPackageForm(eventSystem);
    @Setter
    private IMainController controller;

    private DefinitionVersionMap definitionVersionMap;

    private final JMenuBar menuBar = new JMenuBar();
    private JTree modTree;
    private JPanel mainPanel;
    private JPanel defaultDetailPanel;
    private JScrollPane treeScrollPane;
    private ModPackageTreeNode selectedNode = null;
    private TreeJPopupMenu treePopupMenu;
    private TreePath expandedPath = null;
    private final FilePanelManager filePanelManager;

    public MainForm(EventSystem eventSystem, FilePanelManager filePanelManager) {
        this.filePanelManager = filePanelManager;
        super(eventSystem);
    }

    @Override
    public void showForm() {
        init();
        initForm("Project-Modding", mainPanel, HEIGHT_PERCENTAGE, WIDTH_PERCENTAGE, EXIT_ON_CLOSE);
        controller.loadDefinition();
    }

    private void init() {
        createMenuBar();

        modTree.addTreeExpansionListener(createTreeExpansionListener());
        modTree.setCellRenderer(new ModPackageTreeCellRenderer());
        addModTreePopupMenu();
        modTree.setModel(null);
        defaultDetailPanel.addContainerListener(createDefaultDetailPanelContainerListener());

        this.setMinimumSize(new Dimension(MINIMUM_WIDTH, MINIMUM_HEIGHT));
    }

    private ContainerListener createDefaultDetailPanelContainerListener(){
        return new ContainerListener() {

            @Override
            public void componentAdded(ContainerEvent containerEvent) {
                updateUI();
            }

            @Override
            public void componentRemoved(ContainerEvent containerEvent) {
                updateUI();
            }

            private void updateUI(){
                revalidate();
                repaint();
            }
        };
    }

    private void addModTreePopupMenu() {
        modTree.setComponentPopupMenu(createModTreePopupMenu());
        modTree.addMouseListener(createMouseListener());
    }

    private TreeJPopupMenu createModTreePopupMenu() {
        TreeJPopupMenu popupMenu = new TreeJPopupMenu();

        JMenuItem newItem = new JMenuItem("New");
        newItem.addActionListener(Action -> {
            if (selectedNode != null) {
                ModPackageTreeNode node = new ModPackageTreeNode(controller.generateModData(selectedNode.getVersion(),
                                selectedNode.getModName(), selectedNode.getKey()));
                selectedNode.add(node);

                if (expandedPath != null)
                    modTree.expandPath(expandedPath);
                modTree.updateUI();
                updateLayout();
            }
        });

        popupMenu.add(newItem);
        popupMenu.setDoHide(true);
        this.treePopupMenu = popupMenu;
        return popupMenu;
    }

    private MouseListener createMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                treePopupMenu.setDoHide(true);
                treePopupMenu.setVisible(false);

                int selectedRow = modTree.getRowForLocation(e.getX(), e.getY());
                modTree.setSelectionRow(selectedRow);

                if (selectedRow != -1 && modTree.getLastSelectedPathComponent() instanceof ModPackageTreeNode node){
                    if (e.getButton() == MouseEvent.BUTTON3 && node.canAddFiles()) {
                        expandedPath = modTree.getPathForRow(selectedRow);
                        selectedNode = node;
                        treePopupMenu.setLocation(e.getXOnScreen(), e.getYOnScreen());
                        treePopupMenu.setDoHide(false);
                        treePopupMenu.setVisible(true);
                        removeAttributePanel();
                    }
                    else if ((e.getButton() == MouseEvent.BUTTON1 || e.getButton() == MouseEvent.BUTTON3) && node.getBaseFile() != null) {
                        loadAttributePanel(node.getBaseFile());
                    }
                    else
                        removeAttributePanel();
                    }
                }
            };
    }

    private void loadAttributePanel(BaseFile file) {
        JPanel panel = filePanelManager.getPanel(file.getClass());
        if (panel != null) {
            addJPanelToDefaultDetailPanel(panel);
            eventSystem.fireEvent(new FillItemListEvent(controller.getItemList()));
        }
        else
            removeAttributePanel();
    }

    private void addJPanelToDefaultDetailPanel(JPanel panel) {
        removeJPanelFromDefaultDetailPanel();
        panel.setEnabled(true);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        defaultDetailPanel.add(panel, constraints);
    }

    private void removeJPanelFromDefaultDetailPanel() {
        for (Component component :defaultDetailPanel.getComponents()){
            if (component instanceof JPanel panel){
                panel.setEnabled(false);
                defaultDetailPanel.removeAll();
                break;
            }
        }
    }

    private void removeAttributePanel(){
        eventSystem.fireEvent(new EmptyItemListEvent());
        removeJPanelFromDefaultDetailPanel();
    }

    private TreeExpansionListener createTreeExpansionListener() {
        return new TreeExpansionListener() {

            @Override
            public void treeExpanded(TreeExpansionEvent treeExpansionEvent) {
                updateLayout();
            }

            @Override
            public void treeCollapsed(TreeExpansionEvent treeExpansionEvent) {
                updateLayout();
            }


        };
    }

    private void updateLayout() {
        SwingUtilities.invokeLater(() -> {
            int rowCount = modTree.getRowCount();
            int rowHeight = modTree.getRowHeight() > 0 ? modTree.getRowHeight() + 20: 20;
            modTree.setPreferredSize(new Dimension(modTree.getPreferredSize().width, rowCount * rowHeight));
            treeScrollPane.revalidate();
            treeScrollPane.repaint();
        });
    }

    @Override
    protected void registerEvents() {
        eventSystem.registerEvent(ModCreationEvent.class, this);
    }

    private void createMenuBar() {
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(e -> {
            newPackageForm.showForm();
        });
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem settingMenuItem = new JMenuItem("Settings");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(action -> {
            dispose();
        });

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(settingMenuItem);
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
    }

    @Override
    public <T> void onEvent(Event<T> event) {
        if (event instanceof ModCreationEvent) {
            ModPackageModel eventData = ((ModCreationEvent) event).getData();
            DefaultTreeModel defaultTreeModel = controller.generateTreeModel(eventData);
            modTree.setModel(defaultTreeModel);
        }
    }
}
