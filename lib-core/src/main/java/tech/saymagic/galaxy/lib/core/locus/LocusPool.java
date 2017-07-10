package tech.saymagic.galaxy.lib.core.locus;

import tech.saymagic.galaxy.lib.core.util.pool.GalaxyPool;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class LocusPool extends GalaxyPool<Locus> {

    /**
     * Creates a new instance.
     *
     * @param maxPoolSize The max pool size.
     * @throws IllegalArgumentException If the max pool size is less than zero.
     */
    public LocusPool(int maxPoolSize) {
        super(maxPoolSize);
    }

    @Override
    protected Locus createNew() {
        return new Locus(this);
    }

}
