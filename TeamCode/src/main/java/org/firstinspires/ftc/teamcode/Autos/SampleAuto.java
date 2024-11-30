package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Commands.PlaceBucket;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;

@Autonomous(name = "Greasy Auto")
public class SampleAuto extends LinearOpMode {

    private DriveTrain s_Drivetrain;
    private Elevator s_Elevator;
    private Claw s_Claw;
    private Arm s_Arm;

    private int phase;
    private ElapsedTime timer;

    @Override
    public void runOpMode() throws InterruptedException {

        s_Drivetrain = new DriveTrain(hardwareMap);
        s_Elevator = new Elevator(hardwareMap);
        s_Claw = new Claw(hardwareMap);
        s_Arm = new Arm(hardwareMap);

        phase = 0;
        timer = new ElapsedTime();

        PlaceBucket place = new PlaceBucket(s_Elevator, s_Arm, s_Claw);

        waitForStart();
        while (opModeIsActive()) {
            switch (phase) {
                case 0:
                    place.initialize();
                    place.execute();
                    phase += place.isFinished() ? 1 : 0;
                    break;
            }

        }
    }
}
