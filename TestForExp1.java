package cn.edu.whu;

import java.io.*;

public class TestForExp1 {

    public static void main(String[] args) {
	// write your code here
        ReadyQueue queue = new ReadyQueue();
        System.out.println("How many processes you want to run?");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int times;
        try {
            times = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
            times = 0;
        }
        for (int i = 0; i < times; i++) {
            System.out.println("Please input the process in the format of name required time piority.");
            try {
                String processStr = reader.readLine();
                String[] processInfo = processStr.split(" ");
                String name = processInfo[0];
                Integer requiredTime = Integer.parseInt(processInfo[1]);
                Integer priority = Integer.parseInt(processInfo[2]);
                queue.addProcess(name, requiredTime, priority);
            } catch (Exception e) {
                i--;
                System.out.println("Format error, please try again.");
            }
        }
        while (queue.runNextProcess());

    }
}
