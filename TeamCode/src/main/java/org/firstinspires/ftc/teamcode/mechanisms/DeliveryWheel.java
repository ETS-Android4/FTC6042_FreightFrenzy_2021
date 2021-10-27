package org.firstinspires.ftc.teamcode.APIs; // Include this with our team "APIs" package

// FIRST APIs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; // Need this so we can check if "opModeIsActive"
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors
import com.qualcomm.robotcore.hardware.HardwareMap; // Need this so we can connect to the hardware used by this mechanism

// Team APIs
import org.firstinspires.ftc.teamcode.APIs.PID.PidApi; // Need this so we can use PID to control speed

public class DeliveryWheel {
    // Constants that neeed to be calibrated
    const double TICKS_PER_WHEEL_ROTATION = 100; // Number of encoder ticks for one complete revolution of our delivery wheel
    const double ROTATIONS_FOR_COMPLETED_DELIVERY = 10; // Enough rotations of our wheel to completely deliver the duck (plus some safety)
    const double MAXIMUM_SAFE_RPM = 100; // Speed that will not knock the duck off.
    const DcMotor.Direction DIRECTION_TO_ROTATE = DcMotor.Direction.FORWARD; // Set which way we should normally rotate.

    DcMotor deliveryWheel = null; // Motor connected to our delivery wheel
    LinearOpMode opMode = null; // Store a soft copy of the opMode information (so we can check if we need to exit)

    // Default constructor for a new DeliveryWheel object
    public DeliveryWheel() { }

    // Call this once to set up the mechanism
    public void init(HardwareMap theHardwareMap, LinearOpMode currentOpMode) {
        deliveryWheel = theHardwareMap.get(DcMotor.class, "delivery_motor");
        deliveryWheel.setDirection(DIRECTION_TO_ROTATE);
        deliveryWheel.ZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the wheel from turning when zero power is applied.
        deliveryWheel.setPower(0);
        opMode = currentOpMode;
    }

    // Spin our wheel at a set power
    public void rotateAtPower(double power) {
        // ToDo: Implement this
        // Set the delivery wheel mnotor power to the level requested
    }

    // Bring our wheel to a complete stop
    public void stop() {
        // ToDo: Implement this
        // Set the delivery wheel motor power to zero
    }

    // Spin our wheel a set number of degrees at the fastest safe speed, then stop the motor.
    public void rotateNumberOfDegrees(double degrees) {
        // ToDo: Implement this
        // Determine the number of ticks to rotate
        // Reset the encoders
        // While the opModeIsActive and we haven't reached the desired number of ticks
            // Rotate at a safe spinning power
        // Stop the motors
        // Bonus Points: Spin up the motor to the max safe speed using PID
    }

    //
    // PRIVATE: FUNCTIONS BELOW HERE SHOULD NOT NORMALLY BE CALLED (except by other functions in this file)
    //

    private double ticksFromDegrees(double degrees) {
        // ToDo: Implement this.
        // We know how many ticks are in one revolution of our delivery wheel (see constants)
        // Determine how many ticks are counted by the encoder for one degree of turn of our delivery wheel
        // Convert the number of degrees passed in to the number of "ticks" the motor needs to turn
        return 0;
    }

    // Call this whenever we need to restart the motor count (i.e. start a new delivery)
    public void resetEncoder() {
        deliveryWheel.setMode(DcMotor.RunMode.RESET_ENCODERS);
        deliveryWheel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
