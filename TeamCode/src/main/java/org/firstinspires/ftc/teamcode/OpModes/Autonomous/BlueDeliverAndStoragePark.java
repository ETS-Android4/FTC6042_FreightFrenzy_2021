package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue STORAGE")
public class BlueDeliverAndStoragePark extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        drivetrain.init(this);
        actions.init(this);
        LedController led = new LedController();
        led.init(this, 'b');
        telemetry.addLine("Robot Initialized");
        telemetry.update();

        waitForStart();

        // Rotate from wall
        actions.rotateRightFromWall();
        actions.delay(500);

        // Drive to carousel and deliver
        actions.driveInchesNoPid(20, 0.3);
        actions.deliverDuck();

        // Drive backwards
        actions.driveInchesNoPid(-8, 0.5);
        actions.delay(500);

        // Rotate a little bit
        actions.rotateDegrees(-90);
        actions.delay(500);

        // Drive while rotated
        actions.driveInchesNoPid(16, 0.5);
        actions.delay(500);

        // Rotate back to a close to normal position
        actions.rotateDegrees(90);
        actions.delay(500);

        // Back into warehouse
        actions.driveInchesNoPid(20, 0.5);
    }
}
