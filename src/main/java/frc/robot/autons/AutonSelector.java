package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        DriveToMidlinePosition1,
        DriveToMidlinePosition2,
        DriveToMidlinePosition3;
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
        this.autoChooser.setDefaultOption("Drive To Midline From Pos 1", AutonChoices.DriveToMidlinePosition1);
        this.autoChooser.setDefaultOption("Drive To Midline From Pos 2", AutonChoices.DriveToMidlinePosition2);
        this.autoChooser.setDefaultOption("Drive To Midline From Pos 3", AutonChoices.DriveToMidlinePosition3);
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
            default:
                return null;
        }
    }
}