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

    boolean isDuckBeingDelivered = false;

    @Override
    public void runOpMode() throws InterruptedException {

        final double DRIVE_POWER_MULTIPLIER = 1;

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

        boolean wasIntakeAutoStopped = false;

        // Repeat while the opmode is still active
        while(opModeIsActive()) {
            // Get our power for each side based on the left and right stick values
            double leftTargetPower = -DRIVE_POWER_MULTIPLIER*gamepad1.left_stick_y;
            double rightTargetPower = -DRIVE_POWER_MULTIPLIER*gamepad1.right_stick_y;

            // Write our target powers to the telemetry
            telemetry.addLine("Left: " + leftTargetPower);
            telemetry.addLine("Right: " + rightTargetPower);
            telemetry.addLine("R: " + armdex.getIntakeSensorRed());
            telemetry.addLine("G: " + armdex.getIntakeSensorGreen());
            telemetry.addLine("B: " + armdex.getIntakeSensorBlue());
            telemetry.update();
            drivetrain.driveAtPower(leftTargetPower, rightTargetPower);

            if(armdex.isObjectDetectedInIntake() && !wasIntakeAutoStopped && gamepad1.right_trigger > 0.2) {
                armdex.stopIntake();
                wasIntakeAutoStopped = true;
            }

            if(gamepad1.left_trigger > 0.2) {
                armdex.eject();
                wasIntakeAutoStopped = false;
            } else if(gamepad1.right_trigger > 0.2) {
                if(!wasIntakeAutoStopped) {
                    armdex.intake();
                }
            } else {
                wasIntakeAutoStopped = false;
            }

            if(gamepad1.dpad_up) {
                armdex.runWristUp();
            } else if(gamepad1.dpad_down) {
                armdex.runWristDown();
            } else {
                armdex.stopWrist();
            }


            if(gamepad1.a) {
                led.setStatusDeliveringDuck();
                deliveryWheel.setPower(0.6);
            } else if(gamepad1.b) {
                led.setStatusDeliveringReverse();
                deliveryWheel.setPower(-0.5);
            } else {
                deliveryWheel.stop();
                led.setStatusDeliveryFinished();
            }

        }
    }

}
