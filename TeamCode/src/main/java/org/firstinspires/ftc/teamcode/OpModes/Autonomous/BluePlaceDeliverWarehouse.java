package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue PDW")
public class BluePlaceDeliverWarehouse extends LinearOpMode {

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

        // Drive back to warehouse
        actions.parkInWarehouseFromCarouselBackwards();

        armdex.wristUp();
    }
}
