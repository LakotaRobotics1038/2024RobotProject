package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.AcquireCommand;
import frc.robot.commands.ScoreNoteAmpCommand;
import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        Pos1Amp,
        DriveForward;
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
        this.autoChooser.addOption("Amp Auto", AutonChoices.Pos1Amp);
        this.autoChooser.addOption("DriveForward", AutonChoices.DriveForward);

        NamedCommands.registerCommand("ScoreAmpCommand", new ScoreNoteAmpCommand());
        NamedCommands.registerCommand("AcquireCommand", new AcquireCommand());

    }

    public Auton chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case Pos1Amp:
                return new NotesInAmpAuto(alliance);
            case DriveForward:
                return new DriveForward(alliance);
            default:
                return null;
        }
    }
}