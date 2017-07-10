package tech.saymagic.galaxy.lib.core.factory;

import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;

/**
 * Created by caoyanming on 2017/5/18.
 */

public abstract class GalaxyBridge {

    public abstract Appender createAppender(String name, String[] params, IFilter filter, ILayout layout);

    public abstract IFilter createFilter(String name, String[] params);

    public abstract ILayout createLayout(String name, String[] params);

}
