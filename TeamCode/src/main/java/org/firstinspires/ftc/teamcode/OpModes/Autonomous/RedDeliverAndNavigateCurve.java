package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Red Warehouse CURVE")
public class RedDeliverAndNavigateCurve extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        LedController led = new LedController();
        led.init(this, 'r');
        drivetrain.init(this);
        actions.init(this);
        telemetry.addLine("Robot Initialized");
        telemetry.update();
        waitForStart();

        // Rotate from the wall
        actions.rotateLeftFromWall();
        actions.delay(500);

        // Drive to the carousel and deliver the duck
        actions.driveInchesNoPid(20, 0.3);
        actions.deliverDuckAutonomous();

        // Back up from the carousel
        actions.driveInchesNoPid(-46, 0.5);

        // Rotate the robot
        actions.rotateDegrees(-42);

        // Drive backwards while rotated
        actions.driveInchesNoPid(-16, 0.5);

        // Rotate back to a somewhat normal position
        actions.rotateDegrees(47);

        // Reverse into warehouse
        actions.driveInchesNoPid(-57, 0.5);
    }
}
