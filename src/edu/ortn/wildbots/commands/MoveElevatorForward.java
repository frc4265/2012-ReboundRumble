/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

/**
 *
 * @author Stephen
 */
public class MoveElevatorForward extends CommandBase {
    
    public MoveElevatorForward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // Debug info
//        double speed = (oi.getLeft().getZ()+1)/2;
//        speed = Math.min(speed, 0.6);
        
        if (!elevator.getSensorTop()) { 
            elevator.startForward(1);
            elevator.startForward(2);
        } 
        else if (!elevator.getSensorMiddle()) {
            elevator.startForward(1);
            elevator.startForward(2);
        }
        else if (!elevator.getSensorBottom()) {
            elevator.startForward(2);
            elevator.stopMotor(1);
        }

        else if (elevator.getSensorBottom() && elevator.getSensorMiddle() && 
                elevator.getSensorTop()) {
            elevator.stopMotor(1);
            elevator.stopMotor(2);
        }

        else {
            elevator.startForward(1);
            elevator.startForward(2);
        }
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        elevator.stopMotor(1);
        elevator.stopMotor(2);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        elevator.stopMotor(1);
        elevator.stopMotor(2);
    }
}
