package zad1.folder_size;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long> {

    private final File folder;

    public FolderSizeCalculator(File folder) {
        this.folder = folder;
    }

    @Override
    protected Long compute() {

        long size = 0;
        List<FolderSizeCalculator> subTasks = new ArrayList<>();

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    subTasks.add(new FolderSizeCalculator(file));
                } else {
                    size += file.length();
                }
            }
        }

        if (!subTasks.isEmpty()) {
            for (FolderSizeCalculator subTask : invokeAll(subTasks)) {
                size += subTask.join();
            }
        }

        return size;
    }

    public static void main(String[] args) {

        File rootFolder = new File("").getAbsoluteFile().getAbsoluteFile();
        FolderSizeCalculator task = new FolderSizeCalculator(rootFolder);

        ForkJoinPool pool = new ForkJoinPool();
        Long result = pool.invoke(task);
        System.out.println("Size of folder: " + result + " bytes");
    }
}

