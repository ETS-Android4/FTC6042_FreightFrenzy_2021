package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue Place Deliver Storage Park")
public class BluePlaceDeliverStoragePark extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        Armdex armdex = new Armdex();
        armdex.init(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel();
        deliveryWheel.init(this);
        AutonomousActions actions = new AutonomousActions();
        actions.init(this);
        TelemetryWriter output = new TelemetryWriter().setDrivetrain(drivetrain).setArmdex(armdex).setDeliveryWheel(deliveryWheel);
        LedController led = new LedController();
        led.init(this, 'b');
        output.init(telemetry);
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
