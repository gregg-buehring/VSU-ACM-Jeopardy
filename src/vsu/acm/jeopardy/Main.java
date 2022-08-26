package vsu.acm.jeopardy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Main extends JFrame {
    public static final Color BLUE_BACKGROUND = new Color(0x00309E);
    public static final Color YELLOW_TEXT = new Color(0xE6AA5C);

    private static final int SPACING = 10;
    private static final int CARD_HEIGHT = 130;
    private static final int CARD_WIDTH = (int) (CARD_HEIGHT * 1.6888);

    public static Font questionFont;
    public static Font headerFont;
    public static Font cardFont;
    public static String[] headers = new String[5];
    public static Question[][] questions = new Question[5][5];

    private final SpringLayout layout = new SpringLayout();
    private JTextPane[] headerLabels = new JTextPane[headers.length]; // JLabel does not support word wrapping
    private JButton[][] triviaButtons = new JButton[questions.length][questions[0].length];

    public static void main(String[] args) {
        try {
            Font fq = Font.createFont(Font.TRUETYPE_FONT, new File("font-question.ttf"));
            questionFont = fq.deriveFont(36.0f);
            Font fh = Font.createFont(Font.TRUETYPE_FONT, new File("font-header.ttf"));
            headerFont = fh.deriveFont(36.0f);
            Font fc = Font.createFont(Font.TRUETYPE_FONT, new File("font-card.otf"));
            cardFont = fc.deriveFont(88.0f);
        } catch(Exception ex) {
            questionFont = new Font("Segoe UI", Font.PLAIN, 24);
            headerFont = new Font("Times New Roman", Font.BOLD, 36);
            cardFont = new Font("Times New Roman", Font.BOLD, 72);
            ex.printStackTrace();
        }
        try {
            loadQuestions();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        Main instance = new Main();
        instance.setVisible(true);
    }

    private Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(layout);
        getContentPane().setBackground(Color.BLACK);
        addComponents();
        setTitle("Trivia");
        layout.putConstraint(SpringLayout.EAST, this.getContentPane(), SPACING, SpringLayout.EAST,
                triviaButtons[triviaButtons.length - 1][triviaButtons[0].length - 1]);
        layout.putConstraint(SpringLayout.SOUTH, this.getContentPane(), SPACING, SpringLayout.SOUTH,
                triviaButtons[triviaButtons.length - 1][triviaButtons[0].length - 1]);
        this.pack();
        setLocationRelativeTo(null);
    }

    private void addComponents() {
        for(int i = 0; i < headerLabels.length; i++) {
            headerLabels[i] = new JTextPane();
            headerLabels[i].setText(headers[i].toUpperCase());
            headerLabels[i].setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
            headerLabels[i].setFont(headerFont);
            headerLabels[i].setEditable(false);
            StyledDocument doc = headerLabels[i].getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
            headerLabels[i].setOpaque(true);
            headerLabels[i].setForeground(Color.WHITE);
            headerLabels[i].setBackground(BLUE_BACKGROUND);
            if(i == 0) {
                layout.putConstraint(SpringLayout.WEST, headerLabels[i], SPACING, SpringLayout.WEST,
                        this.getContentPane());
            } else {
                layout.putConstraint(SpringLayout.WEST, headerLabels[i], SPACING, SpringLayout.EAST,
                        headerLabels[i - 1]);
            }
            layout.putConstraint(SpringLayout.NORTH, headerLabels[i], SPACING, SpringLayout.NORTH,
                    this.getContentPane());
            add(headerLabels[i]);
        }
        for(int y = 0; y < triviaButtons.length; y++) {
            for(int x = 0; x < triviaButtons[y].length; x++) {
                triviaButtons[y][x] = new JButton("$" + ((y + 1) * 100));
                triviaButtons[y][x].setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
                triviaButtons[y][x].setFont(cardFont);
                triviaButtons[y][x].setForeground(YELLOW_TEXT);
                triviaButtons[y][x].setBackground(BLUE_BACKGROUND);
                triviaButtons[y][x].setBorder(null);
                triviaButtons[y][x].setFocusPainted(false);
                if(x == 0) {
                    layout.putConstraint(SpringLayout.WEST, triviaButtons[y][x], SPACING, SpringLayout.WEST,
                            this.getContentPane());
                } else {
                    layout.putConstraint(SpringLayout.WEST, triviaButtons[y][x], SPACING, SpringLayout.EAST,
                            triviaButtons[y][x - 1]);
                }
                if(y == 0) {
                    layout.putConstraint(SpringLayout.NORTH, triviaButtons[y][x], SPACING * 2, SpringLayout.SOUTH,
                            headerLabels[x]);
                } else {
                    layout.putConstraint(SpringLayout.NORTH, triviaButtons[y][x], SPACING, SpringLayout.SOUTH,
                            triviaButtons[y - 1][x]);
                }
                final int qy = y;
                final int qx = x;
                triviaButtons[y][x].addActionListener(e -> {
                    new QuestionFrame(Main.this, questions[qy][qx]).setVisible(true);
                });
                add(triviaButtons[y][x]);
            }
        }
    }

    private static void loadQuestions() throws IOException {
        File file = new File("qa.txt");
        if(!file.exists()) {
            JOptionPane.showMessageDialog(null, "Question/answer file not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        int x = -1;
        int y = 0;
        for(String line : Files.readAllLines(file.toPath())) {
            if(line.startsWith("+")) {
                x++;
                y = 0;
                headers[x] = line.substring(1);
            } else {
                String[] split = line.split(";", 2);
                questions[y][x] = new Question(split[0], split[1]);
                y++;
            }
        }
    }
}
