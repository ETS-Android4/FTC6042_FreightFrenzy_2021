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

    final int ARM_MOVE_DELAY_IN_MILLIS = 1500; // The time to wait for the arm to position itself
    final int PLACE_DELAY_IN_MILLIS = 1000; // The time to wait to place after opening the hand

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
        while(opMode.opModeIsActive() && System.currentTimeMillis() < startTime+3000) {
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
        drivetrain.driveForwardInchesNoPid(-11, 0.3);
        delay(500);
        if(drivetrain.getRearLeftDistance() < 20) {
            // Level 3
            led.setColorGreen();
            placer.armLevelThree();
            drivetrain.driveForwardInchesNoPid(-10, 0.2);
            delay(ARM_MOVE_DELAY_IN_MILLIS);
            placer.openHand();
            delay(PLACE_DELAY_IN_MILLIS);
            armdex.wristUp();
            drivetrain.driveUntilFrontDistance(22, 0.3);
            delay(500);
            placer.closeHand();
            placer.armStartingPosition();
        } else if(drivetrain.getRearRightDistance() < 20) {
            // Level 2
            led.setColorYellow();
            placer.armLevelTwo();
            drivetrain.driveForwardInchesNoPid(-7, 0.2);
            delay(ARM_MOVE_DELAY_IN_MILLIS);
            placer.openHand();
            delay(PLACE_DELAY_IN_MILLIS);
            armdex.wristUp();
            drivetrain.driveUntilFrontDistance(22, 0.3);
            delay(500);
            placer.closeHand();
            placer.armStartingPosition();
        } else {
            // Level 1
            led.setColorRed();
            placer.armLevelOne();
            delay(ARM_MOVE_DELAY_IN_MILLIS);
            drivetrain.driveForwardInchesNoPid(-6.5, 0.2);
            placer.openHand();
            delay(PLACE_DELAY_IN_MILLIS);
            armdex.wristUp();
            drivetrain.driveUntilFrontDistance(22, 0.3);
            delay(500);
            placer.closeHand();
            placer.armStartingPosition();
        }

        // Drive back to the wall
        drivetrain.driveUntilFrontDistance(45, 0.3);

    }

    /**
     * Detect and place the freight on the proper level from the right side of the shipping hub
     */
    public void placeFreightFromRightAndReturn() {
        armdex.wristUp();
        drivetrain.driveForwardInchesNoPid(8, 0.3);
        delay(500);
        if(drivetrain.getFrontLeftDistance() < 20) {
            // Level 1
            led.setColorRed();
            drivetrain.driveForwardInchesNoPid(40, 0.35);
            placer.armLevelOne();
            delay(ARM_MOVE_DELAY_IN_MILLIS);
            drivetrain.driveForwardInchesNoPid(-3, 0.2);
            placer.openHand();
            armdex.wristUp();
            delay(PLACE_DELAY_IN_MILLIS);
            armdex.wristUp();
            drivetrain.driveForwardInchesNoPid(4, 0.35);
            delay(500);
            placer.armStartingPosition();
            placer.closeHand();

        } else if(drivetrain.getFrontRightDistance() < 20) {
            // Level 2
            led.setColorYellow();
            drivetrain.driveForwardInchesNoPid(40, 0.35);
            placer.armLevelTwo();
            delay(ARM_MOVE_DELAY_IN_MILLIS);
            drivetrain.driveForwardInchesNoPid(-3, 0.2);
            placer.openHand();
            armdex.wristUp();
            delay(PLACE_DELAY_IN_MILLIS);
            drivetrain.driveForwardInchesNoPid(4, 0.35);
            delay(500);
            placer.armStartingPosition();
            placer.closeHand();
        } else {
            // Level 3
            led.setColorGreen();
            drivetrain.driveForwardInchesNoPid(40, 0.35);
            placer.armLevelThree();
            delay(ARM_MOVE_DELAY_IN_MILLIS);
            drivetrain.driveForwardInchesNoPid(-7, 0.2);
            placer.openHand();
            armdex.wristUp();
            delay(PLACE_DELAY_IN_MILLIS);
            delay(500);
            placer.armStartingPosition();
            placer.closeHand();

        }

        // Drive back to the wall
        drivetrain.driveUntilRearDistance(30, 0.3);
    }

}