package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Acquisition;
import frc.robot.subsystems.ScoringElevator;
import frc.robot.subsystems.Storage;
import frc.robot.subsystems.ScoringElevator.ElevatorSetpoints;

public class FullAcquireSequenceCommand extends Command {
    private Storage storage = Storage.getInstance();
    private Acquisition acquisition = Acquisition.getInstance();
    private ScoringElevator scoringElevator = ScoringElevator.getInstance();

    public FullAcquireSequenceCommand() {
        addRequirements(storage, acquisition);
    }

    @Override
    public void execute() {
        if (scoringElevator.getSetpoint() == ElevatorSetpoints.Ground.value) {
            acquisition.acquire();
            acquisition.runSushi();
            storage.runTransition();
            storage.runStorage();
        }
    }

    @Override
    public boolean isFinished() {
        return storage.noteExitingStorage();
    }

    @Override
    public void end(boolean interrupted) {
        acquisition.stopIntake();
        acquisition.stopSushi();
        storage.stopTransition();
        storage.stopStorage();
    }
}
