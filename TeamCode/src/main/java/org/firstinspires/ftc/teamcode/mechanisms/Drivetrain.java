package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.APIs.Gyroscope.GyroscopeApi;
import org.firstinspires.ftc.teamcode.APIs.PID.PidApi;

public class Drivetrain {

    // Constants
    final double DEFAULT_POWER = 0.5;
    final double DRIVE_WHEEL_DIAMETER_IN_INCHES = 5;
    final double DRIVE_WHEEL_CIRCUMFERENCE_IN_INCHES = 3.1459*DRIVE_WHEEL_DIAMETER_IN_INCHES;
    final double MOTOR_TO_WHEEL_RATIO = 0.5; // The number of rotations on the output shaft of the motor necessary for one rotation of the wheel
    final double ENCODER_TICKS_PER_WHEEL_ROTATION = 560;
    final double DEFAULT_ROTATE_SPEED = 0.2;

    // PID related constants
    //TODO tune these values
    final double DRIVETRAIN_P_GAIN = 0.01;
    final double DRIVETRAIN_I_GAIN = 0;
    final double DRIVETRAIN_D_GAIN = 0.01;
    final double DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS = 2;
    final double ROTATION_P_GAIN = 0;
    final double ROTATION_I_GAIN = 0;
    final double ROTATION_D_GAIN = 0;
    final double ROTATION_PID_DEAD_ZONE = 0;

    final double ROTATION_NO_PID_DEAD_ZONE = 5;

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor rearLeft;
    DcMotor rearRight;
    LinearOpMode opMode;
    GyroscopeApi gyro;

    public Drivetrain() {}

