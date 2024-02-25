package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkFlex;

import frc.robot.constants.ScoringConstants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Scoring extends SubsystemBase {
    private final CANSparkFlex rollerMotor = new CANSparkFlex(
            ScoringConstants.rollerMotorPort, MotorType.kBrushless);

    private static Scoring instance;

    public static Scoring getInstance() {

        if (instance == null) {
            instance = new Scoring();
        }
        return instance;
    }

    public Scoring() {
        rollerMotor.restoreFactoryDefaults();
        rollerMotor.setIdleMode(IdleMode.kCoast);
        rollerMotor.setInverted(false);
        rollerMotor.burnFlash();
    }

    /**
     * Runs the scoring roller at the constant speed designated for regular scoring.
     */
    public void runRoller() {
        rollerMotor.set(ScoringConstants.rollerSpeed);
    }

    /**
     * Runs the scoring roller at the constant speed designated for shooting.
     */
    public void rollerShoot() {
        rollerMotor.set(-ScoringConstants.rollerSpeed);
    }

    /**
     * Stops the scoring roller.
     */
    public void stopRoller() {
        rollerMotor.stopMotor();
    }
}
