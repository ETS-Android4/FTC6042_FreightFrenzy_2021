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

    final double ROTATIONS_TO_DELIVER = 1;
    final double SPEED_TO_DELIVER = 1;

    // Robot and field values
    final int TICKS_PER_MOTOR_REVOLUTION = 1120;
    final double MOTOR_TO_OUTPUT_WHEEL_RATIO = 1;
    final double OUTPUT_WHEEL_DIAMETER_IN_INCHES = 5;
    final double CAROUSEL_DIAMETER_IN_INCHES = 15;

    // Values found automatically by math. Do not adjust.
    final double CAROUSEL_CIRCUMFERENCE_IN_INCHES = CAROUSEL_DIAMETER_IN_INCHES*Math.PI;
    final double OUTPUT_WHEEL_CIRCUMFERENCE_IN_INCHES = OUTPUT_WHEEL_DIAMETER_IN_INCHES*Math.PI;
    final double OUTPUT_WHEEL_ROTATIONS_PER_CAROUSEL_ROTATION = CAROUSEL_CIRCUMFERENCE_IN_INCHES/OUTPUT_WHEEL_CIRCUMFERENCE_IN_INCHES;
    final double MOTOR_TICKS_PER_CAROUSEL_REVOLUTION = TICKS_PER_MOTOR_REVOLUTION*OUTPUT_WHEEL_ROTATIONS_PER_CAROUSEL_ROTATION;

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
     * Start the deliver action
     */
    public void startActionDeliver() {
        isActionRunning = true;
        resetEncoders();
        setPower(SPEED_TO_DELIVER);
    }

    /**
     * Update where we are for the delivery action. This method should be called continuously in a loop in your opmode
     */
    public void updatePosition() {
        if(!isActionRunning) {
            return;
        }
        boolean leftSideStopped = false;
        boolean rightSideStopped = false;
        if(getLeftPosition() > ROTATIONS_TO_DELIVER*MOTOR_TICKS_PER_CAROUSEL_REVOLUTION) {
            stopLeft();
            leftSideStopped = true;
        } else {
            setLeftPower(SPEED_TO_DELIVER);
        }
        if(getRightPosition() > ROTATIONS_TO_DELIVER*MOTOR_TICKS_PER_CAROUSEL_REVOLUTION) {
            stopRight();
            rightSideStopped = true;
        } else {
            setRightPower(SPEED_TO_DELIVER);
        }
        if(leftSideStopped && rightSideStopped) {
            isActionRunning = false;
        }
    }

    /**
     * Cancel the current action
     */
    public void cancelAction() {
        isActionRunning = false;
        stop();
    }

    /**
     * Check if the current action is complete
     * @return Whether or not the action has been completed
     */
    public boolean isActionComplete() {
        return !isActionRunning;
    }

}