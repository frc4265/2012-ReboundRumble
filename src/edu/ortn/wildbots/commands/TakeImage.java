/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author development
 */
public class TakeImage extends CommandBase {
    
    public TakeImage() {
        // Use requires() here to declare subsystem dependencies
        requires(camera);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try {
            camera.getImage();
        } catch (AxisCameraException ex) {
            this.cancel();
        } catch (NIVisionException ex) {
            this.cancel();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
