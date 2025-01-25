package ru.otus.java.basic.homeworks.homework13;

public class TransportFactory {
    public Transport createTransport(TransportType type) {
        Transport transport = null;
        switch (type) {
            case CAR:
                transport = new Car();
                break;
            case HORSE:
                transport = new Horse();
                break;
            case BICYCLE:
                transport = new Bicycle();
                break;
            case OFFROADER:
                transport = new Offroader();
                break;
        }
        return transport;
    }

}
