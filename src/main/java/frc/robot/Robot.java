// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.hal.ControlWord;
import edu.wpi.first.hal.DriverStationJNI;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.autons.Auton;
import frc.robot.autons.AutonSelector;
import frc.robot.commands.LiftDownManualCommand;
import frc.robot.commands.LiftUpCommand;
import frc.robot.constants.SwerveModuleConstants;
import frc.robot.subsystems.Dashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.SwagLights;

public class Robot extends TimedRobot {
    // Singleton Instances
    private AutonSelector autonSelector = AutonSelector.getInstance();
    private SwagLights swagLights = SwagLights.getInstance();

    // Variables
    private Auton autonomousCommand;
    private ControlWord controlWordCache = new ControlWord();

    // Subsystems
    private DriveTrain driveTrain = DriveTrain.getInstance();

    @Override
    public void robotInit() {
        // Singleton instances that need to be created but not referenced
        DriverJoystick.getInstance();
        OperatorJoystick.getInstance();
        Dashboard.getInstance();

        addPeriodic(swagLights::periodic, 0.25);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void disabledInit() {
        System.out.println("Robot Disabled");
        DriverStationJNI.getControlWord(controlWordCache);
        if (controlWordCache.getEStop()) {
            swagLights.setEStop();
        } else {
            swagLights.setDisabled(true);
        }
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void disabledExit() {
        swagLights.setDisabled(false);
    }

    @Override
    public void autonomousInit() {
        new SequentialCommandGroup(
                new LiftDownManualCommand(),
                new LiftUpCommand())
                .schedule();

        driveTrain.zeroHeading();
        autonomousCommand = autonSelector.chooseAuton();
        // if (DriverStation.isFMSAttached()) {
        // vision.startRecording();
        // }

        if (autonomousCommand != null) {
            Pose2d initialPose = autonomousCommand.getInitialPose();
            if (initialPose != null) {
                driveTrain.resetOdometry(initialPose);
            }
            driveTrain.setDrivingIdleMode(SwerveModuleConstants.kAutoDrivingMotorIdleMode);
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void autonomousExit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopInit() {
        Dashboard.getInstance().clearTrajectory();
        driveTrain.setDrivingIdleMode(SwerveModuleConstants.kTeleopDrivingMotorIdleMode);
    }

    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void teleopExit() {
        driveTrain.setX();
        // vision.stopRecording();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
    }

    @Override
    public void testExit() {
    }
}
