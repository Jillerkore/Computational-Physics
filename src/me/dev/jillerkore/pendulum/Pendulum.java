package me.dev.jillerkore.pendulum;

import me.dev.jillerkore.Plot;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/*
* Simulate a general pendulum with visualization
* No small angle approximation
* With damping
*/
public class Pendulum {


    private Plot plot;


    public Pendulum() {
        initializeWindow();
    }

    private void initializeWindow() {
        plot = new Plot("Pendulum Simulation", -10, 10, 1, -10, 10, 1);
        drawBob();
    }

    private void drawBob() {
        plot.setPointShape(Plot.SQUARE);
        plot.setPointSize(50);
        plot.addPoint(0, 0);
    }


}


