package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Red-Deliver")
public class DeliveryRedSide extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        AutonomousActions actions = new AutonomousActions();
        actions.init(this);
        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        telemetry.addLine("Init");
        telemetry.update();

        waitForStart();

        drivetrain.driveForwardInchesNoPid(2, 0.5);
        actions.deliverDuck();
        actions.delay(500);
        drivetrain.rotateDegreesNoPid(-10, 0.3);
        drivetrain.driveForwardInchesNoPid(-100, 1);

    }
}
