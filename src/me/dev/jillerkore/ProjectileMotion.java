package me.dev.jillerkore;


/*
* Air drag solution
 */
public class ProjectileMotion {

    public static final double dt = (double) 1/10000;
    public static final double g = 9.8;

    public static final double mass = 0.15;
    public ProjectileMotion() {
        Plot plot = new Plot("x vs y", 0, 50, 1, 0, 50, 1);
        init(plot, 0, 0, 13.5, 23, 0.001225);
    }

    private void init(Plot plot, double x, double y, double vx, double vy, double drag) {

        while (y >= 0) {

            plot.addPoint(x, y);

            // Update x
            x += vx * dt;

            // Update y
            y += vy * dt;

            // Apply acceleration due to gravity
            vy += -g * dt;

            // Apply retardation due to drag force
            double[] resultantVelocity = applyDrag(vx, vy, drag);
            vx = resultantVelocity[0];
            vy = resultantVelocity[1];
        }

        System.out.println("Final y Velocity: " + vy + " m/s");
        System.out.println("Final x Velocity: " + vx + " m/s");

        System.out.println("Range: " + x + " m");
    }

    private double[] applyDrag(double velX, double velY, double drag) {
        if (drag == 0)
            return new double[]{velX, velY};

        double x, y;
        double vel = Math.sqrt(Math.pow(velX, 2) + Math.pow(velY, 2));

        // Find the unit vector antiparallel to the velocity vector
        double accX = - velX / vel;
        double accY = - velY / vel;

        // Assign magnitude of the drag retardation to the above vector
        double retardation = (drag * Math.pow(vel, 2)) / mass; // Formula: ma = kv^2
        double angle = Math.abs(Math.toDegrees(Math.atan(accY/accX)));
        accX = accX * retardation;
        accY = accY * retardation;

        // Apply the drag retardation to the velocity
        x = velX + accX * dt;
        y = velY + accY * dt;

        // Return the modified velocities
        return new double[]{x, y};

    }

}
