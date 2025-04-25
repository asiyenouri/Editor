
package editor;
import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    public static void openFile(TextEditor textEditor, JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                textArea.setText(content);
                textEditor.currentFile = file;
                textEditor.setTitle(file.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor, "Error reading file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void saveFile(TextEditor textEditor, JTextArea textArea) {
        if (textEditor.currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(textEditor) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(textArea.getText());
                    textEditor.currentFile = file;
                    textEditor.setTitle(file.getName());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(textEditor, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            try (FileWriter writer = new FileWriter(textEditor.currentFile)) {
                writer.write(textArea.getText());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(textEditor, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void newFile(TextEditor textEditor, JTextArea textArea) {
            if (textArea.getText().length() > 0) {
                int option = JOptionPane.showConfirmDialog(
                        textEditor,
                        "می‌خواهید تغییرات فعلی را ذخیره کنید؟",
                        "ذخیره تغییرات",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (option == JOptionPane.YES_OPTION) {
                    saveFile(textEditor, textArea);
                } else if (option == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            textArea.setText("");
            textEditor.currentFile = null;
            textEditor.setTitle("new title");
        }
    }
