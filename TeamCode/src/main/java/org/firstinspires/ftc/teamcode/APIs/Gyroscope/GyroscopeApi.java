package org.firstinspires.ftc.teamcode.APIs.Gyroscope;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class GyroscopeApi {

    private BNO055IMU imu;
    private float xAngle, yAngle, zAngle;
    double xAcceleration, yAcceleration, zAcceleration;

    public GyroscopeApi(HardwareMap hardwareMap) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode    = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        this.imu = hardwareMap.get(BNO055IMU.class, "imu");
        this.imu.initialize(parameters);

        this.xAngle = this.yAngle = this.zAngle = 0;
        this.xAcceleration = this.yAcceleration = this.zAcceleration = 0;
    }

    public void update() {
        // Record angles
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        this.xAngle = orientation.firstAngle;
        this.zAngle = orientation.thirdAngle;

        float rawYValue = orientation.secondAngle;
        float actualYValue = 0;
        if(rawYValue < 0 && rawYValue >= -180) {
            actualYValue = Math.abs(rawYValue);
        } else if(rawYValue <= 180 && rawYValue > 0) {
            actualYValue = 360-rawYValue;
        }

        this.yAngle = Math.round(actualYValue);


        // Record accelerations
        Acceleration acceleration = imu.getAcceleration();
        this.xAcceleration = acceleration.xAccel;
        this.yAcceleration = acceleration.yAccel;
        this.zAcceleration = acceleration.zAccel;
    }



    public float getRawX() {
        return xAngle;
    }

    public float getRawY() {
        return yAngle;
    }

    public float getRawZ() {
        return zAngle;
    }

    public float getStandardizedX() {
        return xAngle%360;
    }

    public float getStandardizedY() {
        return  yAngle%360;
    }

    public float getStandardizedZ() {
        return xAngle%360;
    }

    public double getXAcceleration() {
        return xAcceleration;
    }

    public double getYAcceleration() {
        return yAcceleration;
    }

    public double getZAcceleration() {
        return zAcceleration;
    }
}
