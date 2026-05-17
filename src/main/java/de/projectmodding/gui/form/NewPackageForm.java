package de.projectmodding.gui.form;

import de.projectmodding.core.component.container.Container;
import de.projectmodding.core.component.event.Event;
import de.projectmodding.core.component.event.event.ModCreationEvent;
import de.projectmodding.core.component.event.system.EventSystem;
import de.projectmodding.core.component.factory.DialogFactory;
import de.projectmodding.core.component.validator.NewModFormValidator;
import de.projectmodding.core.controller.INewPackageController;
import de.projectmodding.core.controller.NewPackageController;
import de.projectmodding.core.model.mod.ModPackageModel;
import de.projectmodding.gui.constant.FlatLafIcons;
import lombok.Setter;

import javax.swing.*;
import java.util.Objects;

public final class NewPackageForm extends BaseFrame {
    private final int HEIGHT_PERCENTAGE = 15;
    private final int WIDTH_PERCENTAGE = 20;
    private final String DEFAULT_VERSION = "1.0.0";

    private JButton createButton;
    private JPanel bottomPanel;
    private JPanel mainPanel;
    private JTextField packageNameTextField;
    private JLabel packageNameLabel;
    private JTextField locationTextField;
    private JLabel locationLabel;
    private JButton locationButton;
    private JLabel gameVersionLabel;
    private JComboBox<String> gameVersionCombobox;
    private JLabel packageVersionLabel;
    private JTextField packageVersionTextField;

    public NewPackageForm(Container mainContainer) {
        super(mainContainer);
        initListeners();
    }

    void initListeners(){
        assert locationButton != null;
        locationButton.setIcon(FlatLafIcons.FILE_CHOOSER_ICON);
        locationButton.setText("");
        locationButton.addActionListener(action -> {
            locationTextField.setText(container.resolve(NewPackageController.class).chooseLocation());
        });


        assert createButton != null;
        createButton.addActionListener(action -> {
            try{
                NewModFormValidator.validate(packageNameTextField.getText(), locationTextField.getText());
                ModPackageModel modPackageModel = container.resolve(NewPackageController.class).createPackage(
                        packageNameTextField.getText(),
                        locationTextField.getText(),
                        Objects.requireNonNull(gameVersionCombobox.getSelectedItem()).toString(),
                        packageVersionTextField.getText()
                );

                setVisible(false);
                container.resolve(EventSystem.class).fireEvent(new ModCreationEvent(modPackageModel));
            }
            catch(Exception exception){
                DialogFactory.createErrorDialog(exception.getMessage(), this);
            }
        });
    }

    @Override
    public void showForm() {
        initForm("Mod Creator", mainPanel, HEIGHT_PERCENTAGE, WIDTH_PERCENTAGE, HIDE_ON_CLOSE);
        setResizable(false);
        clear();
        fetchData();
        pack();
    }

    @Override
    protected void registerEvents() {

    }

    private void fetchData(){
        container.resolve(NewPackageController.class).loadGameVersionList().forEach(version -> {
            gameVersionCombobox.addItem(version);
        });
        packageVersionTextField.setText(DEFAULT_VERSION);
    }

    private void clear(){
        packageNameTextField.setText("");
        locationTextField.setText("");
        gameVersionCombobox.removeAllItems();
    }

    @Override
    public <T> void onEvent(Event<T> event) {
    }
}
