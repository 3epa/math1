package ru.itmo.algo;

public enum AmountOfSolution {
    ZERO("ноль"),
    ONE("одно"),
    INFINITY("бесконечное");
    private final String name;

    AmountOfSolution(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    }
