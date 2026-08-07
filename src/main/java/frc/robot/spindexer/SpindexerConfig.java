package frc.robot.spindexer;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class SpindexerConfig {
    public static int STATOR_CURRLIMIT = 80;
    public static double motor_speed = 0.5;
    
    public static final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
    static{
        motorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;

        motorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        motorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    }
    
}
