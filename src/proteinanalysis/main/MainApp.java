package proteinanalysis.main;

import proteinanalysis.api.ApiFetcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// JavaFX imports
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainApp {

    public static void main(String[] args) {

        // IMPORTANT: Initialize JavaFX
        JFXPanel fxPanelInit = new JFXPanel();

        JFrame frame = new JFrame("Protein Analyzer Dashboard");
        frame.setSize(900, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        frame.add(panel);

        // 🔹 Label
        JLabel label = new JLabel("Enter Protein ID:");
        label.setBounds(20, 20, 150, 25);
        panel.add(label);

        // 🔹 Input Field
        JTextField textField = new JTextField();
        textField.setBounds(180, 20, 200, 25);
        panel.add(textField);

        // 🔹 Analyze Button
        JButton analyzeButton = new JButton("Analyze");
        analyzeButton.setBounds(400, 20, 100, 25);
        panel.add(analyzeButton);

        // 🔹 3D Button
        JButton view3DButton = new JButton("View 3D");
        view3DButton.setBounds(520, 20, 120, 25);
        panel.add(view3DButton);

        // 🔹 Output Area
        JTextArea outputArea = new JTextArea();
        outputArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBounds(20, 70, 400, 550);
        panel.add(scrollPane);

        // ✅ JavaFX Panel (REPLACES JEditorPane)
        JFXPanel jfxPanel = new JFXPanel();
        jfxPanel.setBounds(450, 70, 400, 550);
        panel.add(jfxPanel);

        // 🔥 ANALYZE ACTION
        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String proteinId = textField.getText();

                String sequence = ApiFetcher.getProteinSequence(proteinId);

                if (sequence.startsWith("ERROR")) {
                    outputArea.setText(sequence);
                    return;
                }

                int length = sequence.length();

                int countA = 0;
                for (char c : sequence.toCharArray()) {
                    if (c == 'A') countA++;
                }

                String result = "🔬 Protein Report\n\n";
                result += "Protein ID: " + proteinId + "\n\n";
                result += "Sequence:\n" + sequence + "\n\n";
                result += "Length: " + length + "\n";
                result += "Count of A: " + countA + "\n";

                outputArea.setText(result);
            }
        });

        // 🔥 3D VIEW ACTION (JavaFX WebView)
        view3DButton.addActionListener(e -> {
            Platform.runLater(() -> {
                WebView webView = new WebView();
                WebEngine engine = webView.getEngine();

                engine.load("https://molstar.org/viewer/?pdb=2hhb");

                Scene scene = new Scene(webView);
                jfxPanel.setScene(scene);
            });
        });

        frame.setVisible(true);
    }
}