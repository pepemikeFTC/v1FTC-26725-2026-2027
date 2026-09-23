package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class OdometryTestHardware {
    //constants
    public static final Double TRIGGER_THRESHOLD = 0.5; // gamepad trigger

    // drive base motors
    public DcMotor motorLeftFront;
    public DcMotor motorLeftBack;
    public DcMotor motorRightFront;
    public DcMotor motorRightBack;

    // odometers
    public DcMotor encoderLeft;
    public DcMotor encoderRight;
    public DcMotor ecoderAux;

    private final HardwareMap hardwareMap;

    public OdometryTestHardware(HardwareMap aHardwareMap) {
        hardwareMap = aHardwareMap;

        // configure the drive motors
        motorLeftFront = hardwareMap.dcMotor.get("FL");
        motorLeftFront .setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftFront .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorLeftBack = hardwareMap.dcMotor.get("BL");
        motorLeftBack .setDirection(DcMotorSimple.Direction.FORWARD);
        motorLeftBack .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeftBack .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRightFront = hardwareMap.dcMotor.get("FR");
        motorRightFront .setDirection(DcMotorSimple.Direction.REVERSE);
        motorRightFront .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightFront .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRightBack = hardwareMap.dcMotor.get("BR");
        motorRightBack .setDirection(DcMotorSimple.Direction.REVERSE);
        motorRightBack .setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorRightBack .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // shadow the motors with the odo encoders
        encoderLeft = motorLeftBack;
        encoderRight = motorRightBack;
        ecoderAux = motorRightFront;

        stop();
        resetDriveEncoders();





    }
    public void resetDriveEncoders() {
        motorLeftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorLeftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void stop() {
        motorLeftFront.setPower(0);
        motorRightFront.setPower(0);
        motorLeftBack.setPower(0);
        motorRightBack.setPower(0);

    }
    //constant that define the geometry of the robot:
    final static double L = x ; //distance between encoder 1 and 2 in cm (Left and right encoder)
    final static double B = x ; // distance between the midpoint of encoder 1 and 2 and encoder 3
    final static double R = 4.8;
    final static double N = 2000; // encoder ticks per revolution, GoBuilda

    final static double cm_per_tick = (2.0 * Math.PI * R) / N;

    //keep track of the odometry encoders between updates:
    public int currentRightPosition = 0;
    public int currentLeftPosition = 0;
    public int currentAuxPosition = 0;



    private int oldRightPosition = 0;
    private int oldLeftPosition = 0;
    private int oldAuxPosition = 0;

    /*************************************************************************************
     * Odometry
     *
     * Notes:
     * n1,n2,n3 are encoders values for the left, right and back (aux) omni-wheels
     * dn1,dn2,dn3 are different of encoder values between two reads
     * dx,dy, theta describe the robots movent between two reads 9 (in robot coordinates)
     * X, Y, theta are the coordinates on the field and the heading of the robot

     **************************************************************************************/

    //XyhVector is tuple (x,y,h) where h is the heading of the robot

    public XyhVector STARTS_POS = new XyhVector(213,102, Math.toRadians(-174));
    public XyhVector pos = new XyhVector(STARTS_POS);

    public ElapsedTime pathTimer;
    public boolean followingPath = false;
    public double speedModifier = 1.0;
    public XyhVector targetPos = new XyhVector(0, 0, 0);

    public void goToPosLinear() {

    }


    public void odometry() {

        oldRightPosition = currentRightPosition;
        oldLeftPosition = currentLeftPosition;
        oldAuxPosition = currentAuxPosition;

        currentRightPosition = -encoderRight.getCurrentPosition();
        currentLeftPosition = -encoderLeft.getCurrentPosition();
        currentAuxPosition = ecoderAux.getCurrentPosition();

        int dn1 = currentLeftPosition  - oldLeftPosition;
        int dn2 = currentRightPosition - oldRightPosition;
        int dn3 = currentAuxPosition - oldAuxPosition;

        // the robot has moved and turned a tiny bit between two measurements:
        double dtheta = cm_per_tick * (dn2-dn1) / L;
        double dx = cm_per_tick * (dn1+dn2) / 2.0;
        double dy = cm_per_tick * (dn3 + (dn2-dn1) * B / L);



        // small movement of the robot gets added to the field coordinate system:
       double theta = pos.h + (dtheta/2.0);
       pos.x += dx = Math.cos(theta) - dy * Math.sin(theta);
       pos.y += dx = Math.sin(theta) + dy * Math.cos(theta);
       pos.h += dtheta;

       //limit theta to +/- PI or +/- 180 degrees
        // pos.h = pos.h % ( 2.0 * Math.PI
        //if( po.h > Math.PI ) pos.h -=2.0 * math.PI
        //if( po.h < Math.PI ) pos.h +=2.0 * math.PI

    }
}


