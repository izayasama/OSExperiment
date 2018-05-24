package cn.edu.whu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestForExp2 {
    public static void main(String[] args) {
        Bitmap bitmap = new Bitmap();
        bitmap.showBitmap();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Enter X to jump out program.");
            System.out.println("Enter 1 with process info to create a process." +
                    "Enter 0 with process name to terminate a process");
            try {
                String processStr = reader.readLine();
                String[] processInfo = processStr.split(" ");
                String control = processInfo[0];
                if (control.equals("X") || control.equals("x"))
                    return;
                else if(control.equals("1")) {
                    String name = processInfo[1];
                    Integer blocks = Integer.parseInt(processInfo[2]);
                    Process process = new Process(name, blocks);
                    if (bitmap.allocMem(blocks, process)){
                        process.showInfo();
                        bitmap.showBitmap();
                    } else {
                        System.out.println("Insufficient Memory!");
                    }
                } else if (control.equals("0")){
                    String name = processInfo[1];
                    if (bitmap.releaseSpace(name)) {
                        System.out.println("Process " + name + " has been terminated.");
                        bitmap.showBitmap();
                    } else {
                        System.out.println("Please Enter Valid Process Name");
                    }
                } else {
                    System.out.println("Format error, please try again.");
                }


            } catch (IOException e) {
                System.out.println("Format error, please try again.");
            }
        }

    }
}
