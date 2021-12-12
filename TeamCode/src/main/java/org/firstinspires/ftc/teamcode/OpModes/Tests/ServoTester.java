package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Servo Tester")
public class ServoTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Servo servo = hardwareMap.get(Servo.class, "servoToTest");

        telemetry.addLine("Initialized");
        waitForStart();

        telemetry.addLine("DPad Up or Right: Position 1");
        telemetry.addLine("DPad Down or Left: Position 0");
        telemetry.update();

        while(opModeIsActive()) {

            if(gamepad1.dpad_up || gamepad1.dpad_right) {
                servo.setPosition(1);
            } else if(gamepad1.dpad_down || gamepad1.dpad_left) {
                servo.setPosition(0);
            }

        }

    }
}
