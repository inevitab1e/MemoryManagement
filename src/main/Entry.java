package main;

import sim_memory_management.SimMemManagementSystem;
import core.Process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;


public class Entry {
    public static void main(String[] args) {
        Queue<Process> newProcessQueue = new ArrayDeque<>();
        Queue<Process> readyQueue = new ArrayDeque<>();
        Queue<Process> runningQueue = new ArrayDeque<>();
        Queue<Process> terminatedQueue = new ArrayDeque<>();

        SimMemManagementSystem simMemManagementSystem;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("The memory management system is running...");
            System.out.println("The memory size is 102400B.");
            System.out.println("Please choose the page size:");
            System.out.println("1.128B");
            System.out.println("2.256B");
            System.out.println("3.512B");
            System.out.println("4.1024B");
            System.out.println("0.Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    initQueues(newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                    simMemManagementSystem = new SimMemManagementSystem(128);
                    simOperatingSystemFCFS(simMemManagementSystem, newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                }
                case 2 -> {
                    initQueues(newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                    simMemManagementSystem = new SimMemManagementSystem(256);
                    simOperatingSystemFCFS(simMemManagementSystem, newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                }
                case 3 -> {
                    initQueues(newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                    simMemManagementSystem = new SimMemManagementSystem(512);
                    simOperatingSystemFCFS(simMemManagementSystem, newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                }
                case 4 -> {
                    initQueues(newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                    simMemManagementSystem = new SimMemManagementSystem(1024);
                    simOperatingSystemFCFS(simMemManagementSystem, newProcessQueue, readyQueue, runningQueue, terminatedQueue);
                }
                case 0 -> System.exit(0);
                default -> System.out.println("Invalid input!");
            }
        }

    }

    public static void initQueues(Queue<Process> newProcessQueue, Queue<Process> readyQueue, Queue<Process> runningQueue, Queue<Process> terminatedQueue) {
        int pid = 1;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src\\input.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                newProcessQueue.add(new Process(pid, Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
                pid++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        readyQueue.clear();
        runningQueue.clear();
        terminatedQueue.clear();
    }

    public static void simOperatingSystemFCFS(SimMemManagementSystem simMemManagementSystem, Queue<Process> newProcessQueue, Queue<Process> readyQueue, Queue<Process> runningQueue, Queue<Process> terminatedQueue) {
        int currentTime = 1;
        Process process = newProcessQueue.peek();
        Process runningProcess;
        while (!(newProcessQueue.isEmpty() && readyQueue.isEmpty() && runningQueue.isEmpty())) {
            while (true) {

                if (!newProcessQueue.isEmpty() && process.getArrivalTime() <= currentTime) {
                    readyQueue.add(newProcessQueue.poll());
                    if (!newProcessQueue.isEmpty()) {
                        process = newProcessQueue.peek();
                    }
                } else {

                    break;
                }
            }

            simMemManagementSystem.allocateMemory(currentTime, readyQueue, runningQueue);

            if (!runningQueue.isEmpty()) {
                runningProcess = runningQueue.peek();
                if (runningProcess.getStartTime() == 0) {
                    runningProcess.setStartTime(currentTime);
                }
                if (currentTime - runningProcess.getStartTime() == runningProcess.getServiceTime()) {
                    terminatedQueue.add(runningQueue.poll());
                    simMemManagementSystem.getMemory().removePageTable();
                    simMemManagementSystem.freeMemory(terminatedQueue);
                }
            }
            System.out.println("Current time: " + currentTime);
            System.out.println(displayCurrentMemoryStatus(currentTime,simMemManagementSystem));
            currentTime++;
        }
    }

    public static String displayCurrentMemoryStatus(int currentTime,SimMemManagementSystem simMemManagementSystem) {
        return simMemManagementSystem.getMemory().displayCurrentMemoryStatus();
    }
}

