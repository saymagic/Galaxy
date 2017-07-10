package tech.saymagic.galaxy.lib.core.appender;

import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.locus.Locus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class FileAppender extends Appender {

    private OutputStream mOutputStream;

    public FileAppender(String[] params, IFilter IFilter, ILayout ILayout) {
        super(IFilter, ILayout);
        init(params);
    }

    private void init(String[] params) {
        if (params == null || params.length < 1) {
            return;
        }
        String path = params[0];
        File file = new File(path);
        if (file.isDirectory()) {
            file.delete();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            mOutputStream = new FileOutputStream(new File(path));
        } catch (IOException e) {
            mOutputStream = null;
        }
    }

    @Override
    protected void internalAppend(Locus locuses, byte[] msg) {
        if (mOutputStream != null) {
            try {
                mOutputStream.write(msg, 0, msg.length);
                mOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
