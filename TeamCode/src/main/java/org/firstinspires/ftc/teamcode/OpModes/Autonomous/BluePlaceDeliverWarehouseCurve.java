package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue PDWC")
public class BluePlaceDeliverWarehouseCurve extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Drivetrain drivetrain = new Drivetrain(this);
        Armdex armdex = new Armdex(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel(this);
        AutonomousActions actions = new AutonomousActions(this);
        TelemetryWriter output = new TelemetryWriter(telemetry).setDrivetrain(drivetrain).setArmdex(armdex).setDeliveryWheel(deliveryWheel);
        LedController led = new LedController(this, 'b');
        output.robotInitialized();

        waitForStart();

        // Place block
        actions.placeFreightStraightFromWall();
        actions.delay(500);

        // Back into wall
        actions.driveInchesNoPid(-20, 0.4);
        actions.delay(500);

        actions.rotateRightFromWall();
        actions.delay(500);

        // Drive to wheel
        drivetrain.driveForwardInchesNoPid(19.5, 0.5);
        long startTimeInMillis = System.currentTimeMillis();
        while(opModeIsActive() && (System.currentTimeMillis() < startTimeInMillis+2000)) {
            drivetrain.driveAtPower(0.2);
        }
        drivetrain.stopMotors();

        // Deliver
        actions.deliverDuckAutonomous();
        actions.delay(1000);

        // Back up from carousel
        actions.driveInchesNoPid(-46, 0.5);

        // Rotate once
        actions.rotateDegrees(42);

        // Drive a bit while rotated
        actions.driveInchesNoPid(-15, 0.5);

        // Rotate back to a close to normal position
        actions.rotateDegrees(-47);

        // Raise wrist
        armdex.wristUp();

        // Back into the warehouse
        actions.driveInchesNoPid(-57, 0.5);
    }
}
