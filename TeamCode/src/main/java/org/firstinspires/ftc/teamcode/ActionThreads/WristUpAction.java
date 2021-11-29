package org.firstinspires.ftc.teamcode.ActionThreads;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Armdex;

public class WristUpAction extends Thread {

    Armdex armdex;
    LinearOpMode opMode;

    public WristUpAction(Armdex armdex, LinearOpMode opMode) {
        this.armdex = armdex;
        this.opMode = opMode;
    }

    public void run() {
        // Run the wrist until it reaches the correct position or the opmode is disabled
        while(opMode.opModeIsActive() && !armdex.isWristUp()) {
            armdex.runWristUp();
        }
        armdex.stopWrist();
    }

}
