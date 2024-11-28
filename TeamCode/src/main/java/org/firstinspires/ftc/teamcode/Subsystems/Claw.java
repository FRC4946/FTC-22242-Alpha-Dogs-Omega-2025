package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Constants;

public class Claw extends SubsystemBase {
    private final ServoEx leftClaw;
    private final ServoEx rightClaw;

    public Claw(HardwareMap hardwareMap) {
        leftClaw = hardwareMap.get(ServoEx.class, Constants.ClawConstants.leftClaw);
        rightClaw = hardwareMap.get(ServoEx.class, Constants.ClawConstants.rightClaw);

        leftClaw.setInverted(true);
        rightClaw.setInverted(false);
    }

    public void setClaw(double angle) {
        leftClaw.turnToAngle(angle);
        rightClaw.turnToAngle(angle);
    }

    public double getLeftAngle() {
        return leftClaw.getAngle();
    }

    public double getRightAngle() {
        return rightClaw.getAngle();
    }
}
