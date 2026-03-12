package de.projectmodding.core.component.factory;

import de.projectmodding.core.enums.DialogEnum;

import javax.swing.*;
import java.awt.*;

public class DialogFactory {
    private static final String FILE_CHOOSER_OPEN = "Open";
    private static final String ERROR_DIALOG_ERROR = "Error";
    private static final String EMPTY_STRING = "";
    private DialogFactory(){}

    public static String createFolderFileDialogAndGetPath(Component parent, String title){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        chooser.setDialogTitle(title);
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.showDialog(parent, FILE_CHOOSER_OPEN);
        return chooser.getSelectedFile() != null ? chooser.getSelectedFile().getPath() : EMPTY_STRING;
    }

    public static DialogEnum createYesNoDialog(Component parent, String title, String question){
        final JOptionPane optionPane = new JOptionPane(
                question,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(title);
        dialog.pack();
        dialog.setVisible(true);

        return (Integer) optionPane.getValue() == JOptionPane.YES_OPTION ? DialogEnum.YES : DialogEnum.NO;
    }

    public static void createErrorDialog(String exceptionMessage, Component parent){
        JOptionPane.showMessageDialog(parent,
                exceptionMessage,
                ERROR_DIALOG_ERROR,
                JOptionPane.ERROR_MESSAGE);
    }
}