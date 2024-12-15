package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;
import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@Autonomous(name = "Greasy Specimen Auto")
public class TwoPieceSpecimen extends LinearOpMode {
    private DriveTrain s_Drivetrain;
    private Elevator s_Elevator;
    private Intake s_Intake;
    private Extension s_Extension;
    private Claw s_Claw;
    private Arm s_Arm;

    private int phase;

    private int armSetpoint;

    private ElapsedTime timer;

    private double autoPower = 0.3;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Drivetrain = new DriveTrain(hardwareMap);
        s_Intake = new Intake(hardwareMap);
        s_Extension = new Extension(hardwareMap);
        s_Claw = new Claw(hardwareMap);
        s_Arm = new Arm(hardwareMap);

        phase = 0;

        armSetpoint = Constants.ArmConstants.exchangeAngle;

        timer = new ElapsedTime();
        s_Drivetrain.resetEncoders();
        s_Drivetrain.resetYaw();
        s_Arm.enable();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Left Distance", s_Drivetrain.getLeftDistance());
            telemetry.addData("Right Distance", s_Drivetrain.getRightDistance());
            telemetry.addData("Heading", Math.toDegrees(s_Drivetrain.getHeading()));
            telemetry.addData("Phase", phase);
            telemetry.update();
            s_Arm.setPosition(armSetpoint);
            switch (phase) {
                case 0:
                    s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                    s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                    s_Intake.setWrist(Constants.WristConstants.defaultAngle);
                    s_Intake.closeClaw();
                    armSetpoint = Constants.ArmConstants.exchangeAngle;
                    s_Claw.closeClaw();
                    timer.reset();
                    phase++;
                    break;
                case 1:
                    s_Drivetrain.setPower(-autoPower, -autoPower, -autoPower, -autoPower);
                    timer.reset();
                    phase += s_Drivetrain.getLeftDistance() < -900 ? 1 : 0;
                    break;
                case 2:
                    s_Drivetrain.stop();
                    armSetpoint = Constants.ArmConstants.placeSpecimenAngle;
                    s_Claw.closeClaw();
                    phase += timer.seconds() > 1 ? 1 : 0;
                    break;
                case 3:
                    s_Claw.openClaw();
                    s_Drivetrain.setPower(autoPower, autoPower, autoPower, autoPower);
                    phase += s_Drivetrain.getLeftDistance() > -500 ? 1 : 0;
                    break;
                case 4:
                    armSetpoint = Constants.ArmConstants.exchangeAngle;
                    s_Claw.openClaw();
                    s_Drivetrain.setPower(-autoPower, -autoPower, autoPower, autoPower);
                    phase += Math.toDegrees(s_Drivetrain.getHeading()) > 55 ? 1 : 0;
                    s_Drivetrain.resetEncoders();
                    timer.reset();
                    break;
                case 5:
                    s_Drivetrain.stop();
                    s_Extension.setAngle(Constants.ExtensionConstants.extended);
                    s_Intake.openClaw();
                    phase += timer.seconds() > 3 ? 1 : 0;
                case 6:
                    s_Drivetrain.setPower(autoPower, autoPower, autoPower, autoPower);
                    phase += s_Drivetrain.getLeftDistance() > 430 ? 1 : 0;
                    timer.reset();
                    break;
                case 7:
                    s_Drivetrain.stop();
                    s_Intake.closeClaw();
                    phase += timer.seconds() > 1 ? 1 : 0;
                    break;
                case 8:
                    s_Drivetrain.setPower(-autoPower, -autoPower, -autoPower, -autoPower);
                    phase += s_Drivetrain.getLeftDistance() < 0 ? 1 : 0;
                    break;
                case 9:
                    s_Drivetrain.setPower(autoPower, autoPower, -autoPower, -autoPower);
                    s_Extension.setAngle(Constants.ExtensionConstants.retracted);
                    s_Intake.setRotation(Constants.IntakeConstants.defaultRotation);
                    s_Intake.setWrist(Constants.WristConstants.transferAngle);
                    phase += Math.toDegrees(s_Drivetrain.getHeading()) < 15 ? 1 : 0;
                    timer.reset();
                    break;
                case 10:
                    s_Drivetrain.stop();
                    phase += timer.seconds() > .5 ? 1 : 0;
                    break;
                case 11:
                    s_Claw.closeClaw();
                    phase += timer.seconds() > 1 ? 1 : 0;
                    s_Drivetrain.resetEncoders();
                    break;
                case 12:
                    s_Intake.openClaw();
                    phase += timer.seconds() > 1.4 ? 1 : 0;
                    break;
                case 13:
                    s_Drivetrain.setPower(-autoPower, -autoPower, -autoPower, -autoPower);
                    phase += s_Drivetrain.getLeftDistance() < -600 ? 1 : 0;
                    timer.reset();
                    break;
                case 14:
                    s_Drivetrain.stop();
                    armSetpoint = Constants.ArmConstants.placeSpecimenAngle;
                    phase += timer.seconds() > 2 ? 1 : 0;
                    break;
                case 15:
                    s_Claw.openClaw();
                    s_Drivetrain.resetEncoders();
                    phase++;
                    break;
                case 16:
                    s_Drivetrain.setPower(autoPower, autoPower, autoPower, autoPower);
                    phase += s_Drivetrain.getLeftDistance() > 200 ? 1 : 0;
                    break;
                case 17:
                    armSetpoint = Constants.ArmConstants.exchangeAngle;
                    s_Drivetrain.stop();
                    timer.reset();
                    phase++;
                    break;
                case 18:
                    s_Drivetrain.setPower(-autoPower, -autoPower, autoPower, autoPower);
                    s_Intake.setWrist(Constants.WristConstants.defaultAngle);
                    phase += timer.seconds() > 0.5 ? 1 : 0;
                    break;
                case 19:
                    s_Drivetrain.setPower(autoPower, autoPower, autoPower, autoPower);
                    phase += s_Drivetrain.getLeftDistance() > 500 ? 1 : 0;
                    break;

//                    s_Drivetrain.setPower(autoPower, autoPower, -autoPower, -autoPower);
//                    phase += Math.toDegrees(s_Drivetrain.getHeading()) < 45 ? 1 : 0;
//                    break;
//                case 19:
//                    s_Drivetrain.setPower(-autoPower, -autoPower, -autoPower, -autoPower);
//                    phase += s_Drivetrain.getLeftDistance() < 560 ? 1 : 0;
//                    break;
//                case 20:
//                    s_Drivetrain.setPower(autoPower, autoPower, -autoPower, -autoPower);
//                    phase += Math.toDegrees(s_Drivetrain.getHeading())
              }
        }
    }
}