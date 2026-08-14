// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.drivetrain.CommandSwerveDrivetrain;
import frc.robot.drivetrain.TunerConstants;
import frc.robot.feeder.FeederSubsystem;
import frc.robot.hood.HoodSubsystem;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.spindexer.SpindexerSubsystem;
import frc.robot.targeting.Targeting;
import frc.robot.turret.TurretSubsystem;

public class Robot extends TimedRobot {

    public Robot() {
        initBindings();
        initDashboard();
    }

    private SpindexerSubsystem spindexer = new SpindexerSubsystem();
    private final CommandXboxController controller = new CommandXboxController(0);

    private IntakeSubsystem intake = new IntakeSubsystem();

    private FeederSubsystem feeder = new FeederSubsystem();

    private HoodSubsystem hood = new HoodSubsystem();

    private ShooterSubsystem shooter = new ShooterSubsystem();

    private TurretSubsystem turret = new TurretSubsystem();

    private CommandSwerveDrivetrain drivetrain =
            new CommandSwerveDrivetrain(
                    TunerConstants.DrivetrainConstants,
                    TunerConstants.FrontLeft,
                    TunerConstants.FrontRight,
                    TunerConstants.BackLeft,
                    TunerConstants.BackRight);

    @Override
    public void robotInit() {}

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().schedule(returnAiming());
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {}

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {}

    @Override
    public void teleopPeriodic() {}

    @Override
    public void disabledInit() {}

    @Override
    public void disabledPeriodic() {}

    @Override
    public void testInit() {}

    @Override
    public void testPeriodic() {}

    @Override
    public void simulationInit() {}

    @Override
    public void simulationPeriodic() {}

    public Command returnAiming() {
        return Commands.parallel(
                Commands.runOnce(
                        () -> Targeting.turretAngle(new Pose3d(drivetrain.getState().Pose))),
                (Commands.runOnce(
                        () -> Targeting.launchSpeed(new Pose3d(drivetrain.getState().Pose)))));
    }

    /** initialized dashboard and adds data to it */
    public void initDashboard() {
        SmartDashboard.putData("Spindexer", spindexer);
        SmartDashboard.putData("Intake", intake);
        SmartDashboard.putData("Feeder", feeder);
        SmartDashboard.putData("Hood", hood);
        SmartDashboard.putData("Shooter", shooter);
        SmartDashboard.putData("Turret", turret);
    }

    /** initialized bindings */
    public void initBindings() {
        controller
                .leftBumper()
                .whileTrue(
                        new StartEndCommand(
                                () -> spindexer.start(), () -> spindexer.stop(), spindexer));
        controller.povDown().onTrue(intake.runOnce(intake::deploy));
        controller.povUp().onTrue(intake.runOnce(intake::stow));
    }
}
