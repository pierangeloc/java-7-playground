package com.pierangeloc.foundation.ocp.io;

import java.io.IOException;
import java.nio.file.*;

/**
 * Created by pierangeloc on 8-11-14.
 */
public class WatchServicePlayground {

    public static void monitorTmpFolderActivity() throws IOException {
        //1. create watchService (from fs)
        WatchService watchService = FileSystems.getDefault().newWatchService();

        //2. register watchService on the path we want to watch. Tell him which events we want to monitor
        Path tmpPath = Paths.get("/tmp/mysql-configuration");
        tmpPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        //wait (blocking) for events to occur
        while(true) {
            WatchKey watchKey = null;
            try {
                //blocking call, will have the current thread waiting until a watchkey is created, i.e. something happens in the directory being watched
                watchKey = watchService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(watchKey == null) {
                return;
            }

            for(WatchEvent watchEvent : watchKey.pollEvents()) {
                WatchEvent.Kind watchEventKind =  watchEvent.kind();
                System.out.println("event occurred");
                System.out.println("kind-context: " + watchEvent.context()); //context where the event occurred, like the name of directory/file being altered
                System.out.println("kind-name: " + watchEventKind.name());//name of event (ENTRY_DELETE/CREATE/MODIFY)
                System.out.println("kind-type: " + watchEventKind.type());//type affected by event (class) in our case always a Path
            }
            watchKey.reset(); //ready to wait for next event. N.B. we call reset on the key, not on the service!!!
        }

    }

    public static void main(String[] args) throws IOException {
        monitorTmpFolderActivity();
    }
}
