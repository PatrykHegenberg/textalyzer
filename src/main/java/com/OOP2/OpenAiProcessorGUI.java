package com.OOP2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class OpenAiProcessorGUI {
    private JFrame frame;
    private JTextField filePathTextField;
    private JTextField apiKeyTextField;
    private JTextArea resultTextArea;

    public OpenAiProcessorGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("OpenAI Processor GUI");
        frame.setBounds(100, 100, 500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel inputPanel = new JPanel();
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);

        JLabel filePathLabel = new JLabel("File Path:");
        inputPanel.add(filePathLabel);

        filePathTextField = new JTextField();
        inputPanel.add(filePathTextField);
        filePathTextField.setColumns(20);

        JLabel apiKeyLabel = new JLabel("API Key:");
        inputPanel.add(apiKeyLabel);

        apiKeyTextField = new JTextField();
        inputPanel.add(apiKeyTextField);
        apiKeyTextField.setColumns(20);

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

    private void processFile() {
        String filePath = filePathTextField.getText();
        String apiKey = apiKeyTextField.getText();

        if (filePath.isEmpty() || apiKey.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both file path and API key.");
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

    public static void main(String[] args) {
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
    }
}
