/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ortn.wildbots.commands;

/**
 *
 * @author sean
 */
public class KickerRetract extends CommandBase {

    public KickerRetract() {
        requires(kicker);
    }

    protected void initialize() {
    }

    protected void execute() {
        kicker.retract();
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    }

}
