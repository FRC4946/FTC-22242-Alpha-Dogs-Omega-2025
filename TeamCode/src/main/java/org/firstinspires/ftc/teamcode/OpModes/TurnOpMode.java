package org.firstinspires.ftc.teamcode.OpModes;


import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.TurnToAngle;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;

@TeleOp(name = "Turn Teleop")
public class TurnOpMode extends LinearOpMode {
    private GamepadEx m_DriverOp;
    private GamepadEx m_OperatorOp;

    private DriveTrain s_Drivetrain;
    private TurnToAngle c_TurnToAngle;

    @Override
    public void runOpMode() {

        m_DriverOp = new GamepadEx(gamepad1);
        m_OperatorOp = new GamepadEx(gamepad2);

        s_Drivetrain = new DriveTrain(hardwareMap);
        c_TurnToAngle = new TurnToAngle(s_Drivetrain, -90, telemetry);


        waitForStart();

        c_TurnToAngle.initialize();

        while (opModeIsActive()) {
            m_DriverOp.readButtons();
            m_OperatorOp.readButtons();

            telemetry.update();

//            if(m_DriverOp.wasJustPressed(GamepadKeys.Button.A)) {
//                c_TurnToAngle.initialize();
//            }
            if (m_DriverOp.isDown(GamepadKeys.Button.A)) {
                c_TurnToAngle.execute();
            }
        }

    }
}

