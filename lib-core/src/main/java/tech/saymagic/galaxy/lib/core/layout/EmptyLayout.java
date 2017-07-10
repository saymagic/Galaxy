package tech.saymagic.galaxy.lib.core.layout;

import tech.saymagic.galaxy.lib.core.locus.Locus;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class EmptyLayout implements ILayout {

    public static final EmptyLayout DEFAULT = new EmptyLayout();

    private static final byte[] EMPTY = new byte[0];

    @Override
    public byte[] format(Locus locus) {
        return EMPTY;
    }

}
