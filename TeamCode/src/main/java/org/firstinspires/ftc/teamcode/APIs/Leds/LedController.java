package org.firstinspires.ftc.teamcode.APIs.Leds;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class LedController {

    RevBlinkinLedDriver led;
    LinearOpMode opMode;
    final int NONE = -1;
    final int RED = 0;
    final int ORANGE = 1;
    final int YELLOW = 2;
    final int GREEN = 3;
    final int BLUE = 4;
    final int CYAN = 5;
    final int PURPLE = 6;
    final int WHITE = 7;

    int currentColor = NONE;

    /**
     * Initialize this LedController object
     * @param opMode The LinearOpMode this LedController is running in
     */
    public void init(LinearOpMode opMode) {
        this.opMode = opMode;
        led = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "led");
    }

    public void init(LinearOpMode opMode, char alliance) {
        this.opMode = opMode;
        led = opMode.hardwareMap.get(RevBlinkinLedDriver.class, "led");
        if(alliance == 'r') {
            setColorRed();
        } else if(alliance == 'b') {
            setColorBlue();
        }
    }

    /**
     * Set the LEDs to their default effect
     */
    public void setEffectDefault() {
        //TODO make sure this is the same as the current default pattern
        currentColor = NONE;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.COLOR_WAVES_PARTY_PALETTE);
    }

    public void setColorRed() {
        currentColor = RED;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
    }

    public void setColorOrange() {
        currentColor = ORANGE;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
    }

    public void setColorYellow() {
        currentColor = YELLOW;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
    }

    public void setColorGreen() {
        currentColor = GREEN;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
    }

    public void setColorBlue() {
        currentColor = BLUE;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
    }

    public void setColorCyan() {
        currentColor = CYAN;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.SKY_BLUE);
    }

    public void setColorPurple() {
        currentColor = PURPLE;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
    }

    public void setColorWhite() {
        currentColor = WHITE;
        led.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
    }

    /**
     * Switch to the next color in the rainbow
     */
    public void nextColor() {
        switch(currentColor) {
            case NONE:
            case WHITE:
                setColorRed();
                break;
            case RED:
                setColorOrange();
                break;
            case ORANGE:
                setColorYellow();
                break;
            case YELLOW:
                setColorGreen();
                break;
            case GREEN:
                setColorBlue();
                break;
            case BLUE:
                setColorCyan();
                break;
            case CYAN:
                setColorPurple();
                break;
            case PURPLE:
                setColorWhite();
                break;
        }
    }

}
