/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author development
 */
public class Cathodes extends Subsystem {
    
    private Relay cathodesBlue;
    private Relay cathodesRed;
    
    public Cathodes() {
        cathodesBlue = new Relay(RobotMap.BLUE_COLD_CATHODES_RELAY);
        cathodesBlue.setDirection(Relay.Direction.kForward);

        cathodesRed = new Relay(RobotMap.RED_COLD_CATHODES_RELAY);
        cathodesRed.setDirection(Relay.Direction.kForward);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void turnOnBlue() {
        cathodesBlue.set(Relay.Value.kOn);
    }
    
    public void turnOffBlue() {
        cathodesBlue.set(Relay.Value.kOff);
    }

    public void turnOffRed() {
        cathodesRed.set(Relay.Value.kOff);
    }

    public void turnOnRed() {
        cathodesRed.set(Relay.Value.kOn);
    }
    
}
