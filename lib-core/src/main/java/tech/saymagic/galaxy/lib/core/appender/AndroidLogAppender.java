package tech.saymagic.galaxy.lib.core.appender;

import android.util.Log;

import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.locus.Locus;
import tech.saymagic.galaxy.lib.core.Constants;

/**
 * Created by caoyanming on 2017/5/20.
 */

public class AndroidLogAppender extends Appender {

    public AndroidLogAppender(IFilter IFilter, ILayout ILayout) {
        super(IFilter, ILayout);
    }

    @Override
    protected void internalAppend(Locus locus, byte[] msg) {
        String level = locus.getLevel();
        String tag = locus.getMajorTag();
        String log = new String(msg);
        switch (level) {
            case Constants.LEVEL_V:
                Log.v(tag, log);
                break;
            case Constants.LEVEL_D:
                Log.d(tag, log);
                break;
            case Constants.LEVEL_I:
                Log.i(tag, log);
                break;
            case Constants.LEVEL_W:
                Log.w(tag, log);
                break;
            case Constants.LEVEL_EX:
                Log.wtf(tag, log);
                break;
        }
    }

}
