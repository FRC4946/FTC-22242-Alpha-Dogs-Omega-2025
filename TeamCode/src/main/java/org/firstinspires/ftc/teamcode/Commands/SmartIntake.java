package org.firstinspires.ftc.teamcode.Commands;


import org.firstinspires.ftc.teamcode.Subsystems.Extension;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Subsystems.Wrist;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;

public class SmartIntake extends CommandBase {

    Intake s_Intake;
    Wrist s_Wrist;
    Extension s_Extension;

    public SmartIntake(Intake s_Intake, Wrist s_Wrist, Extension s_Extension) {
        this.s_Intake = s_Intake;
        this.s_Wrist = s_Wrist;
        this.s_Extension = s_Extension;
    }

}
