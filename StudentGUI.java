import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class StudentGUI extends JFrame {

    StudentRecordSystem srs = new StudentRecordSystem();

    JTextField rollField, nameField, marksField, searchField;
    DefaultTableModel model;

    public StudentGUI() {
        setTitle("Student Record System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30, 30, 30));
        
    UIManager.put("Label.foreground", Color.WHITE);
    UIManager.put("Button.background", new Color(50, 50, 50));
    UIManager.put("Button.foreground", Color.WHITE);
    UIManager.put("TextField.background", new Color(70, 70, 70));
    UIManager.put("TextField.foreground", Color.WHITE);

        // ===== TOP PANEL (INPUT) =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        topPanel.setBackground(new Color(30, 30, 30));  
        rollField = new JTextField(5);
        nameField = new JTextField(10);
        marksField = new JTextField(5);

        topPanel.add(new JLabel("Roll"));
        topPanel.add(rollField);
        topPanel.add(new JLabel("Name"));
        topPanel.add(nameField);
        topPanel.add(new JLabel("Marks"));
        topPanel.add(marksField);

        add(topPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new String[]{"Roll", "Name", "Marks"}, 0);
        JTable table = new JTable(model);
        table.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        table.setBackground(new Color(45, 45, 60));
        table.setForeground(Color.WHITE);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        rollField.setText(model.getValueAt(row, 0).toString());
        nameField.setText(model.getValueAt(row, 1).toString());
        marksField.setText(model.getValueAt(row, 2).toString());
    }
});
        ChartPanel chart = new ChartPanel(srs);

JPanel centerPanel = new JPanel(new BorderLayout());

centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
centerPanel.add(chart, BorderLayout.SOUTH);   // 👈 IMPORTANT

add(centerPanel, BorderLayout.CENTER);
        // ===== BUTTON PANEL =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBackground(new Color(30, 30, 30));

        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");
        JButton updateBtn = new JButton("Update");
        JButton sortMarksBtn = new JButton("Sort Marks");
        JButton sortNameBtn = new JButton("Sort Name");
        String[] ranges = {"All", "0-50", "50-75", "75-100"};
        JComboBox<String> filterBox = new JComboBox<>(ranges);
        JButton filterBtn = new JButton("Filter");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");
        JButton pieBtn = new JButton("Show Pie Chart");


        searchField = new JTextField(10);
        JButton searchBtn = new JButton("Search");
        addBtn.setBackground(new Color(46, 204, 113));   // green
        deleteBtn.setBackground(new Color(231, 76, 60)); // red
        updateBtn.setBackground(new Color(52, 152, 219));// blue

        btnPanel.add(addBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(updateBtn);
        btnPanel.add(sortMarksBtn);
        btnPanel.add(sortNameBtn);
        btnPanel.add(new JLabel("Search:"));
        btnPanel.add(searchField);
        btnPanel.add(searchBtn);
        btnPanel.add(filterBox);
        btnPanel.add(filterBtn);
        btnPanel.add(saveBtn);
        btnPanel.add(loadBtn);
        btnPanel.add(pieBtn);

        add(btnPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====

        // ADD
        addBtn.addActionListener(e -> {
            int r = Integer.parseInt(rollField.getText());
            String n = nameField.getText();
            int m = Integer.parseInt(marksField.getText());

            srs.addStudent(r, n, m);
            refreshTable();
            chart.updateData();
            chart.revalidate();
        });

        // DELETE
        deleteBtn.addActionListener(e -> {
            int r = Integer.parseInt(rollField.getText());
            srs.deleteStudent(r);
            refreshTable();
            chart.updateData();
            chart.revalidate();
        });

        // UPDATE
        updateBtn.addActionListener(e -> {
            int r = Integer.parseInt(rollField.getText());
            String n = nameField.getText();
            int m = Integer.parseInt(marksField.getText());

            srs.updateStudent(r, n, m);
            refreshTable();
            chart.updateData();
            chart.revalidate();
        });
        pieBtn.addActionListener(e -> {
    new PieChartFrame(srs);
});

        // SORT MARKS
        sortMarksBtn.addActionListener(e -> {
            srs.sortByMarks();
            refreshTable();
            chart.updateData();
            chart.revalidate();
        });

        // SORT NAME
        sortNameBtn.addActionListener(e -> {
            srs.sortByName();
            refreshTable();
            chart.updateData();
            chart.revalidate();
        });
        chart.repaint();
        // SEARCH
        searchBtn.addActionListener(e -> {
            String keyword = searchField.getText().toLowerCase();
            model.setRowCount(0);

            Node temp = srs.head;
            while (temp != null) {
                if (temp.name.toLowerCase().contains(keyword)) {
                    model.addRow(new Object[]{temp.rollNo, temp.name, temp.marks});
                }
                temp = temp.next;
            }
        });

        filterBtn.addActionListener(e -> {
    String selected = filterBox.getSelectedItem().toString();

    model.setRowCount(0);
    Node temp = srs.head;

    while (temp != null) {
        boolean show = false;

        switch (selected) {
            case "0-50":
                show = temp.marks <= 50;
                break;
            case "50-75":
                show = temp.marks > 50 && temp.marks <= 75;
                break;
            case "75-100":
                show = temp.marks > 75;
                break;
            default:
                show = true;
        }

        if (show) {
            model.addRow(new Object[]{temp.rollNo, temp.name, temp.marks});
        }

        temp = temp.next;
    }
});
saveBtn.addActionListener(e -> {
    srs.saveToFile();
});

loadBtn.addActionListener(e -> {
    srs.loadFromFile();
    refreshTable();
    chart.updateData();
    chart.revalidate();
});

        setVisible(true);
    }

    // REFRESH TABLE
    void refreshTable() {
        model.setRowCount(0);
        Node temp = srs.head;

        while (temp != null) {
            model.addRow(new Object[]{temp.rollNo, temp.name, temp.marks});
            temp = temp.next;
        }
    }
    

    public static void main(String[] args) {
        new StudentGUI();
    }
}