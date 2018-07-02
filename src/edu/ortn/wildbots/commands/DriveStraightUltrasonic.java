/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.MainRobotClass;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author development
 */
class DriveStraightUltrasonic extends CommandBase {

    private double inches;
    
    public DriveStraightUltrasonic(int inches) {
        requires(chassis);
        this.inches = inches;
    }

    protected void initialize() {
        chassis.enable();
        chassis.setSetpoint(inches);
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return Math.abs(chassis.getPosition() - chassis.getSetpoint()) < 1;        
    }

    protected void end() {
        chassis.disable();
    }

    protected void interrupted() {
        chassis.disable();
    }
    
}
