/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.MainRobotClass;
import edu.ortn.wildbots.RobotMap;
import edu.ortn.wildbots.commands.JoystickDriveControl;
import edu.ortn.wildbots.sensors.UltrasonicSensor;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the chassis/drive system. It uses
 * the built-in methods of RobotDrive class and two CANJaguar objects 
 * to do this. The CAN interface allows for faster operations, use of encoders,
 * PWM, voltage, current, and position control.
 * 
 * This is also a PIDSubsystem and has the capability to move to any 
 * location with a PIDController based on the ultrasonic sensor reading.
 * 
 * In addition to the PID subsystem, we need to include a PIDController class
 * to handle the encoders.
 * 
 * @author Stephen Schwahn
 */
public class Chassis extends PIDSubsystem {

    private static class MotorOutput implements PIDOutput {
        
        private SpeedController controller;
        
        public MotorOutput(SpeedController controller) {
            this.controller = controller;
        }

        public void pidWrite(double output) {
            controller.set(output);
        }
    }
    
    // Components of the subsystem
    private Victor leftMotor;
    private Victor rightMotor;
    private Victor leftMotor2;
    private Victor rightMotor2;
    private RobotDrive drive;
    private Encoder encoderLeft;
    private Encoder encoderRight;
    private UltrasonicSensor rangefinder;
    
    private PIDController leftController;
    private PIDController rightController;
    
    // Values for PIDController
    private static final double Kp = 0.1; 
    private static final double Ki = 0.00;
    private static final double Kd = 0.0;

    /**
     * The constructor initializes the CANJaguars to the ports
     * specified in the RobotMap.CANJAGUAR_LEFT and RobotMap.CANJAGUAR_RIGHT
     * fields. 
     * 
     * Then, the RobotDrive class is initialized with those motors.
     * 
     * Using CAN Bus, there is the possibility for a CANTimeoutException
     * which may need to be accounted for.
     */
    public Chassis() {
        super("Chassis", Kp, Ki, Kd);
        leftMotor = new Victor(RobotMap.JAGUAR_LEFT);
        rightMotor = new Victor(RobotMap.JAGUAR_RIGHT);
        leftMotor2 = new Victor(RobotMap.JAGUAR_LEFT2);
        rightMotor2 = new Victor(RobotMap.JAGUAR_RIGHT2);
        
        encoderLeft = new Encoder(RobotMap.ENCODER_LEFT_A, RobotMap.ENCODER_LEFT_B);
        encoderRight = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);
        encoderLeft.setDistancePerPulse(0.02432);
        encoderRight.setDistancePerPulse(0.02432);
        encoderLeft.start();
        encoderRight.start();
        
        drive = new RobotDrive(leftMotor, leftMotor2, rightMotor, rightMotor2);
        drive.setSafetyEnabled(false);
        
        leftController = new PIDController(Kp, Ki, Kd, 
                encoderLeft, new MotorOutput(leftMotor));
        rightController = new PIDController(Kp, Ki, Kd,
                encoderRight, new MotorOutput(rightMotor));
        
