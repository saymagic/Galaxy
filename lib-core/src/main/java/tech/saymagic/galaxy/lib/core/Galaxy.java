package tech.saymagic.galaxy.lib.core;

import android.support.annotation.NonNull;

import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.factor.Factor;
import tech.saymagic.galaxy.lib.core.locus.LocusPool;
import tech.saymagic.galaxy.lib.core.star.Star;
import tech.saymagic.galaxy.lib.core.task.TraceTaskExecutor;
import tech.saymagic.galaxy.lib.core.task.TraceTaskPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class Galaxy {

    private static final AtomicInteger sThreadNameCreater = new AtomicInteger();

    private static Object sLock = new Object();

    private static Set<Appender> sAppenderSet;

    private static  LocusPool sLocusPool;

    private static TraceTaskPool sTraceTaskPool;

    private static TraceTaskExecutor sTraceTaskExecutor;

    private static Map<String, Star> sStars;

    public static void bigBang(Factor factor) {
        sStars = new HashMap<>();
        sAppenderSet = new CopyOnWriteArraySet<>(factor.getAppenders());
        sLocusPool = new LocusPool(50);
        int threadSize = factor.getThreadSize();
        if (threadSize > 0) {
            sTraceTaskExecutor = new TraceTaskExecutor(Executors.newFixedThreadPool(factor.getThreadSize(), new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    return new Thread(r, Constants.GALAXY + sThreadNameCreater.incrementAndGet());
                }
            }));
        } else {
            sTraceTaskExecutor = new TraceTaskExecutor(null);
        }
        sTraceTaskPool = new TraceTaskPool(50, sAppenderSet, sTraceTaskExecutor);
    }


    public static Star get(String mainTag) {
        Star star = sStars.get(mainTag);
        if (star == null) {
            synchronized (sLock) {
                if (!sStars.containsKey(mainTag)) {
                    star = new Star(mainTag, sLocusPool, sTraceTaskPool);
                    sStars.put(mainTag, star);
                } else {
                    star = sStars.get(mainTag);
                }
            }
        }
        return star;
    }


    public static void destory(Star star) {
        if (star == null) {
            return;
        }
        synchronized (sLock) {
            sStars.remove(star.getMajorTag());
        }
    }

}
