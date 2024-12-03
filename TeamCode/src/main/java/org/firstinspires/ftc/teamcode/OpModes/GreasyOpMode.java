package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
public class GreasyOpMode extends CommandOpMode {

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

    @Override
    public void initialize() {

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

        c_TeleopDrive = new TeleopDrive(
                s_Drivetrain,
                m_DriverOp,
                telemetry
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

        register(s_Drivetrain, s_Elevator, s_Intake, s_Wrist, s_Extension, s_Arm, s_Claw, s_ColourSensor);

        s_Drivetrain.setDefaultCommand(c_TeleopDrive);
        s_Intake.setDefaultCommand(c_SmartIntake);
        s_Elevator.setDefaultCommand(c_SmartElevator);
    }
}
