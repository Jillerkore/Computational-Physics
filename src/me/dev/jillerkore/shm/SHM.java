package me.dev.jillerkore.shm;

import me.dev.jillerkore.Plot;

import java.awt.*;

/*
* Simulates SHM (and also damping force as an added bonus)
* Displays 2 windows: 1 with graph and the other with the oscillating object.
 */
public class SHM {

    public static double dt = (double) 1/100;

    private Thread renderThread;

    // For the simulation
    private Plot simulation;

    // For the graph
    Plot plot;

    // Particle information
    double x = 80; // Displacement, sign matters
    double y = 0;
    double k = 0.01; // Spring constant
    double t = 0; // Elapsed time
    double a; // Net acceleration
    double v = 0; // Velocity
    double mass = 1;
    double damp = 0.025;

    // Delta time related
    private long currentTime = 0;
    private double deltaTime = 0;
    private long time = 0;
    private Thread deltaThread;
    boolean continuePlotting = true;


    public SHM() {
        initSimulation();
        initGraph();
        initThread();
    }

    private void initThread() {
        deltaThread = new Thread(() -> {

            int fps = 30;
            double timePerTick = (double) 1000000000 / fps;

            while(true) {

                currentTime = System.nanoTime();
                deltaTime += (int) ((currentTime - time) / timePerTick);
                time = currentTime;

                if (deltaTime >= 1) {
                    loop();
                    deltaTime--;
                }
            }
        });
        deltaThread.start();
    }

    private void initGraph() {

        plot = new Plot("displacement vs time", 0, 200, 1, -100, 100, 1);
        plot.setLocation(0, 0);

        // Setting initial acceleration based on the displacement
        a = -k * x / mass + getDamp(v, damp);
    }

    private void loop() {

        double amid, dmid, vmid;

        plot.addPoint(t, x);

        // Find mean of the displacement interval
        dmid = x + v * 0.5 * dt;

        // Find mean of the velocity interval
        vmid = v + a * 0.5 * dt;

        // Find mean of the acceleration interval
        amid = 0.5 * (-k * dmid / mass + getDamp(vmid, damp));

        // Update displacement
        x += vmid * dt;

        // Update velocity
        v += amid * dt;

        // Update acceleration
        a = amid;

        // Update time
        t += dt;

        simulation.clearThePlot();
        simulation.addPoint(
                x / 10, // Changing the scale
                0
        );
    }

    private void initSimulation() {
        simulation = new Plot("Oscillator Simulation", -10, 10, 1, -10, 10, 1);
        simulation.setPointShape(Plot.SQUARE);
        simulation.setColor(Color.BLUE);
        simulation.setPointSize(50);
        simulation.setLocation(660, 0);
    }

    private double getDamp(double vel, double damp) {
        double accnMagnitude = damp * Math.abs(Math.pow(vel, 1));
        double accnVector = -Math.signum(vel); // Opposite of velocity vector
        return accnMagnitude * accnVector;
    }

}
