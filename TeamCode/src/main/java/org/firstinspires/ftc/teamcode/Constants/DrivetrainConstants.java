package org.firstinspires.ftc.teamcode.Constants;

public class DrivetrainConstants {

    public static final double defaultPower = 0.2;

    public static final double driveWheelDiameterInInches = 4.75;

    public static final double driveWheelCircumferenceInInches = 3.14*driveWheelDiameterInInches;

    // How many rotations on the output shaft of the motor we need to get the wheel to move on rotation
    public static final double motorToWheelRatio = 0.5;

    public static final double encoderTicksPerWheelRotation = 560;

    // PID related values
    public static final double driveTrainPGain = 0;
    public static final double driveTrainIGain = 0;
    public static final double driveTrainDGain = 0;
    public static final double driveTrainPidDeadZoneInTicks = 2;

}
