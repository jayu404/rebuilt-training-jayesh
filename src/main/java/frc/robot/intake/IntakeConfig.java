package frc.robot.intake;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class IntakeConfig {
    public static int STATOR_CURRLIMIT = 80;
    public static double motor_speed = 0.5;
    public static int gearReduction = 96;

    public static int rollerSpeed = 1;


    public static final TalonFXConfiguration deployMotorConfiguration = new TalonFXConfiguration();
    public static final TalonFXConfiguration rollerMotorConfiguration = new TalonFXConfiguration();

    static{
        deployMotorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;

        deployMotorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        deployMotorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        deployMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        deployMotorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitThreshold = IntakeConst.MAX_ANGLE;
        
        
        deployMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        deployMotorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitThreshold = IntakeConst.MIN_ANGLE;


        rollerMotorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;

        rollerMotorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Coast;

        rollerMotorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        rollerMotorConfiguration.Feedback.SensorToMechanismRatio = gearReduction;



    }


}
