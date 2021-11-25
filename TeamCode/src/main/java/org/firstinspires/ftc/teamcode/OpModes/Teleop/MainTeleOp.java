package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="Main Teleop")
public class MainTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        final double DRIVE_POWER_MULTIPLIER = 0.5;

        // Instantiate and initialize our drivetrain
        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel();
        deliveryWheel.init(this);
        AutonomousActions autonomousActions = new AutonomousActions();
        autonomousActions.init(this);
        LedController led = new LedController();
        led.init(this);
        Armdex armdex = new Armdex();
        armdex.init(this);

        // Let the driver know the robot is done initializing
        telemetry.addLine("Robot initialized");
        telemetry.update();
        led.setStatusRobotInitialized();


        // Wait for the driver to press play
        waitForStart();

        boolean wasIntakeAutoStoppedSinceLastControllerInput = false;

        // Repeat while the opmode is still active
        while(opModeIsActive()) {

            // If we detect a block, add it to the telemetry
            if(armdex.isObjectDetectedInIntake()) {
                telemetry.addLine("Block detected");
            }
            // Write our intake sensor values to the telemetry
            telemetry.addLine("Intake Sensor R: " + armdex.getIntakeSensorRed());
            telemetry.addLine("Intake Sensor G: " + armdex.getIntakeSensorGreen());
            telemetry.addLine("Intake Sensor B: " + armdex.getIntakeSensorBlue());

            /*
            ========== CODE FOR CONTROLLER 1 ==========
             */

            // Calculate our target drivetrain powers
            double leftTargetPower = -DRIVE_POWER_MULTIPLIER*gamepad1.left_stick_y;
            double rightTargetPower = -DRIVE_POWER_MULTIPLIER*gamepad1.right_stick_y;

            // Write our target powers to the telemetry
            telemetry.addLine("Left Drive: " + leftTargetPower);
            telemetry.addLine("Right Drive: " + rightTargetPower);

            // Drive at the target power
            drivetrain.driveAtPower(leftTargetPower, rightTargetPower);

            // Control the delivery mechanism
            if(gamepad1.right_trigger > 0.2) {
                led.setStatusDeliveringDuck();
                deliveryWheel.setPower(0.6);
            } else if(gamepad1.left_trigger > 0.05) {
                led.setStatusDeliveringReverse();
                deliveryWheel.setPower(-gamepad1.left_trigger);
            } else {
                deliveryWheel.stop();
                led.setStatusDeliveryFinished();
            }

            /*
            ========== CODE FOR CONTROLLER 2 ==========
             */

            /*
            Check if the intake needs to be auto stopped. This checks to see if there's a block and if it was already auto stopped
             */
            if(armdex.isObjectDetectedInIntake() && !wasIntakeAutoStoppedSinceLastControllerInput && gamepad2.right_trigger > 0.2) {
                armdex.stopIntake();
                wasIntakeAutoStoppedSinceLastControllerInput = true;
            }

            // Control the intake
            if(gamepad2.left_trigger > 0.2) {
                // Eject the freight
                armdex.eject();
                wasIntakeAutoStoppedSinceLastControllerInput = false;
            } else if(gamepad2.right_trigger > 0.2) {
                // Intake the freight unless we are stopped
                if(!wasIntakeAutoStoppedSinceLastControllerInput) {
                    armdex.intake();
                }
            } else {
                // Don't intake anything
                wasIntakeAutoStoppedSinceLastControllerInput = false;
            }

            // Operate the wrist
            if(gamepad2.right_bumper) {
                armdex.runWristUp();
            } else if(gamepad2.left_bumper) {
                armdex.runWristDown();
            } else {
                armdex.stopWrist();
            }

            // Force the intake to run
            if(gamepad2.a) {
                armdex.setIntakePower(.4);
            }

            // Update the telemetry
            telemetry.update();

        }
    }

}
