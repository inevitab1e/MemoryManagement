package core;

public class PageTable {
    private int pid;
    private int size;
    private int[] physicalBlockNumber;

    public PageTable(int pid,int size) {
        this.pid = pid;
        this.size = size;
        physicalBlockNumber = new int[size];
    }

    public int getSize() {
        return size;
    }

    public int getPid() {
        return pid;
    }

    public int[] getPhysicalBlockNumber() {
        return physicalBlockNumber;
    }

    public void setPhysicalBlockNumber(int index, int value) {
        physicalBlockNumber[index] = value;
    }

    public String displayCurrentPageTableStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = size - 1; i >= 0; i--) {
            stringBuilder.append(physicalBlockNumber[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}
