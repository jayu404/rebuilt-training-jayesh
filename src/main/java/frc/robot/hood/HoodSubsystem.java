package frc.robot.hood;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class HoodSubsystem extends SubsystemBase {
    
    TalonFX motor = new TalonFX(HoodConst.MOTOR_ID, HoodConst.CAN_BUS);

    private Angle targetPitch;



    public HoodSubsystem(){
        motor.getConfigurator().apply(HoodConfig.motorConfiguration);

        motor.setPosition(HoodConst.MAX_PITCH);
        targetPitch = HoodConst.MAX_PITCH;

    }
    /**
     * Move hood to target{angle} 
     * CLamps to Min_Pitch and Max_pitch
     * sets targetPitch
     * @param target
     */

    public void moveHood(Angle target){
        Angle targetAngle = Rotations.of(MathUtil.clamp(target.in(Rotations),HoodConst.MIN_PITCH.in(Rotations) ,HoodConst.MAX_PITCH.in(Rotations)));

        motor.setControl(new MotionMagicVoltage(targetAngle));

        targetPitch = target;

       
    }

    /**
     * Gets motor pitch as angle
     * @return motor position pitch
     */

    public Angle getPitch(){
        return motor.getPosition().getValue();

    }
    /**
     * sets motor position to stow
     * return to max pitch(stow)
     */

    public void stow(){
        motor.setPosition(HoodConst.MAX_PITCH);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("HoodAngle{degrees}",()-> getPitch().in(Degrees),(angle)->moveHood(Degrees.of(angle)));
        builder.addDoubleProperty("TargetPitch{degres}", ()->(targetPitch).in(Degrees), null);
        super.initSendable(builder);
    }


}
