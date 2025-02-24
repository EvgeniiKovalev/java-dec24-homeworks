package ru.otus.java.basic.homeworks.homework18;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PersonDataBase {

    private final HashMap<Integer, Node> listNodes;
    private final ArrayList<Position> managerPosition;
    private Node leftNode;

    public PersonDataBase() {
        listNodes = new HashMap<>();
        leftNode = null;

        managerPosition = new ArrayList<>();
        managerPosition.add(Position.BRANCH_DIRECTOR);
        managerPosition.add(Position.SENIOR_MANAGER);
        managerPosition.add(Position.DIRECTOR);
        managerPosition.add(Position.MANAGER);

    }

    int size() {
        return listNodes.size();
    }

    /**
     * добавить Person
     *
     * @param person
     */
    void add(Person person) {
        int newkey = Objects.hashCode(person.getId());
        if (listNodes.containsKey(newkey)) {
            return;
        }
        Node node = new Node(person);
        listNodes.put(newkey, node);
        node.saveReferences(leftNode);
        leftNode = node;
    }


    Node findNode(Long id) {
        return listNodes.get(Objects.hashCode(id));
    }

    /**
     * найти Person по id
     *
     * @param id
     * @return
     */
    Person findById(Long id) {
        Node node = listNodes.get(Objects.hashCode(id));
        return node == null ? null : node.getPerson();
    }

    /**
     * true если Position
     *
     * @param person
     * @return
     */
    boolean isManager(Person person) {
        return person == null ? null : managerPosition.contains(person.getPosition());
    }

    /**
     * true если Employee имеет любой другой Position
     *
     * @param id
     * @return
     */
    boolean isEmployee(Long id) {
        Person person = findById(id);
        return person == null ? false : !managerPosition.contains(person.getPosition());
    }
}
