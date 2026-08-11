package frc.robot.hood;

import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.CANBus;

import edu.wpi.first.units.measure.Angle;


public class HoodConst {
    public static final CANBus CAN_BUS = new CANBus("launcher");
    //TODO: Configure MOTOR_ID
    public static final int MOTOR_ID = -1;

    public static final double GEAR_RATO = 24;

    public static final double MIN_DEGREES = 0;

    public static final double MAX_DEGREES = 73.606;

    public static final Angle MIN_PITCH = Rotations.of(MIN_DEGREES);
    public static final Angle MAX_PITCH = Rotations.of(MAX_DEGREES);








}
