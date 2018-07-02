/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.DrawbridgeLocked;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * This subsystem controls the drawbridge system. It uses two Solenoid 
 * objects - one to extend and one to retract. After each operation, there 
 * needs to be a delay before the solenoids are closed.
 * @author Stephen Schwahn
 */
public class Drawbridge extends Subsystem {

    // Components of the subsystem
    private Solenoid pistonOut;
    private Solenoid pistonIn;
    
    
    /**
     * This constructor initializes the solenoids to the Solenoid breakout 
     * values that are specified in RobotMap. One represents a piston
     * that moves out and one the represents a piston that moves in.
     */
    public Drawbridge() {
        pistonOut = new Solenoid(RobotMap.SOLENOID_DRAWBRIDGE_OUT);
        pistonIn  = new Solenoid(RobotMap.SOLENOID_DRAWBRIDGE_IN);
    }
    
    /**
     * This method sets the default command of the Drawbridge to the
     * retracted position.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new DrawbridgeLocked());
    }
    
    /**
     * This method puts the drawbridge into the down position
     */
    public void activate() {
        pistonOut.set(true);
        pistonIn.set(false);
    }
    
    /**
     * This method puts the drawbridge in the up position
     */
    public void retract() {
        pistonOut.set(false);
        pistonIn.set(true);
    }
    
    /**
     * This method closes the solenoids so that the air stops
     */
    public void close() {
        pistonOut.set(false);
        pistonIn.set(false);
    }
    
    /**
     * Returns the position of the drawbridge
     * @return true if extended and false if up
     */
    public boolean isDown() {
        if (pistonOut.get() && !pistonIn.get())
            return true;
        return false;
    }
}
