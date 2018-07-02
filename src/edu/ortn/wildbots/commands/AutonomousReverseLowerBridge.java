/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author sean
 */
public class AutonomousReverseLowerBridge extends CommandGroup {

    public AutonomousReverseLowerBridge() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        addSequential(new WaitCommand(6));
        addSequential(new MoveElevatorReverse(), 2);
        addSequential(new WaitCommand(2));
        addSequential(new MoveElevatorReverse(), 5);
        //addSequential(new TurnByAngle(180));
        //addSequential(new AutonomousLowerBridge());
    }
}