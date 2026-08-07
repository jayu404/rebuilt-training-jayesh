package frc.robot.spindexer;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpindexerSubsystem extends SubsystemBase{

    TalonFX motor = new TalonFX(SpindexerConst.MOTOR_ID);
    

    public SpindexerSubsystem(){
        motor.getConfigurator().apply(SpindexerConfig.motorConfiguration);

    }

    public void moveMotorspeed(double speed){
        motor.set(speed);
    }
    public void start(){
        motor.set(SpindexerConfig.motor_speed);
    }
    public void stop(){
        motor.set(0);
    }
    public double getMotorSpeed(){
        return motor.get();
        
    }
    


    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
        builder.addDoubleProperty("motorSpeed",this::getMotorSpeed, this::moveMotorspeed);
        builder.addDoubleProperty("angularVelocity{rps}",()->motor.getVelocity().getValueAsDouble(), null );
        super.initSendable(builder); 
    }



}
