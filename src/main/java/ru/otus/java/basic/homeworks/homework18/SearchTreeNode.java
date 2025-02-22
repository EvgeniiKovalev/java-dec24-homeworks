package ru.otus.java.basic.homeworks.homework18;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.List.of;

public class SearchTreeNode implements SearchTree<Node> {
    private final Random rand = new Random();
    private Node root;
    private Node firstNode;
    private Node currentRoot;

    public SearchTreeNode() {
    }

    public Node getRoot() {
        return root;
    }

    /**
     * создаст сортированное бинарное дерево, запоминает корневой элемент
     *
     * @param amountElements количество элементов
     *                       вернет корневой элемент
     */
    void buildBinaryTree(int amountElements) {
        List<Position> pos = of(Position.values());
        Node leftNode = null;
        int j = 0;
        for (int i = 1; i <= amountElements; i++) {
            j += 1000;
            Node node = new Node(new Person("name " + j, pos.get(rand.nextInt(pos.size())), (long) j));
            node.saveReferences(leftNode);
            leftNode = node;

            if (i == amountElements / 2) root = node;
            if (i == 1) firstNode = node;
        }
        currentRoot = root;
    }

    @Override
    public Node find(Node element) {
        try {
            if (currentRoot == null) return null;//не нашли и дерево закончилось

            int resultCompare = element.compareTo(currentRoot);
            //нашли
            if (resultCompare == 0) {
                return currentRoot;
            }
            //искомый элемент левее
            if (resultCompare < 0) {
                currentRoot = currentRoot.getLeft();
                return find(element);
            }
            //искомый элемент правее
            currentRoot = currentRoot.getRight();
            return find(element);
        } catch (StackOverflowError e) {
            return null;
        }
    }

    @Override
    public List<Node> getSortedList() {
        List<Node> result = new ArrayList<>();
        Node node = firstNode;
        while (node != null) {
            result.add(node);
            node = node.getRight();
        }
        return result;
    }
}
