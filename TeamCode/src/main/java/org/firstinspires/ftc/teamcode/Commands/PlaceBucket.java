package org.firstinspires.ftc.teamcode.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Subsystems.Arm;
import org.firstinspires.ftc.teamcode.Subsystems.Claw;
import org.firstinspires.ftc.teamcode.Subsystems.Elevator;

public class PlaceBucket extends CommandBase {
    private Elevator s_Elevator;
    private Arm s_Arm;
    private Claw s_Claw;

    private int phase;
    private ElapsedTime timer;
    private int setpoint;

    public PlaceBucket(Elevator s_Elevator, Arm s_Arm, Claw s_Claw) {
        this.s_Elevator = s_Elevator;
        this.s_Arm = s_Arm;
        this.s_Claw = s_Claw;

        addRequirements();
    }

    @Override
    public void initialize() {
        phase = 0;
        timer = new ElapsedTime();
        setpoint = Constants.ElevatorConstants.exchange;
        s_Elevator.enable();
    }

    @Override
    public void execute() {
        s_Elevator.setPosition(setpoint);
        switch (phase) {
            case 0:
                s_Claw.setClaw(Constants.ClawConstants.closed);
                phase++;
                break;
            case 1:
                setpoint = Constants.ElevatorConstants.highBasket;
                phase += timer.seconds() > 0.3 ? 1 : 0;
                break;
            case 2:
                s_Arm.setAngle(Constants.ArmConstants.dropAngle);
                phase += timer.seconds() > 2 ? 1 : 0;
                break;
            case 3:
                s_Claw.setClaw(Constants.ClawConstants.open);
                phase += timer.seconds() > 0.5 ? 1 : 0;
                break;
            case 4:
                s_Arm.setAngle(Constants.ArmConstants.exchangeAngle);
                phase += timer.seconds() > 0.3 ? 1 : 0;
                break;
            case 5:
                setpoint = Constants.ElevatorConstants.exchange;
                phase += timer.seconds() > 1 ? 1 : 0;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return phase == 6;
    }

    @Override
    public void end(boolean interuppted) {

    }



}
