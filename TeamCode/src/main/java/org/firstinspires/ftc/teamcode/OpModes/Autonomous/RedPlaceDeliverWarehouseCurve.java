package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

public class RedPlaceDeliverWarehouseCurve extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain(this);
        Armdex armdex = new Armdex(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel(this);
        AutonomousActions actions = new AutonomousActions(this);
        LedController led = new LedController(this, 'r');
        TelemetryWriter output = new TelemetryWriter(telemetry).setDrivetrain(drivetrain).setArmdex(armdex).setDeliveryWheel(deliveryWheel);
        output.robotInitialized();

        waitForStart();

        // Place block
        actions.dropFreightOnLevelOne();
        actions.delay(500);

        // Back into wall
        actions.driveInchesNoPid(-20, 0.4);
        actions.delay(500);

        actions.rotateLeftFromWall();
        actions.delay(500);

        // Drive to wheel
        drivetrain.driveForwardInchesNoPid(18, 0.5);
        long startTimeInMillis = System.currentTimeMillis();
        while(opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+1500)) {
            drivetrain.driveAtPower(0.2);
        }
        drivetrain.stopMotors();

        // Deliver
        actions.deliverDuckAutonomous();
        actions.delay(1000);

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
