/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.MainRobotClass;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 * @author sean
 */
public class DriveReverse extends CommandBase {

    public static final double FAST = 0.5;
    public static final double SLOW = 0.3;

    private boolean isFast;

    public DriveReverse(boolean fast, double time) {
        requires(chassis);

        isFast = fast;
        setTimeout(time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        chassis.arcadeDrive(((isFast) ? FAST: SLOW), 0, false);
    }

    protected boolean isFinished() {
       return this.isTimedOut();
    }

    protected void end() {
        chassis.arcadeDrive(0, 0, false);
    }

    protected void interrupted() {
        chassis.arcadeDrive(0, 0, false);
    }
}