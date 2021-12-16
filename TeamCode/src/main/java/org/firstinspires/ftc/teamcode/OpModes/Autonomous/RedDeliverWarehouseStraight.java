package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Red DWS")
public class RedDeliverWarehouseStraight extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        LedController led = new LedController();
        drivetrain.init(this);
        actions.init(this);
        led.init(this, 'r');
        telemetry.addLine("Robot Initialized");
        telemetry.update();
        waitForStart();

        // Rotate from the wall
        actions.rotateLeftFromWall();
        actions.delay(500);

        // Drive to the carousel and deliver
        actions.driveInchesNoPid(16, 0.3);
        actions.deliverDuckAutonomous();

        // Reverse into the warehouse
        actions.driveInchesNoPid(-110, 0.5);
    }
}
