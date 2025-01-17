package me.dev.jillerkore.projectileMotion;


import me.dev.jillerkore.Plot;

/*
* Air drag solution
 */
public class ProjectileMotion {

    public static final double dt = (double) 1/100;
    public static final double g = -9.8;

    public static final double mass = 0.15;
    public ProjectileMotion() {
        Plot plot = new Plot("x vs y", 0, 50, 1, 0, 50, 1);
        init(plot, 0, 0, 0, 2000, 0.001225);
    }

    private void init(Plot plot, double x, double y, double vx, double vy, double drag) {

        double ax = 0, ay = g;

        // Temp variables to hold middle values
        double xmid, ymid, vxmid, vymid, axmid = 0, aymid = 0;

        // Apply initial drag to the acceleration
        double[] iDragAccelerations = getDragAcceleration(vx, vy, drag);
        ax += iDragAccelerations[0];
        ay += iDragAccelerations[1];

        while (y >= 0) {

            plot.addPoint(x, y);

            // Calculate mid-position
            xmid = x + vx * 0.5 * dt;
            ymid = y + vy * 0.5 * dt;

            // Calculate mid-velocity
            vxmid = vx + 0.5 * ax * dt;
            vymid = vy + 0.5 * ay * dt;

            // Calculate mid-acceleration from mid-velocity
            double[] dragAccelerations = getDragAcceleration(vxmid, vymid, drag);
            axmid = 0.5 * (dragAccelerations[0]);
            aymid = 0.5 * (g + (dragAccelerations[1]));

            // Update x and y
            x += vxmid * dt;
            y += vymid * dt;

            // Update vx and vy
            vx += axmid * dt;
            vy += aymid * dt;

            // Update ax and ay
            ax = axmid;
            ay = aymid;

        }

        System.out.println("Final y Velocity: " + Math.round(vy) + " m/s");
        System.out.println("Final x Velocity: " + Math.round(vx) + " m/s");

    }

    private double[] getDragAcceleration(double midVelX, double midVelY, double drag) {
        if (drag == 0)
            return new double[]{midVelX, midVelY};

        double vel = Math.sqrt(Math.pow(midVelX, 2) + Math.pow(midVelY, 2));

        // Find the unit vector antiparallel to the velocity vector
        double accX = - midVelX / vel;
        double accY = - midVelY / vel;

        // Assign magnitude of the drag retardation to the above vector
        double retardation = (drag * Math.pow(vel, 2)) / mass; // Formula: ma = kv^2
        accX = accX * retardation;
        accY = accY * retardation;

        // Return the drag acceleration
        return new double[]{accX, accY};

    }

}
