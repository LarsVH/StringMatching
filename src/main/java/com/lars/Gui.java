package com.lars;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by lars vh on 29.05.16.
 */
public class Gui extends JFrame {
    private JList<String> needlePick;
    private JTextField needle1;
    private JTable algoTable;
    private JSlider tresholdSlider;
    private JButton execute;
    private JPanel rootPanel;
    private JLabel tresholdLabel;
    private JButton results;
    private Util util = new Util();
    Double treshold = 0.5;
    ArrayList<String> needles;


    public Gui(ArrayList<String> needles) {
        super("String Matching Performance");

        this.needles = needles;
        needle1.setText(needles.get(0));

        DefaultListModel<String> dlm = new DefaultListModel<>();
        for (String needle : needles) {
            dlm.addElement(needle);
        }

        needlePick.setModel(dlm);
        ListSelectionModel listSelectionModel = needlePick.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new ListSelectionListener() {
                    @Override
                    public void valueChanged(ListSelectionEvent e) {
                        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
                        needle1.setText(needlePick.getSelectedValue());
                    }
                });


        tresholdSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Integer tresholdsl = tresholdSlider.getValue();
                treshold = tresholdsl.doubleValue() / 100;
                DecimalFormat f = new DecimalFormat("#0.0");

                tresholdLabel.setText(f.format(treshold));
            }
        });

        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ArrayList<ResultTriple> results = util.performAlgorithms(needle1.getText(), needle2.getText(), treshold);

                createTable(needles);
            }
        });

        results.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] test = {"hihi", "hehe"};
                Suggestions suggestions = new Suggestions("haha", test);
            }
        });

        createTable(needles);

        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        util.getSynonyms("Location");
    }

    private void createTable(ArrayList<String> needles) {
        MyTableModel mtm = new MyTableModel();
        String ndl0 = needles.get(0);
        ArrayList<ResultTriple> results0 = util.performAlgorithms(needle1.getText(), ndl0, treshold);
        String[] colnames = new String[results0.size() + 1];
        colnames[0] = "^>";
        String[][] data = new String[needles.size()][results0.size() + 1];
        Boolean[][] layout = new Boolean[needles.size()][results0.size() + 1];

        // i = needles (Location, Kitchen, ...)
        // [row] [col]
        for (int i = 0; i < needles.size(); i++) {
            String currNeedle = needles.get(i);
            data[i][0] = currNeedle;

            ArrayList<ResultTriple> resultsi = util.performAlgorithms(needle1.getText(), currNeedle, treshold);

            // j = Algo's (Hamming, Levenshtein, etc.)
            for (int j = 0; j < resultsi.size(); j++) {
                ResultTriple curr = resultsi.get(j);
                colnames[j + 1] = curr.getAlgo();
                data[i][j + 1] = curr.getScore().toString();

                layout[i][j + 1] = curr.getAccepted();
                System.out.println(curr.getScore());
            }

        }
        mtm.data = data;
        mtm.columnNames = colnames;


        algoTable.setModel(mtm);
        algoTable.setDefaultRenderer(Object.class, new CustomTableRenderer(layout));
    }
}

class MyTableModel extends AbstractTableModel {
    private boolean DEBUG = false;

    /*public MyTableModel(String[] columnNames, Object[][] data){
        this.columnNames = columnNames;
        this.data = data;
    }*/

    public String[] columnNames = {"First Name", "Last Name", "Sport",
            "# of Years", "Vegetarian"};

    public Object[][] data;/* = {
            { "Mary", "Campione", "Snowboarding", new Integer(5),
                    new Boolean(false) },
            { "Alison", "Huml", "Rowing", new Integer(3), new Boolean(true) },
            { "Kathy", "Walrath", "Knitting", new Integer(2),
                    new Boolean(false) },
            { "Sharon", "Zakhour", "Speed reading", new Integer(20),
                    new Boolean(true) },
            { "Philip", "Milne", "Pool", new Integer(10),
                    new Boolean(false) } };*/

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        //DecimalFormat f = new DecimalFormat("#0.0");
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If we didn't implement this method, then the last column
     * would contain text ("true"/"false"), rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's editable.
     */
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    /*
     * Don't need to implement this method unless your table's data can
     * change.
     */
    public void setValueAt(Double value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value + " (an instance of "
                    + value.getClass() + ")");
        }

        data[row][col] = value;
        fireTableCellUpdated(row, col);

        if (DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }

    private void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + data[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

}

//Custom DefaultTableCellRenderer
class CustomTableRenderer extends DefaultTableCellRenderer {
    Boolean[][] acceptance;

    public CustomTableRenderer(Boolean[][] acceptance){
        this.acceptance = acceptance;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                          boolean isSelected, boolean hasFocus, int row, int col) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, col);

        Object cell = table.getValueAt(row, col);

        c.setForeground(Color.CYAN);
        if (col != 0) {
            if (acceptance[row][col] == null) {}
            else if (acceptance[row][col]) {
                c.setForeground(Color.GREEN);
                //c.setFont(new Font("Dialog", Font.BOLD, 12));
            } else
                c.setForeground(Color.RED);
        } else if (col == 0) {
            c.setForeground(Color.BLUE);
            //stay at default
            //c.setForeground(Color.BLACK);
            //c.setFont(new Font("Dialog", Font.PLAIN, 12));
        }
        return c;
    }

}