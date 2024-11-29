package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.ColorSensor;

@TeleOp(name = "ColorSensor (Blocks to Java)", group = "Sensor")
public class VisionTest extends LinearOpMode {
    ColorSensor s_Sensor;

    @Override
    public void runOpMode() {
        s_Sensor = new ColorSensor(hardwareMap);
        waitForStart();
        if (opModeIsActive()) {
            while(opModeIsActive()) {
                telemetry.addLine("Vision");
                telemetry.addData("Hue: ", s_Sensor.getHue());
                telemetry.addData("Saturation: ", s_Sensor.getSaturation());
                telemetry.addData("Value: ", s_Sensor.getValue());
                telemetry.update();
            }
        }
    }
}
