package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Tester")
@Disabled
public class ServoTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.get(Servo.class, "servoToTest");

        telemetry.addLine("Initialized");
        waitForStart();

        telemetry.addLine("DPad Up: Position 0");
        telemetry.addLine("DPad Down: Position 1");
        telemetry.addLine("DPad Left or Right: Position 0.5");
        telemetry.update();

        while(opModeIsActive()) {

            if(gamepad1.dpad_up) {
                servo.setPosition(0);
            } else if(gamepad1.dpad_down) {
                servo.setPosition(1);
            } else if(gamepad1.dpad_left || gamepad1.dpad_right) {
                servo.setPosition(0.5);
            }

        }

    }
}
