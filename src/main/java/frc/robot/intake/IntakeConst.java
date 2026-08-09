package frc.robot.intake;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import edu.wpi.first.units.measure.Angle;

public class IntakeConst {
    
    public static final Angle ROTATION_ANGLE_MAX = Rotations.of(128.26/360);
    public static final Angle ROTATION_ANGLE_MIN = Rotations.of(0);
    
    public static final double MAX_ANGLE = ROTATION_ANGLE_MAX.in(Degrees);
    public static final double MIN_ANGLE = ROTATION_ANGLE_MIN.in(Degrees);

    public static final int MOTOR_ID_DEPLOY = -1;
    public static final int MOTOR_ID_ROLLER = -1;
}
