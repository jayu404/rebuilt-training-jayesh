package frc.robot.intake;

import static edu.wpi.first.units.Units.Degrees;
import static edu.wpi.first.units.Units.Rotations;

import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase{

    TalonFX deployMotor = new TalonFX(IntakeConst.MOTOR_ID_DEPLOY);
    TalonFX rollerMotor = new TalonFX(IntakeConst.MOTOR_ID_ROLLER);

    public IntakeSubsystem(){
        deployMotor.getConfigurator().apply(IntakeConfig.deployMotorConfiguration);
        rollerMotor.getConfigurator().apply(IntakeConfig.rollerMotorConfiguration);

        deployMotor.setPosition(IntakeConst.MAX_ANGLE/360);

    }

    public void moveRollerSpeed(int speed){
        rollerMotor.set(speed);
    }

    public void rollersOn(){
        moveRollerSpeed(IntakeConfig.rollerSpeed);
    }

    public void rollersOff(){
        moveRollerSpeed(0);
    }

    public void rollersReverse(){
        moveRollerSpeed(-1);
    }

    public void moveAngle(Angle angle){
        Angle targetAngle = Rotations.of(MathUtil.clamp(angle.in(Rotations), IntakeConst.ROTATION_ANGLE_MIN.in(Rotations), IntakeConst.ROTATION_ANGLE_MAX.in(Rotations)));



        deployMotor.setControl(new MotionMagicVoltage(targetAngle));
    }

    public void moveUp(){
        moveAngle(IntakeConst.ROTATION_ANGLE_MAX);

    }
    public void moveDown(){
        moveAngle(IntakeConst.ROTATION_ANGLE_MIN);
    }

    public void deploy(){
        moveDown();
        rollersOn();
    }
    public void stow(){
        moveUp();
        rollersOff();
    }

    public Angle getAngle(){
        return deployMotor.getPosition().getValue();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
        builder.addDoubleProperty("motor angle",() -> getAngle().in(Degrees) , (angle) -> moveAngle(Degrees.of(angle)));
        super.initSendable(builder); 
    }



}
