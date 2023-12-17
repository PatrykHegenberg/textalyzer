package com.OOP2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class OpenAiProcessorGUI {
    private JFrame frame;
    private String apiKey;
    private JTextArea resultTextArea;

    public OpenAiProcessorGUI() {
        initialize();
    }

    public OpenAiProcessorGUI(String apiKey) {
        this.apiKey = apiKey;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("OpenAI Processor GUI");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel inputPanel = new JPanel();
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);

        JButton fileChooserButton = new JButton("Choose File");
        inputPanel.add(fileChooserButton);
        fileChooserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        JButton processButton = new JButton("Process");
        inputPanel.add(processButton);
        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processFile();
            }
        });

        JScrollPane resultScrollPane = new JScrollPane();
        frame.getContentPane().add(resultScrollPane, BorderLayout.CENTER);

        resultTextArea = new JTextArea();
        resultScrollPane.setViewportView(resultTextArea);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            resultTextArea.setText(selectedFile.getAbsolutePath());
        }
    }

    private void processFile() {
        String filePath = resultTextArea.getText();

        if (filePath.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please choose a file.");
            return;
        }

        try {
            OpenAiProcessor openAiProcessor = new OpenAiProcessor(filePath, apiKey);
            String result = openAiProcessor.callOpenAiApi();
            resultTextArea.setText(result);
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(frame, "Error processing file: " + ex.getMessage());
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    OpenAiProcessorGUI window = new OpenAiProcessorGUI();
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
    public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            try {
                String apiKey = "YOUR_API_KEY_HERE"; // Ersetze dies durch deinen OpenAI API-Schlüssel
                OpenAiProcessorGUI window = new OpenAiProcessorGUI(apiKey);
                window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}
}
