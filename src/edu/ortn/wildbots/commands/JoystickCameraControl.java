/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

/**
 *
 * @author Stephen
 */
public class JoystickCameraControl extends CommandBase {
    
    public JoystickCameraControl() {
        requires(camMount);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        if (oi.getRight().getRawAxis(6) > 0) {
            camMount.tiltUp(4.0);
        }
        else if (oi.getRight().getRawAxis(6) < 0) {
            camMount.tiltDown(4.0);
        }
         
         
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