    /**
     * Initialize this drivetrain object, feeding it the four drive motors and the opmode
     * @param frontLeft Front left drive motor
     * @param frontRight Front right drive motor
     * @param rearLeft Rear left drive motor
     * @param rearRight Rear right drive motor
     * @param opMode The opmode this object is being instantiating in
     */
    public void init(DcMotor frontLeft, DcMotor frontRight, DcMotor rearLeft, DcMotor rearRight, LinearOpMode opMode) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.rearLeft = rearLeft;
        this.rearRight = rearRight;
        this.opMode = opMode;
        gyro = new GyroscopeApi(opMode.hardwareMap);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);
        resetEncoders();
    }

    /**
     * Initialize this drivetrain object, feeding it the opmode which will be for fetching the drive motor objects
     * @param opMode The opmode this object is being instantiated in
     */
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        frontLeft = opMode.hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = opMode.hardwareMap.get(DcMotor.class, "frontRight");
        rearLeft = opMode.hardwareMap.get(DcMotor.class, "rearLeft");
        rearRight = opMode.hardwareMap.get(DcMotor.class, "rearRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rearRight.setDirection(DcMotorSimple.Direction.REVERSE);
        gyro = new GyroscopeApi(opMode.hardwareMap);
        resetEncoders();
    }

    /**
     * Set the drive power of the left side
     * @param power The power to set the left side to
     */
    public void setLeftPower(double power) {
        frontLeft.setPower(power);
        rearLeft.setPower(power);
    }

    /**
     * Set the drive power of the right side
     * @param power The power to set the right side to
     */
    public void setRightPower(double power) {
        frontRight.setPower(power);
        rearRight.setPower(power);
    }

    /**
     * Drive the robot at the specified power
     * @param power The power at which to drive the robot
     */
    public void driveAtPower(double power) {
        setLeftPower(power);
        setRightPower(power);
    }

    /**
     * Drive the robot at the specified left and right power, respectively
     * @param leftPower The power for the left side
     * @param rightPower The power for the right side
     */
    public void driveAtPower(double leftPower, double rightPower) {
        setLeftPower(leftPower);
        setRightPower(rightPower);
    }

    /**
     * Drive the front left motor
     * @param power The power at which to drive the front left motor
     */
    public void driveFrontLeft(double power) {
        frontLeft.setPower(power);
    }

    /**
     * Drive the front right motor
     * @param power The power at which to drive the front right motor
     */
    public void driveFrontRight(double power) {
        frontRight.setPower(power);
    }

    /**
     * Drive the rear left motor
     * @param power The power at which to drive the rear left motor
     */
    public void driveRearLeft(double power) {
        rearLeft.setPower(power);
    }

    /**
     * Drive the rear right motor
     * @param power The power at which to drive the rear right motor
     */
    public void driveRearRight(double power) {
        rearRight.setPower(power);
    }

    /**
     * Convert inches to drive to ticks to drive
     * @param inches The number of inches to drive
     * @return The number of ticks to drive
     */
    private double inchesToTicks(double inches) {
        double rotations = inches/DRIVE_WHEEL_CIRCUMFERENCE_IN_INCHES;
        return rotations*ENCODER_TICKS_PER_WHEEL_ROTATION;
    }

    /**
     * Reset the encoders of our drivetrain
     */
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rearLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rearRight.setMode(DcMotor.RunMode.RESET_ENCODERS);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rearRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Get the average of all of the drivetrain encoders
     * @return The average of all of the drivetrain encoders
     */
    public double getEncoderAverage() {
        double frontLeftEncoder = frontLeft.getCurrentPosition();
        double frontRightEncoder = frontRight.getCurrentPosition();
        double rearLeftEncoder = rearLeft.getCurrentPosition();
        double rearRightEncoder = rearRight.getCurrentPosition();
        return (frontLeftEncoder+frontRightEncoder+rearLeftEncoder+rearRightEncoder)/4;
    }

    /**
     * Get the average of all of the drivetrain encoders
     * @return The average of all of the drivetrain encoders
     */
    public double getEncoderAbsoluteAverage() {
        double frontLeftEncoder = Math.abs(frontLeft.getCurrentPosition());
        double frontRightEncoder = Math.abs(frontRight.getCurrentPosition());
        double rearLeftEncoder = Math.abs(rearLeft.getCurrentPosition());
        double rearRightEncoder = Math.abs(rearRight.getCurrentPosition());
        return (frontLeftEncoder+frontRightEncoder+rearLeftEncoder+rearRightEncoder)/4;
    }

    /**
     * Stop the drive motors
     */
    public void stopMotors() {
        driveAtPower(0);
    }

    /**
     * Drive forward the given number of inches using encoders, with the default power value
     * @param inches The number of inches to drive
     */
    public void driveForwardInchesNoPid(double inches) {
        resetEncoders();

        boolean desiredDirectionIsForward;
        desiredDirectionIsForward = (inches > 0 && DEFAULT_POWER > 0) || (inches < 0 && DEFAULT_POWER < 0);
        double targetTicks = inchesToTicks(inches);

        if(desiredDirectionIsForward) {
            while(opMode.opModeIsActive() && getEncoderAbsoluteAverage() < Math.abs(targetTicks)) {
                driveAtPower(Math.abs(DEFAULT_POWER));
            }
        } else {
            while(opMode.opModeIsActive() && getEncoderAbsoluteAverage() < Math.abs(targetTicks)) {
                driveAtPower(-Math.abs(DEFAULT_POWER));
            }
        }

        stopMotors();
    }

    /**
     * Drive forward the given number of inches using encoders, at a specified power
     * @param inches The number of inches to drive
     * @param power The power at which to drive
     */
    public void driveForwardInchesNoPid(double inches, double power) {

        resetEncoders();

        boolean desiredDirectionIsForward;
        desiredDirectionIsForward = (inches > 0 && power > 0) || (inches < 0 && power < 0);
        double targetTicks = inchesToTicks(inches);

        if(desiredDirectionIsForward) {
            while(opMode.opModeIsActive() && getEncoderAbsoluteAverage() < Math.abs(targetTicks)) {
                driveAtPower(Math.abs(power));
            }
        } else {
            while(opMode.opModeIsActive() && getEncoderAbsoluteAverage() < Math.abs(targetTicks)) {
                driveAtPower(-Math.abs(power));
            }
        }

        stopMotors();
    }

    /**
     * Drive forward the given number of inches using the given PID values
     * @param inches The inches to drive forward
     * @param p The P gain
     * @param i The I gain
     * @param d The D gain
     */
    public void driveForwardInches(double inches, double p, double i, double d) {
        resetEncoders();
        double distanceToTravelInTicks = inchesToTicks(inches);

        // Instantiate our PID objects
        PidApi frontLeftPid = new PidApi(p, i, d, DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS);
        PidApi frontRightPid = new PidApi(p, i, d, DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS);
        PidApi rearLeftPid = new PidApi(p, i, d,DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS);
        PidApi rearRightPid = new PidApi(p, i, d, DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS);

        while(opMode.opModeIsActive() && !(frontLeftPid.hasReachedTarget() && frontRightPid.hasReachedTarget() && rearLeftPid.hasReachedTarget() && rearRightPid.hasReachedTarget())) {
            frontLeft.setPower(frontLeftPid.getLimitedControlLoopOutput(frontLeft.getCurrentPosition(), distanceToTravelInTicks, 1));
            frontRight.setPower(frontRightPid.getLimitedControlLoopOutput(frontRight.getCurrentPosition(), distanceToTravelInTicks, 1));
            rearLeft.setPower(rearLeftPid.getLimitedControlLoopOutput(rearLeft.getCurrentPosition(), distanceToTravelInTicks, 1));
            rearRight.setPower(rearRightPid.getLimitedControlLoopOutput(rearRight.getCurrentPosition(), distanceToTravelInTicks, 1));
        }
        stopMotors();
    }

    /**
     * Drive forward the given number of inches using the default PID values specified in the drivetrain constants class
     * @param inches The number of inches to drive forward
     */
    public void driveForwardInches(double inches) {
        resetEncoders();
        double distanceToTravelInTicks = inchesToTicks(inches);

        double p = DRIVETRAIN_P_GAIN;
        double i = DRIVETRAIN_I_GAIN;
        double d = DRIVETRAIN_D_GAIN;
        double deadZoneInTicks = DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS;

        // Instantiate our PID objects
        PidApi frontLeftPid = new PidApi(p, i, d, deadZoneInTicks);
        PidApi frontRightPid = new PidApi(p, i, d, deadZoneInTicks);
        PidApi rearLeftPid = new PidApi(p, i, d, deadZoneInTicks);
        PidApi rearRightPid = new PidApi(p, i, d, deadZoneInTicks);

        while(opMode.opModeIsActive() && !(frontLeftPid.hasReachedTarget() && frontRightPid.hasReachedTarget() && rearLeftPid.hasReachedTarget() && rearRightPid.hasReachedTarget())) {
            frontLeft.setPower(frontLeftPid.getLimitedControlLoopOutput(frontLeft.getCurrentPosition(), distanceToTravelInTicks, 1));
            frontRight.setPower(frontRightPid.getLimitedControlLoopOutput(frontRight.getCurrentPosition(), distanceToTravelInTicks, 1));
            rearLeft.setPower(rearLeftPid.getLimitedControlLoopOutput(rearLeft.getCurrentPosition(), distanceToTravelInTicks, 1));
            rearRight.setPower(rearRightPid.getLimitedControlLoopOutput(rearRight.getCurrentPosition(), distanceToTravelInTicks, 1));
        }
        stopMotors();
    }

    /**
     * Drive forward the given number of inches with a specified top speed using the default PID values specified in the drivetrain constants class
     * @param inches The number of inches to drive forward
     * @param topSpeed The top speed our robot can drive
     */
    public void driveForwardInches(double inches, double topSpeed) {
        resetEncoders();
        double distanceToTravelInTicks = inchesToTicks(inches);

        double p = DRIVETRAIN_P_GAIN;
        double i = DRIVETRAIN_I_GAIN;
        double d = DRIVETRAIN_D_GAIN;
        double deadZoneInTicks = DRIVETRAIN_PID_DEAD_ZONE_IN_TICKS;

        // Instantiate our PID objects
        PidApi frontLeftPid = new PidApi(p, i, d, deadZoneInTicks);
        PidApi frontRightPid = new PidApi(p, i, d, deadZoneInTicks);
        PidApi rearLeftPid = new PidApi(p, i, d, deadZoneInTicks);
        PidApi rearRightPid = new PidApi(p, i, d, deadZoneInTicks);

        while(opMode.opModeIsActive() && !(frontLeftPid.hasReachedTarget() && frontRightPid.hasReachedTarget() && rearLeftPid.hasReachedTarget() && rearRightPid.hasReachedTarget())) {
            frontLeft.setPower(frontLeftPid.getLimitedControlLoopOutput(frontLeft.getCurrentPosition(), distanceToTravelInTicks, topSpeed));
            frontRight.setPower(frontRightPid.getLimitedControlLoopOutput(frontRight.getCurrentPosition(), distanceToTravelInTicks, topSpeed));
            rearLeft.setPower(rearLeftPid.getLimitedControlLoopOutput(rearLeft.getCurrentPosition(), distanceToTravelInTicks, topSpeed));
            rearRight.setPower(rearRightPid.getLimitedControlLoopOutput(rearRight.getCurrentPosition(), distanceToTravelInTicks, topSpeed));
        }
        stopMotors();
    }

    /**
     * Rotate a certain number of degrees using PID. Negative degrees rotate counterclockwise and positive numbers rotate clockwise
     * @param degreesToRotate The number of degrees to rotate
     */
    public void rotateDegrees(double degreesToRotate) {

        // Get our current orientation and record it as our starting position
        gyro.update();
        //TODO change all axis instances to the proper axis
        float startingRotation = gyro.getY();

        // Instantiate our PID object for rotation
        PidApi rotationPid = new PidApi(ROTATION_P_GAIN, ROTATION_I_GAIN, ROTATION_D_GAIN, ROTATION_PID_DEAD_ZONE);

        if(degreesToRotate > 0) {
            while(opMode.opModeIsActive() && !rotationPid.hasReachedTarget()) {
                gyro.update();
                double pidOutput = rotationPid.getLimitedControlLoopOutput(gyro.getY(), startingRotation+degreesToRotate, 1);
                driveAtPower(pidOutput, -pidOutput);
            }
        } else if(degreesToRotate < 0) {
            while(opMode.opModeIsActive() && !rotationPid.hasReachedTarget()) {
                gyro.update();
                double pidOutput = rotationPid.getLimitedControlLoopOutput(gyro.getY(), startingRotation-degreesToRotate, 1);
                driveAtPower(pidOutput, -pidOutput);
            }
        }
        stopMotors();
    }

    /**
     * Rotate a certain number of degrees at the default power without PID. Negative degrees rotate counter clockwise, while positive degrees rotate clockwise.
     * @param degreesToRotate The number of degrees to rotate
     */
    public void rotateDegreesNoPid(double degreesToRotate) {
        // Get our current orientation and record it as our starting position
        gyro.update();
        float startingRotation = gyro.getY();

        boolean rotatingClockwise;

        rotatingClockwise = (degreesToRotate > 0 && DEFAULT_ROTATE_SPEED > 0) || (degreesToRotate < 0 && DEFAULT_ROTATE_SPEED < 0);
        int numberOfTimesToRotate = (int) String.valueOf(degreesToRotate / 360).charAt(0);

        if(rotatingClockwise) {
            double valueToStopRotatingAt = Math.abs((startingRotation+Math.abs(degreesToRotate))%360);
            for (int i = 0; i < numberOfTimesToRotate; i++) {
                while(!(gyro.getY() >= valueToStopRotatingAt && gyro.getY() <= valueToStopRotatingAt+ROTATION_NO_PID_DEAD_ZONE) && opMode.opModeIsActive()) {
                    driveAtPower(Math.abs(DEFAULT_ROTATE_SPEED), -Math.abs(DEFAULT_ROTATE_SPEED));
                    gyro.update();
                }
            }
        } else {
            double valueToStopRotatingAt = (startingRotation+360-Math.abs(degreesToRotate))%360;
            for (int i = 0; i < numberOfTimesToRotate; i++) {
                while(!(gyro.getY() <= valueToStopRotatingAt && gyro.getY() >= valueToStopRotatingAt-ROTATION_NO_PID_DEAD_ZONE) && opMode.opModeIsActive()) {
                    driveAtPower(-Math.abs(DEFAULT_ROTATE_SPEED), Math.abs(DEFAULT_ROTATE_SPEED));
                    gyro.update();
                }
            }
        }
        stopMotors();
    }

    /**
     * Rotate a certain number of degrees at a certain power and without PID. Negative degrees rotate counter clockwise, while positive degrees rotate clockwise.
     * @param degreesToRotate The number of degrees to rotate
     * @param speedToRotate The speed at which to rotate
     */
    public void rotateDegreesNoPid(double degreesToRotate, double speedToRotate) {
        // Get our current orientation and record it as our starting position
        gyro.update();
        float startingRotation = gyro.getY();

        boolean rotatingClockwise;

        rotatingClockwise = (degreesToRotate > 0 && speedToRotate > 0) || (degreesToRotate < 0 && speedToRotate  < 0);
        int numberOfTimesToRotate = (int) String.valueOf(degreesToRotate / 360).charAt(0);

        if(rotatingClockwise) {
            double valueToStopRotatingAt = Math.abs((startingRotation+Math.abs(degreesToRotate))%360);
            for (int i = 0; i < numberOfTimesToRotate; i++) {
                while(!(gyro.getY() >= valueToStopRotatingAt && gyro.getY() <= valueToStopRotatingAt+ROTATION_NO_PID_DEAD_ZONE) && opMode.opModeIsActive()) {
                    driveAtPower(Math.abs(speedToRotate), -Math.abs(speedToRotate));
                    opMode.telemetry.addLine("Value to stop at" + valueToStopRotatingAt);
                    opMode.telemetry.addLine("Current " + gyro.getY());
                    opMode.telemetry.addLine("Raw " + gyro.getRawY());
                    opMode.telemetry.update();
                    gyro.update();


                }
            }
        } else {
            double valueToStopRotatingAt = (startingRotation+360-Math.abs(degreesToRotate))%360;
            for (int i = 0; i < numberOfTimesToRotate; i++) {
                while(!(gyro.getY() <= valueToStopRotatingAt && gyro.getY() >= valueToStopRotatingAt-ROTATION_NO_PID_DEAD_ZONE) && opMode.opModeIsActive()) {
                    driveAtPower(-Math.abs(speedToRotate), Math.abs(speedToRotate));
                    opMode.telemetry.addLine("Value to stop at" + valueToStopRotatingAt);
                    opMode.telemetry.addLine("Current " + gyro.getY());
                    opMode.telemetry.addLine("Raw " + gyro.getRawY());
                    opMode.telemetry.addLine("counter ");


                    opMode.telemetry.update();
                    gyro.update();
                }
            }
        }
        stopMotors();
    }

    /**
     * Drive at a certain power for a certain number of milliseconds
     * @param power The power at which to drive
     * @param millis The number of milliseconds for which to drive
     */
    public void driveTime(double power, long millis) {
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && System.currentTimeMillis() < startTimeInMillis+millis) {
            driveAtPower(power);
        }
        stopMotors();
    }

    /**
     * Drive the left and right sides at specified powers for a certain number of milliseconds
     * @param leftPower The speed at which to drive the left side of the robot
     * @param rightPower The speed at which to drive the right side of the robot
     * @param millis The number of milliseconds for which to drive
     */
    public void driveTime(double leftPower, double rightPower, long millis) {
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && System.currentTimeMillis() < startTimeInMillis+millis) {
            driveAtPower(leftPower, rightPower);
        }
        stopMotors();
    }

    /**
     * Rotate in place like a tank at a certain power for a certain number of milliseconds
     * @param power The power at which to rotate
     * @param millis The number of milliseconds for which to rotate
     */
    public void rotateInPlaceTime(double power, long millis) {
        long startTimeInMillis = System.currentTimeMillis();
        while(opMode.opModeIsActive() && System.currentTimeMillis() < startTimeInMillis+millis) {
            driveAtPower(power, -power);
        }
        stopMotors();
    }

    public int getFrontLeftEncoderValue() {
        return frontLeft.getCurrentPosition();
    }

    public int getFrontRightEncoderValue() {
        return frontRight.getCurrentPosition();
    }

    public int getRearLeftEncoderValue() {
        return rearLeft.getCurrentPosition();
    }

    public int getRearRightEncoderValue() {
        return rearRight.getCurrentPosition();
    }

    /**
     * Get the gyroscope API used by this drivetrain object
     * @return The gyroscope API used by this drivetrain object
     */
    public GyroscopeApi getGyroscopeApi() {
        return gyro;
    }

}
