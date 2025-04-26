package ru.otus.java.basic.homeworks.homework31;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppHw31Test {

    @Test
    void methodOneTest() {
        assertArrayEquals(new int[]{2, 2}, AppHw31.methodOne(new int[]{1, 2, 1, 2, 2}));
    }

    @Test
    void methodOneThrowTest() {
        assertThrowsExactly(RuntimeException.class, () -> AppHw31.methodOne(new int[]{2, 2, 2, 2}));
    }

    @Test
    void methodTwoTest() {
        assertTrue(AppHw31.methodTwo(new int[]{1, 2, 1, 2, 2}));
    }

    @Test
    void methodTwoFailTest1() {
        assertFalse(AppHw31.methodTwo(new int[]{2, 2, 2, 2}));
    }

    @Test
    void methodTwoFailTest2() {
        assertFalse(AppHw31.methodTwo(new int[]{1, 2, 3}));
    }

}