package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.externalhardware.RobotHardware;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.OdometryTestHardware;

@TeleOp(name= "OdometryDriveTest", group = "Testbed")
@Disabled
public class OdometryDriveTest extends LinearOpMode {

    private OdometryTestHardware robot;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new OdometryTestHardware(hardwareMap);

        ElapsedTime timer = new ElapsedTime();
        robot.pathTimer = new ElapsedTime();

        waitForStart();
        timer.reset();

        while (opModeIsActive()){
            telemetry.addLine("Version 1.0");
            telemetry.addLine("X: reset, target pos, B: goto zero");

            // some buttons will start autonomus movements:
            checkButtons();

            //let the driver override the auto driving
            if (Math.abs(gamepad1.left_stick_y) > 0.3
                || Math.abs(gamepad1.left_stick_x) > 0.3
                || Math.abs(gamepad1.right_stick_x) > 0.3) {
             robot.followingPath = false;
            }

            //auto or manual driving?
            robot.speedModifier = 1.0;
            if (robot.followingPath)   robot.goToPosLinear();
            else                       stickDriving();

            robot.odometry();

            telemetry.addData("LRA", "%6d %6d %6d,", robot.currentLeftPosition, robot.currentRightPosition, robot.currentAuxPosition);
            telemetry.addData("xyh", "%6.1f cm %6.1 deg", robot.pos.x, robot.pos.y, Math.toDegrees(robot.pos.h));
            telemetry.addData("loop", "%6.1f ms", timer.milliseconds());
            timer.reset();


        }

    }
    private boolean wasAPressed = false;
    private boolean wasBPressed = false;
    private boolean wasxPressed = false;


    private void checkButtons() {

    }

    private void stickDriving() {
    }
}
