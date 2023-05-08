package sim_memory_management;

import core.Memory;
import core.Process;

import java.util.Queue;

public class SimMemManagementSystem {
    private Memory memory;

    public SimMemManagementSystem(int pageSize) {
        memory = new Memory(pageSize);
    }

    public Memory getMemory() {
        return memory;
    }

    public void allocateMemory(int currentTime, Queue<Process> readyQueue, Queue<Process> runningQueue) {
        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.peek();
            process.initPageTable(memory.getPageSize());
            int NumOfPages = (int) Math.ceil((double) process.getSize() / memory.getPageSize());
            if (memory.getEmptyPhysicalBlockNumber() < NumOfPages) {
                System.out.println("Memory is full! Please wait for a while.");
                break;
            }
            for (int index = 0; index < memory.getMemoryPhysicalBlock().length; index++) {
                if (memory.getMemoryPhysicalBlock()[index] == 0) {
                    memory.updateMemory(index, process.getPid());
                    process.getPageTable().setPhysicalBlockNumber(NumOfPages - 1, index);
                    NumOfPages--;
                    memory.setEmptyPhysicalBlockNumber(memory.getEmptyPhysicalBlockNumber() - 1);
                }
                if (NumOfPages == 0) {
                    memory.addPageTable(process.getPageTable());
                    Process poll = readyQueue.poll();
                    runningQueue.add(poll);
                    break;
                }
            }
        }
    }

    public void freeMemory(Queue<Process> terminatedQueue) {
        while (!terminatedQueue.isEmpty()) {
            Process process = terminatedQueue.poll();
            for (int index = 0; index < process.getPageTable().getPhysicalBlockNumber().length; index++) {
                if (process.getPageTable().getPhysicalBlockNumber()[index] != -1) {
                    memory.updateMemory(process.getPageTable().getPhysicalBlockNumber()[index], 0);
                    memory.setEmptyPhysicalBlockNumber(memory.getEmptyPhysicalBlockNumber() + 1);
                }
            }
        }
    }
}
