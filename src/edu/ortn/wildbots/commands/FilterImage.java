/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.commands;

import edu.ortn.wildbots.subsystems.Camera;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author development
 */
public class FilterImage extends CommandBase {
    
    private int colorVal;
    
    public FilterImage(int colorVal) {
        // Use requires() here to declare subsystem dependencies
        requires(camera);
        
        this.colorVal = colorVal;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        try {
            camera.getFilteredInformation(camera.getLastImage(), colorVal);
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
