package frc.robot.shooter;

import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.hood.HoodConst;

public class ShooterSubsystem extends SubsystemBase{
    TalonFX rightLeadMotor = new TalonFX(ShooterConst.RIGHT_MOTOR_ID,HoodConst.CAN_BUS);
    TalonFX leftFollowerMotor = new TalonFX(ShooterConst.LEFT_MOTOR_ID,HoodConst.CAN_BUS);

    AngularVelocity targetVelocity;
    Boolean enabled = true;


    public ShooterSubsystem(){
        rightLeadMotor.getConfigurator().apply(ShooterConfig.motorConfiguration);
        leftFollowerMotor.getConfigurator().apply(ShooterConfig.motorConfiguration);

        leftFollowerMotor.setControl(
            new Follower(rightLeadMotor.getDeviceID(), MotorAlignmentValue.Opposed));
    }
    /**
     * given parameter target, convert to angular velocity and set the motors to that speed
     * @param target
     */

    public void moveAngularVelocity(Double target){
        if(enabled){
            double target_speed = MathUtil.clamp(target,ShooterConst.MIN_ANGULAR_VELOCITY, ShooterConst.MAX_ANGULAR_VELOCITY);
            rightLeadMotor.set(target_speed);
            targetVelocity = RotationsPerSecond.of(target_speed);
        }
    }
    /**
     * stops motors
     */

    public void stop(){
        rightLeadMotor.set(0);

        targetVelocity = RotationsPerSecond.of(0);
    }

    /**
     * 
     * @return velocity of motors
     */

    public AngularVelocity getAngularVelocity(){
        return rightLeadMotor.getVelocity().getValue();
    }
    
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addBooleanProperty("ShooterEnabled{boolean}",()-> enabled, val -> enabled = val);
        builder.addDoubleProperty("AngularVelocity{double}",()->getAngularVelocity().in(RotationsPerSecond), null);
        builder.addDoubleProperty("TargetAngularVelocity{double}",()->targetVelocity.in(RotationsPerSecond), (target)-> moveAngularVelocity(target));
        super.initSendable(builder);
    }



}
