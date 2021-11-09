package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Encoder value printout")
public class EncoderValuePrintout extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addLine("Front Left: " + drivetrain.getFrontLeftEncoderValue());
            telemetry.addLine("Front Right: " + drivetrain.getFrontRightEncoderValue());
            telemetry.addLine("Rear Left: " + drivetrain.getRearLeftEncoderValue());
            telemetry.addLine("Rear Right: " + drivetrain.getRearRightEncoderValue());
            telemetry.update();
        }

    }
}
