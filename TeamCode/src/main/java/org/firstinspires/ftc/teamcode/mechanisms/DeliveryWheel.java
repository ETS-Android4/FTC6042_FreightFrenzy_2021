package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class DeliveryWheel {

    LinearOpMode opMode;
    DcMotor deliveryLeft;
    DcMotor deliveryRight;

    final DcMotorSimple.Direction LEFT_DELIVERY_DIRECTION = DcMotorSimple.Direction.REVERSE;
    final DcMotorSimple.Direction RIGHT_DELIVERY_DIRECTION = DcMotorSimple.Direction.FORWARD;

    // Robot and field values
    final int TICKS_PER_MOTOR_REVOLUTION = 1120;
    final double MOTOR_TO_OUTPUT_WHEEL_RATIO = 1;
    final double OUTPUT_WHEEL_DIAMETER_IN_INCHES = 5;
    final double CAROUSEL_DIAMETER_IN_INCHES = 15;

    // Values found automatically by the wonderful thing called math. Do not adjust.
    final double CAROUSEL_CIRCUMFERENCE_IN_INCHES = CAROUSEL_DIAMETER_IN_INCHES*Math.PI;
    final double OUTPUT_WHEEL_CIRCUMFERENCE_IN_INCHES = OUTPUT_WHEEL_DIAMETER_IN_INCHES*Math.PI;
    final double OUTPUT_WHEEL_ROTATIONS_PER_CAROUSEL_ROTATION = CAROUSEL_CIRCUMFERENCE_IN_INCHES/OUTPUT_WHEEL_CIRCUMFERENCE_IN_INCHES;
    final double MOTOR_TICKS_PER_CAROUSEL_REVOLUTION = TICKS_PER_MOTOR_REVOLUTION*OUTPUT_WHEEL_ROTATIONS_PER_CAROUSEL_ROTATION;

    // Ramp values
    final double DELIVERY_TARGET_SPEED = 1;
    final double SPEED_TO_INCREASE_PER_SECOND = 2; // The percent of our top speed to increase by per second run
    final double SPEED_TO_INCREASE_PER_MILLISECOND = SPEED_TO_INCREASE_PER_SECOND/1000;
    final double RAMP_START_SPEED = 0.4;
    long rampStartTimeInMillis = 0;

    boolean isActionRunning = false;

    public DeliveryWheel() {

    }

    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        deliveryLeft = opMode.hardwareMap.get(DcMotor.class, "deliveryLeft");
        deliveryRight = opMode.hardwareMap.get(DcMotor.class, "deliveryRight");
        deliveryLeft.setDirection(LEFT_DELIVERY_DIRECTION);
        deliveryRight.setDirection(RIGHT_DELIVERY_DIRECTION);
        deliveryLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        deliveryRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Spin both delivery wheels at the same power
     * @param power The power at which to spin the delivery wheels
     */
    public void setPower(double power) {
        deliveryLeft.setPower(power);
        deliveryRight.setPower(power);
    }

    /**
     * Set both delivery wheels to two different powers
     * @param leftPower The power at which to spin the left delivery wheel
     * @param rightPower The power at which to spin the right delivery wheel
     */
    public void setPower(double leftPower, double rightPower) {
        deliveryLeft.setPower(leftPower);
        deliveryRight.setPower(rightPower);
    }

    /**
     * Set the power of the left delivery wheel
     * @param power The power to set the left delivery wheel to
     */
    public void setLeftPower(double power) {
        deliveryLeft.setPower(power);
    }

    /**
     * Set the power of the right delivery wheel
     * @param power The power to set the right delivery wheel to
     */
    public void setRightPower(double power) {
        deliveryRight.setPower(power);
    }

    /**
     * Stop both motors
     */
    public void stop() {
        setPower(0);
    }

    /**
     * Stop the left delivery motor
     */
    public void stopLeft() {
        deliveryLeft.setPower(0);
    }

    /**
     * Stop the right delivery motor
     */
    public void stopRight() {
        deliveryRight.setPower(0);
    }

    /**
     * Get the current encoder ticks of the left delivery motor
     * @return The current encoder ticks of the left delivery motor
     */
    public int getLeftPosition() {
        return deliveryLeft.getCurrentPosition();
    }

    /**
     * Get the current encoder ticks of the right delivery motor
     * @return The current encoder ticks of the right delivery motor
     */
    public int getRightPosition() {
        return deliveryRight.getCurrentPosition();
    }

    /**
     * Get the highest encoder tick count
     * @return The highest encoder tick count
     */
    public int getHighestPosition() {
        int leftPosition = getLeftPosition();
        int rightPosition = getRightPosition();
        return Math.max(rightPosition, leftPosition);
    }

    /**
     * Reset the encoders
     */
    public void resetEncoders() {
        deliveryLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        deliveryRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        deliveryLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        deliveryRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Start the ramp. This should be called the first time the button is detected as pressed.
     */
    public void startRamp() {
        rampStartTimeInMillis = System.currentTimeMillis();
        setPower(RAMP_START_SPEED);
    }

    /**
     * Update the ramp. This should be called every time the ramp is supposed to be run after it has started
     */
    public void updateRamp() {
        long millisecondsElapsedSinceStart = (System.currentTimeMillis()-rampStartTimeInMillis);
        double targetPower = millisecondsElapsedSinceStart*SPEED_TO_INCREASE_PER_MILLISECOND;
        setPower(targetPower);
    }

    /**
     * Stop the delivery wheel
     */
    public void stopRamp() {
        stop();
        rampStartTimeInMillis = 0;
    }

}