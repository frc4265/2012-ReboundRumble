/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.subsystems.Launcher;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author sean
 */
public class LauncherSetSpeedToController extends CommandBase {

    public LauncherSetSpeedToController() {
        // Use requires() here to declare subsystem dependencies
        requires(launcher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        double throttleVal = -oi.getLeft().getZ();
        //SmartDashboard.putDouble("ThrottleVal:", throttleVal);

        double speed = 0;
        if (oi.getLeft().getRawButton(6)) {
            //SmartDashboard.putDouble("Sent Values", .295);
            speed = Launcher.GOAL_BOTTOM_FROM_KEY;
        }
        else if (oi.getLeft().getRawButton(4)) {
            //SmartDashboard.putDouble("Sent Values", .345);
            speed = Launcher.GOAL_MIDDLE_FROM_FENDER;
        }
        else if (oi.getLeft().getRawButton(3)) {
            speed = Launcher.GOAL_TOP_FROM_FRONT_OF_KEY;
        }
        else {
            speed = (throttleVal + 1)/2.0;
            SmartDashboard.putDouble("Sent Values", speed);
        }
        launcher.setSpeed(speed);

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
