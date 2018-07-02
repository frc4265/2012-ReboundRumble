/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.MainRobotClass;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author development
 */
class DriveForward extends CommandBase {
    
    public static final double FAST = 0.5;
    public static final double SLOW = 0.3;
    
    private boolean isFast;

    public DriveForward(boolean fast, double time) {
        requires(chassis);
        
        isFast = fast;
        setTimeout(time);

    }

    protected void initialize() {
    }

    protected void execute() {
        chassis.arcadeDrive(-((isFast) ? FAST: SLOW), 0, false);
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
