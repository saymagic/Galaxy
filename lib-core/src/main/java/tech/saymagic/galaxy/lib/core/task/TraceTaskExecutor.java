package tech.saymagic.galaxy.lib.core.task;

import java.util.concurrent.Executor;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class TraceTaskExecutor{

    private Executor mExecutor;

    public TraceTaskExecutor(Executor executor) {
        mExecutor = executor;
    }

    public void execute(TraceTask task) {
        if (mExecutor == null) {
            task.run();
        } else {
            mExecutor.execute(task);
        }
    }

}
