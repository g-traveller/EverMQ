package org.everteam.evermq.util;

public class SystemInformation {

    public static int getProcessorCoreCount() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.availableProcessors();
    }
}
