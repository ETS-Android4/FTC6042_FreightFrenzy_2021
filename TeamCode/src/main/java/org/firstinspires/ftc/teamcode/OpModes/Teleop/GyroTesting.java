package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.Gyroscope.GyroscopeApi;

@TeleOp(name="Gyro Value Printout")
public class GyroTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        GyroscopeApi gyro = new GyroscopeApi(hardwareMap);
        telemetry.addLine("Robot initialized");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {
            gyro.update();
            telemetry.addLine("X: " + gyro.getRawX());
            telemetry.addLine("Y: " + gyro.getRawY());
            telemetry.addLine("Z: " + gyro.getRawZ());
            telemetry.update();
        }
    }

}