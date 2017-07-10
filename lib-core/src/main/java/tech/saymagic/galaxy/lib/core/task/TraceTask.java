package tech.saymagic.galaxy.lib.core.task;

import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.locus.Locus;
import tech.saymagic.galaxy.lib.core.util.pool.IRecyclable;

import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class TraceTask implements Runnable, IRecyclable {

    private Set<Appender> mAppenderSet;

    private TraceTaskExecutor mExecutor;

    private TraceTaskPool mTraceTaskPool;

    private Locus mLocus;

    public TraceTask(Set<Appender> appenderSet, TraceTaskExecutor executor, TraceTaskPool traceTaskPool) {
        mAppenderSet = appenderSet;
        mExecutor = executor;
        this.mTraceTaskPool = traceTaskPool;
    }

    @Override
    public void run() {
        if (mLocus != null && mAppenderSet != null) {
            for (Appender appender : mAppenderSet) {
                appender.append(mLocus);
            }
            this.recycle();
        }
    }

    @Override
    public void recycle() {
        if (mLocus != null) {
            mLocus.recycle();
        }
        mLocus = null;
        if (mTraceTaskPool != null) {
            mTraceTaskPool.release(this);
        }
    }

    public void trace(Locus locus) {
        this.mLocus = locus;
        mExecutor.execute(this);
    }

}
