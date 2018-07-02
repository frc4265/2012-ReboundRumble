/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.JoystickCameraControl;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The CameraMount subsystem controls the tilt angle for
 * the camera. By default, this subsystem is controlled with a joystick.
 * However, this also has the capability of running as a PIDController based 
 * on the tilt angle. 
 * 
 * @author Stephen Schwahn
 */
public class CameraMount extends Subsystem {

    // Components that make up our subsystem.
    private Servo tilt;
    
    // Angular constants
    public static final int MAX_ANGLE = 140;
    public static final int MIN_ANGLE = 0;
    public static final int CENTERED_Y_GROUND = 130;
    public static final int CENTERED_Y_FORWARD = 94;
    public static final int CENTERED_Y_HOOP = 31;

    /**
     * This constructor initializes the servo and maps it to the port mapped in
     * RobotMap. By default, this PIDSubsystem is disabled, however, it can
     * function as one.
     */
    public CameraMount() {
        tilt = new Servo(RobotMap.TILT_SERVO);
        tilt.setAngle(CENTERED_Y_FORWARD);
    }
    
    /**
     * By default, we would like the camera mount to be operated 
     * with a joystick. It will allow for the operator to move the camera up,
     * down, or to a specific setting.
     * 
     * @see JoystickCameraControl
     */
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new JoystickCameraControl());
    }

    /**
     * This method tilts the camera mount up by a given number of degrees or
     * sets the angle to the maximum angle if the given angle is too large.
     * @param angle number of degrees to tilt up
     */    
    public void tiltUp(double angle) {
        if ((tilt.getAngle() + angle) > MAX_ANGLE) 
           tilt.setAngle(MAX_ANGLE);
        else
            tilt.setAngle(tilt.getAngle() + angle);
    }
    
    /**
     * This method tilts the camera mount down by a given number of degrees or
     * sets the angle to the minimum angle if the given angle is too small.
     * @param angle number of degrees to tilt down
     */
    public void tiltDown(double angle) {
        if ((tilt.getAngle() - angle) < MIN_ANGLE)
            tilt.setAngle(MIN_ANGLE);
        else
            tilt.setAngle(tilt.getAngle() - angle);
    }
    
    /**
     * This method sets the camera mount to a specific angle directly.
     * @param angle 
     */
    public void setAngle(double angle) {
        if (angle < MIN_ANGLE || angle > MAX_ANGLE) {
            tilt.set(angle);
        }
    }

    public void printCameraAngle() {
        SmartDashboard.putDouble("CAM ANGLE:", tilt.getAngle());
    }
}
