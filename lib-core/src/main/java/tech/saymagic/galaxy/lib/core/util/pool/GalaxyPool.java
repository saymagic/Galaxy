package tech.saymagic.galaxy.lib.core.util.pool;

/**
 * Created by caoyanming on 2017/5/18.
 */

public abstract class GalaxyPool<T extends IRecyclable> {

    private final Object[] mPool;

    private int mPoolSize;

    private Object mLock;

    /**
     * Creates a new instance.
     *
     * @param maxPoolSize The max pool size.
     *
     * @throws IllegalArgumentException If the max pool size is less than zero.
     */
    public GalaxyPool(int maxPoolSize) {
        if (maxPoolSize <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        mPool = new Object[maxPoolSize];
        mLock = new Object();
    }

    public T acquire(){
        synchronized (mLock) {
            if (mPoolSize > 0) {
                final int lastPooledIndex = mPoolSize - 1;
                T instance = (T) mPool[lastPooledIndex];
                mPool[lastPooledIndex] = null;
                mPoolSize--;
                return instance;
            }
            return createNew();
        }
    }

    public boolean release(T instance){
        synchronized (mLock) {
            if (isInPool(instance)) {
                throw new IllegalStateException("Already in the pool!");
            }
            if (mPoolSize < mPool.length) {
                mPool[mPoolSize] = instance;
                mPoolSize++;
                return true;
            }
            return false;
        }
    }

    private boolean isInPool(T instance) {
        for (int i = 0; i < mPoolSize; i++) {
            if (mPool[i] == instance) {
                return true;
            }
        }
        return false;
    }

    protected abstract T createNew();



}
