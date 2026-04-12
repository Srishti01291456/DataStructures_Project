import java.io.*;

class StudentRecordSystem {
    Node head;
    boolean rollExists(int roll) {
    Node temp = head;

    while (temp != null) {
        if (temp.rollNo == roll) {
            return true;
        }
        temp = temp.next;
    }
    return false;
}
    // ================= ADD =================
    void addStudent(int r, String n, int m) {

    // 🔒 Check duplicate
    if (rollExists(r)) {
        System.out.println("❌ Roll number already exists!");
        return;
    }

    Node newNode = new Node(r, n, m);
    newNode.next = head;
    head = newNode;

    System.out.println("✅ Student Added!");
}
    // ================= DISPLAY =================
    void display() {
        if (head == null) {
            System.out.println("No records found.");
            return;
        }

        Node temp = head;
        while (temp != null) {
            System.out.println(temp.rollNo + " | " + temp.name + " | " + temp.marks);
            temp = temp.next;
        }
    }

    // ================= SEARCH =================
    void search(int roll) {
        Node temp = head;
        while (temp != null) {
            if (temp.rollNo == roll) {
                System.out.println("Found: " + temp.name);
                return;
            }
            temp = temp.next;
        }
        System.out.println("Not Found");
    }

    // ================= DELETE =================
    void deleteStudent(int roll) {
        if (head == null) return;

        if (head.rollNo == roll) {
            head = head.next;
            System.out.println("Deleted");
            return;
        }

        Node temp = head;
        while (temp.next != null) {
            if (temp.next.rollNo == roll) {
                temp.next = temp.next.next;
                System.out.println("Deleted");
                return;
            }
            temp = temp.next;
        }

        System.out.println("Student Not Found");
    }

    // ================= UPDATE =================
    void updateStudent(int roll, String name, int marks) {
    Node temp = head;

    while (temp != null) {
        if (temp.rollNo == roll) {
            temp.name = name;
            temp.marks = marks;
            System.out.println("Updated Successfully");
            return;
        }
        temp = temp.next;
    }

    System.out.println("Student Not Found");
}

    // ================= MERGE SORT (MARKS) =================
    Node mergeSort(Node head) {
        if (head == null || head.next == null) return head;

        Node mid = getMiddle(head);
        Node next = mid.next;
        mid.next = null;

        Node left = mergeSort(head);
        Node right = mergeSort(next);

        return merge(left, right);
    }

    Node merge(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        if (left.marks <= right.marks) {
            left.next = merge(left.next, right);
            return left;
        } else {
            right.next = merge(left, right.next);
            return right;
        }
    }

    void sortByMarks() {
        head = mergeSort(head);
        System.out.println("Sorted by Marks!");
    }

    // ================= MERGE SORT (NAME) =================
    Node mergeSortByName(Node head) {
        if (head == null || head.next == null) return head;

        Node mid = getMiddle(head);
        Node next = mid.next;
        mid.next = null;

        Node left = mergeSortByName(head);
        Node right = mergeSortByName(next);

        return mergeByName(left, right);
    }

    Node mergeByName(Node left, Node right) {
        if (left == null) return right;
        if (right == null) return left;

        if (left.name.compareToIgnoreCase(right.name) <= 0) {
            left.next = mergeByName(left.next, right);
            return left;
        } else {
            right.next = mergeByName(left, right.next);
            return right;
        }
    }

    // ================= FIND MIDDLE =================
    Node getMiddle(Node head) {
        Node slow = head, fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // ================= FILE SAVE =================
    void saveToFile() {
        try {
            FileWriter fw = new FileWriter("students.txt");
            Node temp = head;

            while (temp != null) {
                fw.write(temp.rollNo + "," + temp.name + "," + temp.marks + "\n");
                temp = temp.next;
            }

            fw.close();
            System.out.println("Saved to file!");
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }
    void searchByName(String keyword) {
    Node temp = head;
    boolean found = false;

    while (temp != null) {
        if (temp.name.toLowerCase().contains(keyword.toLowerCase())) {
            System.out.println(temp.rollNo + " | " + temp.name + " | " + temp.marks);
            found = true;
        }
        temp = temp.next;
    }

    if (!found) System.out.println("No matching students found");
}
    void searchByMarksRange(int min, int max) {
    Node temp = head;
    boolean found = false;

    while (temp != null) {
        if (temp.marks >= min && temp.marks <= max) {
            System.out.println(temp.rollNo + " | " + temp.name + " | " + temp.marks);
            found = true;
        }
        temp = temp.next;
    }

    if (!found) System.out.println("No students in this range");
}
    void findTopper() {
    if (head == null) {
        System.out.println("No data");
        return;
    }

    Node temp = head;
    Node topper = head;

    while (temp != null) {
        if (temp.marks > topper.marks) {
            topper = temp;
        }
        temp = temp.next;
    }

    System.out.println("Topper: " + topper.rollNo + " | " + topper.name + " | " + topper.marks);
}
    void searchAdvanced(String keyword, int minMarks) {
    Node temp = head;
    boolean found = false;

    while (temp != null) {
        if (temp.name.toLowerCase().contains(keyword.toLowerCase()) 
            && temp.marks >= minMarks) {

            System.out.println(temp.rollNo + " | " + temp.name + " | " + temp.marks);
            found = true;
        }
        temp = temp.next;
    }

    if (!found) System.out.println("No matching students found");
} 

    // ================= FILE LOAD =================
    void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("students.txt"));
            String line;

            head = null;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                addStudent(
                        Integer.parseInt(data[0]),
                        data[1],
                        Integer.parseInt(data[2])
                );
            }

            br.close();
            System.out.println("Loaded from file!");
        } catch (Exception e) {
            System.out.println("Error loading file");
        }
    }

    public void sortByName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sortByName'");
    }
}
