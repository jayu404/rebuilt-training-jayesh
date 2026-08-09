package frc.robot.feeder;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FeederSubsystem extends SubsystemBase{

    TalonFX motor = new TalonFX(FeederConst.MOTOR_ID, FeederConst.CAN_BUS);

    public FeederSubsystem(){
        motor.getConfigurator().apply(FeederConfig.motorConfiguration);


    }
    /**
     * sets Feeder motor speed
     * @param speed
     */

    public void setMotorSpeed(double speed){
        motor.set(speed);

    }
    /**
     * starts feeder
     */

    public void start(){
        setMotorSpeed(FeederConfig.MOTOR_SPEED);

    }
    /**
     * brakes feeder
     */
    
    public void brake(){
        setMotorSpeed(0);
        
    }
    /**
     * reverses feeder
     */

    public void reverse(){
        setMotorSpeed(FeederConfig.REVERSE_MOTOR_SPEED);
        
    }
    /**
     * 
     * @return motor speed
     */

    public double getMotorSpeed(){
        return motor.get();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        // TODO Auto-generated method stub
        builder.addDoubleProperty("motorSpeed{frac}", this::getMotorSpeed, this::setMotorSpeed);

        super.initSendable(builder);
    }





}
