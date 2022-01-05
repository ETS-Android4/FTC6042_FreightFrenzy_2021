package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Placer;

@TeleOp(name="Placer Servo Calibration")
public class PlacerServoCalibration extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Placer placer = new Placer(this);

        telemetry.addLine("Robot Initialized.");
        telemetry.addLine("Pressing play will move the placer servo to the straight up position.");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {
            placer.armStraightUp();
            telemetry.addLine("The placer is currently set to be straight up.");
            telemetry.addLine("Move it to that position the calibration is complete");
            telemetry.update();
        }

    }
}
