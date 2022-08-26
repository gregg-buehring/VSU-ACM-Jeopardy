package vsu.acm.jeopardy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class QuestionFrame extends JFrame {
    private final Question question;
    private final SpringLayout layout = new SpringLayout();
    private final JTextPane questionPane = new JTextPane();
    private final JButton showButton = new JButton("SHOW ANSWER");
    private final JButton closeButton = new JButton("CLOSE");
    private final JTextPane answerPane = new JTextPane();

    public QuestionFrame(JFrame parent, Question question) {
        this.question = question;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(layout);
        getContentPane().setBackground(Main.BLUE_BACKGROUND);
        addComponents();
        setTitle("Trivia Question");
        layout.putConstraint(SpringLayout.EAST, this.getContentPane(), 100, SpringLayout.EAST, answerPane);
        layout.putConstraint(SpringLayout.SOUTH, this.getContentPane(), 100, SpringLayout.SOUTH, closeButton);
        this.pack();
        setLocationRelativeTo(parent);
    }

    private void addComponents() {
        questionPane.setPreferredSize(new Dimension(600, 400));
        questionPane.setText(question.question());
        questionPane.setFont(Main.questionFont);
        questionPane.setEditable(false);
        questionPane.setBorder(null);
        questionPane.setBackground(Main.BLUE_BACKGROUND);
        questionPane.setForeground(Color.WHITE);
        StyledDocument qdoc = questionPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        qdoc.setParagraphAttributes(0, qdoc.getLength(), center, false);
        layout.putConstraint(SpringLayout.WEST, questionPane, 100, SpringLayout.WEST, this.getContentPane());
        layout.putConstraint(SpringLayout.NORTH, questionPane, 100, SpringLayout.NORTH, this.getContentPane());
        add(questionPane);
        answerPane.setPreferredSize(new Dimension(600, 200));
        answerPane.setFont(questionPane.getFont());
        answerPane.setEditable(false);
        answerPane.setBorder(null);
        answerPane.setBackground(Main.BLUE_BACKGROUND);
        answerPane.setForeground(Color.WHITE);
        StyledDocument adoc = answerPane.getStyledDocument();
        adoc.setParagraphAttributes(0, adoc.getLength(), center, false);
        layout.putConstraint(SpringLayout.WEST, answerPane, 0, SpringLayout.WEST, questionPane);
        layout.putConstraint(SpringLayout.NORTH, answerPane, 20, SpringLayout.SOUTH, questionPane);
        add(answerPane);
        showButton.setPreferredSize(new Dimension(150, 50));
        showButton.setMargin(new Insets(0, 0, 0, 0));
        showButton.setFocusPainted(false);
        showButton.setBackground(getContentPane().getBackground());
        showButton.setForeground(Main.YELLOW_TEXT);
        showButton.setFont(Main.headerFont.deriveFont(18.0f));
        showButton.setBorder(BorderFactory.createLineBorder(Main.YELLOW_TEXT, 3));
        layout.putConstraint(SpringLayout.WEST, showButton, 30, SpringLayout.WEST, answerPane);
        layout.putConstraint(SpringLayout.NORTH, showButton, 20, SpringLayout.SOUTH, answerPane);
        showButton.addActionListener(e -> {
            answerPane.setText(question.answer());
        });
        add(showButton);
        closeButton.setPreferredSize(new Dimension(150, 50));
        closeButton.setMargin(new Insets(0, 0, 0, 0));
        closeButton.setFocusPainted(false);
        closeButton.setBackground(getContentPane().getBackground());
        closeButton.setForeground(Main.YELLOW_TEXT);
        closeButton.setFont(showButton.getFont());
        closeButton.setBorder(showButton.getBorder());
        layout.putConstraint(SpringLayout.EAST, closeButton, -30, SpringLayout.EAST, answerPane);
        layout.putConstraint(SpringLayout.NORTH, closeButton, 20, SpringLayout.SOUTH, answerPane);
        closeButton.addActionListener(e -> {
            dispose();
        });
        add(closeButton);
    }
}
