package org.firstinspires.ftc.teamcode.ActionThreads;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Armdex;

public class WristDownAction extends Thread {

    Armdex armdex;
    LinearOpMode opMode;

    public WristDownAction(Armdex armdex, LinearOpMode opMode) {
        this.armdex = armdex;
        this.opMode = opMode;
    }

    public void run() {
        // Run the wrist until it reaches the correct position or until the opmode is disabled
        while(opMode.opModeIsActive() && !armdex.isWristDown()) {
            armdex.runWristDown();
        }
        armdex.stopWrist();
    }

}
