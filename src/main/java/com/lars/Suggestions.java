package com.lars;

        import javax.swing.*;

/**
 * Created by lars on 31.05.16.
 */
public class Suggestions extends JFrame {
    private JList suggestionList;
    private JLabel toMatch;
    private JPanel rootPanel;

    public Suggestions(String needle, String[] suggestions) {
        super("Suggestions");

        toMatch.setText(needle);
        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (String sugg : suggestions) {
            dlm.addElement(sugg);
        }
        suggestionList.setModel(dlm);


        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
