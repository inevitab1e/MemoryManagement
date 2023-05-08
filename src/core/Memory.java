package core;

import java.util.ArrayDeque;
import java.util.Queue;

public class Memory {
    private final int size = 102400;
    private int emptyPhysicalBlockNumber;
    private int pageSize;
    private int[] memoryPhysicalBlock;
    Queue<PageTable> pageTableQueue;

    public Memory(int pageSize) {
        this.pageSize = pageSize;
        memoryPhysicalBlock = new int[size / pageSize];
        emptyPhysicalBlockNumber = memoryPhysicalBlock.length;
        pageTableQueue = new ArrayDeque<>();
    }

    public int getPageSize() {
        return pageSize;
    }

    public int updateMemory(int index, int value) {
        return memoryPhysicalBlock[index] = value;
    }

    public int[] getMemoryPhysicalBlock() {
        return memoryPhysicalBlock;
    }

    public int getEmptyPhysicalBlockNumber() {
        return emptyPhysicalBlockNumber;
    }

    public void setEmptyPhysicalBlockNumber(int emptyPhysicalBlockNumber) {
        this.emptyPhysicalBlockNumber = emptyPhysicalBlockNumber;
    }

    public void addPageTable(PageTable pageTable) {
        pageTableQueue.add(pageTable);
    }

    public void removePageTable() {
        pageTableQueue.poll();
    }

    public String displayCurrentMemoryStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for (PageTable pageTable : pageTableQueue) {
            stringBuilder.append("Process" + pageTable.getPid() + ": ");
            stringBuilder.append(pageTable.displayCurrentPageTableStatus());
            stringBuilder.append("\n");
        }
        stringBuilder.append("Empty: " + emptyPhysicalBlockNumber + "\n");
        return stringBuilder.toString();
    }
}
