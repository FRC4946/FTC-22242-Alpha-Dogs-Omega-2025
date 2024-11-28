package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Vision {

    private final ColorSensor colourSensor;

    public Vision(HardwareMap hardwareMap) {
        colourSensor = hardwareMap.get(ColorSensor.class, "colorSensor1");
    }
}
