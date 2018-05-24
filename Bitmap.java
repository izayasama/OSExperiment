package cn.edu.whu;

import java.util.ArrayList;

public class Bitmap {
    int [] bitmap = new int[64];
    int remainingBlocks = 64;
    ArrayList<Process> processes = new ArrayList<>();

    public boolean allocMem (int blocks, Process process) {
        if (blocks > remainingBlocks) {
            return false;
        } else {
            remainingBlocks -= blocks;
            processes.add(process);
            for (int i = 0; i < blocks; i++) {
                for (int j = 0; j < 64; j++) {
                    if (bitmap[j] == 0) {
                        bitmap[j] = 1;
                        process.pageTable[i] = j;
                        break;
                    }
                }
            }
            return true;
        }

    }
    private int findProcess(String name) {
        for (int i = 0; i < processes.size(); i++) {
            if (processes.get(i).name.equals(name)) {
                return i;
            }
        }
        return -1;
    }
    public boolean releaseSpace(String name) {
        int index = findProcess(name);
        if (index != -1){
            Process process = processes.get(index);
            for (int i = 0; i < process.blocks; i++) {
                bitmap[process.pageTable[i]] = 0;
            }
            remainingBlocks += process.blocks;
            processes.remove(index);
            return true;
        } else
            return false;
    }

    public void showBitmap() {
        for (int i = 0; i < 8; i++) {
            int m = i * 8;
            for (int j = 0; j < 8; j++) {
                System.out.print(bitmap[m + j]+" ");
            }
            System.out.println();
        }
    }

}

class Process {
    String name;
    int blocks;
    int[] pageTable;

    public Process(String name, int blocks) {
        this.name = name;
        this.blocks = blocks;
        this.pageTable = new int[blocks];
    }
    void showInfo() {
        System.out.println("Process: " + name);
        System.out.println("Total Blocks: " + blocks);
        System.out.println("Virtual Page Number\tPhysical Page number");
        for (int i = 0; i < blocks; i++) {
            System.out.println(i + "\t\t\t\t\t" +  pageTable[i]);
        }
    }
}