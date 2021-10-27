package org.firstinspires.ftc.teamcode.APIs; // Include this with our team "APIs" package

// FIRST APIs
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors
import com.qualcomm.robotcore.hardware.DcMotorSimple; // Need this so we can easily set motor directions
import com.qualcomm.robotcore.hardware.HardwareMap; // Need this so we can connect to the hardware used by this mechanism
import com.qualcomm.robotcore.hardware.Servo; // Need this so we can control the wrist servo

public class Intake {
    // Constants that neeed to be calibrated
    const double INTAKE_POWER = 50; // Power to drive the intake motor when retrieving Freight
    const double EJECT_POWER = -1-0; // Power to drive the intake motor when pushing Freight back out (just in case)
    const double WRIST_DOWN_POSITION = 0; // Servo position when the intake box is down to collect objects.
    const double WRIST_UP_POSITION = 1; // Servo position when the intake box is up (dumping Freight into indexer)
    const DcMotor.Direction INTAKE_DIRECTION = DcMotor.Direction.FORWARD; // Set which way we should normally rotate when bringing freight in

    DcMotor roller; // Motor connected to our intake wheel/star/tubing
    Servo wrist; // Servo connected to the hinge point of our intake box

    // Default constructor for a new Intake object
    public Intake() { }

    // Call this once to set up the mechanism
    public void init(HardwareMap theHardwareMap, LinearOpMode currentOpMode) {
        roller = theHardwareMap.get(DcMotor.class, "intake_motor");
        roller.setPower(0);
        roller.setDirection(INTAKE_DIRECTION);
        roller.ZeroPowerBehavior(DCMotor.ZeroPowerBehavior.BRAKE); // Stop the motor immediately so we don't take in extra objects.
        wrist = wristServo;
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
        // Set the intake motor in reverse to spit Frieght out
    }

    public void up() {
        // ToDo: Implement this
        // Rotate the wrist up, so we can deliver Frieght to the indexer, or score.
    }

    public void down() {
        // ToDo: Implement this
        // Rotate the wrist down, so we can deliver Frieght to the indexer, or score.
    }

    public void autoIntake() {
        // ToDo: Implement this
        // Make sure the wrist is down
        // Spin the intake motor
        // Stop the intake motor when an object is collected
        // Rotate the intake up and eject the object into the inexer
    }

    //
    // PRIVATE: FUNCTIONS BELOW HERE SHOULD NOT NORMALLY BE CALLED (except by other functions in this file)
    //

    // Returns true if frieght is sensed in the intake box.
    private boolean frieghtCollected() {
        // ToDo: Implement this.
        // Check a sensor to determine if freight is present.
    }
}
