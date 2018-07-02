
package edu.ortn.wildbots;

import edu.ortn.wildbots.commands.*;
import edu.ortn.wildbots.subsystems.CameraMount;
import edu.ortn.wildbots.subsystems.Launcher;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 * 
 * JoystickButtons are used to allow the operator to perform tasks with well, the 
 * Joystick. We can use DigitalIOButton objects to interface with the Cypress
 * module to use custom buttons. Finally, there are AnalogButtons and DigitalButtons
 * which respond to changes in the Analog and Digital sidecars.
 */
public class OI {

    private static class Color {
        public static final int BLUE = 1;
        public static final int RED = 2;
    }

    private class CathodesToggle extends CommandBase {

        private boolean isBlueOn = false;
        private boolean isRedOn = false;

        private int color;
        
        public CathodesToggle() {
            requires(cathodes);
        }

        private CathodesToggle(int color) {
            this.color = color;
        }

        protected void initialize() {
            
        }

        protected void execute() {
            if (isBlueOn && color == Color.BLUE) {
                cathodes.turnOffBlue();
                isBlueOn = false;
            }
            else if (isRedOn && color == Color.RED) {
                cathodes.turnOffRed();
                isRedOn = false;
            }
            else if (color == Color.BLUE && !isBlueOn) {
                cathodes.turnOnBlue();
                isBlueOn = true;
            }
            else if (color == Color.RED && !isRedOn) {
                cathodes.turnOnRed();
                isRedOn = true;
                
            }
                
        }

        protected boolean isFinished() {
            return true;
        }

        protected void end() {
        }

        protected void interrupted() {
        }
    }
    
    private class IncrementOffset extends Command {

        protected void initialize() {
        }

        protected void execute() {
            MainRobotClass.launcher_offset += Launcher.OFFSET_INCREMENT;
        }

        protected boolean isFinished() {
            return true;
        }

        protected void end() {
        }

        protected void interrupted() {
        }
                
    }

    private class DecrementOffset extends Command {

        protected void initialize() {
        }

        protected void execute() {
            MainRobotClass.launcher_offset -= Launcher.OFFSET_INCREMENT;
        }

        protected boolean isFinished() {
            return true;
        }

        protected void end() {
        }

        protected void interrupted() {
        }
                
    }
    
    private Joystick left;
    private Joystick right;

    
    /**
     * This constructor sets the joysticks to the ports specified in the 
     * RobotMap class. It also sets the JoystickButtons and the DigitalIOButton 
     * values.
     */
    public OI() {
        left = new Joystick(RobotMap.JOYSTICK_LEFT);
        right = new Joystick(RobotMap.JOYSTICK_RIGHT);
        
        JoystickButton fireButton = new JoystickButton(left, 2);
        fireButton.whenPressed(new LauncherFireCommandGroup());
        fireButton.whenReleased(new KickerDoNothing());
        
        JoystickButton launcherOnToggle = new JoystickButton(left, 1);
        //MotorToggle motorToggle = new MotorToggle();
        //launcherOnToggle.whenPressed(new LauncherSpeedToggle());
        launcherOnToggle.whileHeld(new LauncherSetSpeedToController());
        launcherOnToggle.whenReleased(new LauncherStopMotors());

        //JoystickButton cameraGround = new JoystickButton(left, 3);
        //cameraGround.whenPressed(new SetCameraAngle(CameraMount.CENTERED_Y_GROUND));

        //JoystickButton cameraForward = new JoystickButton(left, 2);
        //cameraForward.whenPressed(new SetCameraAngle(CameraMount.CENTERED_Y_FORWARD));

        //JoystickButton cameraBottom = new JoystickButton(left, 5);
        //cameraBottom.whenPressed(new SetCameraAngle(CameraMount.CENTERED_Y_BOTTOM));

        //JoystickButton cameraMiddle = new JoystickButton(left, 2);
        //cameraMiddle.whenPressed(new SetCameraAngle(CameraMount.CENTERED_Y_MIDDLE));

        //JoystickButton cameraTop = new JoystickButton(left, 4);
        //cameraTop.whenPressed(new SetCameraAngle(CameraMount.CENTERED_Y_TOP));
        
        
        //JoystickButton incrementButton = new JoystickButton(left, 9);
        //JoystickButton decrementButton = new JoystickButton(left, 8);
        //incrementButton.whenPressed(new IncrementOffset());
        //decrementButton.whenPressed(new DecrementOffset());

        JoystickButton stopButton = new JoystickButton(right, 10);
        JoystickButton forwardButton = new JoystickButton(right, 8);
        JoystickButton reverseButton = new JoystickButton(right, 12);
        stopButton.whenPressed(new StopElevator());
        forwardButton.whenPressed(new MoveElevatorForward());
        reverseButton.whenPressed(new MoveElevatorReverse());

        JoystickButton cathodesBlueToggle = new JoystickButton(left, 8);
        CathodesToggle cathBlueTog = new CathodesToggle(Color.BLUE);
        cathodesBlueToggle.whenPressed(cathBlueTog);

        JoystickButton cathodesRedToggle = new JoystickButton(left, 9);
        CathodesToggle cathRedTog = new CathodesToggle(Color.RED);
        cathodesRedToggle.whenPressed(cathRedTog);

        JoystickButton drawDown = new JoystickButton(right, 5);
        JoystickButton drawUp = new JoystickButton(right, 3);
        
        drawDown.whenPressed(new DrawbridgeExtend());
        drawUp.whenPressed(new DrawbridgeRetract());
        drawUp.whenReleased(new DrawbridgeLocked());
        

        
/*        InternalButton turnButton = new InternalButton();
        turnButton.whenPressed(new TurnByAngle());
        SmartDashboard.putData("Turn to setpoint", turnButton);
        
        InternalButton resetEncoderButton = new InternalButton();
        resetEncoderButton.whenPressed(new ResetEncoders());
        SmartDashboard.putData("Reset Encoders", resetEncoderButton);
        
        InternalButton moveDistance = new InternalButton();
        moveDistance.whenPressed(new MoveDistance(SmartDashboard.getDouble("Inches: ", 10.1)));
        SmartDashboard.putData("MoveDistance", moveDistance);
        
        InternalButton imageAndShoot = new InternalButton();
        imageAndShoot.whenPressed(new ImageAndShootTopGoal());
        SmartDashboard.putData("ImageAndShoot", imageAndShoot);
        
        JoystickButton redButton = new JoystickButton(right, 4);
        JoystickButton greenButton = new JoystickButton(right, 5);
        greenButton.whileHeld(new TurnOnGreenLEDs());
        redButton.whileHeld(new TurnOnRedLEDs());    */
    }
    
    /**
     * This method returns the left Joystick object
     * @return the left joystick
     */
    public Joystick getLeft() {
        return left;
    }
    
    /**
     * This method returns the right Joystick object
     * @return the right joystick
     */
    public Joystick getRight() {
        return right;
    }
    
}

