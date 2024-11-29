package org.firstinspires.ftc.teamcode.Commands;

import org.firstinspires.ftc.robotcore.external.Telemetry;
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

    private ColourSensor s_ColourSensor;

    private GamepadEx gamepad;

    private int phase;
    private intakeStates state;
    private ElapsedTime timer = new ElapsedTime();

    private String alliance;

    private Telemetry telemetry;

    public SmartIntake(Intake s_Intake, Wrist s_Wrist, Extension s_Extension, ColourSensor s_ColourSensor, GamepadEx gamepad, String alliance, Telemetry telemetry) {
        this.s_Intake = s_Intake;
        this.s_Wrist = s_Wrist;
        this.s_Extension = s_Extension;

        this.s_ColourSensor = s_ColourSensor;

        this.gamepad = gamepad;

        this.alliance = alliance;

        this.telemetry = telemetry;

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

        //if (s_ColourSensor.hasPiece().equals("Nothing")) {
            if (gamepad.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                if (state == intakeStates.IDLING) {
                    state = intakeStates.EXTENDING;
                    timer.reset();
                    phase = 0;
                } else if (state == intakeStates.EXTENDING) {
                    state = intakeStates.RETURNING;
                    timer.reset();
                    phase = 0;
                } else {
                    state = intakeStates.EXTENDING;
                    timer.reset();
                    phase = 0;
                }
            }
//        } else {
//            if (s_ColourSensor.hasPiece().equals(alliance) || s_ColourSensor.hasPiece().equals("Yellow")) {
//                state = intakeStates.RETURNING;
//                timer.reset();
//                phase = 0;
//            } else {
//                state = intakeStates.OUTTAKING;
//                timer.reset();
//                phase = 0;
//            }
//        }


        switch (state) {
            case EXTENDING:
                switch (phase) {
                    case 0:
                        if(s_Extension.getAngle() == Constants.ExtensionConstants.extended) {
                            phase = 2;
                        }
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
                        break;
                }
                break;
            case RETURNING:
                switch (phase) {
                    case 0:
                        if (!(s_Extension.getAngle() == Constants.ExtensionConstants.retracted)) {
                            s_Wrist.setAngle(Constants.WristConstants.barAngle);
                            phase += timer.seconds() > 0.4 ? 1 : 0;
                        } else {
                            phase = 2;
                        }
                        break;
                    case 1:
                        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                        phase += timer.seconds() > 1 ? 1 : 0;
                        break;
                    case 2:
                        s_Wrist.setAngle(Constants.WristConstants.transferAngle);
                }
                break;
            case INTAKING:
                switch (phase) {
                    case 0:
                        s_Intake.setIntakePower(1);
                        break;
                }
                break;
            case OUTTAKING:
                switch (phase) {
                    case 0:
                        s_Intake.setIntakePower(-1);
                        phase += timer.seconds() > 1 ? 1 : 0;
                        break;
                    case 1:
                        state = intakeStates.INTAKING;
                        timer.reset();
                        phase = 0;
                        break;
                }
                break;
            case IDLING:
                s_Wrist.setAngle(s_Wrist.getLeftAngle());
                s_Extension.setAngle(s_Extension.getAngle());
                break;
        }

        telemetry.addData("Color", s_ColourSensor.hasPiece());
        telemetry.addData("Alliance: ", alliance);
        telemetry.addData("Alliance Piece", s_ColourSensor.hasPiece().equals(alliance));
        telemetry.update();
    }

    private enum intakeStates {
        INTAKING,
        OUTTAKING,
        EXTENDING,
        RETURNING,
        IDLING
    }
}