package org.firstinspires.ftc.TeamCode_reformatted;

import android.media.AudioManager;
import android.media.SoundPool;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Coby on 10/7/2017.
 */
@TeleOp(name = "Lift Op POV 2 Controller", group = "teleop")
public class LiftOpPOV_2Controller extends OpMode {
    private boolean isFast = true;
    private double motorScaleFactor;
    private SoundPool sounds;
    private int beepid;



    double claw_offset = 0.5;
    double claw_speed = .02;
    //HardwareLiftBot robot;
    Hardware robot;
    double flipPosition = 0;
    double relicPosition = 0;
    @Override
    public void init() {
           sounds = new SoundPool(1, AudioManager.USE_DEFAULT_STREAM_TYPE, 0);
  //        beepid = sounds.load(hardwareMap.appContext, R.raw.Holly_Christmas, 1);

        //  runtime.reset();

        isFast = true;
        //robot = new HardwareLiftBot(hardwareMap);

        robot = new Hardware(hardwareMap);
        robot.init();
    }

    @Override
    public void loop() {
        if(gamepad2.dpad_up){
            sounds.pause(beepid);
        }
        double left;
        double right;
        double max;

        if(gamepad1.y){
            if(isFast){
                isFast = false;
            }
            else{
                isFast = true;
            }
            if(isFast){
                motorScaleFactor = 1;
            }
            else{
                motorScaleFactor = .5;
            }
        }


        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        turn *= 1;
        left = drive + turn;
        right = drive - turn;
        max = Math.max(left, right);

        if(max > 1){
            left /= max;
            right /= max;

        }
        double leftSign, rightSign;
        if(left <= 0){
            leftSign = -1;
        }
        else{
            leftSign =1;
        }
        if(right < 0){
            rightSign = -1;
        }
        else{
            rightSign = 1;
        }
        left *= motorScaleFactor;
        right *= motorScaleFactor;
        robot.leftDrive.setPower(leftSign * (left*left));
        robot.rightDrive.setPower(rightSign * (right*right));

        if(gamepad2.right_bumper){
            claw_offset += claw_speed;
        }

        else if(gamepad2.left_bumper){
            claw_offset -= claw_speed;

        }

        robot.liftMotor.setPower(gamepad2.left_stick_y);
        if(gamepad1.right_trigger > 0) {
            robot.relicMotor.setPower(gamepad1.right_trigger);
        }
        else{
            robot.relicMotor.setPower(-gamepad1.left_trigger);
        }

        claw_offset = Range.clip(claw_offset, 0, 1);
        if(gamepad2.left_bumper || gamepad2.right_bumper){
       robot.leftClaw.setPosition(robot.ROBOT_MID_SERVO + claw_offset);
            robot.rightClaw.setPosition(robot.ROBOT_MID_SERVO + claw_offset);

        }

        if(gamepad1.right_bumper){
            relicPosition += .005;
        }
        if(gamepad1.left_bumper){
          relicPosition -= .005;
        }
        if(gamepad1.x){
          flipPosition += .005;
        }
        if(gamepad1.b){
            flipPosition += -.005;
        }
        relicPosition = Range.clip(relicPosition, 0, 1);
        flipPosition = Range.clip(flipPosition, 0, 1);

        robot.relicGrip.setPosition(Range.clip(relicPosition, 0, 1));
        robot.relicFlip.setPosition(Range.clip(flipPosition, 0, 1));
        telemetry.addData("gripd", relicPosition);
        telemetry.addData("flip", flipPosition);

    }

    public void driveControl(){

        double left;
        double right;
        double max;
        double drive = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;


        if(gamepad1.y){
            if(isFast == true){
                isFast = false;
            }
            else{
                isFast = true;
            }
            /*if(isFast){
                motorScaleFactor = 1;
            }
            else{
                motorScaleFactor = .5;
            }*/
        }

        if(isFast){
            motorScaleFactor = 1;
        }
        else{
            motorScaleFactor = .5;
        }



        turn *= 1;
        left = drive + turn;
        right = drive - turn;
        max = Math.max(left, right);

        if(max > 1){
            left /= max;
            right /= max;

        }
        left *= motorScaleFactor;
        right *= motorScaleFactor;
        robot.leftDrive.setPower(-left);
        robot.rightDrive.setPower(-right);
        telemetry.addData("left", left);
        telemetry.addData("right", right);
        telemetry.update();
    }

}
