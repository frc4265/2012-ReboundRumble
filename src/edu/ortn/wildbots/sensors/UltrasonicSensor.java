/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.sensors;

import edu.ortn.wildbots.RobotMap;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.SensorBase;

/**
 *
 * @author Stephen
 */
public class UltrasonicSensor extends SensorBase {
    
    private AnalogChannel sensor;
    
    public UltrasonicSensor(int port) {
        sensor = new AnalogChannel(port);
        sensor.setAverageBits(3);
        sensor.setOversampleBits(0);
    }
    
    public double getRangeInches() {
        double voltage = sensor.getAverageVoltage();
        
        //Assuming 5V input (V_in/512 per inch)
        return voltage/0.0098; // mV / 9.8 (mV/in) = # of inches 
    }
                
}

            
            