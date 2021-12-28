package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue RDeDS")
public class BlueRightDetectDeliverStorage extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain(this);
        AutonomousActions actions = new AutonomousActions(this);
        TelemetryWriter output = new TelemetryWriter(telemetry);
        LedController led = new LedController(this, 'b');

        output.robotInitialized();
        waitForStart();

        actions.placeFreightFromRightAndReturn();
        actions.rotateDegrees(85);
        actions.delay(500);

        // Drive to delivery wheel
        drivetrain.driveForwardInchesNoPid(5, 0.5);
        long startTimeInMillis = System.currentTimeMillis();
        while(opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+1600)) {
            drivetrain.driveAtPower(0.2);
        }
        drivetrain.stopMotors();

        // Deliver duck
        actions.deliverDuckAutonomous();

        // Drive backwards
        actions.driveInchesNoPid(-10, 0.5);
        actions.delay(500);

        // Rotate left
        actions.rotateDegrees(-90);
        actions.delay(500);

        // Drive forward
        actions.driveInchesNoPid(14, 0.5);
        actions.delay(500);

        // Rotate right
        actions.rotateDegrees(90);
        actions.delay(500);

        // Park in zone
        actions.driveInchesNoPid(16, 0.5);

    }
}
