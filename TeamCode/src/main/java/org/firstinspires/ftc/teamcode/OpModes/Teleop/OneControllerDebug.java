package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="Debug/Test (One Controller)")
public class OneControllerDebug extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        final double NORMAL_DRIVE_MAX_POWER = 0.5;
        final double OVERDRIVE_MAX_POWER = 1;

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        Armdex armdex = new Armdex();
        armdex.init(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel();
        deliveryWheel.init(this);
        LedController led = new LedController();
        TelemetryWriter output =  new TelemetryWriter();
        output.setDrivetrain(drivetrain).setArmdex(armdex).setDeliveryWheel(deliveryWheel).init(telemetry);

        led.init(this);
        output.robotInitialized();
        waitForStart();

        boolean wasIntakeAutoStoppedSinceLastControllerInput = false;

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

            // Calculate our target drivetrain powers
            double leftTargetPower = -drivePowerMultiplier*gamepad1.left_stick_y;
            double rightTargetPower = -drivePowerMultiplier*gamepad1.right_stick_y;

            // Write our target powers to the telemetry
            output.addLine("Left Target Power: " + leftTargetPower).addLine("Right Target Power: " + rightTargetPower);

            // Drive at the target power
            drivetrain.driveAtPower(leftTargetPower, rightTargetPower);

            // Control the delivery mechanism
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

            /*
            Check if the intake needs to be auto stopped. This checks to see if there's a block and if it was already auto stopped
             */
            if(armdex.isObjectDetectedInIntake() && !wasIntakeAutoStoppedSinceLastControllerInput && gamepad1.right_trigger > 0.2) {
                armdex.stopIntake();
                armdex.wristUp();
                wasIntakeAutoStoppedSinceLastControllerInput = true;
            }

            // Control the intake
            if(gamepad1.left_trigger > 0.2) {
                // Eject the freight
                armdex.eject();
                wasIntakeAutoStoppedSinceLastControllerInput = false;
            } else if(gamepad1.right_trigger > 0.2) {
                // Determine where our wrist is
                if(armdex.isWristDown()) {
                    // Intake the freight unless the intake is stopped
                    if (!wasIntakeAutoStoppedSinceLastControllerInput) {
                        armdex.intake();
                    }
                } else if(armdex.isWristUp()) {
                    // Place the block at the normal place speed
                    armdex.place();
                } else {
                    // We're probably in an emergency situation, and the driver expects the intake to run normally.
                    armdex.intake();
                }
            } else {
                // Don't intake anything
                wasIntakeAutoStoppedSinceLastControllerInput = false;
                armdex.stopIntake();
            }

            // Operate the wrist
            if(gamepad1.dpad_up) {
                armdex.wristUp();
            } else if(gamepad1.dpad_down) {
                armdex.wristDown();
            } else {
                armdex.stopWrist();
            }

            // Update the telemetry
            output.update();
        }

    }
}
