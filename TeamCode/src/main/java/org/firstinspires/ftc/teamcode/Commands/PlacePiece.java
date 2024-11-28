package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;

public class PlacePiece extends CommandBase {
    Elevator s_Elevator;
    Arm s_Arm;
    Claw s_Claw;

    GamepadEx gamepad;

    private ElapsedTime timer = new ElapsedTime();

    private int phase;

    states state;

    public PlacePiece(Elevator s_Elevator, Arm s_Arm, Claw s_Claw, GamepadEx gamepad) {
        this.s_Elevator = s_Elevator;
        this.s_Arm = s_Arm;
        this.s_Claw = s_Claw;

        this.gamepad = gamepad;

        addRequirements(s_Elevator, s_Arm, s_Claw);
    }

    @Override
    public void initialize() {
        s_Elevator.setPosition(Constants.ElevatorConstants.exchange);
        s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
        //s_Claw.setClaw(Constants.ClawConstants.open);
        s_Elevator.enable();

        state = states.IDLE;
        phase = 0;
    }

    @Override
    public void execute() {

        gamepad.readButtons();

        if (gamepad.wasJustPressed(GamepadKeys.Button.X)) {
            if (state == states.IDLE) {
                state = states.EXTENDING;
                timer.reset();
                phase = 0;
            } else if (state == states.EXTENDING) {
                state = states.RETRACTING;
                timer.reset();
                phase = 0;
            } else {
                state = states.EXTENDING;
                timer.reset();
                phase = 0;
            }
        }

        switch (state) {
            case EXTENDING:
                switch (phase) {
                    case 0:
                        //s_Claw.setClaw(Constants.ClawConstants.closed);
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 1:
                        s_Elevator.setPosition(Constants.ElevatorConstants.highBasket);
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 2:
                        s_Arm.setAngle(Constants.ArmConstants.dropAngle);
                }
                break;
            case RETRACTING:
                switch (phase) {
                    case 0:
                        s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
                        phase += timer.seconds() > 0.3 ? 1 : 0;
                        break;
                    case 1:
                        s_Elevator.setPosition(Constants.ElevatorConstants.exchange);
                        state = states.IDLE;
                }
                break;
        }

    }

    private enum states {
        IDLE,
        EXTENDING,
        RETRACTING
    }
}
