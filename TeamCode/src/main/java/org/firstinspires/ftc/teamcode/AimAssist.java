package org.firstinspires.ftc.teamcode;

/**
 * this will be the main class for the aim assist.
 * it should be able to take an input consisting of the robots position, yaw, velocity(?), acceleration(?), and the position
 * of the target and be able to output the yaw, and pitch required for the robot to launch the ring into the goal
 *
 * parameters with a ? before the description may not be needed.
 *
 * @param   rPosition         a double array with two indexs recording the position of the robot (x,y) relative to the starting position.
 * @param   rYaw              the direction that the robot is facing relative to the starting direction.
 * @param   rVelocity         ?the magnitude of the velocity of the robot
 * @param   rAcceleration     ?the acceleration the robot is undergoing, needed to correct the aim of the turret to anticipate
 *                              the impact the robots acceleration will have on the arc of the projectile
 *                              (may not be needed if the robot stops before firing)
 *
 * @param   tuYaw             ?the direction that the turret is facing relative to the robot
 *
 * @param   tPosition         a double array with three indexs recording the position of the target (x,y,z(up))
 *
 *
 *
 * @return yawToTarget          the yaw that the turret needs to point to (relative to the robot) to face the target
 * @return pitchToTarget        the pitch that the turret needs to be at to hit the target.
 */

public class AimAssist {

    //variables defining the robots characteristics
    double[] robotPosition; // x, y, coords of robot, measured in meters
    double robotYaw;        // direction the robot is facing relative to it's starting direction (0), measured in degrees
    double robotVelocity;   // magnitude of the velocity of the robot, measure in meters per second
    double robotAcceleration; // magnitude of the acceleration of the robot, measured in meters per second per second

    //variables defining the turrets characteristics
    double turretYaw; // the direction that the turret is facing relative to the front of the robot (0), measured in degrees

    //variables defining the targets characteristics
    double[] targetPosition; // x, y, z, coords of the robot, measured in meters

    //variables that are calculated
    double yawToTarget; // the direction that the turret needs to face relative to the front of the robot (0), measured in degrees
    double pitchToTarget; // the pitch that the turret needs to be at to hit the target, measured in degrees

    /**
     * the constructors for the turret
     */
    /**default constructor**/
    public AimAssist(){

    }
    /**constructor**/
    public AimAssist(double[] rPosition, double rYaw, double rVelocity, double rAcceleration, double tuYaw, double[] tPosition)
    {
        robotPosition = rPosition;
        robotYaw = rYaw;
        robotVelocity = rVelocity;
        robotAcceleration = rAcceleration;

        turretYaw = tuYaw;

        targetPosition = tPosition;
    }

    /**
     * method to update the the variables to the most recent values
     */
    public void Update(double[] rPosition, double rYaw, double rVelocity, double rAcceleration, double tuYaw, double[] tPosition)
    {
        robotPosition = rPosition;
        robotYaw = rYaw;
        robotVelocity = rVelocity;
        robotAcceleration = rAcceleration;

        turretYaw = tuYaw;

        targetPosition = tPosition;

        yawToTarget = YawCalculation();
        pitchToTarget = pitchCalculation();
    }

    /**
     * the method to calculate the yaw that the turret needs to point at in order to point to the target
     * @return yawToTarget the yaw to the target
     */
    private double YawCalculation ()
    {
        //x and y values of the robot
        double rX = robotPosition[0];
        double rY = robotPosition[1];
        //x and y values of the target
        double tX = targetPosition[0];
        double tY = targetPosition[1];
        //x and y values of imaginary triangle with the hypotenuse being the line between the turret and the target
        double x = tX - rX;
        double y = tY -rY;

        yawToTarget = Math.atan(y / x); // yaw relative to the field (-|+)
        yawToTarget = yawToTarget - turretYaw; // yaw relative to the robot

        return yawToTarget;
    }

    /**
     * the method to calculate the pitch that the turret needs to be at in order to point to the target
     * @return pitchToTarget the yaw to the target
     */
    // TODO: 9/24/2020 adjust method to take air resistance and gravity into effect, need to know: the initial velocity the turret launches at, weight of the rings, and more 
    private double pitchCalculation()
    {
        final double turretHeight = 0.30; //height from the floor of the field to the turret (measured in meters)

        //calculate distance to target
        double x = targetPosition[0] - robotPosition[0];
        double y = targetPosition[1] - robotPosition[1];
        double distance = Math.sqrt( x*x + y*y);

        //calculate height to target
        double height = targetPosition[2] - turretHeight;

        //calculate pitch to target
        return Math.atan(height / distance);
    }
}
