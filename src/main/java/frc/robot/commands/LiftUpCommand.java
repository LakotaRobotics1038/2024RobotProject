package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.LiftConstants;
import frc.robot.subsystems.Drawbridge;
import frc.robot.subsystems.Lift;

public class LiftUpCommand extends Command {
    private Lift lift = Lift.getInstance();
    private Drawbridge drawbridge = Drawbridge.getInstance();

    public LiftUpCommand() {
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        lift.disableRatchets();
        PIDController leftController = lift.getLeftVerticalController();
        leftController.setP(LiftConstants.kVerticalLeftUpP);
        leftController.setI(LiftConstants.kVerticalLeftUpI);
        leftController.setD(LiftConstants.kVerticalLeftUpD);
        PIDController rightController = lift.getRightVerticalController();
        rightController.setP(LiftConstants.kVerticalRightUpP);
        rightController.setI(LiftConstants.kVerticalRightUpI);
        rightController.setD(LiftConstants.kVerticalRightUpD);
        lift.enable();
        lift.setSetpointLeft(LiftConstants.maxLiftInches - 0.5);
        lift.setSetpointRight(LiftConstants.maxLiftInches);
    }

    @Override
    public boolean isFinished() {
        return lift.isLiftUp();
    }

    @Override
    public void end(boolean interrupted) {
        lift.enableRatchets();
        lift.disable();
        drawbridge.up();
    }
}
