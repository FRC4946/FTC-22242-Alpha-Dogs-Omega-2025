package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.Constants;

public class ColourSensor {

    private final ColorSensor colourSensor;

    private int gain;

    NormalizedRGBA myNormalizedColors;

    int myColor;
    float hue;
    float saturation;
    float value;

    public ColourSensor(HardwareMap hardwareMap) {
        colourSensor = hardwareMap.get(ColorSensor.class, "colourSensor1");

        gain = Constants.VisionConstants.gain;
        ((NormalizedColorSensor) colourSensor).setGain(gain);
    }

    public float getHue() {
        myNormalizedColors = ((NormalizedColorSensor) colourSensor).getNormalizedColors();
        myColor = myNormalizedColors.toColor();
        hue = JavaUtil.rgbToHue(Color.red(myColor), Color.green(myColor), Color.blue(myColor));
        return hue;
    }

    public float getSaturation() {
        myNormalizedColors = ((NormalizedColorSensor) colourSensor).getNormalizedColors();
        myColor = myNormalizedColors.toColor();
        saturation = JavaUtil.rgbToSaturation(Color.red(myColor), Color.green(myColor), Color.blue(myColor));
        return saturation;
    }

    public float getValue() {
        myNormalizedColors = ((NormalizedColorSensor) colourSensor).getNormalizedColors();
        myColor = myNormalizedColors.toColor();
        value = JavaUtil.rgbToValue(Color.red(myColor), Color.green(myColor), Color.blue(myColor));
        return value;
    }

    public String hasPiece() {
        String colour;
        if ((getHue() < Constants.VisionConstants.blueHue + 10) && (getHue() > Constants.VisionConstants.blueHue - 10)) {
            colour = "Blue";
        } else if ((getHue() < Constants.VisionConstants.redHue + 10) && (getHue() > Constants.VisionConstants.redHue - 10)) {
            colour = "Red";
        } else if ((getHue() < Constants.VisionConstants.yellowHue + 10) && (getHue() > Constants.VisionConstants.yellowHue - 10)) {
            colour = "Yellow";
        } else {
            colour = "Nothing";
        }
        return colour;
    }

}
