package frc.robot.turret;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.hood.HoodConst;

public class TurretSubsystem extends SubsystemBase {

    TalonFX motor = new TalonFX(TurretConst.MOTOR_ID, HoodConst.CAN_BUS);
    CANcoder encoder = new CANcoder(TurretConst.ENCODER_ID, HoodConst.CAN_BUS);

    boolean enabled = true;
    Angle targetAngle;

    public TurretSubsystem() {
        motor.getConfigurator().apply(TurretConfig.motorConfiguration);
        encoder.getConfigurator().apply(TurretConfig.enconderConfiguration);
    }

    public Angle getYaw() {
        return encoder.getPosition().getValue();
    }

    public void moveRawYaw(Angle target) {
        if (enabled) {
            targetAngle =
                    Rotations.of(
                            MathUtil.clamp(
                                    target.in(Rotations),
                                    TurretConst.MIN_DEGREES.in(Rotations),
                                    TurretConst.MAX_DEGREES.in(Rotations)));
            motor.setControl(new MotionMagicVoltage(targetAngle));
        }
    }

    public void stow() {
        moveRawYaw(TurretConfig.STOW_YAW);
    }

    /**
     * takes target, finds closest coterminal angle, chooses shortest path to get to target
     *
     * @param target
     */
    public void moveYaw(Angle target) {
        if (enabled) {
            // get currentYaw
            Angle currentYaw = getYaw();
            // clamp our ideal angle to in between the rotations of our max and minimum
            targetAngle =
                    Rotations.of(
                            MathUtil.clamp(
                                    target.in(Rotations),
                                    TurretConst.MIN_DEGREES.in(Rotations),
                                    TurretConst.MAX_DEGREES.in(Rotations)));

            // where we wanna go{rotations}
            Angle desiredYaw = targetAngle;

            // where we are relative to desired{degrees}
            Angle relCurrentYaw = Degrees.of(currentYaw.in(Degrees) - desiredYaw.in(Degrees));

            double relMinYaw = TurretConst.MIN_DEGREES.in(Rotations) - desiredYaw.in(Rotations);
            double relMaxYaw = TurretConst.MAX_DEGREES.in(Rotations) - desiredYaw.in(Rotations);

            // Rounds rotation to closest integer to find closest coterminal
            // angle(unclamped).Chooses shorter path.
            double closCoterminalAngle = Math.round(relCurrentYaw.in(Rotations));

            // Finds the largest and smallest rotations to use to clamp
            double largestCoterminalAngle = Math.floor(relMaxYaw);
            double smallestCoterminalAngle = Math.ceil(relMinYaw);

            // Clamps closest coterminal angle to ensure it doesnt overextend
            Angle closestCoterminalAngle =
                    Rotations.of(
                            MathUtil.clamp(
                                    closCoterminalAngle,
                                    smallestCoterminalAngle,
                                    largestCoterminalAngle));

            // moves to best choice
            moveRawYaw(closestCoterminalAngle.plus(desiredYaw));
        }
    }

    public void calibrateYaw(Angle guess) {
        if (guess.in(Rotations) < TurretConst.MIN_DEGREES.in(Rotations)
                || guess.in(Rotations) > TurretConst.MAX_DEGREES.in(Rotations)) {
            return;
        }
        // get encoder posit ion(hours(wrong) and minutes)
        double encoderCurrent = encoder.getPosition().getValueAsDouble();
        // get guess(hours and minutes) converted from mechanism to encoder rotations
        double encoderGuess = guess.in(Rotations) * TurretConfig.ENCODE_RATIO;
        // subtract current from guess, then round to find the closeset guess differrence(hours)
        double guessDifference = Math.round(encoderGuess - encoderCurrent);
        // get range of max and min offsets, convert max/min to encoder ratio, subtract by
        // encoderGuess to keep offsets referencing the guess
        double MIN_OFFSET =
                TurretConst.MIN_DEGREES.in(Degrees) * TurretConfig.ENCODE_RATIO - encoderGuess;
        double MAX_OFFSET =
                TurretConst.MAX_DEGREES.in(Degrees) * TurretConfig.ENCODE_RATIO - encoderGuess;

        // clamp guessDiference to be in between our possible offsets
        double finalGuessDifference = MathUtil.clamp(guessDifference, MIN_OFFSET, MAX_OFFSET);
        // add back the encoderCurrent(hours(wrong) and minutes) to the clamped guessDifference
        double finalEncoderPosition = encoderCurrent + finalGuessDifference;

        encoder.setPosition(finalEncoderPosition);
    }

    public double getYawError() {
        double error = getYaw().in(Degrees) - targetAngle.in(Degrees);
        return error;
    }

    public boolean inTolerance() {
        if (Math.abs(getYawError()) > TurretConfig.ERROR_TOLERANCE) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("TurretStatus{boolean}", () -> enabled, val -> enabled = val);
        builder.addDoubleProperty(
                "CurrentYaw{degrees}",
                () -> getYaw().in(Degrees),
                (guessYaw) -> calibrateYaw(Degrees.of(guessYaw)));
        builder.addDoubleProperty(
                "TargetYaw{degrees}",
                () -> targetAngle.in(Degrees),
                (guessYaw) -> moveYaw(Degrees.of(guessYaw)));
        builder.addDoubleProperty("YawError{degrees}", () -> getYawError(), null);
        builder.addBooleanProperty("inErrorTolerance{boolean}", () -> inTolerance(), null);
        super.initSendable(builder);
    }
}
