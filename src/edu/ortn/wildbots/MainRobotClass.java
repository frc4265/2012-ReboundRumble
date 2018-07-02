/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.ortn.wildbots;


import edu.ortn.wildbots.commands.AutonomousDoNothing;
import edu.ortn.wildbots.commands.AutonomousShoot2;
import edu.ortn.wildbots.commands.CommandBase;
import edu.ortn.wildbots.commands.AutonomousReverseBalls;
import edu.ortn.wildbots.commands.AutonomousReverseLowerBridge;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class MainRobotClass extends IterativeRobot {

    private Command autonomousCommand;
    private Compressor compressor;
   
    public static double launcher_offset;

    private DigitalInput SW1;
    private DigitalInput SW2;
   private DigitalInput SW3; 

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
   
        
        // Initialize all subsystems
        CommandBase.init();
        
        compressor = new Compressor(RobotMap.PRESSURE_SWITCH,
                RobotMap.COMPRESSOR_RELAY);
        compressor.start();

        SW1 = new DigitalInput(RobotMap.SWITCH1);
        SW2 = new DigitalInput(RobotMap.SWITCH2);
        SW3 = new DigitalInput(RobotMap.SWITCH3);

        if (SW1.get() && !SW2.get() && !SW3.get()) {
            
        }
        else if (!SW1.get() && SW2.get() && !SW3.get()) {
            
        }
        else if (!SW1.get() && !SW2.get() && SW3.get()) {
            autonomousCommand = new AutonomousReverseBalls();
        }
        else if (!SW1.get() && SW2.get() && SW3.get()) {
            autonomousCommand = new AutonomousReverseLowerBridge();
        }
        else if (SW1.get() && !SW2.get() && SW3.get()) {
            autonomousCommand = new AutonomousShoot2();
        }
        else {
            autonomousCommand = new AutonomousDoNothing();
        }
        //SmartDashboard.putData("SchedulerData", Scheduler.getInstance());
        //SmartDashboard.putDouble("Setpoint: ", 10.0);
        //SmartDashboard.putDouble("P: ", .01);
        //SmartDashboard.putDouble("I: ", .001);
        //SmartDashboard.putDouble("D: ", .2);
        
        //SmartDashboard.putDouble("Inches: ", 10);

    }

    public void autonomousInit() {
        if (SW1.get() && !SW2.get() && !SW3.get()) {
            
        }
        else if (!SW1.get() && SW2.get() && !SW3.get()) {
            
        }
        else if (!SW1.get() && !SW2.get() && SW3.get()) {
            autonomousCommand = new AutonomousReverseBalls();
        }
        else if (!SW1.get() && SW2.get() && SW3.get()) {
            autonomousCommand = new AutonomousReverseLowerBridge();
        }
        else if (SW1.get() && !SW2.get() && SW3.get()) {
            autonomousCommand = new AutonomousShoot2();
        }
        else {
            autonomousCommand = new AutonomousDoNothing();
        }
        // schedule the autonomous command (example)
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        if (compressor.getPressureSwitchValue() && compressor.enabled()) {
            compressor.stop();
            SmartDashboard.putString("Compressor", "Signal Recieved");
        }
            
        else if (!compressor.getPressureSwitchValue()) 
            compressor.start();
            
        MainRobotClass.launcher_offset = 0;
        Scheduler.getInstance().run();

        
        CommandBase.camMount.printCameraAngle();
        updateDashboard();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {

        if (compressor.getPressureSwitchValue() && compressor.enabled()) {
            compressor.stop();
            SmartDashboard.putString("Compressor", "Signal Recieved");
        }
            
        else if (!compressor.getPressureSwitchValue()) 
            compressor.start();
            
        MainRobotClass.launcher_offset = 0;
        Scheduler.getInstance().run();
        
        CommandBase.launcher.outputVals();
        CommandBase.camMount.printCameraAngle();
        updateDashboard();
    }


    void updateDashboard() {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        lowDashData.addCluster();
        {
            lowDashData.addCluster();
            {     //analog modules
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
                lowDashData.addCluster();
                {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                }
                lowDashData.finalizeCluster();
            }
            lowDashData.finalizeCluster();

            lowDashData.addCluster();
            { //digital modules
                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 1;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

                lowDashData.addCluster();
                {
                    lowDashData.addCluster();
                    {
                        int module = 2;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster();
                        {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        }
                        lowDashData.finalizeCluster();
                    }
                    lowDashData.finalizeCluster();
                }
                lowDashData.finalizeCluster();

            }
            lowDashData.finalizeCluster();

            lowDashData.addByte(Solenoid.getAllFromDefaultModule());
        }
        lowDashData.finalizeCluster();
        lowDashData.commit();

    }
    
}