        rangefinder = new UltrasonicSensor(RobotMap.RANGEFINDER_PORT);
        // By default, we do not want PID enabled. Uncomment the
        // following line if we want to use it by default.
        //this.enable();

        
    }
    
    /**
     * This method will set the default command of the Chassis
     * to the JoystickDriveControl command, which simply handles Joystick input to move
     * the chassis.
     * 
     * @see JoystickDriveControl
     */
    public void initDefaultCommand() {
        //setDefaultCommand(new TunePID());
        setDefaultCommand(new JoystickDriveControl());
    }
    
    /**
     * This method provides access to the RobotDrive arcadeDrive method. It
     * takes a speed (y-axis), a turn (x-axis), and a boolean variable that
     * specifies sensitivity.
     * 
     * @param power the speed at which the robot moves in y-axis [-1.0,1.0]
     * @param curl the speed at which the robot moves in the x-axis [-1.0,1.0]
     * @param squaredInputs increased sensitivity at low speeds (if true)
     */
    public void arcadeDrive(double power, double curl, boolean squaredInputs) {
        drive.arcadeDrive(power, curl, squaredInputs);
    }
    
    /**
     * This method provides access to the RobotDrive tankDrive method. It 
     * takes a power for the left and the right motors. This turns by differing
     * the values of the powers of each axis.
     * 
     * @param left power of the left motor
     * @param right power of the right motor
     */
    public void tankDrive(double left, double right) {
        drive.tankDrive(left, right);
    }


		public void somethingSomethingArcadeDrive(double moveValue, double rotateValue){
                    double SIDE_BIAS = 055;
                    double NEUTRAL_DEADBAND =0.17;

                       if( (moveValue > -NEUTRAL_DEADBAND) &&
                           (moveValue < NEUTRAL_DEADBAND )   )
                       { // in the Y deadband ... spin only so no bias to spin
                           moveValue = 0. ;
                       }
                       else // translating fwd/rev add the bias to the spin axis
                       {
                           rotateValue += SIDE_BIAS ; // set sign correctly, here
                           rotateValue = (rotateValue > -1.) ? rotateValue : -1. ; // keep in range
                           rotateValue = (rotateValue < 1.) ? rotateValue :  1. ;
                       }

			//myRobot.ArcadeDrive(stick); // drive with arcade style

                        // using squared inputs has a smoothing effect on handling
                        drive.arcadeDrive(moveValue, rotateValue);

    }

    public void seansArcadekDrive(double moveValue, double rotateValue, double scale, boolean correctBias, boolean deadBand, boolean squaredInputs, boolean inverted){
        double deadBandPercent = 0.05;
        double forBoundR = -0.195;
        double revBoundR = 0.12;
        double maxRightMotorSpeed = 1.0;
        double forBoundL = -0.4;
        double revBoundL = 0.117;
        double maxLeftMotorSpeed = 1.0;
        double leftMotorSpeed = 0;
        double rightMotorSpeed = 0;
        double corLeftMotorSpeed = 0;
        double corRightMotorSpeed = 0;



        if (squaredInputs) {
            // square the inputs (while preserving the sign) to increase fine control while permitting full power
            if (moveValue >= 0.0) {
                moveValue = (moveValue * moveValue);
            } else {
                moveValue = -(moveValue * moveValue);
            }
            if (rotateValue >= 0.0) {
                rotateValue = (rotateValue * rotateValue);
            } else {
                rotateValue = -(rotateValue * rotateValue);
            }
        }

        //This is Arcade Drive coppied from the wpilibj source code
        if (moveValue > 0.0) {
            if (rotateValue > 0.0) {
                leftMotorSpeed = moveValue - rotateValue;
                rightMotorSpeed = Math.max(moveValue, rotateValue);
            } else {
                leftMotorSpeed = Math.max(moveValue, -rotateValue);
                rightMotorSpeed = moveValue + rotateValue;
            }
        } else {
            if (rotateValue > 0.0) {
                leftMotorSpeed = -Math.max(-moveValue, rotateValue);
                rightMotorSpeed = rotateValue + moveValue;
            } else {
                leftMotorSpeed = -rotateValue + moveValue;
                rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
            }
        }

        //This is where the axis are inverted if inverted is true
        if(inverted){
            rightMotorSpeed = -leftMotorSpeed;
            leftMotorSpeed = -rightMotorSpeed;
        }

        if(correctBias){
        //This is were the motor bias is corrected if correctBias is true
            if(rightMotorSpeed < -deadBandPercent * Math.max(maxLeftMotorSpeed, maxRightMotorSpeed)){
                corRightMotorSpeed = ((rightMotorSpeed + forBoundR) * ((Math.min(maxLeftMotorSpeed, maxRightMotorSpeed) + forBoundR) / (1.0 + forBoundR))) + forBoundR;
            }else if(rightMotorSpeed > deadBandPercent * Math.max(maxLeftMotorSpeed, maxRightMotorSpeed)){
                corRightMotorSpeed = (rightMotorSpeed + revBoundR) * (Math.min(maxLeftMotorSpeed, maxRightMotorSpeed) / (1.0 - revBoundR)) + revBoundR;
            }
            if(leftMotorSpeed < -deadBandPercent * Math.max(maxLeftMotorSpeed, maxRightMotorSpeed)){
                corLeftMotorSpeed = ((leftMotorSpeed + forBoundL) * ((Math.min(maxLeftMotorSpeed, maxRightMotorSpeed) + forBoundL) / (1.0 + forBoundL))) + forBoundR;
            }else if(leftMotorSpeed > deadBandPercent * Math.max(maxLeftMotorSpeed, maxRightMotorSpeed)){
                corLeftMotorSpeed = (leftMotorSpeed + revBoundL) * (Math.min(maxLeftMotorSpeed, maxRightMotorSpeed) / (1.0 - revBoundL)) + revBoundL;
            }
            limit(corRightMotorSpeed, Math.min(maxLeftMotorSpeed, maxRightMotorSpeed));
            limit(corLeftMotorSpeed, Math.min(maxLeftMotorSpeed, maxRightMotorSpeed));
        }
        SmartDashboard.putDouble("scale ", scale);
        SmartDashboard.putDouble("rightMotorSpeed ", rightMotorSpeed);
        SmartDashboard.putDouble("leftMotorSpeed ", leftMotorSpeed);
        if(correctBias){
            if(deadBand){
                if(corRightMotorSpeed < deadBandPercent * maxRightMotorSpeed){
                    corRightMotorSpeed = 0.0;
                }
                if(corLeftMotorSpeed < deadBandPercent * maxLeftMotorSpeed){
                    corLeftMotorSpeed = 0.0;
                }
            }
            drive.tankDrive(corLeftMotorSpeed, corRightMotorSpeed);
        }else{
            if(deadBand){
                if(rightMotorSpeed < deadBandPercent * rightMotorSpeed){
                    rightMotorSpeed = 0.0;
                }
                if(leftMotorSpeed < deadBandPercent * leftMotorSpeed){
                    leftMotorSpeed = 0.0;
                }
            }
            drive.tankDrive(leftMotorSpeed, rightMotorSpeed);
        }
    }

     public static double limit(double num, double limit) {
        limit = Math.abs(limit);
        if(limit > 1.0){
            limit = 1.0;
        }
        if (num > limit) {
            return limit;
        }
        if (num < -limit) {
            return -limit;
        }
        return num;
    }
    
    /**
     * This method takes a distance in inches and moves that distance
     * using the CANBus interface.
     * @param inches The distance to move
     * @throws CANTimeoutException 
     */
    public void driveSetDistance(double inches)  {
        encoderLeft.reset();
        encoderRight.reset();
        
        leftController.enable();
        rightController.enable();
        
        leftController.setSetpoint(inches);
        rightController.setSetpoint(inches);
    }

    protected double returnPIDInput() {
        return rangefinder.getRangeInches();
    }

    protected void usePIDOutput(double output) {
       
    }

    
    public void resetEncoders() {
        encoderLeft.reset();
        encoderRight.reset();
    }
    
    public boolean isLeftFinished() {
        return Math.abs(leftController.getSetpoint() - encoderLeft.getDistance()) < 0.1;
    }
    
    public boolean isRightFinished() {
        return Math.abs(rightController.getSetpoint() - encoderRight.getDistance()) < 0.1;
    }
    
    public void disablePIDs() {
        leftController.disable();
        rightController.disable();
    }
    
}

