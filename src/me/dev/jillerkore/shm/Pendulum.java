package me.dev.jillerkore.shm;

import me.dev.jillerkore.Plot;

public class Pendulum {

    public static double dt = (double) 1/1000;

    public Pendulum() {
        init();
    }

    private void init() {

        Plot plot = new Plot("displacement vs time", 0, 200, 1, -100, 100, 1);

        double d = 40; // Displacement, sign matters
        double k = 0.1; // Spring constant
        double t = 0; // Elapsed time
        double a; // Net acceleration
        double v = 0; // Velocity
        double mass = 1;
        double damp = 0.1;
        boolean continuePlotting = true;

        double previousd = d;

        // Temp values
        double amid, dmid, vmid;

        // Setting initial acceleration based on the displacement
        a = -k * d / mass + getDamp(v, damp);

        // Amplitude counter
        double ampCounter = 0;
        double ampLimit = 1000;

        // Goal: To simulate dv/dt = kd/m
        while (continuePlotting) {

            plot.addPoint(t, d);

            // Find mean of the displacement interval
            dmid = d + v * 0.5 * dt;

            // Find mean of the velocity interval
            vmid = v + a * 0.5 * dt;

            // Find mean of the acceleration interval
            amid = 0.5 * (-k * dmid / mass + getDamp(vmid, damp));

            // Update displacement
            d += vmid * dt;

            // Update velocity
            v += amid * dt;

            // Update acceleration
            a = amid;

            // Update time
            t += dt;

            // Check slope
            double slope = (d - previousd) / dt;

            previousd = d;

            if (Math.abs(slope) < dt) {
                ampCounter ++;
            }

            if (ampCounter > ampLimit) {
                continuePlotting = false;
            }
        }
        System.out.println("Elapsed time: " + t);
    }

    private double getDamp(double vel, double damp) {
        double accnMagnitude = damp * Math.abs(Math.pow(vel, 1));
        double accnVector = -Math.signum(vel); // Opposite of velocity vector
        return accnMagnitude * accnVector;
    }

}
