package tech.saymagic.galaxy.lib.core.filter;

import tech.saymagic.galaxy.lib.core.locus.Locus;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/19.
 */

public class ListFilter implements IFilter {

    Set<IFilter> mFilters;

    public ListFilter() {
        this.mFilters = new HashSet<>();
    }

    public void addFilter(IFilter filter) {
        mFilters.add(filter);
    }

    @Override
    public boolean canTrace(Locus locus) {
        for (IFilter filter : mFilters) {
            if (filter.canTrace(locus)) {
                return true;
            }
        }
        return false;
    }
}
