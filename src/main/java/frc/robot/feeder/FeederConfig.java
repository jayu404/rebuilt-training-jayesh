package frc.robot.feeder;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class FeederConfig {
    public static int STATOR_CURRLIMIT = 80;
    //TODO: tune speed
    public static double MOTOR_SPEED = 1;

    //TODO: tune speed
    public static double REVERSE_MOTOR_SPEED = -1;

    public static final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();

    static{
        motorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;
        motorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        motorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;


    }


    
}
