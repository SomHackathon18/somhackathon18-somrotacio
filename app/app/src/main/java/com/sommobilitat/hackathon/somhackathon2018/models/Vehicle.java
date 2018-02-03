package com.sommobilitat.hackathon.somhackathon2018.models;

public class Vehicle {
    private String matricula;
    private boolean particular;

    public Vehicle(String matricula, boolean particular) {
        super();
        this.matricula = matricula;
        this.particular = particular;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public boolean isParticular() {
        return particular;
    }

    public void setParticular(boolean particular) {
        this.particular = particular;
    }
}
