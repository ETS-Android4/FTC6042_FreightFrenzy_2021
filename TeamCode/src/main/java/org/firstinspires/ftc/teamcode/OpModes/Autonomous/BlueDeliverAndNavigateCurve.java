package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.source.doctree.StartElementTree;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue Warehouse CURVE")
public class BlueDeliverAndNavigateCurve extends LinearOpMode {

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

        // Rotate from wall
        actions.rotateRightFromWall();
        actions.delay(500);

        // Drive to carousel and deliver
        actions.driveInchesNoPid(20, 0.3);
        actions.deliverDuck();

        // Back up from carousel
        actions.driveInchesNoPid(-43, 0.5);

        // Rotate once
        actions.rotateDegrees(42);

        // Drive a bit while rotated
        actions.driveInchesNoPid(-16, 0.5);

        // Rotate back to a close to normal position
        actions.rotateDegrees(-50);

        // Back into the warehouse
        actions.driveInchesNoPid(-53, 0.5);
    }
}
