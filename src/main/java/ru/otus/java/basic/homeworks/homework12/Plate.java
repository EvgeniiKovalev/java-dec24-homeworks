package ru.otus.java.basic.homeworks.homework12;

public class Plate {
    private int currentVolume;
    private final int maxVolume;

    public Plate(int maxVolume) throws Exception {
        if (maxVolume <= 0) {
            throw new Exception("Максимальное количество еды должно быть > 0");
        }

        this.maxVolume = maxVolume;
        this.currentVolume = maxVolume;
    }

    public void addFood(int volume) throws Exception {
        //правильно ли использовать для проверки входных данных Exception, как это сделано строкой ниже ?
        if (volume <= 0) {
            throw new Exception("Количество еды должно быть > 0");
        }
        //правильно ли использовать для проверки бизнес требований Exception, как это сделано строкой ниже ?
        if (currentVolume + volume > maxVolume) {
            throw new Exception("Еда не помещается в тарелке");
        }
        currentVolume += volume;
    }

    public boolean reduceFood(int volume) throws Exception {
        if (volume <= 0) {
            throw new Exception("Количество еды должно быть > 0");
        }
        if (currentVolume - volume < 0) {
            System.out.println("В тарелке еды меньше чем количество на которое пытаетесь уменьшить");
            return false;
        }
        currentVolume -= volume;
        return true;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    @Override
    public String toString() {
        return "Plate{ currentVolume=" + currentVolume + ", maxVolume=" + maxVolume + '}';
    }
}
