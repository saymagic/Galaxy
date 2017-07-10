package tech.saymagic.galaxy.lib.core.factor;

import tech.saymagic.galaxy.lib.core.Constants;
import tech.saymagic.galaxy.lib.core.appender.Appender;
import tech.saymagic.galaxy.lib.core.factory.GalaxyBridge;
import tech.saymagic.galaxy.lib.core.filter.EmptyFilter;
import tech.saymagic.galaxy.lib.core.filter.IFilter;
import tech.saymagic.galaxy.lib.core.filter.ListFilter;
import tech.saymagic.galaxy.lib.core.layout.ILayout;
import tech.saymagic.galaxy.lib.core.util.IOUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by caoyanming on 2017/5/18.
 */

public class JsonFactor extends Factor {

    private GalaxyBridge mBridge;

    private int mMaxThreadSize = Constants.MAX_THREAD_DEFAULT_SIZE ;

    private Set<Appender> mAppenders;

    public JsonFactor(File file, GalaxyBridge bridge) {
        super(bridge);
        init(file, bridge);
    }

    private void init(File file, GalaxyBridge bridge) {
        try {
            String text = IOUtil.readFileAsText(file, -1, null);
            init(text, bridge);
        } catch (IOException e) {
            // TODO: 2017/5/19
        }
    }

    private void init(String text, GalaxyBridge bridge) {
        try {
            init(new JSONObject(text), bridge);
        } catch (JSONException e) {
            // TODO: 2017/5/19
        }
    }

    private void init(JSONObject json, GalaxyBridge bridge) {
        this.mAppenders = new HashSet<>();
        this.mMaxThreadSize = json.optInt(Constants.MAX_THREAD_SIZE, Constants.MAX_THREAD_DEFAULT_SIZE);
        JSONArray appenderArray = json.optJSONArray(Constants.APPENDERS);
        if (appenderArray != null) {
            int size = appenderArray.length();
            for (int i = 0; i < size; i++) {
                JSONObject appenderSource = appenderArray.optJSONObject(i);
                Appender appender = generateAppenderFromJson(appenderSource);
                mAppenders.add(appender);
            }
        }
    }

    public Appender generateAppenderFromJson(JSONObject json){
        JSONObject layoutObj = json.optJSONObject(Constants.LAYOUT);
        String type = layoutObj.optString(Constants.TYPE);
        JSONArray params = layoutObj.optJSONArray(Constants.PARAMETERS);
        ILayout layout = generateLayout(type, jsonArray2Array(params));
        JSONArray filters = json.optJSONArray(Constants.FILTER);
        IFilter filter = EmptyFilter.INSTANCE;
        if (filters != null) {
            int size = filters.length();
            if (size == 1) {
                JSONObject obj = filters.optJSONObject(0);
                type = obj.optString(Constants.TYPE);
                params = obj.optJSONArray(Constants.PARAMETERS);
                filter = generateFilter(type, jsonArray2Array(params));
            } else {
                ListFilter listFilter = new ListFilter();
                for (int i = 0; i < size; i++) {
                    JSONObject obj = filters.optJSONObject(i);
                    type = obj.optString(Constants.TYPE);
                    params = obj.optJSONArray(Constants.PARAMETERS);
                    IFilter subFilter = generateFilter(type, jsonArray2Array(params));
                    listFilter.addFilter(subFilter);
                }
                filter = listFilter;
            }
        }
        type = json.optString(Constants.TYPE);
        params = json.optJSONArray(Constants.PARAMETERS);
        return generateAppender(type, jsonArray2Array(params), filter, layout);
    }

    public JsonFactor(String ori, GalaxyBridge bridge) {
        super(bridge);
        init(ori, bridge);
    }

    private String[] jsonArray2Array(JSONArray array) {
        if (array == null || array.length() == 0) {
            return new String[0];
        }
        int length = array.length();
        String[] arr = new String[length];
        for (int i = 0; i < length; i++) {
            arr[i] = String.valueOf(array.opt(i));
        }
        return arr;
    }

    @Override
    public Set<Appender> getAppenders() {
        return mAppenders == null ? Collections.<Appender>emptySet() : mAppenders;
    }

    @Override
    public int getThreadSize() {
        return mMaxThreadSize;
    }
}
