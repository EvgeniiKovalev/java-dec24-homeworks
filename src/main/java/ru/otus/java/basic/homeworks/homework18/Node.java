package ru.otus.java.basic.homeworks.homework18;

public class Node implements Comparable<Node> {
    private final Person person;
    private Node left;
    private Node right;

    public void saveReferences(Node leftNode) {
        left = leftNode;
        if (leftNode != null) {
            left.right = this;
        }
    }

    public Node(Person person) {
        this.person = person;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    @Override
    public String toString() {
        return String.format("Node{ person=%s, left=%s, right=%s }",
                person,
                left == null ? "NULL":left.person,
                right == null ? "NULL":right.person);
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public int compareTo(Node o) {
        return person.compareTo(o.person);
    }
}