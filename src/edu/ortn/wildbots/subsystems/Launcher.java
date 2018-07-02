/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.MainRobotClass;
import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.KickerDoNothing;
import edu.ortn.wildbots.commands.LauncherUpdateSpeed;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the variable-speed launcher and the pneumatic kicker.
 * It uses a  relay to control the piston and a Jaguar speed controller to 
 * spin the CIM motor. 
 * 
 * Generally, two types of commands will operate on this subsytem. One should be 
 * one to control the speed and another should be a command to control the 
 * kicker. 
 * 
 * This system has PID functionality, so that a desired speed can be set and 
 * the speed is incrementally increased/decreased.
 * @author Stephen
 */
public class Launcher extends PIDSubsystem {
    
//    private final double G = 9.8;   // meter/sec
//    private double BETA = Math.PI/3.0;
//    private double RADIUS = 0.0762; // meters
//    public double TOP_HEIGHT = 2.4892; // meters
//    public final double H_c = 0.69;
//    public final double H_r = 0.84;
//    private double MAX_SPEED = 5.2;  // Constant to be experimentaly determined

    // Shooting PWM constants for 1 CIM
    public static final double GOAL_BOTTOM_FROM_KEY = 0.286;
    public static final double GOAL_MIDDLE_FROM_KEY = 0.410;
    public static final double GOAL_MIDDLE_FROM_FENDER = 0.377;
    public static final double GOAL_TOP_FROM_FRONT_OF_KEY = 0.451;
    public static final double GOAL_TOP_FROM_BACK_OF_KEY = 0.466;
    
    public static final double OFFSET_INCREMENT = 0.05;
    
    // PIDController constants
    private static final double Kp = 2.0;
    private static final double Ki = 0.0;
    private static final double Kd = 0.0;
    
    // System Components
    private Jaguar launcher_master;
    private Jaguar launcher_slave;
    
    private double setpoint_speed;
    
    public static boolean isActive = false; 

    /**
     * This constructor sets up the launcher speed controller to the port
     * specified in RobotMap. The firing mechanism is a solenoid that is 
     * wired through a relay. Therefore, it is controlled from the relay
     * component on the digital sidecar. Again, this value is set from
     * RobotMap.
     */
    public Launcher() {        
        super("Launcher", Kp, Ki, Kd);
        
        launcher_master = new Jaguar(RobotMap.LAUNCHER_MASTER);
        launcher_slave  = new Jaguar(RobotMap.LAUNCHER_SLAVE);

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    /**
     * The default command for the launcher system needs to make sure that the 
     * Jaguar is stopped and that the firing piston is retracted.
     */
    public void initDefaultCommand() {
        setDefaultCommand(new LauncherUpdateSpeed());
    }
    

    /**
     * The PIDController will set the speed of the launcher incrementally based
     * on voltage.
     * @param output 
     */
    protected void usePIDOutput(double output) {
        launcher_master.set(output);
        launcher_slave.set(output);
    }
    
    protected double returnPIDInput() {
        return launcher_master.get(); // Useless until CAN
    }
    
    /**
     * This method will set the speed of the motor without PID. However, it may
     * or may not take a few seconds to ramp up entirely.
     * @param speed 
     */
    public void setSpeed(double speed) {
        speed += MainRobotClass.launcher_offset;
        if (speed < 0.0)
            speed = 0.0;
        else if (speed > 1)
            speed = 1;
        
        //speed = -speed;
        setpoint_speed = speed;
        
        launcher_master.set(speed);
        launcher_slave.set(speed);

        SmartDashboard.putDouble("SPEED: ", speed);
    }

    public void setSpeedToggle(double speed) {
        if (isActive) {
            setSpeed(0.0);   
        }
        else {
            setSpeed(speed);
        }
        isActive = !isActive;
    }
    
    public void stopLauncher() {
        isActive = false;
        setSpeed(0.0);
    }
    
    public double getSetpoint() {
        if (this.getPIDController().isEnable())
            return this.getSetpoint();
        else
            return setpoint_speed;
    }
    
    public void outputVals() {
        SmartDashboard.putDouble("Bane1:", launcher_master.get());
        SmartDashboard.putDouble("Bane2:", launcher_slave.get());
    }

    
}
