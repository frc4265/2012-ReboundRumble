/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author development
 */
public class LauncherUpdateSpeed extends CommandBase {
    
    public LauncherUpdateSpeed() {
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (launcher.isActive) {
            double throttleVal = oi.getLeft().getZ();
            SmartDashboard.putDouble("ThrottleVal:", throttleVal);

            double speed = 0;
            if (oi.getLeft().getRawButton(2)) {
                SmartDashboard.putDouble("Sent Values", .295);
                speed = 0.295;
            }
            else if (oi.getLeft().getRawButton(3)) {
                SmartDashboard.putDouble("Sent Values", .345);
                speed = 0.345;
            }
            else {
                speed = (throttleVal + 1)/2.0;
                SmartDashboard.putDouble("Sent Values", speed);
            }
            launcher.setSpeed(speed);
        }
        else {
            launcher.setSpeed(0.0);
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
