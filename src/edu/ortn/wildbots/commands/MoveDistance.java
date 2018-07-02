/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

/**
 *
 * @author development
 */
public class MoveDistance extends CommandBase {
    
    private double inches;
    
    public MoveDistance(double inches) {
        requires(chassis);
        
        this.inches = inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        chassis.driveSetDistance(inches);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return chassis.isLeftFinished() && chassis.isRightFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
