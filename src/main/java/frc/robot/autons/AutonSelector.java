package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.AcquisitionRunCommand;
import frc.robot.commands.FullAcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ScoringElevatorPositionCommand;
import frc.robot.commands.ShootNoteCommand;
import frc.robot.commands.StorageRunCommand;
import frc.robot.commands.TransitionRunCommand;
import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        DriveToMidlinePosition1,
        DriveToMidlinePosition2,
        DriveToMidlinePosition3,
        ScoreInAmp,
        ShootInSpeakerPosition2,
        ShootInSpeakerPosition3;
    }

    // Choosers
    SendableChooser<AutonChoices> autoChooser;

    private Pose2d initialPose;
    protected Alliance alliance;

    // Singleton Setup
    private static AutonSelector instance;

    public static AutonSelector getInstance() {
        if (instance == null) {
            System.out.println("Creating new AutonSelector");
            instance = new AutonSelector();
        }
        return instance;
    }

    private AutonSelector() {
        this.autoChooser = Dashboard.getInstance().getAutoChooser();

        this.autoChooser.setDefaultOption("No Auto", AutonChoices.NoAuto);
        this.autoChooser.addOption("Drive to Midline Position 1", AutonChoices.DriveToMidlinePosition1);
        this.autoChooser.addOption("Drive to Midline Position 2", AutonChoices.DriveToMidlinePosition2);
        this.autoChooser.addOption("Drive to Midline Position 3", AutonChoices.DriveToMidlinePosition3);
        this.autoChooser.addOption("Score In Amp Position 1", AutonChoices.ScoreInAmp);
        this.autoChooser.addOption("Shoot in Speaker Position 2", AutonChoices.ShootInSpeakerPosition2);
        this.autoChooser.addOption("Shoot in Speaker Position 3", AutonChoices.ShootInSpeakerPosition3);

        NamedCommands.registerCommand("ScoreNoteAmpCommand", new ScoreNoteAmpCommand());
        NamedCommands.registerCommand("ShootNoteCommand", new ShootNoteCommand());
        NamedCommands.registerCommand("AcquisitionRunCommand", new AcquisitionRunCommand());
        NamedCommands.registerCommand("TransitionRunCommand", new TransitionRunCommand());
        NamedCommands.registerCommand("ScoringElevatorPositionCommand", new ScoringElevatorPositionCommand(null));
        NamedCommands.registerCommand("StorageRunCommand", new StorageRunCommand());
        NamedCommands.registerCommand("FullAcquireCommand", new FullAcquireCommand());
    }

    public Auton chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case DriveToMidlinePosition1:
                return new DriveToMidlinePosition1(alliance);
            case DriveToMidlinePosition2:
                return new DriveToMidlinePosition2(alliance);
            case DriveToMidlinePosition3:
                return new DriveToMidlinePosition3(alliance);
            case ShootInSpeakerPosition2:
                return new ShootInSpeakerPosition2(alliance);
            case ShootInSpeakerPosition3:
                return new ShootInSpeakerPosition3(alliance);
            default:
                return null;
        }
    }
}
