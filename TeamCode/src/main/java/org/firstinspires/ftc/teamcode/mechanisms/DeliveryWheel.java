package org.firstinspires.ftc.teamcode.mechanisms;

// FIRST APIs
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode; // Need this so we can check if "opModeIsActive"
import com.qualcomm.robotcore.hardware.DcMotor; // Need this so we can define our motors

// Team APIs


public class DeliveryWheel {
    // Constants that need to be calibrated
    final double TICKS_PER_WHEEL_ROTATION = 100; // Number of encoder ticks for one complete revolution of our delivery wheel
    final double ROTATIONS_FOR_COMPLETED_DELIVERY = 10; // Enough rotations of our wheel to completely deliver the duck (plus some safety)
    final double MAXIMUM_SAFE_RPM = 100; // Speed that will not knock the duck off.
    final DcMotor.Direction DIRECTION_FOR_LEFT = DcMotor.Direction.FORWARD; // Set which way we should normally rotate.
    final DcMotor.Direction DIRECTION_FOR_RIGHT = DcMotor.Direction.REVERSE; // Set which way we should normally rotate.
    final int TICKS_PER_CAROUSEL_REVOLUTION = 3360;

    DcMotor deliveryLeft = null; // Motor connected to our delivery wheel
    DcMotor deliveryRight = null; // Motor connected to our delivery wheel
    LinearOpMode opMode = null; // Store a soft copy of the opMode information (so we can check if we need to exit)

    // Default constructor for a new DeliveryWheel object
    public DeliveryWheel() { }

    // Call this once to set up the mechanism
    public void init(LinearOpMode opMode) {
        deliveryLeft = opMode.hardwareMap.get(DcMotor.class, "deliveryLeft");
        deliveryRight = opMode.hardwareMap.get(DcMotor.class, "deliveryRight");
        deliveryLeft.setDirection(DIRECTION_FOR_LEFT);
        deliveryRight.setDirection(DIRECTION_FOR_RIGHT);
        deliveryLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the wheel from turning when zero power is applied.
        deliveryLeft.setPower(0);
        deliveryRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Stop the wheel from turning when zero power is applied.
        deliveryRight.setPower(0);
        this.opMode = opMode;
    }

    // Spin our wheel at a set power
    public void rotateAtPower(double power) {
        deliveryLeft.setPower(power);
        deliveryRight.setPower(power);
    }

    // Bring our wheel to a complete stop
    public void stop() {
        deliveryLeft.setPower(0);
        deliveryRight.setPower(0);
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
        deliveryLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        deliveryLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        deliveryRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        deliveryRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Rotate a specified number of ticks at a specified power
     * @param numberOfTicks The number of ticks to rotate
     * @param power The power at which to rotate
     */
    public void rotateNumberOfTicks(int numberOfTicks, double power) {
        //TODO add second delivery motor to this

        // Make sure the power is positive
        if(power < 0) {
            power *= -1;
        }
        resetEncoder();
        if(numberOfTicks > 0) {
            while(opMode.opModeIsActive() && deliveryLeft.getCurrentPosition() < numberOfTicks) {
                rotateAtPower(power);
            }
        } else if(numberOfTicks < 0) {
            while(opMode.opModeIsActive() && deliveryLeft.getCurrentPosition() > numberOfTicks) {
                rotateAtPower(-power);
            }
        }
        stop();
    }

    /**
     * Get the number of ticks for a certain number of carousel revolutions
     * @param numberOfCarouselRotations The number of carousel revolutions
     * @return The number of ticks
     */
    public int carouselRotationsToTicks(double numberOfCarouselRotations) {
        return (int) (numberOfCarouselRotations*TICKS_PER_CAROUSEL_REVOLUTION);
    }

    /**
     * Rotate the carousel a number of rotations
     * @param numberOfCarouselRotations The number of carousel rotations to rotate
     * @param power The power at which to rotate
     */
    public void rotateNumberOfCarouselRotations(double numberOfCarouselRotations, double power) {
        rotateNumberOfTicks(carouselRotationsToTicks(numberOfCarouselRotations), power);
    }


}
