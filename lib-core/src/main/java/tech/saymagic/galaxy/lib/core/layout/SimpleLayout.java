package tech.saymagic.galaxy.lib.core.layout;

import tech.saymagic.galaxy.lib.core.locus.Locus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class SimpleLayout implements ILayout {

    private static final SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public byte[] format(Locus locus) {
        StringBuilder builder = new StringBuilder();
        Calendar calender = Calendar.getInstance();
        String date = DATE_FORMAT.format(calender.getTime());
        builder.append(date).append(" ").append(locus.getThreadName())
                .append(locus.getMajorTag()).append(" - ");
        Set<String> tags = locus.getMinorTag();
        if (tags != null && !tags.isEmpty()) {
            builder.append("[");
            for (String tag : tags) {
                builder.append(tag).append(",");
            }
            builder.append(locus.getLevel());
            builder.append("] ");
        }
        builder.append(locus.getMsg()).append("\r\n");
        return builder.toString().getBytes();
    }

}
