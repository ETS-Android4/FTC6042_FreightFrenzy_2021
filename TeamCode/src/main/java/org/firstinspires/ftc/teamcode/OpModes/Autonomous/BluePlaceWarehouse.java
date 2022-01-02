package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Blue PW")
public class BluePlaceWarehouse extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain(this);
        AutonomousActions actions = new AutonomousActions(this);
        TelemetryWriter output = new TelemetryWriter(telemetry);
        LedController led = new LedController(this, 'b');

        output.robotInitialized();
        waitForStart();

        actions.dropFreightOnLevelOne();

        actions.driveInchesNoPid(-20, 0.4);
        actions.delay(1000);

        drivetrain.driveForwardInchesNoPid(3.5, 0.4);
        drivetrain.rotateDegreesNoPid(-72, 0.2);

        actions.driveInchesNoPid(67, 0.5);

    }
}
