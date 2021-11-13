package org.firstinspires.ftc.teamcode.APIs.Leds;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class LedController {

    RevBlinkinLedDriver ledDriver;
    LinearOpMode opMode;
    char alliance;

    public LedController() {}

    /**
     * Initialize the LED controller
     * @param opMode The opmode this object is in
     */
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        ledDriver = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "led");
        setStatusRobotInitialized();
    }

    public void initNoInitLight(LinearOpMode opMode) {
        this.opMode = opMode;
        ledDriver = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "led");
    }

    public void init(LinearOpMode opMode, char alliance) {
        this.opMode = opMode;
        this.alliance = alliance;
        ledDriver = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "led");
        if(alliance == 'b') {
            setStatusRobotInitializedBlue();
        } else if(alliance == 'r') {
            setStatusRobotInitializedRed();
        }
    }

    /**
     * Signify that the duck is being delivered
     */
    public void setStatusDeliveringDuck() {
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
    }

    /**
     * Signify that the duck has been delivered
     */
    public void setStatusDeliveryFinished() {
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    /**
     * Show that the robot is initialized
     */
    public void setStatusRobotInitialized() {
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_GRAY);
    }

    /**
     * Show that the robot is initialized for the red side
     */
    public void setStatusRobotInitializedRed() {
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
    }

    /**
     * Show that the robot is initialized for the blue side
     */
    public void setStatusRobotInitializedBlue() {
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_BLUE);
    }

    public void setStatusDeliveringReverse() {
        ledDriver.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
    }

}
