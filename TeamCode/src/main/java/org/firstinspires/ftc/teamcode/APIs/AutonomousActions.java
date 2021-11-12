package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.PID.PidApi;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.mechanisms.Indexer;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Lift;

public class AutonomousActions {

    LinearOpMode opMode;
    Drivetrain drivetrain;
    DeliveryWheel deliveryWheel;
    LedController led;
    //Indexer indexer;
    //Intake intake;
    //Lift lift;

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
        led = new LedController();
        //indexer = new Indexer();
        //intake = new Intake();
        //lift = new Lift();

        // Initialize our mechanisms
        drivetrain.init(opMode);
        deliveryWheel.init(opMode);
        led.initNoInitLight(opMode);
        //indexer.init(opMode);
        //intake.init(opMode);
        //lift.init(opMode);
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
     * Score a preloaded object, assuming the alliance specific shipping hub is to our left.
     */
    public void scorePreloadLeft() {
        //TODO Implement this
    }

    /**
     * Score a preload object, assuming the alliance specific shipping hub is to our right.
     */
    public void scorePreloadRight() {
        //TODO Implement this
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

    // TODO create a backwards version of the method below

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
    public void deliverDuck() {
        deliveryWheel.setPower(1);
        delay(3000);
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
        drivetrain.rotateDegreesNoPid(degreesToRotate);
    }

}