package edu.ortn.wildbots;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    
    // PWMs
    public static final int JAGUAR_LEFT             = 1;
    public static final int JAGUAR_LEFT2            = 2;
    public static final int JAGUAR_RIGHT            = 8;
    public static final int JAGUAR_RIGHT2           = 7;
    public static final int MOTOR_CHAMBER_BOTTOM    = 5;
    public static final int MOTOR_CHAMBER_TOP       = 6;
    public static final int LAUNCHER_MASTER         = 3;
    public static final int LAUNCHER_SLAVE          = 4;
    public static final int TILT_SERVO              = 9;
    
    // Digital IO
    public static final int SENSOR_CHAMBER_BOTTOM   = 3;
    public static final int SENSOR_CHAMBER_MIDDLE   = 2;
    public static final int SENSOR_CHAMBER_TOP      = 1;    
    public static final int PRESSURE_SWITCH         = 4;

    public static final int SWITCH1                 = 5;
    public static final int SWITCH2                 = 6;
    public static final int SWITCH3                 = 7;

    public static final int ENCODER_LEFT_A          = 11;
    public static final int ENCODER_LEFT_B          = 12;
    public static final int ENCODER_RIGHT_A         = 13;
    public static final int ENCODER_RIGHT_B         = 14;  
    
    // Analog
    public static final int GYRO_PORT               = 1; 
    public static final int RANGEFINDER_PORT        = 2;
    
    // Solenoids
    public static final int SOLENOID_KICKER_IN      = 1;
    public static final int SOLENOID_KICKER_OUT     = 2;
    public static final int SOLENOID_DRAWBRIDGE_IN  = 3;
    public static final int SOLENOID_DRAWBRIDGE_OUT = 4;
    
    // Spikes
    public static final int COMPRESSOR_RELAY        = 1;   
    public static final int BLUE_COLD_CATHODES_RELAY= 2;
    public static final int RED_COLD_CATHODES_RELAY = 3;
    // Driver Station Ports
    public static final int JOYSTICK_LEFT           = 1;
    public static final int JOYSTICK_RIGHT          = 2;  
    public static final String CAM_IP               = "10.42.65.11";

    
    
}
