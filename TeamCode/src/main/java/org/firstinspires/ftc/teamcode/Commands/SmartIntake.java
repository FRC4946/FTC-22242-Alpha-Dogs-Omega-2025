package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.ColourSensor;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SmartIntake extends CommandBase {

    private Intake s_Intake;
    private Wrist s_Wrist;
    private Extension s_Extension;

    private ColourSensor colourSensor;

    private GamepadEx gamepad;

    private int phase;
    private intakeStates state;
    private ElapsedTime timer = new ElapsedTime();

    private String Alliance;

    public SmartIntake(Intake s_Intake, Wrist s_Wrist, Extension s_Extension, ColourSensor colourSensor, GamepadEx gamepad, String alliance) {
        this.s_Intake = s_Intake;
        this.s_Wrist = s_Wrist;
        this.s_Extension = s_Extension;

        this.colourSensor = colourSensor;

        this.gamepad = gamepad;

        addRequirements(s_Intake);
    }

    @Override
    public void initialize() {
        s_Wrist.setAngle(Constants.WristConstants.transferAngle);
        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
        state = intakeStates.IDLING;
        phase = 0;
    }

    @Override
    public void execute() {

        //gamepad.readButtons();

        s_Intake.setIntakePower(gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));

        if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            if (state == intakeStates.IDLING) {
                state = intakeStates.INTAKING;
                timer.reset();
                phase = 0;
            } else if (state == intakeStates.INTAKING) {
                state = intakeStates.RETURNING;
                timer.reset();
                phase = 0;
            } else {
                state = intakeStates.INTAKING;
                timer.reset();
                phase = 0;
            }
        }

        switch (state) {
            case INTAKING:
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
            case RETURNING:
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

    private enum intakeStates {
        INTAKING,
        RETURNING,
        IDLING
    }
}