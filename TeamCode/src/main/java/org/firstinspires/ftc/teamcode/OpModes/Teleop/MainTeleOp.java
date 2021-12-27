package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
        Drivetrain drivetrain = new Drivetrain(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel(this);
        LedController led = new LedController(this);
        Armdex armdex = new Armdex(this);
        TelemetryWriter output = new TelemetryWriter(telemetry).setDrivetrain(drivetrain).setDeliveryWheel(deliveryWheel).setArmdex(armdex);

        // Let the driver know the robot is done initializing
        output.robotInitialized();

        // Wait for the driver to press play
        waitForStart();

        armdex.wristUp();
        boolean isWristSupposedToBeUp = true;
        boolean isRampRunning = false;
        boolean overrideIntake = false;
        boolean isAutoDriveEnabled = false;
        boolean wasRightTriggerPressedPreviously = false;

        output.addLine("Robot Running");

        // Repeat while the opmode is still active
        while(opModeIsActive()) {

            double drivePowerMultiplier = NORMAL_DRIVE_MAX_POWER;
            // Check if driver is using overdrive mode or auto drive mode
            if(gamepad1.right_bumper) {
                drivePowerMultiplier = OVERDRIVE_MAX_POWER;
            } else if(gamepad1.left_bumper) {
                isAutoDriveEnabled = true;
            } else {
                isAutoDriveEnabled = false;
            }

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


            // Drive at the target power
            if(!isAutoDriveEnabled) {
                drivetrain.driveAtPower(leftTargetPower, rightTargetPower);
            } else {
                drivetrain.driveAtPower(-1);
            }

            // Control the delivery mechanism
            if(gamepad1.right_trigger > 0.5 && !(gamepad1.left_trigger > 0.1)) {
                led.setColorRed();
                if (!isRampRunning) {
                    deliveryWheel.startRamp();
                    isRampRunning = true;
                } else {
                    deliveryWheel.updateRamp();
                }
            } else if(gamepad1.left_trigger > 0.1 && !(gamepad1.right_trigger > 0.5)) {
                deliveryWheel.setPower(-gamepad1.left_trigger);
                led.setColorYellow();
            } else {
                if(isRampRunning) {
                    deliveryWheel.stopRamp();
                    isRampRunning = false;
                    led.setColorGreen();
                } else {
                    deliveryWheel.stop();
                }
            }

            // Control the wrist
            if(gamepad1.dpad_up) {
                isWristSupposedToBeUp = true;
                armdex.wristUp();
            } else if(gamepad1.dpad_down) {
                isWristSupposedToBeUp = false;
                armdex.wristDown();
            }

            /*
            ========== CODE FOR CONTROLLER 2 ==========
             */

            boolean isRightTriggerPressed = gamepad2.right_trigger > 0.2;
            boolean isBlockPresentInIntake = armdex.isObjectDetectedInIntake();

            if(isBlockPresentInIntake && isWristSupposedToBeUp && overrideIntake) {
                led.setColorGreen();
            }

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
                            led.setColorYellow();
                        } else {
                            armdex.stopIntake();
                        }
                    } else {
                        armdex.stopIntake();
                        isWristSupposedToBeUp = true;
                        armdex.wristUp();
                        led.setColorRed();
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

            if(!isRightTriggerPressed && wasRightTriggerPressedPreviously && !isBlockPresentInIntake) {
                led.setEffectDefault();
            }

            wasRightTriggerPressedPreviously = isRightTriggerPressed;

            // Operate the wrist
            if(gamepad2.y) {
                armdex.wristUp();
                isWristSupposedToBeUp = true;
            } else if(gamepad2.a) {
                isWristSupposedToBeUp = false;
                armdex.wristDown();
            } else if(gamepad2.x || gamepad2.b) {
                if(armdex.isWristUp()) {
                    isWristSupposedToBeUp = false;
                    armdex.wristDown();
                } else if(armdex.isWristDown()) {
                    isWristSupposedToBeUp = true;
                    armdex.wristUp();
                } else {
                    isWristSupposedToBeUp = false;
                    armdex.wristDown();
                }
            }

            // Force block to expel
            if(gamepad2.dpad_up) {
                armdex.setIntakePower(1);
            }

            // Raise the wrist if it's supposed to be up but it's falling down
            if(isWristSupposedToBeUp && armdex.isWristDetectingRed()) {
                armdex.wristUp();
            }
        }
    }

}
