package frc.robot.targeting;

import static edu.wpi.first.units.Units.Inches;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class Targeting {
    public static final double g = -9.8;
    public static final double HOOD_ANGLE = 60;
    public static final double hubX = 182.11 / 39.37;
    public static final double hubY = 158.84 / 39.37;
    public static final double hubZ = 72 / 39.37;
    public static final double hoodPitch = 60;
    public static final Transform3d ROBOT_TO_LAUNCHER =
            new Transform3d(
                    new Translation3d(Inches.of(-2.6967), Inches.of(4.50), Inches.of(17.9)),
                    Rotation3d.kZero);

    public static double turretAngle(Pose3d robotPosition) {
        robotPosition.transformBy(ROBOT_TO_LAUNCHER);
        double robotX = robotPosition.getX();
        double robotY = robotPosition.getY();
        double robotAngle = robotPosition.getRotation().getZ() * 180 / Math.PI;

        // Alliance alliance = DriverStation.getAlliance().orElse(Alliance.Blue);
        double xOffset = hubX - robotX;

        double yOffset = hubY - robotY;

        double angle = Math.atan(xOffset / yOffset) * 180 / Math.PI;

        return angle + robotAngle;
    }

    public static double launchSpeed(Pose3d robotPosition) {
        robotPosition.transformBy(ROBOT_TO_LAUNCHER);
        double robotX = robotPosition.getX();
        double robotY = robotPosition.getY();
        double robotZ = robotPosition.getZ();
        double xOffset = hubX - robotX;
        double yOffset = hubY - robotY;
        double zOffset = hubZ - robotZ;

        double displacement = Math.sqrt((xOffset * xOffset + yOffset * yOffset));

        double velocity =
                (displacement / Math.cos(hoodPitch))
                        * Math.sqrt(g / (2 * (displacement * Math.tan(hoodPitch) - zOffset)));

        return velocity;
    }
}
