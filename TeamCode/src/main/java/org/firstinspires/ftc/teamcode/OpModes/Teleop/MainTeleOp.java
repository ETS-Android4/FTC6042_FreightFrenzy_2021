package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="Main TeleOp (Two Controllers)")
public class MainTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        final double NORMAL_DRIVE_MAX_POWER = 0.5;
        final double OVERDRIVE_MAX_POWER = 1;

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
        TelemetryWriter output = new TelemetryWriter().setDrivetrain(drivetrain).setDeliveryWheel(deliveryWheel).setArmdex(armdex).init(telemetry);

        // Let the driver know the robot is done initializing
        output.robotInitialized();
        led.setStatusRobotInitialized();

        // Wait for the driver to press play
        waitForStart();

        armdex.wristUp();

        boolean overrideIntake = false;

        // Repeat while the opmode is still active
        while(opModeIsActive()) {

            double drivePowerMultiplier = NORMAL_DRIVE_MAX_POWER;
            // Check if driver is using overdrive mode
            if(gamepad1.right_bumper) {
                drivePowerMultiplier = OVERDRIVE_MAX_POWER;
                output.addLine("OVERDRIVE ENABLED");
            }

            // If we detect a block, write it to the telemetry
            if(armdex.isObjectDetectedInIntake()) {
                output.addFreightDetected();
            }

            // Write our wrist sensor info to the telemetry
            output.addIsWristUp().addIsWristDown().addWristSensorValues();

            // Write our intake sensor values to the telemetry
            output.addIntakeSensorValues();

            // Emergency stop drivetrain
            if(gamepad2.dpad_left || gamepad2.dpad_right || (gamepad2.left_stick_y > 0.5 && gamepad2.right_stick_y > 0.5)) {
                drivePowerMultiplier = -0.2;
            }

            /*
            ========== CODE FOR CONTROLLER 1 ==========
             */

            // Calculate our target drivetrain powers
            double leftTargetPower = -drivePowerMultiplier*gamepad1.left_stick_y;
            double rightTargetPower = -drivePowerMultiplier*gamepad1.right_stick_y;

            // Write our target powers to the telemetry
            output.addLine("Left Target Power: " + leftTargetPower).addLine("Right Target Power: " + rightTargetPower);

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

            // Control the arm
            if(gamepad1.dpad_up) {
                armdex.wristUp();
            } else if(gamepad1.dpad_down) {
                armdex.wristDown();
            }

            /*
            ========== CODE FOR CONTROLLER 2 ==========
             */

            boolean isRightTriggerPressed = gamepad2.right_trigger > 0.2;
            boolean isBlockPresentInIntake = armdex.isObjectDetectedInIntake();

            if(!isRightTriggerPressed) {
                if(isBlockPresentInIntake) {
                    overrideIntake = true;
                } else {
                    overrideIntake = false;
                }
            }
            if(gamepad2.left_trigger > 0.2) {
                armdex.eject();
            } else if(isRightTriggerPressed) {
                if(!overrideIntake) {
                    if(!isBlockPresentInIntake) {
                        if(!armdex.isWristUp()) {
                            armdex.intake();
                        } else {
                            armdex.stopIntake();
                        }
                    } else {
                        armdex.stopIntake();
                        armdex.wristUp();
                    }
                } else {
                    if(armdex.isWristUp()) {
                        armdex.place();
                    } else {
                        armdex.intake();
                    }
                }
            } else {
                armdex.stopIntake();
            }

            // Operate the wrist
            if(gamepad2.y) {
                armdex.wristUp();
            } else if(gamepad2.a) {
                armdex.wristDown();
            } else if(gamepad2.x || gamepad2.b) {
                if(armdex.isWristUp()) {
                    armdex.wristDown();
                } else if(armdex.isWristDown()) {
                    armdex.wristUp();
                } else {
                    armdex.wristDown();
                }
            }

            // Force block to expel
            if(gamepad2.dpad_up) {
                armdex.setIntakePower(1);
            }

            // Update the telemetry
            output.update();
        }
    }

}
