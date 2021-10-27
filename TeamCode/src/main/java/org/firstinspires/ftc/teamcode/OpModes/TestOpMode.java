package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="TestOpmode")
public class TestOpMode extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Code to run when opmode is initialized goes here

        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        DcMotor rearLeft = hardwareMap.get(DcMotor.class, "rearLeft");
        DcMotor rearRight = hardwareMap.get(DcMotor.class, "rearRight");

        Drivetrain drive = new Drivetrain(frontLeft, frontRight, rearLeft, rearRight, this);

        waitForStart();

        drive.driveForwardInches(15);
        drive.stopMotors();

    }

}
