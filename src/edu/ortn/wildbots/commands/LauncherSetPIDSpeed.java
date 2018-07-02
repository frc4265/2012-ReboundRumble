/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

/**
 *
 * @author Stephen
 */
public class LauncherSetPIDSpeed extends CommandBase {
    
    private double voltage;
    
    public LauncherSetPIDSpeed(double voltage) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(launcher);
        
        this.voltage = voltage;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        launcher.enable();
        launcher.setSetpoint(voltage);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(launcher.getPosition() - launcher.getSetpoint()) <= 0.05;
    }

    // Called once after isFinished returns true
    protected void end() {
        launcher.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        launcher.disable();
    }
}
