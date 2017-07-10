package tech.saymagic.galaxy.lib.core.appender;

import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.locus.Locus;

/**
 * Created by caoyanming on 2017/5/18.
 */

public abstract class Appender {

    protected IFilter mIFilter;

    protected ILayout mILayout;

    public Appender(IFilter IFilter, ILayout ILayout) {
        mIFilter = IFilter;
        mILayout = ILayout;
    }

    public void append(Locus locus) {
        if (mIFilter.canTrace(locus)) {
            byte[] finalMsg = mILayout.format(locus);
            internalAppend(locus, finalMsg);
        }
    }

    protected abstract void internalAppend(Locus locus, byte[] msg);

}
