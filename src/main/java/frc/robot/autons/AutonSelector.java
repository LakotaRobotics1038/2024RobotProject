package frc.robot.autons;

import java.util.Optional;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        Pos1Amp;
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
        this.autoChooser.addOption("2 Note in Amp", AutonChoices.Pos1Amp);
    }

    public Command chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case Pos1Amp:
                return new PathPlannerAuto("2 Notes in Amp");
            default:
                return null;
        }
    }
}