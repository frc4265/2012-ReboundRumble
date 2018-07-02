/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.TurnOffLEDs;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author development
 */
public class LEDRing extends Subsystem {
    
   // private Relay ledRing;
    
    public LEDRing() {
        //ledRing = new Relay(RobotMap.RELAY_LED_RING, Relay.Direction.kBoth);
    }
    
    public void initDefaultCommand() {
        //setDefaultCommand(new TurnOffLEDs());
    }

    /*
    public void turnOffLEDs() {
        ledRing.set(Relay.Value.kOff);
    }
    
    public void turnOnGreenLEDs() {
        ledRing.set(Relay.Value.kForward);
    }
    
    public void turnOnRedLEDs() {
        ledRing.set(Relay.Value.kReverse);
    }*/
}
