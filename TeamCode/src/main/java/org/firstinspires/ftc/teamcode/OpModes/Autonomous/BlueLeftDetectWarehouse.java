package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.Placer;

@Autonomous(name="1. Blue LDeW")
public class BlueLeftDetectWarehouse extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        AutonomousActions actions = new AutonomousActions(this);
        Placer placer = new Placer(this);
        Armdex armdex = new Armdex(this);
        LedController led = new LedController(this, 'b');

        waitForStart();

        placer.armStraightUp();
        actions.delay(100);
        armdex.wristUp();

        actions.placeFreightFromLeftAndReturn();
        led.setColorBlue();
        actions.rotateDegrees(90);
        actions.driveInchesNoPid(50, 0.4);

    }
}
