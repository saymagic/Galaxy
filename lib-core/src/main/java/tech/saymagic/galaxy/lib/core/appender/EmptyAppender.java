package tech.saymagic.galaxy.lib.core.appender;

import tech.saymagic.galaxy.lib.core.filter.EmptyFilter;
import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.EmptyLayout;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.locus.Locus;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class EmptyAppender extends Appender {

    public static final EmptyAppender INSTANCE = new EmptyAppender(EmptyFilter.INSTANCE, EmptyLayout.DEFAULT);

    public EmptyAppender(IFilter IFilter, ILayout ILayout) {
        super(IFilter, ILayout);
    }

    @Override
    protected void internalAppend(Locus locus, byte[] msg) {

    }

}
