/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ortn.wildbots.subsystems;

import edu.ortn.wildbots.RobotMap;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;

/**
 * The Camera subsystem controls the AxisCamera and allows us to run 
 * imaging functions on the Subsystems. It uses Color, particle, saturation,
 * and luminosity filters.
 * @author Stephen
 */
public class Camera extends Subsystem {
    
    public static class Values {
        public static final int green_value = 1;
        public static final int red_value = 2;
        
        public static final int bottom_goal = 10;
        public static final int midLeft_goal = 11;
        public static final int midRight_goal = 12;
        public static final int top_goal = 13;
    }
    
    // Green Constants
    static final int GREEN_HUE_MIN = 75;
    static final int GREEN_HUE_MAX = 125;
    static final int GREEN_SATURATION_MIN = 90;
    static final int GREEN_SATURATION_MAX = 255;
    static final int GREEN_LUM_MIN = 110;
    static final int GREEN_LUM_MAX = 200;
    
    // Red Constants
    static final int RED_HUE_MIN = 75;
    static final int RED_HUE_MAX = 125;
    static final int RED_SATURATION_MIN = 90;
    static final int RED_SATURATION_MAX = 255;
    static final int RED_LUM_MIN = 110;
    static final int RED_LUM_MAX = 200;

    private CriteriaCollection cc;
    
    private ParticleAnalysisReport[] recentReport;
    private ColorImage recentImage;
            
    private AxisCamera camera;
    
    
    /**
     * For the camera, there is no default command. All we do is take
     * a picture when a command tells it to.
     */
    public void initDefaultCommand() {}
    
    /**
     * This constructor sets up the camera to a set IP, a resolution
     * of 320x240 and a maximum FPS of 30.
     */
    public Camera() {
        camera = AxisCamera.getInstance(RobotMap.CAM_IP);
        camera.writeResolution(AxisCamera.ResolutionT.k320x240);
        camera.writeMaxFPS(30);
        
        cc = new CriteriaCollection();
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_WIDTH, 30, 400, false);
        cc.addCriteria(MeasurementType.IMAQ_MT_BOUNDING_RECT_HEIGHT, 40, 400,false);
        
    }
    
    /**
     * This method simply takes one image from the camera and return it.
     * @return The Colorimage on the AxisCamera
     * @throws AxisCameraException
     * @throws NIVisionException 
     */
    public ColorImage getImage() throws AxisCameraException, NIVisionException {
        if (camera.freshImage()) {
            ColorImage ci = camera.getImage();
            recentImage = ci;
            return ci;
        }
        return null;
    }
    
    /**
     * This method takes an image analysis report and filters it based on 
     * threshold, object size, convex hull, and particle dimensions.
     * @param img The ColorImage taken from the screen
     * @return The Particle Analysis Report that results from the filters.
     * @throws NIVisionException 
     */
    public ParticleAnalysisReport[] getFilteredInformation(ColorImage img,int colorVal) throws NIVisionException {
        BinaryImage thresholdImage = null;
        BinaryImage bigObjectsImage = null;
        BinaryImage convexHullImage = null;
        BinaryImage filteredImage = null;
        
        // Filter the image
        if (colorVal == Values.green_value) {
            thresholdImage = img.thresholdHSL(GREEN_HUE_MIN, GREEN_HUE_MAX,
                        GREEN_SATURATION_MIN, GREEN_SATURATION_MAX, GREEN_LUM_MIN, GREEN_LUM_MAX);
        }
        else if (colorVal == Values.red_value) {
            thresholdImage = img.thresholdHSL(RED_HUE_MIN, RED_HUE_MAX,
                        RED_SATURATION_MIN, RED_SATURATION_MAX, RED_LUM_MIN, RED_LUM_MAX);
        }
        else {
            return null;
        }
        
        bigObjectsImage = thresholdImage.removeSmallObjects(false, 2);
        convexHullImage = bigObjectsImage.convexHull(false);
        filteredImage = convexHullImage.particleFilter(cc);           

        // Get the reports
        ParticleAnalysisReport[] reports = 
                filteredImage.getOrderedParticleAnalysisReports();
        
        // Free the native memory
        thresholdImage.free();
        bigObjectsImage.free();
        convexHullImage.free();
        filteredImage.free();
        
        // Return the reports.
        recentReport = reports;
        return reports;
    }
    
    /**
     * Returns the absolute distance from the top goal based on the
     * width of the goal versus the apparent. 
     * @param report 
     * @return 
     */
    public double getDistance(ParticleAnalysisReport report) {  
        double P1 = report.boundingRectWidth;
        //lcd.println(DriverStationLCD.Line.kUser4, 1, "Width: " + P1);
        int width = camera.getResolution().width;
        return (width/(P1*(Math.tan(Math.toRadians(23.5)))));      
    }
    
    public double getScaledXOffset(ParticleAnalysisReport report) {
        double part_centered = report.center_mass_x_normalized;
        int width = camera.getResolution().width/2;
        return 27.5/width*part_centered; 
    }
    
    public double getScaledYOffset(ParticleAnalysisReport report) {
        double part_centered = report.center_mass_y_normalized;
        int height = camera.getResolution().height/2;
        return 27.5/height*part_centered; 
    }
    
    /**
     * This method takes an array of particle analysis and sorts them based 
     * on height.
     * @param reports An array of particle locations.
     */
    public void orderByHeight(ParticleAnalysisReport[] reports) {
        boolean swapped = false;
        do {
            for (int i = 1; i < reports.length; i++) {
                if (reports[i].center_mass_y < reports[i-1].center_mass_y) {
                    ParticleAnalysisReport temp = reports[i];
                    reports[i] = reports[i-1];
                    reports[i-1] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
//        int smallest = reports[0].center_mass_y;
//        int smallestIndex = 0;
//        for (int i = 1; i < reports.length; i++) {
//            if (reports[i].center_mass_y < smallest) {
//                smallest = reports[i].center_mass_y;
//                smallestIndex = i;
//            }
//        }
//        return reports[smallestIndex];
    }
    
    public ColorImage getLastImage() {
        return recentImage;
    }
    
    public ParticleAnalysisReport[] getLastReport() {
        return recentReport;
    }
    
}
