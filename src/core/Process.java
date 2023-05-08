package core;

public class Process {
    private final int pid;
    private final int arrivalTime;
    private final int serviceTime;
    private int startTime;
    private final int size;
    private PageTable pageTable;

    public Process(int pid, int arrivalTime, int serviceTime, int size) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.size = size;
        this.startTime = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getSize() {
        return size;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getPid() {
        return pid;
    }

    public PageTable getPageTable() {
        return pageTable;
    }

    public void initPageTable(int pageSize) {
        pageTable = new PageTable(pid,(int) Math.ceil((double) size / pageSize));
    }
}
