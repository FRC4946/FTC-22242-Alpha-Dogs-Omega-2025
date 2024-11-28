package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.JavaUtil;
import org.firstinspires.ftc.teamcode.Constants;

public class ColorSensor {

    private final com.qualcomm.robotcore.hardware.ColorSensor colourSensor;

    private int gain;

    NormalizedRGBA myNormalizedColors;

    int myColor;
    float hue;
    float saturation;
    float value;

    public ColorSensor(HardwareMap hardwareMap) {
        colourSensor = hardwareMap.get(com.qualcomm.robotcore.hardware.ColorSensor.class, "colorSensor1");

        gain = Constants.VisionConstants.gain;
        ((NormalizedColorSensor) colourSensor).setGain(gain);
    }

    public float getColor() {
        myNormalizedColors = ((NormalizedColorSensor) colourSensor).getNormalizedColors();
        myColor = myNormalizedColors.toColor();
        return  JavaUtil.rgbToHue(Color.red(myColor);
    }

}
