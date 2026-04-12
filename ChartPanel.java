import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class ChartPanel extends JPanel {

    StudentRecordSystem srs;

    int low = 0, mid = 0, high = 0;
    int animLow = 0, animMid = 0, animHigh = 0;

    Rectangle lowBar, midBar, highBar;

    public ChartPanel(StudentRecordSystem srs) {
        this.srs = srs;
        setPreferredSize(new Dimension(600, 260));
        setBackground(new Color(25, 25, 35));

        // CLICK DETECTION
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (lowBar != null && lowBar.contains(e.getPoint())) {
                    showStudents("0-50");
                } else if (midBar != null && midBar.contains(e.getPoint())) {
                    showStudents("50-75");
                } else if (highBar != null && highBar.contains(e.getPoint())) {
                    showStudents("75-100");
                }
            }
        });
    }

    // UPDATE DATA
    void updateData() {
        low = mid = high = 0;

        Node temp = srs.head;
        while (temp != null) {
            if (temp.marks < 50) low++;
            else if (temp.marks <= 75) mid++;
            else high++;
            temp = temp.next;
        }

        animate();
    }

    // ANIMATION
    void animate() {
        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            if (animLow < low) animLow++;
            if (animMid < mid) animMid++;
            if (animHigh < high) animHigh++;

            repaint();

            if (animLow >= low && animMid >= mid && animHigh >= high) {
                timer.stop();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int baseY = getHeight() - 40;
        int barWidth = 80;
        int scale = 25;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // AXIS
        g2.setColor(Color.LIGHT_GRAY);
        g2.drawLine(50, baseY, getWidth() - 50, baseY);

        // LOW
        int lowH = animLow * scale;
        g2.setColor(new Color(231, 76, 60));
        g2.fillRoundRect(100, baseY - lowH, barWidth, lowH, 20, 20);
        lowBar = new Rectangle(100, baseY - lowH, barWidth, lowH);
        g2.setColor(Color.WHITE);
        g2.drawString("0-50 (" + low + ")", 100, baseY + 15);

        // MID
        int midH = animMid * scale;
        g2.setColor(new Color(241, 196, 15));
        g2.fillRoundRect(250, baseY - midH, barWidth, midH, 20, 20);
        midBar = new Rectangle(250, baseY - midH, barWidth, midH);
        g2.setColor(Color.WHITE);
        g2.drawString("50-75 (" + mid + ")", 250, baseY + 15);

        // HIGH
        int highH = animHigh * scale;
        g2.setColor(new Color(46, 204, 113));
        g2.fillRoundRect(400, baseY - highH, barWidth, highH, 20, 20);
        highBar = new Rectangle(400, baseY - highH, barWidth, highH);
        g2.setColor(Color.WHITE);
        g2.drawString("75-100 (" + high + ")", 400, baseY + 15);

        // TITLE
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.drawString("Marks Distribution", getWidth() / 2 - 70, 20);
    }

    // SHOW STUDENTS ON CLICK
    void showStudents(String range) {
        StringBuilder result = new StringBuilder();

        Node temp = srs.head;
        while (temp != null) {
            if ((range.equals("0-50") && temp.marks < 50) ||
                (range.equals("50-75") && temp.marks <= 75 && temp.marks >= 50) ||
                (range.equals("75-100") && temp.marks > 75)) {

                result.append(temp.rollNo)
                      .append(" - ")
                      .append(temp.name)
                      .append(" (")
                      .append(temp.marks)
                      .append(")\n");
            }
            temp = temp.next;
        }

        JOptionPane.showMessageDialog(this,
                result.length() == 0 ? "No students" : result.toString(),
                "Students in " + range,
                JOptionPane.INFORMATION_MESSAGE);
    }
}