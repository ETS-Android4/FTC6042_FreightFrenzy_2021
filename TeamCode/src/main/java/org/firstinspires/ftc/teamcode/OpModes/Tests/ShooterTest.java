package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="Shooter Test")
public class ShooterTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        Armdex armdex = new Armdex();
        armdex.init(this);
        TelemetryWriter output = new TelemetryWriter().setDrivetrain(drivetrain).setArmdex(armdex).init(telemetry);

        output.robotInitialized();

        waitForStart();

        while(opModeIsActive()) {

            if(gamepad2.right_trigger > 0.2) {
                armdex.setIntakePower(1);
            } else if(gamepad2.left_trigger > 0.2) {
                armdex.setIntakePower(-1);
            } else {
                armdex.stopIntake();
            }

            if(Math.abs(gamepad2.right_stick_y) > 0.1) {
                armdex.setWristPower(-gamepad2.right_stick_y);
            } else {
                armdex.stopWrist();
            }

        }

    }
}
