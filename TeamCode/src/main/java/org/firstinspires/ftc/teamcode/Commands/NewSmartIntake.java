package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class NewSmartIntake extends CommandBase {

    private Intake s_Intake;
    private Extension s_Extension;

    private GamepadEx driver;
    private GamepadEx operator;

    private int phase;
    private intakeStates state;
    private ElapsedTime timer = new ElapsedTime();

    private String alliance;

    private Telemetry telemetry;


    private boolean hasPieceLogic;

    public NewSmartIntake(Intake s_Intake, Extension s_Extension, GamepadEx driver, GamepadEx operator, String alliance, Telemetry telemetry) {
        this.s_Intake = s_Intake;
        this.s_Extension = s_Extension;

        this.driver = driver;
        this.operator = operator;

        this.alliance = alliance;

        this.telemetry = telemetry;

        addRequirements(s_Intake);
    }

    @Override
    public void initialize() {
        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
        s_Intake.setWrist(Constants.WristConstants.defaultAngle);
        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
        s_Intake.closeClaw();
        setState(intakeStates.IDLING);
        hasPieceLogic = false;
    }

    @Override
    public void execute() {

        if (driver.wasJustPressed(GamepadKeys.Button.RIGHT_BUMPER)) {
            if (state == intakeStates.SEARCHING) {
                setState(intakeStates.RETRACTING);
            } else if (state == intakeStates.IDLING || state == intakeStates.RETRACTING) {
                setState(intakeStates.SEARCHING);
            } else {
                setState(intakeStates.IDLING);
            }
        }

        switch (state) {
            case IDLING:
                if (s_Intake.getClaw() == Constants.IntakeConstants.closed) {
                    s_Intake.closeClaw();
                } else {
                    s_Intake.openClaw();
                }
                s_Intake.setRotation(s_Intake.getRotation());
                s_Intake.setWrist(s_Intake.getWrist());
                s_Extension.setAngle(s_Extension.getAngle());
                break;
            case SEARCHING:

                switch (phase) {
                    case 0:
                        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                        if (s_Intake.getWrist() == Constants.WristConstants.defaultAngle) {
                            phase++;
                        }
                        s_Intake.closeClaw();
                        s_Intake.setWrist(Constants.WristConstants.defaultAngle);
                        phase += timer.seconds() > 0.5 ? 1 : 0;
                        break;
                    case 1:
                        s_Intake.setWrist(Constants.WristConstants.defaultAngle);
                        s_Intake.openClaw();
                        s_Extension.setAngle(Constants.ExtensionConstants.extended);
                        phase++;
                        break;
                    case 2:
                        if (driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
                            if (s_Intake.getClaw() == Constants.IntakeConstants.closed) {
                                s_Intake.openClaw();
                            } else {
                                s_Intake.closeClaw();
                            }
                        }
                        break;
                }
                break;
            case RETRACTING:
                switch (phase) {
                    case 0:
                        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                        if (s_Intake.getClaw() == Constants.IntakeConstants.closed) {
                            s_Intake.setWrist(Constants.WristConstants.transferAngle);
                            phase += timer.seconds() > 0.5 ? 1 : 0;
                        } else {
                            phase++;
                        }
                        break;
                    case 1:
                        if (s_Extension.getAngle() == Constants.ExtensionConstants.retracted) {
                            phase++;
                        } else {
                            s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                            s_Intake.setWrist(Constants.WristConstants.transferAngle);
                            phase += timer.seconds() > 1.5 ? 1 : 0;
                        }
                        break;
                    case 2:
                        s_Intake.closeClaw();
                        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                        s_Intake.setWrist(Constants.WristConstants.transferAngle);
                        s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                        setState(intakeStates.IDLING);
                        break;
                }
                break;
            case EXCHANGING:
                switch (phase) {
                    case 0:
                        s_Intake.openClaw();
                        phase += timer.seconds() > 2 ? 1 : 0;
                        break;
                    case 1:
                        setState(intakeStates.IDLING);
                        break;
                }
                break;
        }

        telemetry.addData("Alliance", alliance);
        telemetry.addLine("Intake");
        telemetry.addData("State", state);
        telemetry.addLine("Vision");
        telemetry.addLine();
    }

    private enum intakeStates {
        IDLING,
        SEARCHING,
        RETRACTING,
        EXCHANGING
    }

    public void setState(intakeStates stateToBe) {
        state = stateToBe;
        timer.reset();
        phase = 0;
    }
}