package org.firstinspires.ftc.teamcode.OpModes.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.APIs.TelemetryWriter;
import org.firstinspires.ftc.teamcode.mechanisms.Armdex;
import org.firstinspires.ftc.teamcode.mechanisms.DeliveryWheel;
import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

@TeleOp(name="Encoder/Sensor Value Printout")
public class EncoderAndSensorValuePrintout extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        Drivetrain drivetrain = new Drivetrain(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel(this);
        Armdex armdex = new Armdex(this);

        TelemetryWriter output = new TelemetryWriter(telemetry).setDrivetrain(drivetrain).setDeliveryWheel(deliveryWheel).setArmdex(armdex);

        output.robotInitialized();

        waitForStart();

        while(opModeIsActive()) {
            output
                    .addAllDriveEncoders()
                    .addGyroValues()
                    .addDeliveryEncoders()
                    .addAllArmdexEncoders()
                    .addIntakeSensorValues()
                    .addWristSensorValues()
                    .addIsWristUp()
                    .addIsWristDown()
                    .addAllDistanceSensors()
                    .addAverageDistances()
                    .update();
        }

    }
}
