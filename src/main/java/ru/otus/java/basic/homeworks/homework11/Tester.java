package ru.otus.java.basic.homeworks.homework11;

public class Tester {
    public static void testRun(TestAction testAction, int distance, Object obj) {
        if (testAction == TestAction.RUN) {
            if (obj instanceof Animal) {
                int sec = ((Animal) obj).run(distance);
                if (sec > 0) {
                    System.out.printf("'%s' пробежал расстояние %dм за %d секунд\n", ((Animal) obj).getName(), distance, sec);
                }
            }
        }
        else if (testAction == TestAction.SWIM) {
            if (obj instanceof Swimable) {
                int sec = ((Swimable) obj).swim(distance);
                if (sec > 0) {
                    System.out.printf("'%s' проплыл расстояние %dм за %d секунд\n", ((Animal) obj).getName(), distance, sec);
                }
            }
        }
    }
}
