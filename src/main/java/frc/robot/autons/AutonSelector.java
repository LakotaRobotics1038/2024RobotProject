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
        ScoreAmpBack,
        AmpAutoMidline,
        AmpAutoAcquire,
        AmpAutoTrap,
        TaxiPos1,
        TaxiPos2,
        TaxiPos3;
    }

    // Choosers
    SendableChooser<AutonChoices> autoChooser;
    SendableChooser<Double> delayChooser;

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
        this.autoChooser.addOption("Score Amp Back", AutonChoices.ScoreAmpBack);
        this.autoChooser.addOption("Score 2 in amp from midline Pos 1", AutonChoices.AmpAutoMidline);
        this.autoChooser.addOption("Score 2 in amp and acquire Pos 1", AutonChoices.AmpAutoAcquire);
        this.autoChooser.addOption("Score 1 in amp trap pos 1", AutonChoices.AmpAutoTrap);
        this.autoChooser.addOption("Taxi Position 1", AutonChoices.TaxiPos1);
        this.autoChooser.addOption("Taxi Position 2", AutonChoices.TaxiPos2);
        this.autoChooser.addOption("Taxi Position 3", AutonChoices.TaxiPos3);

        this.delayChooser = Dashboard.getInstance().getDelayChooser();

        this.delayChooser.setDefaultOption("0 Seconds", 0.0);
        this.delayChooser.addOption("1 Second", 1.0);
        this.delayChooser.addOption("2 Seconds", 2.0);
        this.delayChooser.addOption("3 Seconds", 3.0);
        this.delayChooser.addOption("4 Seconds", 4.0);
        this.delayChooser.addOption("5 Seconds", 5.0);
        this.delayChooser.addOption("6 Seconds", 6.0);
        this.delayChooser.addOption("7 Seconds", 7.0);
        this.delayChooser.addOption("8 Seconds", 8.0);
        this.delayChooser.addOption("9 Seconds", 9.0);
        this.delayChooser.addOption("10 Seconds", 10.0);
        this.delayChooser.addOption("11 Seconds", 11.0);
        this.delayChooser.addOption("12 Seconds", 12.0);
        this.delayChooser.addOption("13 Seconds", 13.0);
        this.delayChooser.addOption("14 Seconds", 14.0);
    }

    public Auton chooseAuton() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        switch (this.autoChooser.getSelected()) {
            case AmpAuto:
                return new ScoreInAmp(alliance);
            case ScoreAmpBack:
                return new ScoreInAmpBack(alliance);
            case AmpAutoMidline:
                return new ScoreInAmpMidline(alliance);
            case AmpAutoAcquire:
                return new ScoreInAmpAcquire(alliance);
            case AmpAutoTrap:
                return new ScoreInAmpTrap(alliance);
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
        return this.delayChooser.getSelected();
    }
}