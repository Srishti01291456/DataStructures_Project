import javax.swing.*;
import java.awt.*;

class PieChartFrame extends JFrame {

    StudentRecordSystem srs;

    public PieChartFrame(StudentRecordSystem srs) {
        this.srs = srs;
        setTitle("Pie Chart");
        setSize(400, 400);
        setLocationRelativeTo(null);

        add(new PiePanel());
        setVisible(true);
    }

    class PiePanel extends JPanel {

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int low = 0, mid = 0, high = 0;

            Node temp = srs.head;
            while (temp != null) {
                if (temp.marks < 50) low++;
                else if (temp.marks <= 75) mid++;
                else high++;
                temp = temp.next;
            }

            int total = low + mid + high;
            if (total == 0) return;

            int start = 0;

            int lowAngle = (int) (360.0 * low / total);
            int midAngle = (int) (360.0 * mid / total);
            int highAngle = 360 - lowAngle - midAngle;

            g.setColor(Color.RED);
            g.fillArc(100, 100, 200, 200, start, lowAngle);

            g.setColor(Color.ORANGE);
            g.fillArc(100, 100, 200, 200, start + lowAngle, midAngle);

            g.setColor(Color.GREEN);
            g.fillArc(100, 100, 200, 200, start + lowAngle + midAngle, highAngle);
        }
    }
}