class Node {
    int rollNo;
    String name;
    int marks;
    Node next;

    Node(int r, String n, int m) {
        this.rollNo = r;
        this.name = n;
        this.marks = m;
        this.next = null;
    }
}