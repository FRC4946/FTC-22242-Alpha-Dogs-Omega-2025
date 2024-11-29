package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Commands.PlacePiece;
import org.firstinspires.ftc.teamcode.Commands.SmartIntake;
import org.firstinspires.ftc.teamcode.Commands.TeleopDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.ColourSensor;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;
import org.jetbrains.annotations.NotNull;

@TeleOp(name = "oiui Teleop")
public class CommandTeleop extends CommandOpMode {

    private GamepadEx m_driverOp;

    private DriveTrain s_Drivetrain;
    private Elevator s_Elevator;
    private Intake s_Intake;
    private Wrist s_Wrist;
    private Extension s_Extension;
    private Claw s_Claw;
    private Arm s_Arm;

    private ColourSensor s_ColourSensor;

    private TeleopDrive c_TeleopDrive;
    private PlacePiece c_PlacePiece;
    private SmartIntake c_SmartIntake;

    private String allianceColour;

    @Override
    public void initialize() {

        m_driverOp = new GamepadEx(gamepad1);

        s_Drivetrain = new DriveTrain(hardwareMap);
        s_Elevator = new Elevator(hardwareMap);
        s_Intake = new Intake(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);
        s_Extension = new Extension(hardwareMap);
        s_Claw = new Claw(hardwareMap);
        s_Arm = new Arm(hardwareMap);

        s_ColourSensor = new ColourSensor(hardwareMap);

        c_TeleopDrive = new TeleopDrive(
                s_Drivetrain,
                m_driverOp
        );

        c_PlacePiece = new PlacePiece(
                s_Elevator,
                s_Arm,
                s_Claw,
                m_driverOp
        );

        c_SmartIntake = new SmartIntake(
                s_Intake,
                s_Wrist,
                s_Extension,
                s_ColourSensor,
                m_driverOp,
                allianceColour
        );

        register(s_Drivetrain, s_Elevator, s_Intake, s_Wrist, s_Extension);

        s_Drivetrain.setDefaultCommand(c_TeleopDrive);
        s_Elevator.setDefaultCommand(c_PlacePiece);
        s_Intake.setDefaultCommand(c_SmartIntake);

//        schedule(c_TeleopDrive);
        schedule(c_SmartIntake);
//        schedule(c_PlacePiece);

        allianceColour = "Blue";
    }

//    @Override
//    public void run() {
//
//        //m_driverOp.readButtons();
//
//        if (!isStarted()) {
//            if (m_driverOp.wasJustPressed(GamepadKeys.Button.B)) {
//                allianceColour = allianceColour.equals("Blue") ? "Red" : "Blue";
//            }
//        }
//
//        telemetry.addData("(Press B) Alliance Colour: ", allianceColour);
//        telemetry.addLine("Vision");
//        telemetry.addData("Hue:", s_ColourSensor.getHue());
//        telemetry.addData("Position", s_Elevator.getPosition());
//        telemetry.update();
//
//    }
}
