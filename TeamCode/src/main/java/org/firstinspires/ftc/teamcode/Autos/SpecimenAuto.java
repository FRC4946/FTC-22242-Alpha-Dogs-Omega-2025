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
public class SpecimenAuto extends LinearOpMode {
    private DriveTrain s_Drivetrain;
    private Elevator s_Elevator;
    private Intake s_Intake;
    private Extension s_Extension;
    private Claw s_Claw;
    private Arm s_Arm;

    private int phase;

    private int armSetpoint;

    private ElapsedTime timer;

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
        s_Arm.enable();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Left Distance", s_Drivetrain.getLeftDistance());
            telemetry.addData("Right Distance", s_Drivetrain.getRightDistance());
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
                    s_Drivetrain.setPower(-0.2, -0.2, -0.2, -0.2);
                    timer.reset();
                    phase += s_Drivetrain.getLeftDistance() < -900 ? 1 : 0;
                    break;
                case 2:
                    s_Drivetrain.stop();
                    armSetpoint = Constants.ArmConstants.placeSpecimenAngle;
                    s_Claw.closeClaw();
                    phase += timer.seconds() > 2 ? 1 : 0;
                    break;
                case 3:
                    s_Claw.openClaw();
                    s_Drivetrain.setPower(0.2, 0.2, 0.2, 0.2);
                    phase += s_Drivetrain.getLeftDistance() > -500 ? 1 : 0;
                    break;
                case 4:
                    armSetpoint = Constants.ArmConstants.exchangeAngle;
                    s_Claw.openClaw();
                    s_Drivetrain.setPower(-0.2, 0.2, -0.2, 0.2);
                    phase += Math.toDegrees(s_Drivetrain.getHeading())
                    break;
            }
        }
    }
}