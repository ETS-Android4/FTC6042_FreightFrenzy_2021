package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.mechanisms.Placer;

public class AutonomousActions {

    LinearOpMode opMode;
    Drivetrain drivetrain;
    DeliveryWheel deliveryWheel;
    Armdex armdex;
    LedController led;
    Placer placer;

    // The number of time to wait between setting the placer position and opening the hand in millis
    final int PLACE_DELAY_IN_MILLIS = 500;

    /**
     * Instantiate and initialize this autonomous actions. This also instantiates and initializes each of our mechanisms.
     * @param opMode The OpMode this Autonomous Actions object exists in
     */
    public AutonomousActions(LinearOpMode opMode) {
        this.opMode = opMode;

        // Instantiate our mechanisms
        drivetrain = new Drivetrain(opMode);
        deliveryWheel = new DeliveryWheel(opMode);
        armdex = new Armdex(opMode);
        led = new LedController(opMode);
        placer = new Placer(opMode);
    }

    /**
     * Delay for a certain number of milliseconds
     * @param millisToDelay The number of milliseconds to delay
     */
    public void delay(long millisToDelay) {
        long loopStartTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && System.currentTimeMillis() < loopStartTimeInMillis+millisToDelay) {

        }
    }

    /**
     * Rotate left off of the wall
     */
    public void rotateLeftFromWall() {
        drivetrain.driveForwardInchesNoPid(3.5, 0.4);
        drivetrain.rotateDegreesNoPid(-85, 0.2);
    }

    /**
     * Rotate right off of the wall
     */
    public void rotateRightFromWall() {
        drivetrain.driveForwardInchesNoPid(5, 0.4);
        drivetrain.rotateDegreesNoPid(85, 0.2);
    }

    /**
     * Deliver the duck by spinning the wheel clockwise
     */
    public void deliverDuckAutonomous() {
        long startTime = System.currentTimeMillis();
        deliveryWheel.startRamp();
        while(opMode.opModeIsActive() && System.currentTimeMillis() < startTime+4000) {
            deliveryWheel.updateRamp();
        }
        deliveryWheel.stopRamp();
    }

    /**
     * Park in the warehouse from the Carousel
     */
    public void parkInWarehouseFromCarousel() {
        driveInchesNoPid(102, 0.5);
    }

    /**
     * Park in the warehouse by driving backwards
     */
    public void parkInWarehouseFromCarouselBackwards() {
        driveInchesNoPid(-102, 0.5);
    }

    /**
     * Drive forward a specified number of inches
     */
    public void driveInches(double inchesToDrive) {
        drivetrain.driveForwardInches(inchesToDrive);
    }

    /**
     * Drive forward a specific number of inches at a specified power with no PDI
     * @param inchesToDrive The number of inches to drive forward
     * @param power The power at which to drive forward
     */
    public void driveInchesNoPid(double inchesToDrive, double power) {
        drivetrain.driveForwardInchesNoPid(inchesToDrive, power);
    }

    /**
     * Rotate a specified number of degrees
     * @param degreesToRotate The number of degrees to rotate. Negative degrees are counterclockwise, and positive degrees are clockwise
     */
    public void rotateDegrees(double degreesToRotate) {
        drivetrain.rotateDegreesNoPid(degreesToRotate, 0.15);
    }

    /**
     * Move straight off the wall and place a loaded freight onto the bottom level of the shipping hub
     */
    public void dropFreightOnLevelOne() {
        drivetrain.driveForwardInchesNoPid(13, 0.4);
        armdex.place();
        delay(3000);
        armdex.stopIntake();
    }

    /**
     * Detect and place the freight on the proper level from the left side of the shipping hub
     */
    public void placeFreightFromLeftAndReturn() {
        drivetrain.driveForwardInchesNoPid(-10, 0.3);
        delay(500);
        if(drivetrain.getRearLeftDistance() < 20) {
            // Level 3
            led.setColorGreen();
        } else if(drivetrain.getRearRightDistance() < 20) {
            // Level 2
            led.setColorYellow();
        } else {
            // Level 1
            led.setColorRed();
        }

        //TODO add placement code here

        // Drive back to the wall
        drivetrain.driveUntilFrontDistance(25, 0.3);

    }

    /**
     * Detect and place the freight on the proper level from the right side of the shipping hub
     */
    public void placeFreightFromRightAndReturn() {
        drivetrain.driveForwardInchesNoPid(8, 0.3);
        delay(500);
        if(drivetrain.getFrontLeftDistance() < 20) {
            // Level 1
            led.setColorRed();
        } else if(drivetrain.getFrontRightDistance() < 20) {
            // Level 2
            led.setColorYellow();
        } else {
            // Level 3
            led.setColorGreen();
        }

        //TODO add placement code here

        // Drive back to the wall
        drivetrain.driveUntilRearDistance(30, -0.3);
    }

    private void placeLevelOne() {
        placer.armLevelOne();
        delay(PLACE_DELAY_IN_MILLIS);
        placer.openHand();
    }

    private void placeLevelTwo() {
        placer.armLevelTwo();
        delay(PLACE_DELAY_IN_MILLIS);
        placer.openHand();
    }

    private void placeLevelThree() {
        placer.armLevelThree();
        delay(PLACE_DELAY_IN_MILLIS);
        placer.openHand();
    }

}