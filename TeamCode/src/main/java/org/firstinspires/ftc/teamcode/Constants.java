package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Servo;

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

        public static final double kP = 0.001;
        public static final double kI = 0.001;
        public static final double kD = 0.001;
        public static final double kF = 0.0;
        public static final double PIDTolerance = 10;
    }

    public static final class WristConstants {
        public static final String leftWrist2 = "leftWrist2";
        public static final String rightWrist3 = "rightWrist3";
        public static final double intakeAngle = 0.1;
        public static final double transferAngle = 0.03;
        public static final double barAngle = 0.25;
        public static final Servo.Direction wristInvertL = Servo.Direction.FORWARD;
        public static final Servo.Direction wristInvertR = Servo.Direction.REVERSE;
    }

    public static final class IntakeConstants {

        public static final String leftIntake0 = "leftIntake0";
        public static final String rightIntake1 = "rightIntake1";
        public static final String topRoller0 = "topRoller0";
        public static final String colourSensor = "colourSensor";
        public static final double intakeReduction = 0.3;
    }

    public static final class ExtensionConstants {
        public static final String extension = "extension3";
        public static final double retracted = 0.24;
        public static final double clearBar = 0.05;
        public static final double extended = 0;
    }

    public static final class ArmConstants {
        public static final String arm = "arm4";
        public static final double exchangeAngle = 0.98;
        public static final double dropAngle = 0.32;
        public static final double grabSpecimenAngle = 0.8; // This one is redundant now
        public static final double raiseSpecimenAngle = 0.6;
        public static final double placeSpecimenAngle = 0.7;

    }

    public static final class ClawConstants {
        public static final String leftClaw = "claw1";
        public static final double open = 0.1;
        public static final double closed = 0;
    }

    public static final class ElevatorConstants {
        public static final String elevator = "elevator0";
        public static final int highBasket = 1450;
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
