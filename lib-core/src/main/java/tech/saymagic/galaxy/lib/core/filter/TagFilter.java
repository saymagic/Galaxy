package tech.saymagic.galaxy.lib.core.filter;

import tech.saymagic.galaxy.lib.core.locus.Locus;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class TagFilter implements IFilter {

    private Set<String> mTagList;

    public TagFilter(String[] params){
        init(params);
    }

    private void init(String[] params) {
        if (params == null || params.length == 0) {
            mTagList = Collections.EMPTY_SET;
        } else {
            mTagList = new HashSet<>(Arrays.asList(params));
        }
    }

    public TagFilter(Set<String> tagList) {
        mTagList = tagList == null ? Collections.EMPTY_SET : tagList;
    }

    @Override
    public boolean canTrace(Locus locus) {
        Set<String> tags = locus.getMinorTag();
        tags.add(locus.getMajorTag());
        for (String s : mTagList) {
            if (tags.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
