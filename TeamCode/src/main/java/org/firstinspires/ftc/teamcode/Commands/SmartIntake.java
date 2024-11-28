package org.firstinspires.ftc.teamcode.Commands;


import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

public class SmartIntake extends CommandBase {

    Intake s_Intake;
    Wrist s_Wrist;
    Extension s_Extension;

    GamepadEx gamepad;

    private int phase;

    public SmartIntake(Intake s_Intake, Wrist s_Wrist, Extension s_Extension, GamepadEx gamepad) {
        this.s_Intake = s_Intake;
        this.s_Wrist = s_Wrist;
        this.s_Extension = s_Extension;

        this.gamepad = gamepad;

        addRequirements(s_Intake, s_Wrist, s_Extension);
    }

    @Override
    public void initialize() {
        phase = 0;
    }

    @Override
    public void execute() {

        s_Intake.setIntakePower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));


    }
}
