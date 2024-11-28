package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Extension extends SubsystemBase {

    ServoEx extension;

    public Extension(HardwareMap hardwareMap) {
        extension = hardwareMap.get(ServoEx.class, Constants.ExtensionConstants.extension);
    }

    public void setAngle(double angle) {
        extension.turnToAngle(angle);
    }
    
    public double getAngle() {
        return extension.getAngle();
    }

}
