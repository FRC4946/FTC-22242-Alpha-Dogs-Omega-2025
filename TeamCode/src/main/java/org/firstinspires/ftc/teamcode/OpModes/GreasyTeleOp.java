package org.firstinspires.ftc.teamcode.OpModes;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.Commands.TeleopDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

@TeleOp(name = "greasy", group = "TeleOp")
public class GreasyTeleOp extends LinearOpMode {

    Gamepad activeGamepad1;

    Gamepad previousGamepad1;

    static boolean manualMode;

    DriveTrain s_DriveTrain;
    Elevator s_Elevator;
    Wrist s_Wrist;
    Intake s_Intake;
    Extension s_Extension;
    Arm s_Arm;
    Claw s_Claw;


    @Override
    public void runOpMode() {
        telemetry.update();

        activeGamepad1 = new Gamepad();

        previousGamepad1 = new Gamepad();

        manualMode = true;

        s_DriveTrain = new DriveTrain(hardwareMap);
        s_Elevator = new Elevator(hardwareMap);
        s_Wrist = new Wrist(hardwareMap);
        s_Intake = new Intake(hardwareMap);
        s_Extension = new Extension(hardwareMap);
        s_Arm = new Arm(hardwareMap);
        s_Claw = new Claw(hardwareMap);

        s_Elevator.enable();

        waitForStart();

        while (opModeIsActive()) {

            CommandScheduler.getInstance().run();

            previousGamepad1.copy(activeGamepad1);
            activeGamepad1.copy(gamepad1);

            if (activeGamepad1.back) {
                manualMode = !manualMode;
            }

//            s_DriveTrain.setDefaultCommand(new TeleopDrive(
//                    s_DriveTrain,
//                    -activeGamepad1.left_stick_y,
//                    activeGamepad1.left_stick_x,
//                    activeGamepad1.right_stick_x,
//
//            ));

            if (activeGamepad1.options) {
                s_DriveTrain.resetYaw();
            }

//            if (manualMode) {
//                s_Intake.setIntakePower(activeGamepad1.right_trigger - activeGamepad1.left_trigger);
//
//                if (activeGamepad1.dpad_right) {
//                    s_Extension.setAngle(Constants.ExtensionConstants.extended);
//                } else if (activeGamepad1.dpad_left) {
//                    s_Extension.setAngle(Constants.ExtensionConstants.retracted);
//                } else {
//                    s_Extension.setAngle(s_Extension.getAngle());
//                }
//
//                if(activeGamepad1.dpad_up) {
//                    s_Wrist.setAngle(Constants.WristConstants.transferAngle);
//                } else if (activeGamepad1.dpad_down) {
//                    s_Wrist.setAngle(Constants.WristConstants.intakeAngle);
//                } else {
//                    s_Wrist.setAngle(Constants.WristConstants.barAngle);
//                }
//
//                if(activeGamepad1.right_bumper) {
//                    s_Elevator.setPosition(Constants.ElevatorConstants.highBasket);
//                } else if (activeGamepad1.left_bumper) {
//                    s_Elevator.setPosition(Constants.ElevatorConstants.exchange);
//                }
//
//                if(activeGamepad1.a) {
//                    s_Arm.setAngle(Constants.ArmConstants.dropAngle);
//                } else {
//                    s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
//                }
//
//                if(activeGamepad1.x) {
//                    s_Claw.setClaw(Constants.ClawConstants.closed);
//                } else if (activeGamepad1.y) {
//                    s_Claw.setClaw(Constants.ClawConstants.open);
//                }
//
//            } else {
//                //s_Intake.setIntakePower(activeGamepad1.right_trigger - activeGamepad1.left_trigger);
//                if (activeGamepad1.right_trigger > 0) {
//                    s_Intake.setIntakePower(1);
//                    s_Wrist.setAngle(Constants.WristConstants.intakeAngle);
//                } else if (activeGamepad1.left_trigger > 0) {
//                    s_Intake.setIntakePower(-1);
//                    s_Wrist.setAngle(Constants.WristConstants.intakeAngle);
//                } else {
//                    s_Intake.setIntakePower(0);
//                }
//
//                if (activeGamepad1.right_bumper) {
//                    s_Wrist.setAngle(Constants.WristConstants.barAngle);
////                //Will eventually say shoot out horizontal slides :p
//                } else if (activeGamepad1.left_bumper) {
//                    s_Wrist.setAngle(Constants.WristConstants.barAngle);
////                //Will pull in horizontal slides
//                }
//
//                if (activeGamepad1.dpad_down) {
//                    s_Wrist.setAngle(Constants.WristConstants.transferAngle);
//                }
//            }
        }
    }
}
