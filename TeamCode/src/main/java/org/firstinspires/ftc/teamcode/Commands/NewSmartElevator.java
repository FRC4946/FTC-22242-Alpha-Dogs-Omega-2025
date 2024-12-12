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

public class NewSmartElevator extends CommandBase {
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
    private boolean openClaw;

    public NewSmartElevator(Elevator s_Elevator, Arm s_Arm, Claw s_Claw, GamepadEx driver, GamepadEx operator, Telemetry telemetry) {
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
        s_Elevator.enable();

        state = elevatorStates.IDLE;
        phase = 0;
        openClaw = true;
    }

    @Override
    public void execute() {

        s_Elevator.setPosition(setpoint);

        if (operator.wasJustPressed(GamepadKeys.Button.START)) {
            s_Elevator.resetEncoder();
        }

        if (openClaw) {
            s_Claw.openClaw();
        } else {
            s_Claw.closeClaw();
        }

        if (driver.wasJustPressed(GamepadKeys.Button.A)) {
            openClaw = !openClaw;
        }

        if (driver.wasJustPressed(GamepadKeys.Button.B)) {
            if (state == elevatorStates.PLACE_SPECIMEN) {
                setState(elevatorStates.RETRACTING);
            } else if (!openClaw) {
                setState(elevatorStates.PLACE_SPECIMEN);
            }
        }

        if (NewSmartIntake.isRetracting()) {
            openClaw = true;
        }

        switch (state) {
            case PLACE_SPECIMEN:
                switch (phase) {
                    case 0:
                        openClaw = false;
                        s_Arm.setAngle(Constants.ArmConstants.placeSpecimenAngle);
                        phase++;
                        break;
                    case 1:
                        break;
                }
                break;
            case PLACE_HIGH:
                setpoint = Constants.ElevatorConstants.highBasket;
                s_Arm.setAngle(Constants.ArmConstants.dropAngle);
                break;

            case PLACE_LOW:
                setpoint = Constants.ElevatorConstants.lowBasket;
                s_Arm.setAngle(Constants.ArmConstants.dropAngle);
                break;

            case RETRACTING:
                s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
                setpoint = Constants.ElevatorConstants.exchange;
                setState(elevatorStates.IDLE);
                break;

        }
//        if (driver.wasJustPressed(GamepadKeys.Button.Y)) {
//            if (state == elevatorStates.PLACE_HIGH) {
//                state = elevatorStates.RETRACTING;
//                timer.reset();
//                phase = 0;
//            } else {
//                state = elevatorStates.PLACE_HIGH;
//                timer.reset();
//                phase = 0;
//            }
//        }
//
//        if (driver.wasJustPressed(GamepadKeys.Button.X)) {
//            if (state == elevatorStates.PLACE_LOW) {
//                state = elevatorStates.RETRACTING;
//                timer.reset();
//                phase = 0;
//            } else {
//                state = elevatorStates.PLACE_LOW;
//                timer.reset();
//                phase = 0;
//            }
//        }
//
//        if (driver.wasJustPressed(GamepadKeys.Button.A)) {
//            if (s_Claw.isOpen()) {
//                s_Claw.setClaw(Constants.ClawConstants.closed);
//            } else {
//                s_Claw.setClaw(Constants.ClawConstants.open);
//            }
//        }
//
//        if (driver.wasJustPressed(GamepadKeys.Button.B)) {
//            if (state == elevatorStates.EXCHANGING) {
//                state = elevatorStates.RETRACTING;
//                timer.reset();
//                phase = 0;
//            } else {
//                state = elevatorStates.EXCHANGING;
//                timer.reset();
//                phase = 0;
//            }
//        }
//
//        if (driver.wasJustPressed(GamepadKeys.Button.DPAD_UP)) {
//            if (state == elevatorStates.PLACE_SPECIMEN) {
//                state = elevatorStates.RETRACTING;
//                timer.reset();
//                phase = 0;
//            } else {
//                state = elevatorStates.PLACE_SPECIMEN;
//                timer.reset();
//                phase = 0;
//            }
//        }
//
//        if (NewSmartIntake.isExchanging()) {
//            state = elevatorStates.EXCHANGING;
//            timer.reset();
//            phase = 0;
//        }

//        switch (state) {
//            case PLACE_HIGH:
//                switch (phase) {
//                    case 0:
//                        if (!s_Claw.isOpen()) {
//                            phase++;
//                        }
//                        s_Claw.setClaw(Constants.ClawConstants.closed);
//                        phase += timer.seconds() > 0.7 ? 1 : 0;
//                        break;
//                    case 1:
//                        if (setpoint == Constants.ElevatorConstants.highBasket) {
//                            phase++;
//                        }
//                        setpoint = Constants.ElevatorConstants.highBasket;
//                        phase += timer.seconds() > 0.3 ? 1 : 0;
//                        break;
//                    case 2:
//                        s_Arm.setAngle(Constants.ArmConstants.dropAngle);
//                }
//                break;
//            case PLACE_LOW:
//                switch (phase) {
//                    case 0:
//                        if (!s_Claw.isOpen()) {
//                            phase++;
//                        }
//                        s_Claw.setClaw(Constants.ClawConstants.closed);
//                        phase += timer.seconds() > 0.3 ? 1 : 0;
//                        break;
//                    case 1:
//                        if (setpoint == Constants.ElevatorConstants.lowBasket) {
//                            phase++;
//                        }
//                        setpoint = Constants.ElevatorConstants.lowBasket;
//                        phase += timer.seconds() > 0.3 ? 1 : 0;
//                        break;
//                    case 2:
//                        s_Arm.setAngle(Constants.ArmConstants.dropAngle);
//                }
//                break;
//            case EXCHANGING:
//                switch (phase) {
//                    case 0:
//                        s_Claw.openClaw();
//                        phase += driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0 ? 1 : 0;
//                        break;
//                    case 1:
//                        s_Claw.closeClaw();
//                        state = elevatorStates.IDLE;
//                        break;
//                }
//                break;
//            case PLACE_SPECIMEN:
//                switch (phase) {
//                    case 0:
//                        if (setpoint == Constants.ElevatorConstants.exchange) {
//                            phase++;
//                        }
//                        setpoint = Constants.ElevatorConstants.exchange;
//                        phase += timer.seconds() > 0.2 ? 1 : 0;
//                    case 1:
//                        s_Arm.setAngle(Constants.ArmConstants.raiseSpecimenAngle);
//                        break;
//                }
//                break;
//            case RETRACTING:
//                switch (phase) {
//                    case 0:
//                        if (s_Arm.getAngle() == Constants.ArmConstants.exchangeAngle) {
//                            phase++;
//                        }
//                        s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
//                        phase += timer.seconds() > 0.3 ? 1 : 0;
//                        break;
//                    case 1:
//                        setpoint = Constants.ElevatorConstants.exchange;
//                        if (timer.seconds() > 2) {
//                            state = elevatorStates.IDLE;
//                        }
//                }
//                break;
//            case IDLE:
//                s_Claw.setClaw(s_Claw.getAngle());
//                s_Arm.setAngle(s_Arm.getAngle());
//        }

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
        PLACE_SPECIMEN,
        RETRACTING
    }

    private void setState(elevatorStates stateToBe) {
        state = stateToBe;
        timer.reset();
        phase = 0;
    }
}