package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="Encoder value printout")
public class EncoderValuePrintout extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel();
        deliveryWheel.init(this);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addLine("Front Left: " + drivetrain.getFrontLeftEncoderValue());
            telemetry.addLine("Front Right: " + drivetrain.getFrontRightEncoderValue());
            telemetry.addLine("Rear Left: " + drivetrain.getRearLeftEncoderValue());
            telemetry.addLine("Rear Right: " + drivetrain.getRearRightEncoderValue());
            telemetry.addLine("Delivery Left: " + deliveryWheel.getLeftPosition());
            telemetry.addLine("Delivery Right: " + deliveryWheel.getRightPosition());
            telemetry.update();
        }

    }
}
