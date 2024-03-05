package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.DrawbridgeConstants;
import frc.robot.subsystems.Drawbridge;

public class DrawbridgeDownCommand extends Command {
    private Drawbridge drawbridge = Drawbridge.getInstance();

    public DrawbridgeDownCommand() {
        addRequirements(drawbridge);
    }

    @Override
    public void execute() {
        drawbridge.DrawbridgeDown();
    }

    @Override
    public boolean isFinished() {
        return (drawbridge.getDrawbridgePos() >= DrawbridgeConstants.minDrawbridgeExtension - 0.05
                && drawbridge.getDrawbridgePos() <= DrawbridgeConstants.minDrawbridgeExtension + 0.05);
    }
}
