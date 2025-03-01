package org.example.backend.data.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Entity
@Table(name = "points")
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "master", nullable = false)
    private int master;

    @Column(name = "x", nullable = false)
    private Float x;

    @Column(name = "y", nullable = false)
    private Float y;

    @Column(name = "z", nullable = false)
    private Float z;

    @Column(name = "r", nullable = false)
    private Float r;

    @Column(name = "result", nullable = false)
    private boolean result;

    @Column(name = "time", nullable = false)
    private long time;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Point(int master, Float x, Float y, Float z, Float r, boolean result, long time) {
        this.master = master;
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.result = result;
        this.time = time;
        this.date = new Date();
    }


    public String getXyzr(){
        return x + "," + y + "," + z +"," + r ;
    }


    @Override
    public String toString() {
        if (result) return "Попал";
        return "Мимо";
    }
}

