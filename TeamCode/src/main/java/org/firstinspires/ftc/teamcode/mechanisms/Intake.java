package org.firstinspires.ftc.teamcode.mechanisms;
// FIRST APIs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors
import com.qualcomm.robotcore.hardware.DcMotorSimple; // Need this so we can easily set motor directions
import com.qualcomm.robotcore.hardware.HardwareMap; // Need this so we can connect to the hardware used by this mechanism
import com.qualcomm.robotcore.hardware.Servo; // Need this so we can control the wrist servo

public class Intake {

    LinearOpMode opMode;

    // Constants that need to be calibrated
    final double INTAKE_POWER = 50; // Power to drive the intake motor when retrieving Freight
    final double EJECT_POWER = -1; // Power to drive the intake motor when pushing Freight back out (just in case)
    final double WRIST_DOWN_POSITION = 0; // Servo position when the intake box is down to collect objects.
    final double WRIST_UP_POSITION = 1; // Servo position when the intake box is up (dumping Freight into indexer)
    final DcMotor.Direction INTAKE_DIRECTION = DcMotor.Direction.FORWARD; // Set which way we should normally rotate when bringing freight in

    DcMotor roller; // Motor connected to our intake wheel/star/tubing
    Servo wrist; // Servo connected to the hinge point of our intake box
    boolean isWristUp;

    // Default constructor for a new Intake object
    public Intake() { }

    // Call this once to set up the mechanism
    public void init(LinearOpMode opMode) {
        roller = opMode.hardwareMap.get(DcMotor.class, "intake_motor");
        roller.setPower(0);
        roller.setDirection(INTAKE_DIRECTION);
        roller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the motor immediately so we don't take in extra objects.
        wrist = opMode.hardwareMap.get(Servo.class, "wristServo");
        wrist.setPosition(WRIST_UP_POSITION);
        isWristUp = true;
        this.opMode = opMode;
    }

    /**
     * Spin the intake wheel at its default power, if the intake is down. If it's up, don't spin at all.
     */
    public void intake() {
        if(!isWristUp) {
            roller.setPower(INTAKE_POWER);
        } else {
            roller.setPower(0);
        }
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
        if(!isWristUp) {
            roller.setPower(EJECT_POWER);
        } else {
            roller.setPower(0);
        }
    }

    /**
     * Move the wrist to its "up" position
     */
    public void up() {
        wrist.setPosition(WRIST_UP_POSITION);
    }

    /**
     * Move the wrist to its "down" position
     */
    public void down() {
        wrist.setPosition(WRIST_DOWN_POSITION);
    }

    public void autoIntake() {
        // ToDo: Implement this
    }

    //
    // PRIVATE: FUNCTIONS BELOW HERE SHOULD NOT NORMALLY BE CALLED (except by other functions in this file)
    //

    // Returns true if freight is sensed in the intake box.
    private boolean freightCollected() {
        // ToDo: Implement this.
        // Check a sensor to determine if freight is present.
        return true; //TODO Remove this, as this return exists only to allow the code to build
    }
}
