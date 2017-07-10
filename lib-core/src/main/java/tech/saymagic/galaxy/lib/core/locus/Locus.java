package tech.saymagic.galaxy.lib.core.locus;

import tech.saymagic.galaxy.lib.core.util.pool.IRecyclable;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class Locus implements IRecyclable {

    private LocusPool mLocusPool;

    private String mMajorTag;

    private String mLevel;

    private Set<String> mMinorTag;

    private String mMsg;

    private long mThreadId;

    private String mThreadName;

    private StackTraceElement[] mStackTraceElements;

    public Locus(LocusPool locusPool) {
        mLocusPool = locusPool;
    }

    public String getMajorTag() {
        return mMajorTag;
    }

    public Set<String> getMinorTag() {
        return mMinorTag == null ? new HashSet<String>() : new HashSet<String>(mMinorTag);
    }

    public void addMinorTag(String tag) {
        if (mMinorTag == null) {
            synchronized (Locus.this) {
                if (mMinorTag == null) {
                    mMinorTag = new HashSet<>();
                }
            }
        }
        mMinorTag.add(tag);
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        mLevel = level;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMajorTag(String majorTag) {
        mMajorTag = majorTag;
    }

    public void setMinorTag(Set<String> minorTag) {
        mMinorTag = minorTag;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    /**
     * must call on the invoke thread
     */
    public void collectCurrentThreadInfo() {
        Thread thread = Thread.currentThread();
        mThreadId = thread.getId();
        mThreadName = thread.getName();
        mStackTraceElements = thread.getStackTrace();
    }

    public long getThreadId() {
        return mThreadId;
    }

    public String getThreadName() {
        return mThreadName;
    }

    public StackTraceElement[] getStackTraceElements() {
        return mStackTraceElements;
    }

    @Override
    public void recycle() {
        mMajorTag = null;
        if (mMinorTag != null) {
            mMinorTag.clear();
        }
        mStackTraceElements = null;
        mThreadId = -1l;
        mThreadName = null;
        mMsg = null;
        mLevel = null;
        mLocusPool.release(this);
    }

}
