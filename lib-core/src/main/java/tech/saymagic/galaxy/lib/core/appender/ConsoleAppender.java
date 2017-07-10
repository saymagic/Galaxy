package tech.saymagic.galaxy.lib.core.appender;

import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.locus.Locus;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class ConsoleAppender extends Appender {

    public ConsoleAppender(IFilter IFilter, ILayout ILayout) {
        super(IFilter, ILayout);
    }

    @Override
    protected void internalAppend(Locus locus, byte[] msg) {
        System.out.println(locus.getLevel() + " " + new String(msg));
    }

}
