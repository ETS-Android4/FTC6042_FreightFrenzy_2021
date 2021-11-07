package org.firstinspires.ftc.teamcode.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;

@Autonomous(name="Delivery Wheel Tester")
public class DeliveryWheelTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DeliveryWheel deliveryWheel = new DeliveryWheel();
        deliveryWheel.init(this);
        telemetry.addLine("Robot initialized");
        telemetry.update();
        waitForStart();
        deliveryWheel.rotateNumberOfCarouselRotations(1.5, 0.5);
    }
}
