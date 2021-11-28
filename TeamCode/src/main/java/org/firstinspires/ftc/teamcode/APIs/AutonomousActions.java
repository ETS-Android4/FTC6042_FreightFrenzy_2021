package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.checkerframework.checker.units.qual.A;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

public class AutonomousActions {

    LinearOpMode opMode;
    Drivetrain drivetrain;
    DeliveryWheel deliveryWheel;
    Armdex armdex;
    LedController led;

    public AutonomousActions() {}

    /**
     * Initialize this object. This instantiates and initializes an object for each of our mechanisms.
     * @param opMode The opMode this autonomous actions object is running in
     */
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;

        // Instantiate our mechanisms
        drivetrain = new Drivetrain();
        deliveryWheel = new DeliveryWheel();
        armdex = new Armdex();
        led = new LedController();

        // Initialize our mechanisms
        drivetrain.init(opMode);
        deliveryWheel.init(opMode);
        armdex.init(opMode);
        led.initNoInitLight(opMode);
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
        drivetrain.driveForwardInchesNoPid(2.5, 0.4);
        drivetrain.rotateDegreesNoPid(-85, 0.2);
    }

    /**
     * Rotate right off of the wall
     */
    public void rotateRightFromWall() {
        drivetrain.driveForwardInchesNoPid(2.5, 0.4);
        drivetrain.rotateDegreesNoPid(85, 0.2);
    }

    /**
     * Drive straight to the delivery wheel from near the far barcode
     */
    public void driveToDeliveryWheelFromFarBarcode() {
        // Get us close to the delivery wheel
        drivetrain.driveForwardInchesNoPid(54, 0.5);

        // Slow down but keep moving forward until we hit the wheel
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+3000)) {
            drivetrain.driveAtPower(0.2);
        }
    }

    /**
     * Drive straight to the delivery wheel from near the far barcode
     */
    public void driveToDeliveryWheelFromFarBarcodeBackwards() {
        // Get us close to the delivery wheel
        drivetrain.driveForwardInchesNoPid(-54, 0.5);

        // Slow down but keep moving forward until we hit the wheel
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+3000)) {
            drivetrain.driveAtPower(-0.2);
        }
    }

    /**
     * Drive straight to the delivery wheel from near the near barcode
     */
    public void driveToDeliveryWheelFromNearBarcode() {

        // Get us close to the delivery wheel
        drivetrain.driveForwardInches(12, 0.5);

        // Slow down but keep moving forward until we hit the week
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+3000)) {
            drivetrain.driveAtPower(0.2);
        }
    }

    /**
     * Drive straight to the delivery wheel from near the near barcode
     */
    public void driveToDeliveryWheelFromNearBarcodeBackwards() {

        // Get us close to the delivery wheel
        drivetrain.driveForwardInches(-12, 0.5);

        // Slow down but keep moving forward until we hit the week
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+3000)) {
            drivetrain.driveAtPower(-0.2);
        }
    }

    /**
     * Deliver the duck by spinning the wheel clockwise
     */
    public void deliverDuckAutonomous() {
        deliveryWheel.setPower(0.4);
        delay(5000);
        deliveryWheel.stop();
    }

    /**
     * Park in the warehouse from the Carousel
     */
    public void parkInWarehouseFromCarousel() {
        driveInches(102);
    }

    /**
     * Park in the warehouse by driving backwards
     */
    public void parkInWarehouseFromCarouselBackwards() {
        driveInches(-102);
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
     * Move straight off the wall and place a loaded freight onto the shipping hub
     */
    public void placeFreightStraightFromWall() {
        drivetrain.driveForwardInchesNoPid(13, 0.4);
        armdex.place();
        delay(3000);
        armdex.stopIntake();
    }

}