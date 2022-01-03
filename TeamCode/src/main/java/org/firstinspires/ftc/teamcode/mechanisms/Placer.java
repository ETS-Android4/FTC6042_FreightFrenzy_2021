package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Placer {

    LinearOpMode opMode;
    Servo arm;
    Servo hand;

    // Servo positions
    final double STARTING_POSITION = 1;
    final double STRAIGHT_UP_POSITION = 0.63;
    final double LEVEL_1_POSITION = 0;
    final double LEVEL_2_POSITION = 0.2;
    final double LEVEL_3_POSITION = 0.4;
    final double OPEN = 0.5;
    final double CLOSED = 0;

    /**
     * Instantiate the Placer object
     * @param opMode The OpMode this Placer object exists in
     */
    public Placer(LinearOpMode opMode) {
        this.opMode = opMode;
        arm = opMode.hardwareMap.get(Servo.class, "placerArm");
        hand = opMode.hardwareMap.get(Servo.class, "placerHand");
    }

    public Placer(LinearOpMode opMode, boolean init) {
        this.opMode = opMode;
        arm = opMode.hardwareMap.get(Servo.class, "placerArm");
        hand = opMode.hardwareMap.get(Servo.class, "placerHand");
        if(init) {
            init();
        }
    }

    /**
     * Set the servos in this mechanism to their initialized position
     */
    public void init() {
        closeHand();
        armStartingPosition();
    }

    /**
     * Open the hand
     */
    public void openHand() {
        hand.setPosition(OPEN);
    }

    /**
     * Close the hand
     */
    public void closeHand() {
        hand.setPosition(CLOSED);
    }

    /**
     * Set the arm to the starting position
     */
    public void armStartingPosition() {
        arm.setPosition(STARTING_POSITION);
    }

    /**
     * Set the arm to the straight up position
     */
    public void armStraightUp() {
        arm.setPosition(STRAIGHT_UP_POSITION);
    }

    /**
     * Set the arm to its level 1 position
     */
    public void armLevelOne() {
        arm.setPosition(LEVEL_1_POSITION);
    }

    /**
     * Set the arm to its level 2 position
     */
    public void armLevelTwo() {
        arm.setPosition(LEVEL_2_POSITION);
    }

    /**
     * Set the arm to its level 3 position
     */
    public void armLevelThree() {
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

    /**
     * Get the hand's current position
     * @return
     */
    public double getHandPosition() {
        return hand.getPosition();
    }

    /**
     * Get the arm's current position
     * @return
     */
    public double getArmPosition() {
        return arm.getPosition();
    }

}
