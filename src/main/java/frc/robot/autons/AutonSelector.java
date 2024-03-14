package frc.robot.autons;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import frc.robot.subsystems.Dashboard;

public class AutonSelector {
    public enum AutonChoices {
        NoAuto,
        AmpAuto,
        AmpAutoWait,
        AmpAutoAcquire,
        TaxiPos1,
        TaxiPos2,
        TaxiPos3;
    }

    // Choosers
    SendableChooser<AutonChoices> autoChooser;
    SendableChooser<String> delayChooser;

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
        this.autoChooser.addOption("Score 2 In Amp Position 1", AutonChoices.AmpAuto);
        this.autoChooser.addOption("Wait Score in amp position 1", AutonChoices.AmpAutoWait);
        this.autoChooser.addOption("Score 2 in amp and acquire Pos 1", AutonChoices.AmpAutoAcquire);
        this.autoChooser.addOption("Taxi Position 1", AutonChoices.TaxiPos1);
        this.autoChooser.addOption("Taxi Position 2", AutonChoices.TaxiPos2);
        this.autoChooser.addOption("Taxi Position 3", AutonChoices.TaxiPos3);

        this.delayChooser = Dashboard.getInstance().getDelayChooser();

        this.delayChooser.setDefaultOption("0 Seconds", "0");
        this.delayChooser.addOption("1 Second", "1");
        this.delayChooser.addOption("2 Seconds", "2");
        this.delayChooser.addOption("3 Seconds", "3");
        this.delayChooser.addOption("4 Seconds", "4");
        this.delayChooser.addOption("5 Seconds", "5");
        this.delayChooser.addOption("6 Seconds", "6");
        this.delayChooser.addOption("7 Seconds", "7");
        this.delayChooser.addOption("8 Seconds", "8");
        this.delayChooser.addOption("9 Seconds", "9");
        this.delayChooser.addOption("10 Seconds", "10");
        this.delayChooser.addOption("11 Seconds", "11");
        this.delayChooser.addOption("12 Seconds", "12");
        this.delayChooser.addOption("13 Seconds", "13");
        this.delayChooser.addOption("14 Seconds", "14");
    }

    public Auton chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case AmpAuto:
                return new ScoreInAmp(alliance);
            case AmpAutoWait:
                return new ScoreInAmpWait(alliance);
            case AmpAutoAcquire:
                return new ScoreInAmpAcquire(alliance);
            case TaxiPos1:
                return new TaxiPos1(alliance);
            case TaxiPos2:
                return new TaxiPos2(alliance);
            case TaxiPos3:
                return new TaxiPos3(alliance);
            default:
                return null;
        }
    }

    public double chooseDelay() {
        switch (this.delayChooser.getSelected()) {
            case "1":
                return 1.0;
            case "2":
                return 2.0;
            case "3":
                return 3.0;
            case "4":
                return 4.0;
            case "5":
                return 5.0;
            case "6":
                return 6.0;
            case "7":
                return 7.0;
            case "8":
                return 8.0;
            case "9":
                return 9.0;
            case "10":
                return 10.0;
            case "11":
                return 11.0;
            case "12":
                return 12.0;
            case "13":
                return 13.0;
            case "14":
                return 14.0;
            default:
                return 0.0;
        }
    }
}