package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.ActionThreads.WristDownAction;
import org.firstinspires.ftc.teamcode.ActionThreads.WristUpAction;

public class Armdex {

    LinearOpMode opMode;
    DcMotor intake;
    DcMotor wrist;
    ColorSensor wristSensor;
    ColorSensor intakeSensor;

    // Variables for this subsystem
    final double WRIST_UP_SPEED = 1;
    final double WRIST_DOWN_SPEED = -0.5;
    final double INTAKE_SPEED = 0.75;
    final double EJECT_SPEED = -1;
    final double PLACE_SPEED = 0.7;
    final boolean INTAKE_SENSOR_LED_DEFAULT_STATE = true;
    final boolean WRIST_SENSOR_LED_DEFAULT_STATE = true;

    // The minimum RGB values that must be met for the intake sensor to consider freight present
    final int INTAKE_SENSOR_DETECTION_THRESHOLD_RED = 1600;
    final int INTAKE_SENSOR_DETECTION_THRESHOLD_GREEN = 2700;
    final int INTAKE_SENSOR_DETECTION_THRESHOLD_BLUE = 0;

    final int INTAKE_COUNTS_PER_REVOLUTION = 288;
    final int WRIST_COUNTS_PER_REVOLUTION = 288;

    public Thread currentlyRunningThread = null;

    public Armdex() {}

    /**
     * Initialize this armdex object. Warning: The blocker will move on initialization
     * @param opMode The opmode this armdex object exists in
     */
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        intake = opMode.hardwareMap.get(DcMotor.class, "intake");
        wrist = opMode.hardwareMap.get(DcMotor.class, "wrist");
        wristSensor = opMode.hardwareMap.get(ColorSensor.class, "wristSensor");
        wristSensor.enableLed(WRIST_SENSOR_LED_DEFAULT_STATE);
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
     * Get whether the wrist is in the down position based on the wrist color sensor
     * @return Whether the wrist is in the down position
     */
    public boolean isWristDown() {
        return isWristDetectingGreen();
    }

    /**
     * Get whether the wrist is in the up position based on the wrist color sensor
     * @return Whether the wrist is in the up position
     */
    public boolean isWristUp() {
        return isWristDetectingPurple();
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
     * Place the object at the default place speed
     */
    public void place() {
        intake.setPower(PLACE_SPEED);
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
        // Don't continue to run if the wrist is already up
        if(isWristUp()) {
            return;
        }
        // See if we're currently running a thread, and cancel it if we are
        if(currentlyRunningThread != null) {
            // Check if the running thread is for a wrist up action, and don't create another action if it is
            if(currentlyRunningThread.getClass() == WristUpAction.class) {
                return;
            }
            currentlyRunningThread.interrupt();
        }
        // Create a new action thread and run it
        WristUpAction wristUpAction = new WristUpAction(this, opMode);
        currentlyRunningThread = wristUpAction;
        wristUpAction.start();
    }

    /**
     * Run the wrist down until it reaches its down position
     */
    public void wristDown() {
        // Don't continue to run if the wrist is already up
        if(isWristDown()) {
            return;
        }
        // See if we're currently running a thread, and cancel it if we are
        if(currentlyRunningThread != null) {
            // Check if the running thread is for a wrist up action, and don't create another action if it is
            if(currentlyRunningThread.getClass() == WristDownAction.class) {
                return;
            }
            currentlyRunningThread.interrupt();
        }
        // Create a new action thread and run it
        WristDownAction wristDownAction = new WristDownAction(this, opMode);
        currentlyRunningThread = wristDownAction;
        wristDownAction.start();
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
     * Get the intake sensor blue
     * @return The blue value of the intake sensor
     */
    public int getIntakeSensorBlue() {
        return intakeSensor.blue();
    }

    /*
     * Set whether the intake sensor LED is enabled or disabled
     * @param state Whether the light should be enabled or disabled. True and false respectively.
     */
    public void setIntakeSensorLed(boolean state) {
        intakeSensor.enableLed(state);
    }

    /**
     * Get the intake sensor red
     * @return
     */
    public int getWristSensorRed() {
        return wristSensor.red();
    }

    /**
     * Get the intake sensor green
     * @return
     */
    public int getWristSensorGreen() {
        return wristSensor.green();
    }

    /**
     * Get the intake sensor blue
     * @return
     */
    public int getWristSensorBlue() {
        return wristSensor.blue();
    }

    /**
     * Set whether ine wrist sensor LED is enabled or disabled
     * @param state Whether the light should be enabled or disabled
     */
    public void setWristSensorLed(boolean state) {
        wristSensor.enableLed(state);
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
     * Reset the encoder for the intake
     */
    public void resetIntakeEncoder() {
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Check if the wrist color sensor is detecting the color purple
     * @return Whether the wrist color sensor is detecting the color purple
     */
    public boolean isWristDetectingPurple() {
        return getWristSensorBlue() > 2 * getWristSensorRed() && getWristSensorGreen() < getWristSensorBlue();
    }

    /**
     * CHeck if the wrist color sensor is detecting the color green
     * @return Whether the wrist color sensor is detecting the color green
     */
    public boolean isWristDetectingGreen() {
        return getWristSensorGreen() > 0.8*(getWristSensorRed()+getWristSensorBlue()) && getWristSensorBlue() > 1.7*getWristSensorRed();
    }

    /**
     * Check if the wrist color sensor is detecting the color red
     * @return Whether the wrist color sensor is detecting the color red
     */
    public boolean isWristDetectingRed() {
        return getWristSensorRed() > 1.8*getWristSensorBlue();
    }

}
