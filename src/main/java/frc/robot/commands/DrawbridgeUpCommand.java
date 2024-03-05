package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DrawbridgeConstants;
import frc.robot.subsystems.Drawbridge;

public class DrawbridgeUpCommand extends Command {
    private Drawbridge drawbridge = Drawbridge.getInstance();

    public DrawbridgeUpCommand() {
        addRequirements(drawbridge);
    }

    @Override
    public void execute() {
        drawbridge.DrawbridgeUp();
    }

    @Override
    public boolean isFinished() {
        return (drawbridge.getDrawbridgePos() >= DrawbridgeConstants.maxDrawbridgeExtension - 0.05
                && drawbridge.getDrawbridgePos() <= DrawbridgeConstants.maxDrawbridgeExtension + 0.05);
    }
}
