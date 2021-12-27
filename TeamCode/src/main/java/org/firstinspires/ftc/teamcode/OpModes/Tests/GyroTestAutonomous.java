package org.firstinspires.ftc.teamcode.OpModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Drive and rotate")
@Disabled
public class GyroTestAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain(this);

        waitForStart();

        drivetrain.driveForwardInches(12);

        sleep(1000);

        drivetrain.rotateDegreesNoPid(90, 0.3);

    }
}
