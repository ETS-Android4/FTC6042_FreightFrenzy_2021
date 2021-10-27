package org.firstinspires.ftc.teamcode.mechanisms;

// FIRST APIs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; // Need this so we can check if "opModeIsActive"
import com.qualcomm.robotcore.hardware.ColorSensor; // Need this so we can define our color sensor
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our servos
import com.qualcomm.robotcore.hardware.HardwareMap; // Need this so we can connect to the hardware used by this mechanism
import com.qualcomm.robotcore.hardware.Servo;

public class Lift {
    // The lift is a linear slide mechanism (controlled by one motor) that has four preset heights (bottom, low, mid, & high)
    // The lift's box also has a servo that dumps/pushes/releases the Freight.
    // There is a color sensor on the lift that looks at a white bar with colored areas that indicate the height (black=load, red, orange=low, yellow, green=middle, blue, purple=high)

    // Constants that need to be calibrated
    final double UP_POWER = 100; // Power that we should use when moving up.
    final double DOWN_POWER = -50; // Safe power for moving down.
    final DcMotor.Direction UP_DIRECTION = DcMotor.Direction.FORWARD; // Set which way the motor should rotate to slide the lift up.
    final double BUCKET_OPEN = 1.0; // Servo position to remove Freight from the bucket.
    final double BUCKET_CLOSED = 0.0; // Servo position to keep Freight in the bucket.
    final int BLACK_THRESHOLD = 10; // If all color readings are less than this, we are at the BOTTOM (loading position)
    final int COLOR_THRESHOLD = 200; // If a color is greater than this, we are seeing that color.

    DcMotor liftMotor = null; // Motor connected to a string that raises and lowers our lift
    Servo bucketServo = null; // Servo that releases the Freight
    ColorSensor colorSensor = null; // Used for detecting the position of the lift
    LinearOpMode opMode = null; // Points to the opMode information (so we can check if we need to exit)
    HardwareMap hardwareMap = null; // Points to the robot's hardware mapping of ports to component names (defined by the phone).

    // Default constructor for a new Lift object
    public Lift() { }

    // Call this once to set up the mechanism
    public void init(HardwareMap theHardwareMap, LinearOpMode currentOpMode) {
        liftMotor = theHardwareMap.get(DcMotor.class, "lift_motor");
        liftMotor.setDirection(UP_DIRECTION);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the lift from falling when zero power is applied.
        liftMotor.setPower(0);
        bucketServo = theHardwareMap.get(Servo.class, "bucket_servo");
        bucketServo.setPosition(BUCKET_CLOSED);
        colorSensor = theHardwareMap.get(ColorSensor.class, "lift_color_sensor");;
        opMode = currentOpMode;
    }

    // Continuous raising (not using sensor positions)
    public void raise() {
        // ToDo: Implement this
    }

    // Stop the lift at the current height
    public void stop() {
        // ToDo: Implement this
    }

    // Continuous lowering (not using sensor positions)
    public void lower() {
        // ToDo: Implement this
    }

    // Release the Freight in the bucket
    public void open() {
        // ToDo: Implement this
    }

    // Set the servo so that freight will stay in the bucket
    public void open(double temporaryVarForBuilding) {
        // ToDo: Implement this
        //TODO remove the temporary variable, as it exists only to allow our code to build
    }
    
    // Start (or continue) sending the bucket down to the loading position
    // Keep calling this so it can stop the motors at the correct point
    // Returns true if at the correct position
    public boolean autoGoToLoadPosition() {
        // ToDo: Implement this
        // If we don't see black, we need to go down
        return true; //TODO remove this, as it exists only to allow our code to build
    }
 
    // Start (or continue) sending the bucket to the low scoring position
    // Keep calling this so it can stop the motors at the correct point
    // Returns true if at the correct position
    public boolean autoGoToLowPosition() {
        // ToDo: Implement this
        // If we see red, stop.
        // If we see black, we need to go up.
        // Otherwise, go down.
        return true; //TODO remove this, as it exists only to allow our code to build
    }

    // Start (or continue) sending the bucket to the low scoring position
    // Keep calling this so it can stop the motors at the correct point
    // Returns true if at the correct position
    public boolean autoGoToMediumPosition() {
        // ToDo: Implement this
        return true; //TODO remove this, as it exists only to allow our code to build
    }

    // Start (or continue) sending the bucket to the low scoring position
    // Keep calling this so it can stop the motors at the correct point
    // Returns true if at the correct position
    public boolean autoGoToHighPosition() {
        // ToDo: Implement this
        return true; //TODO remove this, as it exists only to allow our code to build
    }
}
