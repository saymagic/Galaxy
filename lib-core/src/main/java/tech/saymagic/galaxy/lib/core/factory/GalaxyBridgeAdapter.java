package tech.saymagic.galaxy.lib.core.factory;

import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class GalaxyBridgeAdapter extends GalaxyBridge {

    @Override
    public Appender createAppender(String name, String[] params, IFilter filter, ILayout layout) {
        return null;
    }

    @Override
    public IFilter createFilter(String name, String[] params) {
        return null;
    }

    @Override
    public ILayout createLayout(String name, String[] params) {
        return null;
    }
}
