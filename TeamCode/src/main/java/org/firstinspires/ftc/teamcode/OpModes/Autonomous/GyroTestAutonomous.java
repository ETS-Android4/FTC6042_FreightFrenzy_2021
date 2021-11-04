package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Drive and rotate")
public class GyroTestAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);

        waitForStart();

        drivetrain.driveForwardInches(12);

        sleep(1000);

        drivetrain.rotateDegreesNoPid(90, 0.3);

    }
}
