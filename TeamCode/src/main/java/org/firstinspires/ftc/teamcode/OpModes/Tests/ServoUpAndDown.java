package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Up and Down")
public class ServoUpAndDown extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        double amountToIncrement = 0.1;
        double defaultPosition = 0;

        Servo servo = hardwareMap.get(Servo.class, "servoToTest");

        waitForStart();

        servo.setPosition(defaultPosition);
        double targetPosition = defaultPosition;
        boolean wasUpPreviouslyPressed = false;
        boolean wasDownPreviouslyPressed = false;

        while(opModeIsActive()) {
            if(gamepad1.dpad_up) {
                if(!wasUpPreviouslyPressed) {
                    wasUpPreviouslyPressed = true;
                    if(targetPosition+amountToIncrement <= 1) {
                        targetPosition = targetPosition + amountToIncrement;
                    }
                }
            } else {
                wasUpPreviouslyPressed = false;
            }

            if(gamepad1.dpad_down) {
                if(!wasDownPreviouslyPressed) {
                    wasDownPreviouslyPressed = true;
                    if(targetPosition-amountToIncrement >= 0) {
                        targetPosition = targetPosition-amountToIncrement;
                    }
                }
            } else {
                wasDownPreviouslyPressed = false;
            }
            servo.setPosition(targetPosition);
            telemetry.addLine("Target Position " + targetPosition);
            telemetry.addLine("Current Position: " + servo.getPosition());
            telemetry.update();
        }

    }
}
