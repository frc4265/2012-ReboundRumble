package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.MainRobotClass;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.ortn.wildbots.OI;
import edu.ortn.wildbots.subsystems.*;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author Author
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Chassis chassis = new Chassis();
    public static CameraMount camMount = new CameraMount();
    public static Elevator elevator = new Elevator();
    public static Drawbridge drawbridge = new Drawbridge();
    public static Launcher launcher = new Launcher();
    public static Camera camera = new Camera();
    public static LEDRing ledRing = new LEDRing();
    public static Cathodes cathodes = new Cathodes();
    public static Kicker kicker = new Kicker();   

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(chassis);
        SmartDashboard.putData(camera);
//        SmartDashboard.putData(camMount);
//        SmartDashboard.putData(elevator);
//        SmartDashboard.putData(drawbridge);
//        SmartDashboard.putData(launcher);
//        SmartDashboard.putData(camera);          
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
