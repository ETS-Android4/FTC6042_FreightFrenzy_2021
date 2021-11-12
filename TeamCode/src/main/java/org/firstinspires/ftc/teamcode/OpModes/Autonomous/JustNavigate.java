package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@Autonomous(name="Navigate Only")
public class JustNavigate extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        LedController led = new LedController();
        Drivetrain drivetrain = new Drivetrain();
        AutonomousActions actions = new AutonomousActions();
        led.init(this);
        drivetrain.init(this);
        actions.init(this);

        telemetry.addLine("Robot Initialized");
        telemetry.update();

        waitForStart();

        drivetrain.driveForwardInchesNoPid(40, 0.75);

    }
}
