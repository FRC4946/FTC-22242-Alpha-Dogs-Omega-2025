package org.firstinspires.ftc.teamcode;

public class Constants {

    public static final String blueAlliance = "Blue";
    public static final String redAlliance = "Red";

    public static final class DriveTrainConstants {

        public static final String frontLeftMotor = "frontLeft0";
        public static final String frontRightMotor = "frontRight1";
        public static final String backLeftMotor = "backLeft2";
        public static final String backRightMotor = "backRight3";

        public static final double strafingBalancer = 1.1;

        public static final double ticksPerRevolution = 537.6; //Ticks per revolution on the NeveRest Orbital 20 Gearmotor

        public static final double kP = 0.01;
        public static final double kI = 0.0;
        public static final double kD = 0.001;
        public static final double kF = 0.0;
        public static final double PIDTolerance = 1.0;
    }

    public static final class WristConstants {
        public static final String wrist2 = "wrist1";
        public static final double defaultAngle = 0;
        public static final double transferAngle = 0.4;
    }

    public static final class IntakeConstants {
        public static final String intakeClaw0 = "intakeClaw0";
        public static final String intakeRotate1 = "intakeRotate2";
        public static final double open = 0;
        public static final double closed = 0.21;
        public static final double defaultRotation = 0.6;
    }

    public static final class ExtensionConstants {
        public static final String leftExtension = "leftExtension3";
        public static final String rightExtension = "rightExtension4";
        public static final double retracted = 0.24;
        public static final double extended = 0;
    }

    public static final class ArmConstants {
        public static final String leftArm = "leftArm1";
        public static final String rightArm = "rightArm2";
        public static final double exchangeAngle = 0.98;
        public static final double dropAngle = 0.32;
        public static final double grabSpecimenAngle = 0.8; //TODO FIND THESE VALUE
        public static final double raiseSpecimenAngle = 0.2;
        public static final double placeSpecimenAngle = 0.1;

    }

    public static final class ClawConstants {
        public static final String claw0 = "claw0";
        public static final double open = 0.1;
        public static final double closed = 0;
    }

    public static final class ElevatorConstants {
        public static final String leftElevator = "leftElevator0";
        public static final String rightElevator = "rightElevator1";
        public static final int highBasket = 1300;
        public static final int lowBasket = 80;
        public static final int exchange = 80;
    }

    public static final class VisionConstants {
        public static final int gain = 100;
        public static final int neutralHue = 167;
        public static final int redHue = 24;
        public static final int yellowHue = 82;
        public static final int blueHue = 223;
        public static final int hueTolerance = 10;
    }

}
