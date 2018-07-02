/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author development
 */
class WaitUntilReadyToFire extends CommandBase {
    
    public WaitUntilReadyToFire() {
        requires(elevator);
    }

    protected void initialize() {
    }

    protected void execute() {
//        if (!elevator.getSensorTop()) {
//            elevator.startForward(1);
//            elevator.startForward(2);
//        }
//        else if (!elevator.getSensorMiddle()) {
//            elevator.startForward(1);
//            elevator.startForward(2);
//        }
//        else if (!elevator.getSensorBottom()) {
//            elevator.startForward(2);
//            elevator.stopMotor(1);
//        }
//
//        else if (elevator.getSensorBottom() && elevator.getSensorMiddle() &&
//                elevator.getSensorTop()) {
//            elevator.stopMotor(1);
//            elevator.stopMotor(2);
//        }
//
//        else {
//            elevator.startForward(1);
//            elevator.startForward(2);
//        }
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

    protected boolean isFinished() {
        return elevator.getSensorTop();        
    }

    protected void end() {
    }

    protected void interrupted() {
    }
    
}
