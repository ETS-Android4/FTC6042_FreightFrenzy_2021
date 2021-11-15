package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
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

        // Let the driver know the robot is done initializing
        telemetry.addLine("Robot initialized");
        telemetry.update();
        led.setStatusRobotInitialized();


        // Wait for the driver to press play
        waitForStart();

        boolean wasRightTriggerPressedLastRun = gamepad1.right_trigger > 0.2;

        // Repeat while the opmode is still active
        while(opModeIsActive()) {
            // Get our power for each side based on the left and right stick values
            double leftTargetPower = -DRIVE_POWER_MULTIPLIER*gamepad1.left_stick_y;
            double rightTargetPower = -DRIVE_POWER_MULTIPLIER*gamepad1.right_stick_y;

            // Write our target powers to the telemetry
            telemetry.addLine("Left: " + leftTargetPower);
            telemetry.addLine("Right: " + rightTargetPower);
            telemetry.update();

            drivetrain.driveAtPower(leftTargetPower, rightTargetPower);

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

//            boolean isRightTriggerPressedThisRun = gamepad1.right_trigger > 0.2;

//            // Check if we've changed the position of the trigger since last run
//            if(isRightTriggerPressedThisRun == wasRightTriggerPressedLastRun) {
//                // We have not changed the trigger position
//                if(!deliveryWheel.isActionComplete()) {
//                    // We have an action running, so we need to update our positition
//                    deliveryWheel.updatePosition();
//                }
//            } else {
//                // We have changed the trigger position
//                if (deliveryWheel.isActionComplete() && isRightTriggerPressedThisRun) {
//                    // No action is running and the right trigger is being pressed, so we should start a new action
//                    deliveryWheel.startActionDeliver();
//                } else if (!deliveryWheel.isActionComplete() && isRightTriggerPressedThisRun) {
//                    // An action is running but we're pressing the trigger, so we should cancel the action
//                    deliveryWheel.cancelAction();
//                } else if(!deliveryWheel.isActionComplete()) {
//                    // An action is running so we need to update our position
//                    deliveryWheel.updatePosition();
//                }
//            }

        }
    }

}
