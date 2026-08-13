package frc.robot.turret;

import static edu.wpi.first.units.Units.Degrees;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;

import edu.wpi.first.units.measure.Angle;

public class TurretConfig {
    public static final int STATOR_CURRLIMIT = 30;

    public static final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
    public static final CANcoderConfiguration enconderConfiguration = new CANcoderConfiguration();

    public static final Angle STOW_YAW = Degrees.of(0);

    public static final double ENCODE_RATIO = 8.5;
    public static final double ERROR_TOLERANCE = 3;

    static {
        motorConfiguration.CurrentLimits.StatorCurrentLimit = STATOR_CURRLIMIT;

        motorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        motorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        enconderConfiguration.MagnetSensor.MagnetOffset = -0.444;

        enconderConfiguration.MagnetSensor.SensorDirection =
                SensorDirectionValue.Clockwise_Positive;

        motorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;
        motorConfiguration.SoftwareLimitSwitch.ForwardSoftLimitThreshold =
                TurretConst.MAX_DEGREES.in(Degrees);

        motorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
        motorConfiguration.SoftwareLimitSwitch.ReverseSoftLimitThreshold =
                TurretConst.MIN_DEGREES.in(Degrees);

        motorConfiguration.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;

        motorConfiguration.Feedback.FeedbackRemoteSensorID = TurretConst.ENCODER_ID;

        motorConfiguration.Feedback.SensorToMechanismRatio = ENCODE_RATIO;
    }
}
