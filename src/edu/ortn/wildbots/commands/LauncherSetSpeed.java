/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 * @author development
 */
public class LauncherSetSpeed extends CommandBase {
    
    private double speed;
    
    public LauncherSetSpeed(double speed, double time) {
        // Use requires() here to declare subsystem dependencies
        requires(launcher);
        
        this.speed = speed;
        this.setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        launcher.setSpeed(speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
