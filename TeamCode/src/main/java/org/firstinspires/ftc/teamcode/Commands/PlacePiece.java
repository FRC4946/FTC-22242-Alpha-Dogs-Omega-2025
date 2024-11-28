package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.Subsystems.Elevator;

public class PlacePiece extends CommandBase {
    Elevator s_Elevator;

    GamepadEx gamepad;

    private int testAngle;

    public PlacePiece(Elevator s_Elevator, GamepadEx gamepad) {
        this.s_Elevator = s_Elevator;

        this.gamepad = gamepad;

        addRequirements(s_Elevator);
    }

    @Override
    public void initialize() {
        testAngle = 0;
        s_Elevator.enable();
    }

    @Override
    public void execute() {
        s_Elevator.setPosition(testAngle);
        if(gamepad.getButton(GamepadKeys.Button.DPAD_UP)) {
            testAngle = 2000;
        } else if (gamepad.getButton(GamepadKeys.Button.DPAD_DOWN)) {
            testAngle = 0;
        }
    }
}
