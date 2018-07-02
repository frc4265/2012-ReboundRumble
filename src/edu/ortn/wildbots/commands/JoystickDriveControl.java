/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.wpi.first.wpilibj.Joystick.ButtonType;

/**
 *
 * @author Stephen
 */
public class JoystickDriveControl extends CommandBase {
    
    public JoystickDriveControl() {
        requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //chassis.arcadeDrive(oi.getRight(), oi.getLeft(), true); //Arcade drive without bias correction, power scale, but squares inputs
        double power = oi.getRight().getY();
        double curl = oi.getRight().getZ();
        double mult = (-oi.getRight().getThrottle()+1.0)/2.0;
        //chassis.somethingSomethingArcadeDrive(power, curl);
        chassis.arcadeDrive(power * mult, curl * mult, false);
        //chassis.seansArcadekDrive(power, curl, mult, false, false, false, false);
        //chassis.tankDrive(power * mult, power * mult);
        

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
