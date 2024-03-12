package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drawbridge;

public class DrawbridgeDownCommand extends Command {
    private Drawbridge drawbridge = Drawbridge.getInstance();

    public DrawbridgeDownCommand() {
        addRequirements(drawbridge);
    }

    @Override
    public void initialize() {
        drawbridge.down();
    }

    @Override
    public boolean isFinished() {
        return true;
        // return drawbridge.getPosition() >= DrawbridgeConstants.minDrawbridgeExtension
        // - 0.05
        // && drawbridge.getPosition() <= DrawbridgeConstants.minDrawbridgeExtension +
        // 0.05;
    }
}
