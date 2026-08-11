package frc.robot.hood;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;


public class HoodConfig {
    public static int STATOR_CURRLIMIT = 80;
    //TODO: configure motor speed
    public static double motor_speed = 0.5;
    
    public static final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
    static{
        motorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;

        motorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        motorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        motorConfiguration.Feedback.SensorToMechanismRatio = HoodConst.GEAR_RATO;

        motorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        motorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitThreshold = HoodConst.MAX_DEGREES;
        
        
        motorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        motorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitThreshold = HoodConst.MIN_DEGREES;

    }
}
