package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="MotorTest")
public class MotorTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Code to run when opmode is initialized goes here

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        DcMotor rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        Drivetrain drivetrain = new Drivetrain(frontLeft, frontRight, rearLeft, rearRight, this);

        waitForStart();

        drivetrain.driveFrontLeft(0.1);
        sleep(3000);
        drivetrain.stopMotors();
        drivetrain.driveFrontRight(0.1);
        sleep(3000);
        drivetrain.stopMotors();
        drivetrain.driveRearLeft(0.1);
        sleep(3000);
        drivetrain.stopMotors();
        drivetrain.driveRearRight(0.1);
        sleep(3000);
        drivetrain.stopMotors();

    }

}
