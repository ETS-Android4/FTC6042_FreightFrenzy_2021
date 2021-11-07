package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Motor Tester")
public class MotorTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Instantiate and initialize our drivetrain
        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        DeliveryWheel delivery = new DeliveryWheel();
        delivery.init(this);

        // Wait for the driver to press play
        waitForStart();

        /*drivetrain.driveFrontLeft(0.1);
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
        drivetrain.stopMotors(); */


        delivery.rotateAtPower(-1);
        sleep(5000);
        delivery.rotateAtPower(0);
    }
}
