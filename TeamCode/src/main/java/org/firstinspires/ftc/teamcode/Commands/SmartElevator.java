package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;

public class SmartElevator extends CommandBase {
    private Elevator s_Elevator;
    private Arm s_Arm;
    private Claw s_Claw;

    private GamepadEx driver;
    private GamepadEx operator;

    private final Telemetry telemetry;

    private ElapsedTime timer = new ElapsedTime();

    private int phase;

    private elevatorStates state;

    private int setpoint;

    public SmartElevator(Elevator s_Elevator, Arm s_Arm, Claw s_Claw, GamepadEx driver, GamepadEx operator, Telemetry telemetry) {
        this.s_Elevator = s_Elevator;
        this.s_Arm = s_Arm;
        this.s_Claw = s_Claw;

        this.driver = driver;
        this.operator = operator;

        this.telemetry = telemetry;

        addRequirements(s_Elevator, s_Arm, s_Claw);
    }

    @Override
    public void initialize() {
        setpoint = Constants.ElevatorConstants.exchange;
        s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
        s_Claw.setClaw(Constants.ClawConstants.open);
        s_Elevator.enable();

        state = elevatorStates.IDLE;
        phase = 0;
    }

    @Override
    public void execute() {

        if (operator.wasJustPressed(GamepadKeys.Button.START)) {
            s_Elevator.resetEncoder();
        }

        if(!(operator.isDown(GamepadKeys.Button.DPAD_DOWN))) {
            s_Elevator.setPosition(setpoint);
        }

        if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
            if (state == elevatorStates.PLACE_HIGH) {
                state = elevatorStates.RETRACTING;
                timer.reset();
                phase = 0;
            } else {
                state = elevatorStates.PLACE_HIGH;
                timer.reset();
                phase = 0;
            }
        }

        if (driver.wasJustPressed(GamepadKeys.Button.X)) {
            if (state == elevatorStates.PLACE_LOW) {
                state = elevatorStates.RETRACTING;
                timer.reset();
                phase = 0;
            } else {
                state = elevatorStates.PLACE_LOW;
                timer.reset();
                phase = 0;
            }
        }

        if (driver.wasJustPressed(GamepadKeys.Button.A)) {
            if (s_Claw.getAngle() == Constants.ClawConstants.open) {
                s_Claw.setClaw(Constants.ClawConstants.closed);
            } else {
                s_Claw.setClaw(Constants.ClawConstants.open);
            }
        }

        if (driver.wasJustPressed(GamepadKeys.Button.B)) {
            if (state == elevatorStates.GRAB_SPECIMEN) {
                state = elevatorStates.RETRACTING;
                timer.reset();
                phase = 0;
            } else {
                state = elevatorStates.GRAB_SPECIMEN;
                timer.reset();
                phase = 0;
            }
        }

        if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
            if (state == elevatorStates.RAISE_SPECIMEN) {
                state = elevatorStates.RETRACTING;
                timer.reset();
                phase = 0;
            } else {
                state = elevatorStates.RAISE_SPECIMEN;
                timer.reset();
                phase = 0;
            }
        }

        if (driver.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) {
            if (state == elevatorStates.PLACE_SPECIMEN) {
                state = elevatorStates.RETRACTING;
                timer.reset();
                phase = 0;
            } else {
                state = elevatorStates.PLACE_SPECIMEN;
                timer.reset();
                phase = 0;
            }
        }

        switch (state) {
            case PLACE_HIGH:
                switch (phase) {
                    case 0:
                        if (s_Claw.getAngle() == Constants.ClawConstants.closed) {
                            phase++;
                        }
                        s_Claw.setClaw(Constants.ClawConstants.closed);
                        phase += timer.seconds() > 0.7 ? 1 : 0;
                        break;
                    case 1:
                        if (setpoint == Constants.ElevatorConstants.highBasket) {
                            phase++;
                        }
                        setpoint = Constants.ElevatorConstants.highBasket;
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 2:
                        s_Arm.setAngle(Constants.ArmConstants.dropAngle);
                }
                break;
            case PLACE_LOW:
                switch (phase) {
                    case 0:
                        if (s_Claw.getAngle() == Constants.ClawConstants.closed) {
                            phase++;
                        }
                        s_Claw.setClaw(Constants.ClawConstants.closed);
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 1:
                        if (setpoint == Constants.ElevatorConstants.lowBasket) {
                            phase++;
                        }
                        setpoint = Constants.ElevatorConstants.lowBasket;
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 2:
                        s_Arm.setAngle(Constants.ArmConstants.dropAngle);
                }
                break;
            case GRAB_SPECIMEN:
                switch (phase) {
                    case 0:
                        if (setpoint == Constants.ElevatorConstants.exchange) {
                            phase++;
                        }
                        setpoint = Constants.ElevatorConstants.exchange;
                        phase += timer.seconds() > 0.2 ? 1 : 0;
                    case 1:
                        s_Arm.setAngle(Constants.ArmConstants.grabSpecimenAngle);
                        break;
                }
                break;
            case RAISE_SPECIMEN:
                switch (phase) {
                    case 0:
                        if (setpoint == Constants.ElevatorConstants.exchange) {
                            phase++;
                        }
                        setpoint = Constants.ElevatorConstants.exchange;
                        phase += timer.seconds() > 0.2 ? 1 : 0;
                    case 1:
                        s_Arm.setAngle(Constants.ArmConstants.raiseSpecimenAngle);
                        break;
                }
                break;
            case PLACE_SPECIMEN:
                switch (phase) {
                    case 0:
                        if (setpoint == Constants.ElevatorConstants.exchange) {
                            phase++;
                        }
                        setpoint = Constants.ElevatorConstants.exchange;
                        phase += timer.seconds() > 0.2 ? 1 : 0;
                    case 1:
                        s_Arm.setAngle(Constants.ArmConstants.placeSpecimenAngle);
                        break;
                }
                break;
            case RETRACTING:
                switch (phase) {
                    case 0:
                        if (s_Arm.getAngle() == Constants.ArmConstants.exchangeAngle) {
                            phase++;
                        }
                        s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 1:
                        setpoint = Constants.ElevatorConstants.exchange;
                        if (timer.seconds() > 2) {
                            state = elevatorStates.IDLE;
                        }
                }
                break;
            case IDLE:
                s_Claw.setClaw(s_Claw.getAngle());
                s_Arm.setAngle(s_Arm.getAngle());
        }

        telemetry.addLine("Elevator");
        telemetry.addData("Setpoint", setpoint);
        telemetry.addData("Left Height", s_Elevator.getLeftPosition());
        telemetry.addData("Right Height", s_Elevator.getRightPosition());
        telemetry.addData("State", state);
        telemetry.addLine();
    }

    private enum elevatorStates {
        IDLE,
        PLACE_HIGH,
        PLACE_LOW,
        GRAB_SPECIMEN,
        RAISE_SPECIMEN,
        PLACE_SPECIMEN,
        RETRACTING
    }
}