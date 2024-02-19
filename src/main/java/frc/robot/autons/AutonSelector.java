package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        Test;
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
        this.autoChooser.addOption("test", AutonChoices.Test);
    }

    public Auton chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case Test:
                return new TestableScoreInAmp(alliance);
            default:
                return null;
        }
    }
}