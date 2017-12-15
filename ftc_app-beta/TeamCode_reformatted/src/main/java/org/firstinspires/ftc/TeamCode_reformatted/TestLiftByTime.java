package org.firstinspires.ftc.TeamCode_reformatted;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.TeamCode_reformatted.AutonomousCommandGroup;
import org.firstinspires.ftc.TeamCode_reformatted.TestLiftMotor;
import org.firstinspires.ftc.TeamCode_reformatted.commands.UseLiftByTime;
import org.firstinspires.ftc.TeamCode_reformatted.commands.Wait;

/**
 * Created by Coby on 12/12/2017.
 */
@Autonomous(name = "test lift by time", group = "test")
public class TestLiftByTime extends AutonomousCommandGroup {
    public TestLiftByTime(){
        addSequential(new UseLiftByTime(.6, 500));
        addSequential(new Wait(3000));
        addSequential(new UseLiftByTime(-.6, 500));
    }

    @Override
    public void addCommands() {

    }
}
