package tech.saymagic.galaxy.lib.core.filter;

import tech.saymagic.galaxy.lib.core.locus.Locus;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/20.
 */

public class LevelFilter implements IFilter{

    private Set<String> mLevelList;

    public LevelFilter(String[] params){
        init(params);
    }

    public LevelFilter(Set<String> levelList) {
        mLevelList = levelList == null ? Collections.EMPTY_SET : levelList;
    }

    private void init(String[] params) {
        if (params == null || params.length == 0) {
            mLevelList = Collections.EMPTY_SET;
        } else {
            mLevelList = new HashSet<>(Arrays.asList(params));
        }
    }

    @Override
    public boolean canTrace(Locus locus) {
        String level = locus.getLevel();
        return mLevelList.contains(level);
    }

}
