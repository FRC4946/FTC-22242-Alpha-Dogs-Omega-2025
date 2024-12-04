package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.SmartElevator;
import org.firstinspires.ftc.teamcode.Commands.SmartIntake;
import org.firstinspires.ftc.teamcode.Commands.TeleopDrive;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.ColourSensor;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

@TeleOp(name = "Greasy Teleop")
public class GreasyOpMode extends LinearOpMode {

    private GamepadEx m_DriverOp;
    private GamepadEx m_OperatorOp;

    private DriveTrain s_Drivetrain;
    private Elevator s_Elevator;
    private Intake s_Intake;
    private Wrist s_Wrist;
    private Extension s_Extension;
    private Claw s_Claw;
    private Arm s_Arm;

    private ColourSensor s_ColourSensor;

    private TeleopDrive c_TeleopDrive;
    private SmartElevator c_SmartElevator;
    private SmartIntake c_SmartIntake;

    private String allianceColour;
    private ElapsedTime runtime;

    private boolean slowMode;

    @Override
    public void runOpMode() {

        allianceColour = Constants.redAlliance;

        m_DriverOp = new GamepadEx(gamepad1);
        m_OperatorOp = new GamepadEx(gamepad2);

        s_Drivetrain = new DriveTrain(hardwareMap);
        s_Elevator = new Elevator(hardwareMap);
        s_Intake = new Intake(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);
        s_Extension = new Extension(hardwareMap);
        s_Claw = new Claw(hardwareMap);
        s_Arm = new Arm(hardwareMap);

        s_ColourSensor = new ColourSensor(hardwareMap, allianceColour);

        runtime = new ElapsedTime();

        slowMode = false;

        c_TeleopDrive = new TeleopDrive(
                s_Drivetrain,
                m_DriverOp,
                telemetry,
                slowMode
        );

        c_SmartElevator = new SmartElevator(
                s_Elevator,
                s_Arm,
                s_Claw,
                m_DriverOp,
                m_OperatorOp,
                telemetry
        );

        c_SmartIntake = new SmartIntake(
                s_Intake,
                s_Wrist,
                s_Extension,
                s_ColourSensor,
                m_DriverOp,
                m_OperatorOp,
                allianceColour,
                telemetry
        );

        waitForStart();
        runtime.reset();

        c_TeleopDrive.initialize();
        c_SmartIntake.initialize();
        c_SmartElevator.initialize();

        while (opModeIsActive()) {
            telemetry.update();
            m_DriverOp.readButtons();
            m_OperatorOp.readButtons();

            c_TeleopDrive.execute();
            c_SmartIntake.execute();
            c_SmartElevator.execute();

            if(m_DriverOp.wasJustPressed(GamepadKeys.Button.DPAD_LEFT)) {
                slowMode = !slowMode;
            }

        }
    }
}
