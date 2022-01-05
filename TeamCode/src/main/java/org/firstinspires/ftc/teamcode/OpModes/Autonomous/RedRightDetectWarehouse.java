package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.APIs.AutonomousActions;
import org.firstinspires.ftc.teamcode.APIs.Leds.LedController;

@Autonomous(name="Red RDeW")
public class RedRightDetectWarehouse extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        AutonomousActions actions = new AutonomousActions(this);
        LedController led = new LedController(this, 'r');

        waitForStart();

        actions.placeFreightFromRightAndReturn();
        led.setColorRed();
        actions.rotateRightFromWall();
        actions.driveInchesNoPid(50, 0.4);

    }
}
