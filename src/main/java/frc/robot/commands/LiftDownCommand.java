package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.LiftConstants;
import frc.robot.subsystems.Lift;

public class LiftDownCommand extends Command {
    private Lift lift = Lift.getInstance();

    public LiftDownCommand() {
        addRequirements(lift);
    }

    @Override
    public void initialize() {
        PIDController leftController = lift.getLeftVerticalController();
        leftController.setP(LiftConstants.kVerticalDownP);
        leftController.setI(LiftConstants.kVerticalDownI);
        leftController.setD(LiftConstants.kVerticalDownD);
        PIDController rightController = lift.getRightVerticalController();
        rightController.setP(LiftConstants.kVerticalDownP);
        rightController.setI(LiftConstants.kVerticalDownI);
        rightController.setD(LiftConstants.kVerticalDownD);
        lift.enable();
        lift.setSetpointLeft(LiftConstants.minLiftInches);
        lift.setSetpointRight(LiftConstants.minLiftInches);
    }

    @Override
    public boolean isFinished() {
        return lift.leftLowerLimitReached() && lift.rightLowerLimitReached();
    }

    @Override
    public void end(boolean interrupted) {
        lift.disable();
    }
}
