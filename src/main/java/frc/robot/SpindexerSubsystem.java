package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SpindexerSubsystem extends SubsystemBase{

    TalonFX motor = new TalonFX(-1);
    
    private static final TalonFXConfiguration motorConfiguration = new TalonFXConfiguration();
    static{
        motorConfiguration.CurrentLimits.StatorCurrentLimit = 80;

        motorConfiguration.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        motorConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
    }

    public SpindexerSubsystem(){
        motor.getConfigurator().apply(motorConfiguration);

    }

    public void moveMotorspeed(double speed){
        motor.set(speed);
    }
    public void start(){
        motor.set(0.5);
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
