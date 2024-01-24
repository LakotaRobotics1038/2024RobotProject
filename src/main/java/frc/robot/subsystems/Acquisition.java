package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.AcquisitionConstants;

public class Acquisition extends SubsystemBase {
    private static Acquisition instance;

    private static CANSparkMax driveMotor = new CANSparkMax(AcquisitionConstants.motorPort, MotorType.kBrushless);

    /**
     * Resets the motor and sets the idle mode to brake, then burns the settings to
     * the flash.
     */
    private Acquisition() {
        driveMotor.restoreFactoryDefaults();

        driveMotor.setIdleMode(IdleMode.kBrake);

        driveMotor.burnFlash();
    }

    /**
     * Creates a new instance of the Acquisition subsystem if it does not exist.
     * 
     * @return An instance of the Acquisition subsystem
     */
    public static Acquisition getInstance() {
        if (instance == null) {
            instance = new Acquisition();
        }
        return instance;
    }

    /**
     * Clamps the given speed to fit between the minimum and maximum speeds, then
     * runs the motor at the clamped speed.
     * 
     * @param speed The speed at which the subsystem is to run its motor.
     */
    public void acquire(double speed) {
        speed = MathUtil.clamp(speed, AcquisitionConstants.minMotorSpeed, AcquisitionConstants.maxMotorSpeed);
        driveMotor.set(speed);
    }

    /**
     * Runs the Acquistion motor backwards at a constant speed.
     */
    public void dispose() {
        driveMotor.set(-AcquisitionConstants.motorSpeed);
    }

    /**
     * Stops the motor.
     */
    public void stop() {
        driveMotor.stopMotor();
    }
}
