package ru.otus.java.basic.homeworks.homework11;

public class Tester {
    public static void testRun(TestAction testAction, int distance, Object obj) {
        if (testAction == TestAction.RUN) {
            int sec =  ((Animal)obj).run(distance);
            if (sec > 0) {
                System.out.println("'" + ((Animal)obj).name + "' пробежал расстояние " + distance + "м за " + sec + " секунд");
            }

        }
        else if (testAction == TestAction.SWIM) {
            int sec =  ((Swimable)obj).swim(distance);
            if (sec > 0 ) {
                System.out.println("'" + ((Animal) obj).name + "' проплыл расстояние " + distance + "м за " + sec + " секунд");
            }
        }

    }
}
