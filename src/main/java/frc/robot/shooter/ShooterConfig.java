package frc.robot.shooter;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class ShooterConfig {

    public static final int STATOR_CURRLIMIT = 80;


    public static final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
    static{
        motorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;

        motorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        motorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;


    }
}
