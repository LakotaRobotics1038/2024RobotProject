package frc.robot.autons;

import java.util.Optional;

import org.ejml.interfaces.decomposition.TridiagonalSimilarDecomposition;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        ToMid,
        AmpAuto1,
        AmpAuto2,
        Rotate,
        Straight;
    }

    // Choosers
    SendableChooser<AutonChoices> autoChooser;

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
        this.autoChooser.addOption("Drive to Midline Position 1", AutonChoices.ToMid);
        this.autoChooser.addOption("Score In Amp Position 1 (1)", AutonChoices.AmpAuto1);
        this.autoChooser.addOption("Score In Amp Position 1 (2)", AutonChoices.AmpAuto2);
        this.autoChooser.addOption("Rotate Auto", AutonChoices.Rotate);
        this.autoChooser.addOption("Straight Path Auto", AutonChoices.Straight);

    }

    public Auton chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case ToMid:
                return new DriveToMidlinePosition1(alliance);
            case AmpAuto1:
                return new ScoreInAmp(alliance);
            case AmpAuto2:
                return new NotesInAmpAuto(alliance);
            case Rotate:
                return new RotateAuton(alliance);
            case Straight:
                return new StraightAuton(alliance);
            default:
                return null;
        }
    }
}