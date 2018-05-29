package cn.edu.whu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestForExp3 {
    public static void main(String[] args) {
        FreeSpaceTable fsTable = new FreeSpaceTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Enter X to jump out program.");
            System.out.println("Enter 1 with file info to create a file. " +
                    "Enter 0 with process name to terminate a process");
            try {
                String processStr = reader.readLine();
                String[] processInfo = processStr.split(" ");
                String control = processInfo[0];
                if (control.equals("X") || control.equals("x"))
                    return;
                else if(control.equals("1")) {
                    String name = processInfo[1];
                    Integer size = Integer.parseInt(processInfo[2]);
                    File file = new File(size, name);
                    if (fsTable.createFile(file)){
                        file.fileArea.showDiskInfo();
                        System.out.println("\tStarting Block\tBlocks");
                        for (int i = 0; i < fsTable.fsTable.size(); i++) {
                            System.out.println(i+ "\t\t" + fsTable.fsTable.get(i));
                        }
                    } else {
                        System.out.println("Insufficient Space!");
                    }
                } else if (control.equals("0")){
                    String name = processInfo[1];
                    if (fsTable.freeSpace(name)) {
                        System.out.println("Process " + name + " has been terminated.");
                        System.out.println("\tStarting Block\tBlocks");
                        for (int i = 0; i < fsTable.fsTable.size(); i++) {
                            System.out.println(i+ "\t" + fsTable.fsTable.get(i));
                        }
                    } else {
                        System.out.println("Please Enter Valid Process Name");
                    }
                } else {
                    System.out.println("Format error, please try again.");
                }


            } catch (Exception e) {
                System.out.println("Format error, please try again.");
            }
        }


    }
}
