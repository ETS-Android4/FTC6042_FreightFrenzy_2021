package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Red STORAGE")
public class RedDeliverAndStoragePark extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        LedController led = new LedController();
        led.init(this, 'r');
        drivetrain.init(this);
        actions.init(this);
        waitForStart();

        // Rotate from wall
        actions.rotateLeftFromWall();
        actions.delay(500);

        // Drive to carousel
        actions.driveInchesNoPid(16, 0.3);

        // Deliver
        actions.deliverDuckAutonomous();

        // Drive backwards
        actions.driveInchesNoPid(-10, 0.5);
        actions.delay(500);

        // Rotate right
        actions.rotateDegrees(90);
        actions.delay(500);

        // Drive forward
        actions.driveInchesNoPid(14, 0.5);
        actions.delay(500);

        // Rotate left
        actions.rotateDegrees(-90);
        actions.delay(500);

        // Park in zone
        actions.driveInchesNoPid(16, 0.5);

    }
}
