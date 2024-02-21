package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Vision;

public class NewDriveToVisionCommand extends Command {

    private Vision vision;
    private boolean detected;

    public NewDriveToVisionCommand() {
        addRequirements(vision);
    }

    public void execute() {

    }

    public boolean isFinished() {
        if (detected) {
            return true;
        }
        return false;
    }

    public void end() {

    }
}
