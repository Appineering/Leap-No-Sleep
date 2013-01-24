/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package leapmouse;

/**
 * ****************************************************************************\
 * Copyright (C) 2012-2013 Leap Motion, Inc. All rights reserved. * Leap Motion
 * proprietary and confidential. Not for distribution. * Use subject to the
 * terms of the Leap Motion SDK Agreement available at *
 * https://developer.leapmotion.com/sdk_agreement, or another agreement *
 * between Leap Motion and you, your company or other organization. *
 * \*****************************************************************************
 */
import java.io.IOException;
import java.lang.Math;
import com.leapmotion.leap.*;
import java.awt.AWTException;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

class SampleListener extends Listener {

    public Screen iScreen;
    public Controller iController;
    private Robot robot;
    private int cursorid;
    private int scrollid;
    private int clickid;

    @Override
    public void onInit(Controller controller) {

        System.out.println("Initialized");

        try {
            robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(SampleListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onConnect(Controller controller) {
        System.out.println("Connected");
        cursorid = -1;
        scrollid = -1;
        clickid = -1;
        iScreen = controller.calibratedScreens().get(0);
    }

    @Override
    public void onDisconnect(Controller controller) {
        System.out.println("Disconnected");
    }

    @Override
    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    @Override
    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
        Pointable[] pointers = getPointers(frame);

        Pointable cursor = pointers[0];
        Pointable scroller = pointers[1];
        Pointable clicker = pointers[2];
        if (cursor != null && scroller==null) {
            Vector v = iScreen.intersect(cursor, true, 1);

            float pointerX = v.getX() * iScreen.widthPixels();
            float pointerY = v.getY() * iScreen.heightPixels();
            System.out.println("Position: " + pointerX + ", " + pointerY);

            pointerY = iScreen.heightPixels() - pointerY;
            int x = Math.round(pointerX);
            int y = Math.round(pointerY);
            if (Math.abs(cursor.tipVelocity().getX()) > 5 && Math.abs(cursor.tipVelocity().getY()) > 7) {
                //System.out.println("Position: " +  + ", " + cursor.tipVelocity().magnitude());
                robot.mouseMove(x, y);
            }
        }else
        if (cursor != null && scroller!=null) {
            Vector v = iScreen.intersect(cursor, true, 1);

            float pointerX = v.getX() * iScreen.widthPixels();
            float pointerY = v.getY() * iScreen.heightPixels();
            System.out.println("Position: " + pointerX + ", " + pointerY);

            pointerY = iScreen.heightPixels() - pointerY;
            int x = Math.round(pointerX);
            int y = Math.round(pointerY);
            //System.out.println("Distance: " + scroller.tipPosition().distanceTo(cursor.tipPosition()));

                if (scroller.tipPosition().distanceTo(cursor.tipPosition()) < 150 && scroller.tipPosition().distanceTo(cursor.tipPosition()) > 40
                        ) {
                                System.out.println("Speed: " + cursor.tipVelocity().getY());

                    if(cursor.tipVelocity().getY()>50){
                        robot.mouseWheel(-1);
                    }
                    if(cursor.tipVelocity().getY()>100){
                        robot.mouseWheel(-3);
                    }
                    if(cursor.tipVelocity().getY()>150){
                        robot.mouseWheel(-5);
                    }
                    if(cursor.tipVelocity().getY()>200){
                        robot.mouseWheel(-7);
                    }
                    if(cursor.tipVelocity().getY()>250){
                        robot.mouseWheel(-9);
                    }
                    if(cursor.tipVelocity().getY()>300){
                        robot.mouseWheel(-11);
                    }
                    if(cursor.tipVelocity().getY()>450){
                        robot.mouseWheel(-13);
                    }
                    if(cursor.tipVelocity().getY()>500){
                        robot.mouseWheel(-15);
                    }
                    
                    
                    if(cursor.tipVelocity().getY()<-50){
                        robot.mouseWheel(1);
                    }
                    if(cursor.tipVelocity().getY()<-100){
                        robot.mouseWheel(3);
                    }
                    if(cursor.tipVelocity().getY()<-150){
                        robot.mouseWheel(5);
                    }
                    if(cursor.tipVelocity().getY()<-200){
                        robot.mouseWheel(7);
                    }
                    if(cursor.tipVelocity().getY()<-250){
                        robot.mouseWheel(9);
                    }
                    if(cursor.tipVelocity().getY()<-300){
                        robot.mouseWheel(11);
                    }
                    if(cursor.tipVelocity().getY()<-450){
                        robot.mouseWheel(13);
                    }
                    if(cursor.tipVelocity().getY()<-500){
                        robot.mouseWheel(15);
                    }
                }
        }
        //if (thumb.tipPosition().distanceTo(cursor.tipPosition())<5){
        //   robot.mouseWheel(Math.round(cursor.tipVelocity().getY()*-1));
        //} 
        //if (cursor)
        /*System.out.println("Frame id: " + frame.id()
         + ", timestamp: " + frame.timestamp()
         + ", hands: " + frame.hands().count()
         + ", fingers: " + frame.fingers().count()
         + ", tools: " + frame.tools().count());

         if (!frame.hands().empty()) {
         // Get the first hand
         Hand hand = frame.hands().get(0);

         // Check if the hand has any fingers
         FingerList fingers = hand.fingers();
         if (!fingers.empty()) {
         // Calculate the hand's average finger tip position
         Vector avgPos = Vector.zero();
         for (Finger finger : fingers) {
         avgPos = avgPos.plus(finger.tipPosition());
         }
         avgPos = avgPos.divide(fingers.count());
         System.out.println("Hand has " + fingers.count()
         + " fingers, average finger tip position: " + avgPos);
         }

         // Get the hand's sphere radius and palm position
         System.out.println("Hand sphere radius: " + hand.sphereRadius()
         + " mm, palm position: " + hand.palmPosition());

         // Get the hand's normal vector and direction
         Vector normal = hand.palmNormal();
         Vector direction = hand.direction();

         // Calculate the hand's pitch, roll, and yaw angles
         System.out.println("Hand pitch: " + Math.toDegrees(direction.pitch()) + " degrees, "
         + "roll: " + Math.toDegrees(normal.roll()) + " degrees, "
         + "yaw: " + Math.toDegrees(direction.yaw()) + " degrees\n");
         }
        
         */


    }

    public Pointable[] getPointers(Frame frame) {
        Pointable cursor = null;
        Pointable scroller = null;
        Pointable clicker = null;
        if (frame.pointables().count() == 0) {
            cursorid=-1;
            scrollid=-1;
            clickid=-1;
        }
        if (frame.pointables().count() >= 1) {
            if (frame.pointables().count() == 1) {
                scrollid=-1;
            clickid=-1;
            }
            if (cursorid == -1) {
                cursor = frame.pointables().get(0);
                cursorid = cursor.id();
                
            } else {
                for (int i = 0; i <= frame.pointables().count(); i++) {

                    Pointable temp = frame.pointables().get(i);
                    if (temp.id() == cursorid) {
                        cursor = temp;
                    } else {
                        cursorid = -1;
                    }
                }
            }
        }

        if (frame.pointables().count() >= 2) {
            if (scrollid == -1 | clickid == -1 && cursorid!=-1) {
               // Pointable temp1 = frame.pointables().get()
                if (cursor.hand().id() == frame.pointables().get(1).hand().id()) {
                    scroller = frame.pointables().get(1);
                    scrollid = scroller.id();
                } else {
                    clicker = frame.pointables().get(1);
                    clickid = clicker.id();
                }

            } else if (cursorid!=-1){
                for (int i = 0; i <= frame.pointables().count(); i++) {

                    Pointable temp = frame.pointables().get(i);
                    if (temp.id() == scrollid) {
                        scroller = temp;
                    } else if (temp.id() == clickid) {
                        clicker = temp;
                    }
                }
            }
        }

        System.out.println("Id: " + cursorid+" "+scrollid+" "+scrollid+" ");

        Pointable[] pointables = new Pointable[3];
        pointables[0] = cursor;
        pointables[1] = scroller;
        pointables[2] = clicker;

        return pointables;
    }
}

class LeapMouse {

    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();


        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);
    }
}
