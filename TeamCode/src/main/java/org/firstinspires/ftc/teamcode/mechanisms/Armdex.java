package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Armdex {

    LinearOpMode opMode;
    DcMotor intake;
    DcMotor wrist;
    TouchSensor downSwitch;
    TouchSensor upSwitch;
    ColorSensor intakeSensor;

    final double WRIST_UP_SPEED = 1;
    final double WRIST_DOWN_SPEED = -1;
    final double INTAKE_SPEED = 1;
    final double EJECT_SPEED = -1;
    final boolean INTAKE_SENSOR_LED_DEFAULT_STATE = true;

    final int INTAKE_SENSOR_DETECTION_THRESHOLD_RED = 2800;
    final int INTAKE_SENSOR_DETECTION_THRESHOLD_GREEN = 2900;
    final int INTAKE_SENSOR_DETECTION_THRESHOLD_BLUE = 0;

    final int INTAKE_COUNTS_PER_REVOLUTION = 288;
    final int WRIST_COUNTS_PER_REVOLUTION = 288;


    public Armdex() {}

    /**
     * Initialize this armdex object. Warning: The blocker will move on initialization
     * @param opMode The opmode this armdex object exists in
     */
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        intake = opMode.hardwareMap.get(DcMotor.class, "intake");
        wrist = opMode.hardwareMap.get(DcMotor.class, "wrist");
        //downSwitch = opMode.hardwareMap.get(TouchSensor.class, "downSwitch");
        //upSwitch = opMode.hardwareMap.get(TouchSensor.class, "upSwitch");
        intakeSensor = opMode.hardwareMap.get(ColorSensor.class, "intakeSensor");
        intakeSensor.enableLed(INTAKE_SENSOR_LED_DEFAULT_STATE);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        wrist.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetWristEncoder();
        resetIntakeEncoder();
    }

    /**
     * Set the intake motor's power
     * @param power The power to set the intake motor to
     */
    public void setIntakePower(double power) {
        intake.setPower(power);
    }

    /**
     * Stop the intake motor
     */
    public void stopIntake() {
        intake.setPower(0);
    }

    /**
     * Set the wrist motor's power
     * @param power The power to set the wrist motor to
     */
    public void setWristPower(double power) {
        wrist.setPower(power);
    }

    /**
     * Stop the wrist motor
     */
    public void stopWrist() {
        wrist.setPower(0);
    }

    /**
     * Stop all of the motors in the armdex
     */
    public void stopAllMotors() {
        intake.setPower(0);
        wrist.setPower(0);
    }

    /**
     * Get whether the down position limit switch is being activated
     * @return
     */
    public boolean isWristDown() {
        return downSwitch.isPressed();
    }

    /**
     * Get whether the up position limit switch is being activated
     * @return
     */
    public boolean isWristUp() {
        return upSwitch.isPressed();
    }

    /**
     * Intake at the default intake speed
     */
    public void intake() {
        intake.setPower(INTAKE_SPEED);
    }

    /**
     * Eject at the default eject speed
     */
    public void eject() {
        intake.setPower(EJECT_SPEED);
    }

    /**
     * Reset the wrist encoder
     */
    public void resetWristEncoder() {
        wrist.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wrist.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Run the wrist at it's default up power
     */
    public void runWristUp() {
        wrist.setPower(WRIST_UP_SPEED);
    }

    /**
     * Run the wrist at it's default down power
     */
    public void runWristDown() {
        wrist.setPower(WRIST_DOWN_SPEED);
    }

    /**
     * Run the wrist up until it reaches its up position
     */
    public void wristUp() {
        while(opMode.opModeIsActive() && !isWristUp()) {
            runWristUp();
        }
        stopWrist();
    }

    /**
     * Run the wrist down until it reaches its down position
     */
    public void wristDown() {
        while(opMode.opModeIsActive() && !isWristDown()) {
            runWristDown();
        }
        stopWrist();
    }

    /**
     * Set the wrist to the down position and reset the encoder so it's relative to that position
     */
    public void homeWrist() {
        while(opMode.opModeIsActive() && !isWristDown()) {
            runWristDown();
        }
        stopWrist();
        resetWristEncoder();
    }

    /**
     * Get intake sensor red
     * @return The red value of the intake sensor
     */
    public int getIntakeSensorRed() {
        return intakeSensor.red();
    }

    /**
     * Get the intake sensor green
     * @return The green value of the intake sensor
     */
    public int getIntakeSensorGreen() {
        return intakeSensor.green();
    }

    /**
     * Get intake sensor blue
     * @return The blue value of the intake sensor
     */
    public int getIntakeSensorBlue() {
        return intakeSensor.blue();
    }

    /*[ph
     * Set whether the intake sensor LED is enabled or disabled
     * @param state Whether the light should be enabled or disabled. True and false respectively.
     */
    public void setIntakeSensorLed(boolean state) {
        intakeSensor.enableLed(state);
    }

    /**
     * Determine if an object is detected in the intake
     * @return Whether an object is detected in the intake
     */
    public boolean isObjectDetectedInIntake() {
        return getIntakeSensorRed() > INTAKE_SENSOR_DETECTION_THRESHOLD_RED && getIntakeSensorGreen() > INTAKE_SENSOR_DETECTION_THRESHOLD_GREEN && getIntakeSensorBlue() > INTAKE_SENSOR_DETECTION_THRESHOLD_BLUE;
    }

    /**
     * Get the intake's current encoder value
     * @return The intake's current encoder value
     */
    public int getIntakePosition() {
        return intake.getCurrentPosition();
    }

    /**
     * Get the wrist's current position
     * @return The wrist's current position
     */
    public int getWristPosition() {
        return wrist.getCurrentPosition();
    }

    /**
     * Reset the encoder for hte intake
     */
    public void resetIntakeEncoder() {
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
