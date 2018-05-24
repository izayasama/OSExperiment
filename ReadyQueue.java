package cn.edu.whu;


import java.util.ArrayList;

class PCB {
    protected String name;
    protected PCB next;
    protected Integer requiredTime;
    protected Integer piority;
    protected boolean status;

    public PCB(String name, PCB next, Integer requiredTime, Integer piority) {
        this.name = name;
        this.next = next;
        this.requiredTime = requiredTime;
        this.piority = piority;
        this.status = true;
    }

    public void run() {
        requiredTime--;
        piority--;
        if(requiredTime <= 0) {
            status = false;
        }
    }
}

public class ReadyQueue {
    PCB queueHead;
    PCB queueTail;

    public void addProcess(String name, Integer requiredTime, Integer piority) {
        if(queueHead == null) {
            queueHead = new PCB(name, null, requiredTime, piority);
            queueTail = queueHead;
        }
        else {
            PCB queuePTR = queueHead;
            PCB queuePTRPrev = new PCB(null, queuePTR, 0, 0);
            while (queuePTR != null) {
                if (piority > queuePTR.piority) {
                    if (queuePTR == queueHead) {
                        queueHead = new PCB(name, queuePTR, requiredTime, piority);
                        return;
                    } else {
                        queuePTRPrev.next = new PCB(name, queuePTR, requiredTime, piority);
                        return;
                    }
                } else {
                    queuePTRPrev = queuePTR;
                    queuePTR = queuePTR.next;
                }
            }
            queueTail.next = new PCB(name, null, requiredTime, piority);
            queueTail = queueTail.next;

        }
    }
    public void addProcess(PCB pcb) {
        if(queueHead == null) {
            queueHead = pcb;
            queueTail = queueHead;
        }
        else {
            PCB queuePTR = queueHead;
            PCB queuePTRPrev = new PCB(null, queuePTR, 0, 0);
            while (queuePTR != null) {
                if (pcb.piority > queuePTR.piority) {
                    if (queuePTR == queueHead) {
                        queueHead = pcb;
                        pcb.next = queuePTR;
                        return;
                    } else {
                        queuePTRPrev.next = pcb;
                        pcb.next = queuePTR;
                        return;
                    }
                } else {
                    queuePTRPrev = queuePTR;
                    queuePTR = queuePTR.next;
                }
            }
            queueTail.next = pcb;
            queueTail = queueTail.next;

        }
    }

    public boolean runNextProcess() {
        PCB runningProcess;
        if (queueHead != null){
            runningProcess = queueHead;
            queueHead = queueHead.next;
            runningProcess.next = null;
            if (queueHead == null) {
                //运行到最后一个进程
                queueTail = null;
            }
            runningProcess.run();
            if (runningProcess.status == true) {
                System.out.println("Current running process is " + runningProcess.name + ".");
                addProcess(runningProcess);
                System.out.println("Remaining time: " +runningProcess.requiredTime);
                System.out.println("Piority: " + runningProcess.piority);
            }
            return true;
        }
        else {
            System.out.println("Running out of process!");
            return false;
        }
    }


}
