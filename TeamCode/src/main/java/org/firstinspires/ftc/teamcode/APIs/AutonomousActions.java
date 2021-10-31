package org.firstinspires.ftc.teamcode.APIs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.mechanisms.Indexer;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Lift;

public class AutonomousActions {

    LinearOpMode opMode;
    Drivetrain drivetrain;
    DeliveryWheel deliveryWheel;
    Indexer indexer;
    Intake intake;
    Lift lift;

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
        indexer = new Indexer();
        intake = new Intake();
        lift = new Lift();

        // Initialize our mechanisms
        drivetrain.init(opMode);
        deliveryWheel.init(opMode);
        indexer.init(opMode);
        intake.init(opMode);
        lift.init(opMode);
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
        //TODO Implement this
    }

    /**
     * Rotate right off of the wall
     */
    public void rotateRightFromWall() {
        //TODO Implement this
    }

    /**
     * Drive straight to the delivery wheel from wherever we are
     */
    public void driveToDeliveryWheel() {
        //TODO Implement this
    }

    /**
     * Deliver the duck by spinning the wheel clockwise
     */
    public void deliverDuckClockwise() {
        //TODO Implement this
    }

    /**
     * Deliver the duck by spinning the wheel counter clockwise
     */
    public void deliverDuckCounterClockwise() {
        //TODO Implement this
    }

    /**
     * Park in the warehouse by driving straight forwards
     */
    public void parkInWarehouse() {
        //TODO Implement this
    }

    /**
     * Park in the warehouse by driving backwards
     */
    public void parkInWarehouseBackwards() {
        //TODO Implement this
    }

    /**
     * Drive forward a specified number of inches
     */
    public void driveInches(double inchesToDrive) {
        drivetrain.driveForwardInches(inchesToDrive);
    }

    /**
     * Rotate a specified number of degrees
     */
    public void rotateDegrees() {
        //TODO Implement this
    }

}
