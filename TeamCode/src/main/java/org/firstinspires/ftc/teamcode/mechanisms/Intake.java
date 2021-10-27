package org.firstinspires.ftc.teamcode.mechanisms;
// FIRST APIs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors
import com.qualcomm.robotcore.hardware.DcMotorSimple; // Need this so we can easily set motor directions
import com.qualcomm.robotcore.hardware.HardwareMap; // Need this so we can connect to the hardware used by this mechanism
import com.qualcomm.robotcore.hardware.Servo; // Need this so we can control the wrist servo

public class Intake {
    // Constants that need to be calibrated
    final double INTAKE_POWER = 50; // Power to drive the intake motor when retrieving Freight
    final double EJECT_POWER = -1; // Power to drive the intake motor when pushing Freight back out (just in case)
    final double WRIST_DOWN_POSITION = 0; // Servo position when the intake box is down to collect objects.
    final double WRIST_UP_POSITION = 1; // Servo position when the intake box is up (dumping Freight into indexer)
    final DcMotor.Direction INTAKE_DIRECTION = DcMotor.Direction.FORWARD; // Set which way we should normally rotate when bringing freight in

    DcMotor roller; // Motor connected to our intake wheel/star/tubing
    Servo wrist; // Servo connected to the hinge point of our intake box

    // Default constructor for a new Intake object
    public Intake() { }

    // Call this once to set up the mechanism
    public void init(HardwareMap theHardwareMap, LinearOpMode currentOpMode) {
        roller = theHardwareMap.get(DcMotor.class, "intake_motor");
        roller.setPower(0);
        roller.setDirection(INTAKE_DIRECTION);
        roller.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the motor immediately so we don't take in extra objects.
        wrist = theHardwareMap.get(Servo.class, "wristServo");
        wrist.setPosition(WRIST_UP_POSITION);
    }

    public void intake() {
        // ToDo: Implement this
        // Spin the intake motor at the normal power.
    }

    public void stop() {
        // ToDo: Implement this
        // Set the delivery wheel motor power to zero
    }

    public void eject() {
        // ToDo: Implement this
        // Set the intake motor in reverse to spit Freight out
    }

    public void up() {
        // ToDo: Implement this
        // Rotate the wrist up, so we can deliver Freight to the indexer, or score.
    }

    public void down() {
        // ToDo: Implement this
        // Rotate the wrist down, so we can deliver Freight to the indexer, or score.
    }

    public void autoIntake() {
        // ToDo: Implement this
        // Make sure the wrist is down
        // Spin the intake motor
        // Stop the intake motor when an object is collected
        // Rotate the intake up and eject the object into the indexer
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
