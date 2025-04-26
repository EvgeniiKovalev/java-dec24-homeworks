package ru.otus.java.basic.homeworks.homework31;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("methodTwoDataFail")
    void methodTwoFailTest(int[] data) {
        assertFalse(AppHw31.methodTwo(data));
    }

    private static Stream<Arguments> methodTwoDataFail() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments((Object) new int[]{2, 2, 2, 2}));
        out.add(Arguments.arguments((Object) new int[]{1, 2, 3}));
        return out.stream();
    }
}