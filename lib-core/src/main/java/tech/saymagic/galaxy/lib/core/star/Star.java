package tech.saymagic.galaxy.lib.core.star;

import tech.saymagic.galaxy.lib.core.locus.Locus;
import tech.saymagic.galaxy.lib.core.locus.LocusPool;
import tech.saymagic.galaxy.lib.core.task.TraceTask;
import tech.saymagic.galaxy.lib.core.task.TraceTaskPool;

import java.util.HashSet;
import java.util.Set;

import tech.saymagic.galaxy.lib.core.Constants;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class Star {

    private String mMajorTag;

    private Set<String> mMinorTags;

    private LocusPool mLocusPool;

    private TraceTaskPool mTraceTaskPool;

    public Star(String majorTag, LocusPool locusPool, TraceTaskPool traceTaskPool) {
        mMajorTag = majorTag;
        mLocusPool = locusPool;
        mTraceTaskPool = traceTaskPool;
        mMinorTags = new HashSet<>();
    }

    public Star addMinorTag(String... tags) {
        if (tags == null) {
            return this;
        }
        for (String tag : tags) {
            mMinorTags.add(tag);
        }
        return this;
    }

    public String getMajorTag() {
        return mMajorTag;
    }

    public  void v(String s, Object... extras){
        internalTrace(Constants.LEVEL_V, s, extras);
    }

    public void d(String s, Object... extras){
        internalTrace(Constants.LEVEL_D, s, extras);
    }

    public void i(String s, Object... extras){
        internalTrace(Constants.LEVEL_I, s, extras);
    }

    public void w(String s, Object... extras){
        internalTrace(Constants.LEVEL_W, s, extras);
    }

    public void e(String s, Object... extras){
        internalTrace(Constants.LEVEL_E, s, extras);
    }

    public void exception(Throwable throwable){
        internalTrace(Constants.LEVEL_EX, throwable.getMessage());
    }


    private void internalTrace(String level, String s, Object... extras) {
        Locus locus =  mLocusPool.acquire();
        locus.collectCurrentThreadInfo();
        locus.setMajorTag(mMajorTag);
        locus.setMinorTag(new HashSet<String>(mMinorTags));
        locus.setLevel(level);
        locus.setMsg(formatAndAddTag(locus, s, extras));
        TraceTask traceTask = mTraceTaskPool.acquire();
        traceTask.trace(locus);
    }

    public static String formatAndAddTag(Locus locus, String ori, Object... objects) {
        if (objects == null || objects.length == 0) {
            return ori == null ? "" : ori;
        }
        StringBuilder sb = new StringBuilder(ori);
        int length = objects.length;
        int left = length;
        int start = 0;
        int index;
        while (left > 0) {
            String str = String.valueOf(objects[length - left]);
            if ((index = sb.indexOf("{}", start)) != -1) {
                sb.replace(index, index + 2, str);
                start += str.length();
            } else {
                locus.addMinorTag(str);
            }
            left--;
        }

        return sb.toString();
    }

}
