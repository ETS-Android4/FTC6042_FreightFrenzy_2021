package org.firstinspires.ftc.teamcode.mechanisms;
// FIRST APIs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors
import com.qualcomm.robotcore.hardware.DcMotorSimple; // Need this so we can easily set motor directions
import com.qualcomm.robotcore.hardware.HardwareMap; // Need this so we can connect to the hardware used by this mechanism
import com.qualcomm.robotcore.hardware.Servo; // Need this so we can control the wrist servo
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Intake {

    LinearOpMode opMode;

    // Constants that need to be calibrated
    final double INTAKE_POWER = 0.5; // Power to drive the intake motor when retrieving Freight
    final double EJECT_POWER = -1; // Power to drive the intake motor when pushing Freight back out (just in case)
    final double WRIST_POWER = 0.2;
    final double WRIST_TRAVEL_DISTANCE_IN_TICKS = 500; // The distance the wrist has to travel to get from being down to being up
    final DcMotor.Direction INTAKE_DIRECTION = DcMotor.Direction.FORWARD; // Set which way we should normally rotate when bringing freight in

    // The RGB values used to check whether freight is in the intake
    final double boxR = 0; // The approximate red value for the boxes
    final double boxG = 0; // The approximate green value for the boxes
    final double boxB = 0; // The approximate blue value for the boxes
    final double cargoR = 0; // The approximate red value for the cargo
    final double cargoG = 0; // The approximate green value for the cargo
    final double cargoB = 0; // The approximate blue value for the cargo
    final double colorDeadZone = 0; // The range of values that can be accepted still considered the object


    DcMotor roller; // Motor connected to our intake wheel/star/tubing
    DcMotor wrist; // Motor connected to the hinge point of our intake box
    TouchSensor downSwitch; // The magnetic limit switch for when the wrist is down
    TouchSensor upSwitch; // The magnetic limit switch for when the wrist is up
    ColorSensor freightSensor; // The color sensor for detecting whether there is freight in our intake

    // Default constructor for a new Intake object
    public Intake() { }

    // Call this once to set up the mechanism
    public void init(LinearOpMode opMode) {
        roller = opMode.hardwareMap.get(DcMotor.class, "intakeRoller");
        roller.setPower(0);
        roller.setDirection(INTAKE_DIRECTION);
        roller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the motor immediately so we don't take in extra objects.
        wrist = opMode.hardwareMap.get(DcMotor.class, "intakeWrist");
        this.opMode = opMode;
        downSwitch = opMode.hardwareMap.get(TouchSensor.class, "intakeDownSwitch");
        upSwitch = opMode.hardwareMap.get(TouchSensor.class, "intakeUpSwitch");
        freightSensor = opMode.hardwareMap.get(ColorSensor.class, "intakeFreightSensor");
    }

    /**
     * Spin the intake wheel at its default power, if the intake is down. If it's up, don't spin at all.
     */
    public void intake() {
        roller.setPower(INTAKE_POWER);
    }

    /**
     * Stop the intake motor
     */
    public void stop() {
        roller.setPower(0);
    }

    /**
     * Run the intake backwards to "eject" an element
     */
    public void eject() {
        roller.setPower(EJECT_POWER);
    }

    /**
     * Move the wrist to its "up" position
     */
    public void up() {
        wrist.setPower(WRIST_POWER);
    }

    /**
     * Move the wrist to its "down" position
     */
    public void down() {
        wrist.setPower(-WRIST_POWER);
    }

    /**
     * Set the intake to a specific power
     * @param power The power to set the intake to
     */
    public void setIntakePower(double power) {
        roller.setPower(power);
    }

    /**
     * Set the wrist to a specific power
     * @param power The power to set the wrist to
     */
    public void setWristPower(double power) {
        wrist.setPower(power);
    }

    /**
     * Automatically intake until an object is detected, or until the auto intake button is pressed again
     */
    public void autoIntake() {
        // TODO make this the proper button
        while(opMode.opModeIsActive() && !opMode.gamepad1.a) {
            intake();
        }
        stop();
    }

    //
    // PRIVATE: FUNCTIONS BELOW HERE SHOULD NOT NORMALLY BE CALLED (except by other functions in this file)
    //

    // Returns true if freight is sensed in the intake box.
    private boolean isFreightPresent() {
        // Check to see what object is present
        if(isInDeadZone(freightSensor.red(), boxR) && isInDeadZone(freightSensor.green(), boxG) && isInDeadZone(freightSensor.blue(), boxB)) {
            // The object detected meets to requirements to be considered a box!
            // TODO make this log that we have detected a box
            return true;
        } else if(isInDeadZone(freightSensor.red(), cargoR) && isInDeadZone(freightSensor.green(), cargoG) && isInDeadZone(freightSensor.blue(), cargoB)) {
            // The object detected meets the requirements ot be considered a cargo!
            // TODO make this log that we have detected a cargo
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks whether a given value is within the dead zone of values relative to the second given value
     * @param valueToCheck The value
     * @param valueToCheckAgainst The value used to create the dead zone by adding and subtracting the dead zone from it
     * @return
     */
    private boolean isInDeadZone(double valueToCheck, double valueToCheckAgainst) {
        return valueToCheck > valueToCheckAgainst - colorDeadZone && valueToCheck < valueToCheckAgainst + colorDeadZone;
    }

}
