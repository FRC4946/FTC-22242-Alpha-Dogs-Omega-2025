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

    private GamepadEx driver;
    private GamepadEx operator;

    private int phase;
    private intakeStates state;
    private ElapsedTime timer = new ElapsedTime();

    private String alliance;

    private Telemetry telemetry;

    private double intakeSpeed;

    private boolean hasPieceLogic;

    public SmartIntake(Intake s_Intake, Wrist s_Wrist, Extension s_Extension, ColourSensor s_ColourSensor, GamepadEx driver, GamepadEx operator, String alliance, Telemetry telemetry) {
        this.s_Intake = s_Intake;
        this.s_Wrist = s_Wrist;
        this.s_Extension = s_Extension;

        this.s_ColourSensor = s_ColourSensor;

        this.driver = driver;
        this.operator = operator;

        this.alliance = alliance;

        this.telemetry = telemetry;

        addRequirements(s_Intake);
    }

    @Override
    public void initialize() {
        s_Wrist.setAngle(Constants.WristConstants.transferAngle);
        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
        setState(intakeStates.IDLING);
        hasPieceLogic = false;
    }

    @Override
    public void execute() {

        if (!state.equals(intakeStates.PLACING)) {
            if (operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) != 0) {
                s_Intake.setIntakePower(operator.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - operator.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
            } else {
                s_Intake.setIntakePower(driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) - driver.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER));
            }
        }

        if (operator.isDown(GamepadKeys.Button.RIGHT_BUMPER)) {
            s_Intake.setTopPoewr(1);
        } else if (operator.isDown(GamepadKeys.Button.LEFT_BUMPER)) {
            s_Intake.setTopPoewr(-1);
        }

        if (driver.wasJustPressed(GamepadKeys.Button.X) || driver.wasJustPressed(GamepadKeys.Button.Y)) {
            setState(intakeStates.PLACING);
        }


        if (s_ColourSensor.checkPiece().equals("Nothing")) {

            if (hasPieceLogic && !s_ColourSensor.hasPiece()) {
                hasPieceLogic = false;
                setState(intakeStates.IDLING);
            }

            if (driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
                setState(intakeStates.EXTENDING);
            }

            if (driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
                setState(intakeStates.RETURNING);
            }
        } else {

            if (s_ColourSensor.hasAlliancePiece()) {
                if (s_Extension.getAngle() == Constants.ExtensionConstants.retracted) {
                    setState(intakeStates.TRANSFERRING);
                } else {
                    if (!hasPieceLogic) {
                        hasPieceLogic = true;
                        setState(intakeStates.RETURNING);
                    }
                }
            } else {
                setState(intakeStates.OUTTAKING);
            }
        }

        switch (state) {
            case EXTENDING:
                switch (phase) {
                    case 0:
                        if (s_Extension.getAngle() == Constants.ExtensionConstants.extended) {
                            phase = 2;
                        }
                        s_Wrist.setAngle(Constants.WristConstants.barAngle);
                        phase += timer.seconds() > 0.5 ? 1 : 0;
                        break;
                    case 1:
                        s_Wrist.setAngle(Constants.WristConstants.barAngle);
                        s_Extension.setAngle(Constants.ExtensionConstants.clearBar);
                        phase += timer.seconds() > 0.8 ? 1 : 0;
                        break;
                    case 2:
                        s_Wrist.setAngle(Constants.WristConstants.intakeAngle);
                        s_Extension.setAngle(Constants.ExtensionConstants.clearBar);
                        phase += timer.seconds() > 1.6 ? 1 : 0;
                        break;
                    case 3:
                        s_Wrist.setAngle(Constants.WristConstants.intakeAngle);
                        s_Extension.setAngle(Constants.ExtensionConstants.extended);
                        setState(intakeStates.INTAKING);
                        break;
                }
                break;
            case RETURNING:
                switch (phase) {
                    case 0:
                        if ((s_Extension.getAngle() == Constants.ExtensionConstants.retracted)) {
                            phase = 2;
                        }
                        s_Wrist.setAngle(Constants.WristConstants.barAngle);
                        phase += timer.seconds() > 0.4 ? 1 : 0;
                        break;
                    case 1:
                        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                        phase += timer.seconds() > 1 ? 1 : 0;
                        break;
                    case 2:
                        s_Wrist.setAngle(Constants.WristConstants.transferAngle);
                        if (s_ColourSensor.hasAlliancePiece()) {
                            setState(intakeStates.TRANSFERRING);
                        } else {
                            setState(intakeStates.IDLING);
                        }
                        break;
                }
                break;
            case INTAKING:
                s_Intake.setIntakePower(1);
                break;
            case OUTTAKING:
                switch (phase) {
                    case 0:
                        s_Intake.setIntakePower(-4);
                        phase += timer.seconds() > 1 ? 1 : 0;
                        break;
                    case 1:
                        setState(intakeStates.INTAKING);
                        break;
                }
                break;
            case IDLING:
                s_Wrist.setAngle(s_Wrist.getLeftAngle());
                s_Extension.setAngle(s_Extension.getAngle());
                break;
            case TRANSFERRING:
                s_Wrist.setAngle(Constants.WristConstants.transferAngle);
                s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                break;
            case PLACING:
                switch (phase) {
                    case 0:
                        s_Intake.setIntakePower(1);
                        phase += timer.seconds() > 0.5 ? 1 : 0;
                        break;
                    case 1:
                        setState(intakeStates.IDLING);
                        break;
                }

        }

        telemetry.addData("Alliance", alliance);
        telemetry.addLine("Intake");
        telemetry.addData("State", state);
        telemetry.addLine("Vision");
        telemetry.addData("Color", s_ColourSensor.checkPiece());
        telemetry.addData("Alliance Piece", s_ColourSensor.hasAlliancePiece());
        telemetry.addData("Hue", s_ColourSensor.getHue());
        telemetry.addLine();
        //telemetry.update();
    }

    private enum intakeStates {
        INTAKING,
        OUTTAKING,
        EXTENDING,
        RETURNING,
        IDLING,
        TRANSFERRING,
        PLACING
    }

    public void setState(intakeStates stateToBe) {
        state = stateToBe;
        timer.reset();
        phase = 0;
    }
}