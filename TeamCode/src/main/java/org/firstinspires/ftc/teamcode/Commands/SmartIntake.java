package org.firstinspires.ftc.teamcode.Commands;


import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SmartIntake extends CommandBase {

    Intake s_Intake;
    Wrist s_Wrist;
    Extension s_Extension;

    GamepadEx gamepad;

    Button intake;

    private int phase;
    private States state;

    private ElapsedTime timer = new ElapsedTime();

    public SmartIntake(Intake s_Intake, Wrist s_Wrist, Extension s_Extension, GamepadEx gamepad) {
        this.s_Intake = s_Intake;
        this.s_Wrist = s_Wrist;
        this.s_Extension = s_Extension;

        this.gamepad = gamepad;

        addRequirements(s_Intake, s_Wrist, s_Extension);
    }

    @Override
    public void initialize() {
        s_Wrist.setAngle(Constants.WristConstants.transferAngle);
        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
        phase = 0;
        state = States.IDLING;
    }

    @Override
    public void execute() {

        gamepad.readButtons();

        s_Intake.setIntakePower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            phase = 0;
            timer.reset();
            state = States.EXTENDING;
        } else if (gamepad.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            phase = 0;
            timer.reset();
            state = States.RETRACTING;
        }

        switch (state) {
            case EXTENDING:
                switch (phase) {
                    case 0:
                        s_Wrist.setAngle(Constants.WristConstants.barAngle);
                        phase += timer.seconds() > 0.5 ? 1 : 0;
                        break;
                    case 1:
                        s_Wrist.setAngle(Constants.WristConstants.barAngle);
                        s_Extension.setAngle(Constants.ExtensionConstants.clearBar);
                        phase += timer.seconds() > 1 ? 1 : 0;
                        break;
                    case 2:
                        s_Wrist.setAngle(Constants.WristConstants.transferAngle);
                        s_Extension.setAngle(Constants.ExtensionConstants.extended);
                        s_Intake.setIntakePower(1);
                        break;
                }
                break;
            case RETRACTING:
                switch (phase) {
                    case 0:
                        s_Wrist.setAngle(Constants.WristConstants.barAngle);
                        phase += timer.seconds() > 0.4 ? 1 : 0;
                        break;
                    case 1:
                        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                        phase += timer.seconds() > 1 ? 1 : 0;
                        break;
                    case 2:
                        s_Wrist.setAngle(Constants.WristConstants.transferAngle);

                }
                break;
        }

    }

    private enum States {
        EXTENDING,
        RETRACTING,
        IDLING
    }
}
