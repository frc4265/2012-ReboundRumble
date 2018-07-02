/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.MoveElevatorForward;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem controls the ball pickup system. It uses two
 * motors and three photogates to advance the three balls through the 
 * system. It also allows for the balls to be backed out. 
 * @author Stephen Schwahn
 */
public class Elevator extends Subsystem {
   
    // Components of the subsystem.
    private Victor topMotor;
    private Victor bottomMotor;
    private DigitalInput sensorTop;
    private DigitalInput sensorMiddle;
    private DigitalInput sensorBottom;
    
    // Boolean values
    private boolean forwardTop;
    private boolean forwardBottom;
    private boolean enabled;
    private boolean topStarted;
    private boolean bottomStarted;
    
    // Class constants
    private static final double SPEED_TOP = 0.3;
    private static final double SPEED_BOTTOM = 0.8;

    /**
     * This constructor initializes the sensors for the elevator and the two
     * motors. It maps their ports to the values specified in the RobotMap 
     * class. 
     */
    public Elevator() {
        sensorBottom = new DigitalInput(RobotMap.SENSOR_CHAMBER_BOTTOM);
        sensorMiddle = new DigitalInput(RobotMap.SENSOR_CHAMBER_MIDDLE);
        sensorTop    = new DigitalInput(RobotMap.SENSOR_CHAMBER_TOP   );
        bottomMotor  = new Victor(RobotMap.MOTOR_CHAMBER_BOTTOM);
        topMotor     = new Victor(RobotMap.MOTOR_CHAMBER_TOP   );
    }
    
    /**
     * This method sets the default command of the Elevator to advance
     * the balls through the elevator (in the forward direction)
     */
    public void initDefaultCommand() {
        setDefaultCommand(new MoveElevatorForward());
    }
    
    /**
     * This method tells a given motor to move forward if it is currently moving
     * in reverse or if if it is currently stopped. The speed at which the motors 
     * move is given as a constant SPEED.
     * @param num the motor to start moving forward.
     */
    public void startForward(int num) {
        
        if (num == 1) {
            if (!forwardTop || !topStarted) {
                topStarted = true;
                forwardTop = true;
                topMotor.set(-SPEED_TOP);
            }
        }
        if (num == 2) {
            if (!forwardBottom || !bottomStarted) {
                bottomStarted = true;
                forwardBottom = true;
                bottomMotor.set(SPEED_BOTTOM);
            }
        }
    }
    
    /**
     * Debug method
     */
//    public void startForward(int num, double speed) {
//
//        if (num == 1) {
//            if (!forwardTop || !topStarted) {
//                topStarted = true;
//                forwardTop = true;
//                topMotor.set(-speed);
//            }
//        }
//        if (num == 2) {
//            if (!forwardBottom || !bottomStarted) {
//                bottomStarted = true;
//                forwardBottom = true;
//                bottomMotor.set(speed);
//            }
//        }
//    }
    
    /**
     * This method tells a given motor to move in reverse if it is currently moving
     * forward or if if it is currently stopped. The speed at which the motors 
     * move is given as a constant SPEED.
     * @param num the motor to start moving in reverse.
     */
    public void startReverse(int num) {
        if (num == 1) {
            if (forwardTop || !topStarted) {
                topStarted = true;
                forwardTop = false;
                topMotor.set(SPEED_TOP);
            }
        }
        if (num == 2) {
            if (forwardBottom || !bottomStarted) {
                bottomStarted = true;
                forwardBottom = false;
                bottomMotor.set(-SPEED_BOTTOM);
            }
        }        
    }
    
    /**
     * Debug Method
     */
//    public void startReverse(int num, double speed) {
//        if (num == 1) {
//            if (forwardTop || !topStarted) {
//                topStarted = true;
//                forwardTop = false;
//                topMotor.set(speed);
//            }
//        }
//        if (num == 2) {
//            if (forwardBottom || !bottomStarted) {
//                bottomStarted = true;
//                forwardBottom = false;
//                bottomMotor.set(-speed);
//            }
//        }
//    }
    
    /**
     * This method tells a given motor to stop if it is currently moving. 
     * @param num the motor to stop.
     */
    public void stopMotor(int num) {
        if (num == 1) {
            if (topStarted) {
                topStarted = false;
                topMotor.set(0.0);
            }
        }
        if (num == 2) {
            if (bottomStarted) {
                bottomStarted = false;
                bottomMotor.set(0.0);
            }
        }
    }
    
    /**
     * Return the value of the bottom sensor
     * @return the value of the bottom sensor
     */
    public boolean getSensorBottom() {
        return sensorBottom.get();
    }
    
    /**
     * Return the value of the middle sensor
     * @return the value of the middle sensor
     */
    public boolean getSensorMiddle() {
        return sensorMiddle.get();
    }
    
    /**
     * Return the value of the top sensor
     * @return the value of the top sensor
     */
    public boolean getSensorTop() {
        return sensorTop.get();
    }
    
    /**
     * Returns the enabled state of the elevator.
     * @return the enabled/disabled state of the elevator.
     */
    public boolean isDisabled() {
        return !enabled;
    }
    
    /**
     * This will set the elevator state to enabled or disabled.
     * @param disabled disabled state of the elevator
     */
    public void setDisabled(boolean disabled) {
        enabled = !disabled;
    }
}
