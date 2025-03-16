package ru.otus.java.basic.homeworks.homework20.server;

import java.io.Closeable;
import java.io.IOException;

public class Common {
    static void safetyClose(Closeable closeObject) throws IOException {
        if (closeObject != null) {
            try {
                closeObject.close();
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}
