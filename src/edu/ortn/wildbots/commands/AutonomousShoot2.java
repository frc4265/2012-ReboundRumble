/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.subsystems.Launcher;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author development
 */
public class AutonomousShoot2 extends CommandGroup {
    
    public AutonomousShoot2() {
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
        addSequential(new LauncherSetSpeed(Launcher.GOAL_MIDDLE_FROM_KEY,5));
        addSequential(new WaitUntilReadyToFire());
        addSequential(new LauncherFireCommandGroup());
        addSequential(new KickerRetract());
        addParallel(new LauncherSetSpeed(Launcher.GOAL_MIDDLE_FROM_KEY, 1));
        addSequential(new WaitUntilReadyToFire());
        addSequential(new LauncherFireCommandGroup());
        addSequential(new KickerRetract());
       
    }
}
