package com.zel.fibo.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Sucesion {
    @Column(name = "s")
    private String s;

    public Sucesion(String s) {
        this.s = s;
    }

    public String getS() {
        return s;
    }
}
