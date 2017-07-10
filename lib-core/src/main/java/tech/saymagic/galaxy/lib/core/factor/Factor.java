package tech.saymagic.galaxy.lib.core.factor;

import android.text.TextUtils;

import tech.saymagic.galaxy.lib.core.Constants;
import tech.saymagic.galaxy.lib.core.appender.AndroidLogAppender;
import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.appender.ConsoleAppender;
import tech.saymagic.galaxy.lib.core.appender.EmptyAppender;
import tech.saymagic.galaxy.lib.core.appender.FileAppender;
import tech.saymagic.galaxy.lib.core.factory.GalaxyBridge;
import tech.saymagic.galaxy.lib.core.filter.EmptyFilter;
import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.filter.LevelFilter;
import tech.saymagic.galaxy.lib.core.filter.TagFilter;
import tech.saymagic.galaxy.lib.core.layout.EmptyLayout;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.layout.SimpleLayout;

import java.io.File;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public abstract class Factor {

    private GalaxyBridge mBridge;

    public Factor(GalaxyBridge bridge) {
        mBridge = bridge;
    }

    public static Factor creatFromXml(File file, GalaxyBridge factory){
        return new XmlFactor(file, factory);
    }

    public static Factor creatFromJson(File file, GalaxyBridge factory){
        return new JsonFactor(file, factory);
    }

    public static Factor creatFromJson(String ori, GalaxyBridge factory){
        return new JsonFactor(ori, factory);
    }

    public abstract Set<Appender> getAppenders();

    public abstract int getThreadSize();

    public ILayout generateLayout(String type, String[] params){
        if (TextUtils.isEmpty(type)) {
            return EmptyLayout.DEFAULT;
        }
        switch (type) {
            case Constants.LAYOUT_SIMPLE:
                return new SimpleLayout();
        }
        ILayout layout = null;
        if (mBridge != null) {
            layout = mBridge.createLayout(type, params);
        }
        return layout == null ? EmptyLayout.DEFAULT : layout;
    }

    public IFilter generateFilter(String type, String[] params) {
        if (TextUtils.isEmpty(type)) {
            return EmptyFilter.INSTANCE;
        }
        switch (type) {
            case Constants.FILTER_TAG:{
                return new TagFilter(params);
            }
            case Constants.FILTER_LEVEL:{
                return new LevelFilter(params);
            }
        }
        IFilter filter = null;
        if (mBridge != null) {
            filter = mBridge.createFilter(type, params);
        }
        return filter == null ? EmptyFilter.INSTANCE : filter;
    }

    public Appender generateAppender(String type, String[] params, IFilter filter, ILayout layout) {
        if (TextUtils.isEmpty(type)) {
            return EmptyAppender.INSTANCE;
        }
        switch (type) {
            case Constants.APPENDER_CONSOLE:
                return new ConsoleAppender(filter, layout);
            case Constants.APPENDER_FILE:
                return new FileAppender(params, filter, layout);
            case Constants.APPENDER_ANDROID:
                return new AndroidLogAppender(filter, layout);
        }
        Appender appender = null;
        if (mBridge != null) {
            appender = mBridge.createAppender(type, params, filter, layout);
        }
        return appender == null ? EmptyAppender.INSTANCE : appender;
    }
}
