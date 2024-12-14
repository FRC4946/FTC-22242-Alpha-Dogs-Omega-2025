package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants.DriveTrainConstants;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

public class DriveTrain extends SubsystemBase {

    private final DcMotorEx frontLeft0;
    private final DcMotorEx frontRight1;
    private final DcMotorEx backLeft2;
    private final DcMotorEx backRight3;

    private final IMU imu;

    public DriveTrain(HardwareMap hardwareMap) {
        frontLeft0 = hardwareMap.get(DcMotorEx.class, DriveTrainConstants.frontLeftMotor);
        frontRight1 = hardwareMap.get(DcMotorEx.class, DriveTrainConstants.frontRightMotor);
        backLeft2 = hardwareMap.get(DcMotorEx.class, DriveTrainConstants.backLeftMotor);
        backRight3 = hardwareMap.get(DcMotorEx.class, DriveTrainConstants.backRightMotor);

        frontLeft0.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight1.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft2.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight3.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeft0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontLeft0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);
    }

    public void setPower(double frontLeftPower, double backLeftPower, double frontRightPower, double backRightPower) {
        frontLeft0.setPower(frontLeftPower);
        backLeft2.setPower(backLeftPower);
        frontRight1.setPower(frontRightPower);
        backRight3.setPower(backRightPower);
    }

    public void setVelocity(int frontLeftPower, int frontRightPower, int backLeftPower, int backRightPower) {
        frontLeft0.setVelocity(frontLeftPower);
        frontRight1.setVelocity(frontRightPower);
        backLeft2.setVelocity(backLeftPower);
        backRight3.setVelocity(backRightPower);
    }

    public double getLeftDistance() {
        return (frontLeft0.getCurrentPosition() + backLeft2.getCurrentPosition()) / 2.0;
    }

    public double getRightDistance() {
        return (frontRight1.getCurrentPosition() + backRight3.getCurrentPosition()) / 2.0;
    }

    public void stop() {
        frontLeft0.setPower(0);
        frontRight1.setPower(0);
        backLeft2.setPower(0);
        backRight3.setPower(0);
    }

    public void resetEncoders() {
        frontLeft0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    /**
     * Updates the yaw offset to the current heading
     */
    public void resetYaw() {
        imu.resetYaw();
    }

    /**
     * Returns the updated yaw after the yaw offset is applied
     *
     * @return adjusted heading of the robot
     */
    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

    public double angleWrap(double radians) {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }

//    public double getHeadingDegrees() {
//        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
//    }
}
