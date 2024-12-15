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
    private static intakeStates state;
    private ElapsedTime timer = new ElapsedTime();

    private String alliance;

    private Telemetry telemetry;


    private static  boolean isClawClosed;
    private static boolean isRetracted;
    private static boolean isExchanging;
    private static boolean isRotated;

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
        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
        setState(intakeStates.IDLING);
        isClawClosed = false;
        isExchanging = false;
        isRetracted = true;
        isRotated = false;
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

        if (driver.wasJustPressed(GamepadKeys.Button.LEFT_BUMPER)) {
            isClawClosed = !isClawClosed;
        }

        if (isClawClosed) {
            s_Intake.closeClaw();
        } else {
            s_Intake.openClaw();
        }

        if (isRetracted) {
            s_Extension.setAngle(Constants.ExtensionConstants.retracted);
        } else {
            s_Extension.setAngle(Constants.ExtensionConstants.extended);
        }

        if (isExchanging) {
            s_Intake.setWrist(Constants.WristConstants.transferAngle);
        } else {
            s_Intake.setWrist(Constants.WristConstants.defaultAngle);
        }

        switch (state) {
            case IDLING:
                s_Intake.setRotation(s_Intake.getRotation());
                break;
            case SEARCHING:
                switch (phase) {
                    case 0:
                        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                        if (!isExchanging) {
                            isClawClosed = false;
                            isRetracted = false;
                            phase++;
                        } else {
                            isClawClosed = true;
                            isRetracted = false;
                            isExchanging = false;
                            phase++;
                        }
                        break;
                    case 1:
                        phase += timer.seconds() > 0.6 ? 1 : 0;
                        break;
                    case 2:
                        isClawClosed = false;
                        phase++;
                        break;
                    case 3:
                        if (driver.wasJustPressed(GamepadKeys.Button.DPAD_RIGHT)) {
                            if (s_Intake.getRotation() < 0.28) {
                                s_Intake.setRotation(s_Intake.getRotation() + 0.14);
                            }
                        }

                        if (driver.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                            if (s_Intake.getRotation() > 0) {
                                s_Intake.setRotation(s_Intake.getRotation() - 0.14);
                            }
                        }
                        break;
                }
                break;
            case RETRACTING:
                switch (phase) {
                    case 0:
                        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                        if (isClawClosed) {
                            isExchanging = true;
                            phase += timer.seconds() > 0.35 ? 1 : 0;
                        } else {
                            phase++;
                        }
                        break;
                    case 1:
                        isRetracted = true;
                        phase++;
                    case 2:
                        if (isRetracted) {
                            phase++;
                        } else {
                            isRetracted = true;
                            isExchanging = false;
                            phase += timer.seconds() > 1.5 ? 1 : 0;
                        }
                        break;
                    case 3:
                        s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                        isRetracted = true;
                        if (isExchanging) {
                            setState(intakeStates.EXCHANGING);
                        } else {
                            setState(intakeStates.IDLING);
                        }
                        break;
                }
                break;
            case EXCHANGING:
                switch (phase) {
                    case 0:
                        isClawClosed = true;
                        if(driver.wasJustPressed(GamepadKeys.Button.A)){
                            phase++;
                        }
                        break;
                    case 1:
                        phase += timer.seconds() > 1 ? 1 : 0;
                    case 2:
                        isClawClosed = false;
                        phase++;
                    case 3:
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

    private void setState(intakeStates stateToBe) {
        state = stateToBe;
        timer.reset();
        phase = 0;
    }

    public static boolean isSearching() {
        return state == intakeStates.SEARCHING;
    }

    public static boolean isRetracting() {
        return state == intakeStates.RETRACTING && isClawClosed;
    }
}