/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.KickerDoNothing;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author development
 */
public class Kicker extends Subsystem {
    
    private DoubleSolenoid firing;
    
    public Kicker() {
        firing = new DoubleSolenoid(RobotMap.SOLENOID_KICKER_OUT, RobotMap.SOLENOID_KICKER_IN);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new KickerDoNothing());
    }
    
        /**
     * This method fires the ball by setting the value to forward.
     */
    public void fire() {
        firing.set(DoubleSolenoid.Value.kForward);
    }
    
    /**
     * This method retracts the firing piston by setting the value to reversed.
     */
    public void retract() {
        firing.set(DoubleSolenoid.Value.kReverse);
    }
    
    /**
     * This method stops air from flowing to the pistons.
     */
    public void ceaseFire() {
        firing.set(DoubleSolenoid.Value.kOff);
    }
}
