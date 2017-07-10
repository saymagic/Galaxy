package tech.saymagic.galaxy.lib.core.task;

import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.util.pool.GalaxyPool;

import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class TraceTaskPool extends GalaxyPool<TraceTask> {

    private TraceTaskExecutor mExecutor;

    private Set<Appender> mAppenders;

    /**
     * Creates a new instance.
     *
     * @param maxPoolSize The max pool size.
     * @throws IllegalArgumentException If the max pool size is less than zero.
     */
    public TraceTaskPool(int maxPoolSize, Set<Appender> appenders, TraceTaskExecutor executor) {
        super(maxPoolSize);
        this.mAppenders = appenders;
        this.mExecutor = executor;
    }

    @Override
    protected TraceTask createNew() {
        return new TraceTask(mAppenders, mExecutor, this);
    }
}
