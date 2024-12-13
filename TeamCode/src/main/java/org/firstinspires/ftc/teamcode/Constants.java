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

        public static final double kP = 0.4;
        public static final double kI = 0.1;
        public static final double kD = 0.05;
        public static final double kF = 0.0;
        public static final double PIDTolerance = 3;
    }

    public static final class WristConstants {
        public static final String wrist2 = "wrist1";
        public static final double defaultAngle = 0.1;
        public static final double transferAngle = 0.76;
    }

    public static final class IntakeConstants {
        public static final String intakeClaw0 = "intakeClaw0";
        public static final String intakeRotate1 = "intakeRotate2";
        public static final double open = 0.28;
        public static final double closed = 0;
        public static final double defaultRotation = 0;
    }

    public static final class ExtensionConstants {
        public static final String leftExtension = "leftExtension3";
        public static final String rightExtension = "rightExtension4";
        public static final double retracted = 0.24;
        public static final double extended = 0;
    }

    public static final class ArmConstants {
        public static final String arm2 = "arm2";
        public static final int exchangeAngle = 0;
        public static final int dropAngle = 500;
        public static final int grabSpecimenAngle = 1; // This one is redundant now
        public static final int raiseSpecimenAngle = 1;
        public static final int placeSpecimenAngle = 500;

    }

    public static final class ClawConstants {
        public static final String claw0 = "claw0";
        public static final double open = 0.1;
        public static final double closed = 0.03;
    }

    public static final class ElevatorConstants {
        public static final String leftElevator = "leftElevator0";
        public static final String rightElevator = "rightElevator1";
        public static final int highBasket = 4325;
        public static final int lowBasket = 1000;
        public static final int exchange = 50;
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
