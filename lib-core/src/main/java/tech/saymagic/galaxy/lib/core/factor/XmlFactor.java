package tech.saymagic.galaxy.lib.core.factor;

import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.factory.GalaxyBridge;

import java.io.File;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class XmlFactor extends Factor {

    public XmlFactor(File file, GalaxyBridge factory) {
        super(factory);
    }

    @Override
    public Set<Appender> getAppenders() {
        return null;
    }

    @Override
    public int getThreadSize() {
        return 0;
    }
}
