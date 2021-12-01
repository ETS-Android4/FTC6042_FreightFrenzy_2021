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

        Drivetrain drivetrain = new Drivetrain();
        drivetrain.init(this);
        DeliveryWheel deliveryWheel = new DeliveryWheel();
        deliveryWheel.init(this);
        Armdex armdex = new Armdex();
        armdex.init(this);

        TelemetryWriter output = new TelemetryWriter().setDrivetrain(drivetrain).setDeliveryWheel(deliveryWheel).setArmdex(armdex);
        output.init(telemetry);

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
                    .update();
        }

    }
}
