package org.firstinspires.ftc.teamcode.APIs.PID;

/**
 * This class consists of a math API for a PID controller.
 *
 * The P (proportional), I (integral), and D (derivative) weights must be included in the
 * constructor when instantiating an object of this class, but can be modified at any time with
 * their respective methods.
 *
 * For more information on the PID controller, including the best way to tune it, please see
 * https://gm0.org/en/latest/docs/software/control-loops.html (we are in no way affiliated with
 * Game Manual 0, their article is just really useful).
 */
public class PidApi {

    double pGain = 0; // The P gain for this object
    double iGain = 0; // The I gain for this object
    double dGain = 0; // The D gain for ths object
    double previousError = 0; // The error from the previous loop
    double previousTimeInMillis = 0; // The UTC time at which the previous loop was run, in milliseconds
    double output; // The output of the most recently run loop

    double iMax = 1; // The maximum I value

    /**
     * Instantiate a new PID object
     * @param pGain The P gain for this new PID object
     * @param iGain The I gain for this new PID object
     * @param dGain The D gain for this new PID object
     */
    public PidApi(double pGain, double iGain, double dGain) {
        this.pGain = pGain;
        this.iGain = iGain;
        this.dGain = dGain;
    }

    /**
     * Calculate the output of the control loop
     * @param currentPosition The current position
     * @param targetPosition The target position
     */
    private void calculate(double currentPosition, double targetPosition) {
        // Create our P, I, and D values for this loop
        double p = 0;
        double i = 0;
        double d = 0;

        // Determine the change in time (UTC in milliseconds) since the last time this method was run
        double currentTimeInMillis = System.currentTimeMillis();
        double currentError = targetPosition-currentPosition;

        // Calculate our final P value
        p = pGain*currentError;

        // Calculate our final I value
        i += iGain*(currentError*(currentTimeInMillis-previousTimeInMillis));
        if(i > iMax) {
            i = iMax;
        } else if(i < -iMax) {
            i = -iMax;
        }

        // Calculate our final D value
        d = dGain*(currentError-previousError)/(currentTimeInMillis-previousTimeInMillis);

        // Sum the P, I, and D values to find out output
        output = p+i+d;

        // Set variables to be used for the next run of this method
        previousError = currentError;
        previousTimeInMillis = currentTimeInMillis;
    }

    /**
     * Calculate and return the output of the control loop based on the current and target positions
     * @param currentPosition The current position
     * @param targetPosition The target position
     * @return The control loop output
     */
    public double getControlLoopOutput(double currentPosition, double targetPosition) {
        calculate(currentPosition, targetPosition);
        return output;
    }

    /**
     * Calculate and return a limited output of the control loop based on the current and target positions
     * @param currentPosition The current position
     * @param targetPosition The target position
     * @param minValue The minimum value that the output can be
     * @param maxValue The maximum value that the output can be
     * @return The control loop output, but limited by the minimum and maximum values provided
     */
    public double getLimitedControlLoopOutput(double currentPosition, double targetPosition, double minValue, double maxValue) {
        calculate(currentPosition, targetPosition);
        if(output > maxValue) {
            return maxValue;
        } else if(output < minValue) {
            return minValue;
        } else {
            return output;
        }
    }

    /**
     * Calculate and return a limited output of the control loop based on the current and target positions
     * @param currentPosition The current position
     * @param targetPosition The target position
     * @param minAndMaxValue The value to be both added and subtracted from zero, which will create the minimum and maximum output values
     * @return The control loop output, but limited by the minimum and maximum values based on the value provided
     */
    public double getLimitedControlLoopOutput(double currentPosition, double targetPosition, double minAndMaxValue) {
        // If the value entered is negative, flip it to be positive
        if(minAndMaxValue < 0) {
            minAndMaxValue *= -1;
        }
        calculate(currentPosition, targetPosition);
        if(output > minAndMaxValue) {
            return minAndMaxValue;
        } else if(output < -minAndMaxValue) {
            return -minAndMaxValue;
        } else {
            return output;
        }
    }

    /**
     * Gets the previous control loop output
     * @return The output of the previous control loop
     */
    public double getPreviousOutput() {
        return output;
    }

    /**
     * Update the P gain
     * @param newPGain The new P gain
     */
    public void updatePGain(double newPGain) {
        pGain = newPGain;
    }

    /**
     * Update the I gain
     * @param newIGain The new I gain
     */
    public void updateIGain(double newIGain) {
        iGain = newIGain;
    }

    /**
     * Update the D gain
     * @param newDGain The new D gain
     */
    public void updateDGain(double newDGain) {
        dGain = newDGain;
    }

    /**
     * Get the current P gain
     * @return The current P gain
     */
    public double getPGain() {
        return pGain;
    }

    /**
     * Get the current I gain
     * @return The current I gain
     */
    public double getIGain() {
        return iGain;
    }

    /**
     * Get the current D gain
     * @return The current D gain
     */
    public double getDGain() {
        return dGain;
    }

}
