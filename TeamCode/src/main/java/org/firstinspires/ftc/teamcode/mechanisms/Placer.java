package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Placer {

    LinearOpMode opMode;
    Servo arm;
    Servo hand;

    // Servo positions
    final double DEFAULT_POSITION = 0.2;
    final double LEVEL_1_POSITION = 0;
    final double LEVEL_2_POSITION = 0.5;
    final double LEVEL_3_POSITION = 1;
    final double OPEN = 0;
    final double CLOSED = 1;

    /**
     * Instantiate the Placer object
     * @param opMode The OpMode this Placer object exists in
     */
    public Placer(LinearOpMode opMode) {
        this.opMode = opMode;
        arm = opMode.hardwareMap.get(Servo.class, "PlacerArm");
        hand = opMode.hardwareMap.get(Servo.class, "PlacerHand");
    }

    /**
     * Set the servos in this mechanism to their initialized position
     */
    public void init() {
        handClosed();
        armDefaultPosition();
    }

    /**
     * Open the hand
     */
    public void handOpen() {
        hand.setPosition(OPEN);
    }

    /**
     * Close the hand
     */
    public void handClosed() {
        hand.setPosition(CLOSED);
    }

    /**
     * Set the arm to its default position
     */
    public void armDefaultPosition() {
        arm.setPosition(DEFAULT_POSITION);
    }

    /**
     * Set the arm to its level 1 position
     */
    public void armLevel1() {
        arm.setPosition(LEVEL_1_POSITION);
    }

    /**
     * Set the arm to its level 2 position
     */
    public void armLevel2() {
        arm.setPosition(LEVEL_2_POSITION);
    }

    /**
     * Set the arm to its level 3 position
     */
    public void armLevel3() {
        arm.setPosition(LEVEL_3_POSITION);
    }

    /**
     * Set the arm servo to a specified position
     * @param position The position to set the arm servo to
     */
    public void setArmPosition(double position) {
        arm.setPosition(position);
    }

    /**
     * Set the hand servo to a specified position
     * @param position The position to set the arm servo to
     */
    public void setHandPosition(double position) {
        hand.setPosition(position);
    }

}
