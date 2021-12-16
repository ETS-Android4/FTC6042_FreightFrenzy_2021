package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue DWS")
public class BlueDeliverWarehouseStraight extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        LedController led = new LedController();
        led.init(this, 'b');
        drivetrain.init(this);
        actions.init(this);
        telemetry.addLine("Robot Initialized");
        telemetry.update();

        waitForStart();

        // Rotate from the wall
        actions.rotateRightFromWall();
        actions.delay(500);

        // Drive to the carousel and deliver
        actions.driveInchesNoPid(22, 0.3);
        actions.deliverDuckAutonomous();

        // Drive backwards into the warehouse
        actions.driveInchesNoPid(-110, 0.5);

    }
}
