package tech.saymagic.galaxy.lib.core.filter;

import tech.saymagic.galaxy.lib.core.locus.Locus;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class EmptyFilter implements IFilter {

    public static final EmptyFilter INSTANCE = new EmptyFilter();

    @Override
    public boolean canTrace(Locus locus) {
        return false;
    }

}
