package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Red-Rotate and Deliver")
public class RotateAndDeliverRed extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        drivetrain.init(this);
        actions.init(this);
        waitForStart();
        actions.rotateLeftFromWall();
        actions.delay(500);
        actions.driveInchesNoPid(16, 0.3);
        actions.deliverDuck();
        actions.driveInchesNoPid(-100, 0.5);
    }
}
