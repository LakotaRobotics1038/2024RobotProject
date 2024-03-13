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

    public enum DelayChoices {
        s0,
        s1,
        s2,
        s3,
        s4,
        s5,
        s6,
        s7,
        s8,
        s9,
        s10,
        s11,
        s12,
        s13,
        s14;
    }

    // Choosers
    SendableChooser<AutonChoices> autoChooser;
    SendableChooser<DelayChoices> delayChooser;

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

        this.delayChooser.setDefaultOption("0 Seconds", DelayChoices.s0);
        this.delayChooser.setDefaultOption("1 Second", DelayChoices.s1);
        this.delayChooser.setDefaultOption("2 Seconds", DelayChoices.s2);
        this.delayChooser.setDefaultOption("3 Seconds", DelayChoices.s3);
        this.delayChooser.setDefaultOption("4 Seconds", DelayChoices.s4);
        this.delayChooser.setDefaultOption("5 Seconds", DelayChoices.s5);
        this.delayChooser.setDefaultOption("6 Seconds", DelayChoices.s6);
        this.delayChooser.setDefaultOption("7 Seconds", DelayChoices.s7);
        this.delayChooser.setDefaultOption("8 Seconds", DelayChoices.s8);
        this.delayChooser.setDefaultOption("9 Seconds", DelayChoices.s9);
        this.delayChooser.setDefaultOption("10 Seconds", DelayChoices.s10);
        this.delayChooser.setDefaultOption("11 Seconds", DelayChoices.s11);
        this.delayChooser.setDefaultOption("12 Seconds", DelayChoices.s12);
        this.delayChooser.setDefaultOption("13 Seconds", DelayChoices.s13);
        this.delayChooser.setDefaultOption("14 Seconds", DelayChoices.s14);
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

    public int chooseDelay() {
        switch (this.delayChooser.getSelected()) {
            default:
                return 0;
            case s1:
                return 1;
            case s2:
                return 2;
            case s3:
                return 3;
            case s4:
                return 4;
            case s5:
                return 5;
            case s6:
                return 6;
            case s7:
                return 7;
            case s8:
                return 8;
            case s9:
                return 9;
            case s10:
                return 10;
            case s11:
                return 11;
            case s12:
                return 12;
            case s13:
                return 13;
            case s14:
                return 14;
        }
    }
}