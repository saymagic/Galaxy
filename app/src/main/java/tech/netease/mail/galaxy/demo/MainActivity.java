package tech.netease.mail.galaxy.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.netease.mail.galaxy.demo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import tech.saymagic.galaxy.lib.core.Constants;
import tech.saymagic.galaxy.lib.core.Galaxy;
import tech.saymagic.galaxy.lib.core.factor.Factor;
import tech.saymagic.galaxy.lib.core.factory.GalaxyBridgeAdapter;
import tech.saymagic.galaxy.lib.core.star.Star;

public class MainActivity extends AppCompatActivity {

    Star mStar;

    private AtomicInteger mAtomicInteger = new AtomicInteger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGalaxy();
    }

    private void initGalaxy() {
        JSONObject json = new JSONObject();
        try {
            json.put(Constants.MAX_THREAD_SIZE, 5);
            JSONArray appenderArray = new JSONArray();
            JSONObject appender = new JSONObject();
            appender.put(Constants.TYPE, Constants.APPENDER_CONSOLE);
            JSONObject layout = new JSONObject();
            layout.put(Constants.TYPE, Constants.LAYOUT_SIMPLE);
            appender.put(Constants.LAYOUT, layout);
            JSONArray filters = new JSONArray();
            JSONObject filter = new JSONObject();
            filter.put(Constants.TYPE, Constants.FILTER_TAG);
            JSONArray array = new JSONArray();
            array.put("Main");
            filter.put(Constants.PARAMETERS, array);
            filters.put(filter);
            appender.put(Constants.FILTER, filters);
            appenderArray.put(appender);

            appender = new JSONObject();
            appender.put(Constants.TYPE, Constants.APPENDER_FILE);
            JSONArray appenderParams = new JSONArray();
            appenderParams.put(new File(MainActivity.this.getFilesDir(), "galaxy.log").getAbsolutePath());
            appender.put(Constants.PARAMETERS, appenderParams);
            layout = new JSONObject();
            layout.put(Constants.TYPE, Constants.LAYOUT_SIMPLE);
            appender.put(Constants.LAYOUT, layout);
            filters = new JSONArray();
            filter = new JSONObject();
            filter.put(Constants.TYPE, Constants.FILTER_LEVEL);
            array = new JSONArray();
            array.put("e");
            array.put("ew");
            filter.put(Constants.PARAMETERS, array);
            filters.put(filter);
            appender.put(Constants.FILTER, filters);
            appenderArray.put(appender);

            json.put(Constants.APPENDERS, appenderArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Main", json.toString());
        Galaxy.bigBang(Factor.creatFromJson(json.toString(), new GalaxyBridgeAdapter()));
        mStar = Galaxy.get("Main");

    }

    public void clickBtn(View view) {
        if (mStar != null) {
            mStar.d("clickBtn - {} times", mAtomicInteger.getAndIncrement());
            mStar.i("clickBtn - {} times", mAtomicInteger.getAndIncrement());
            mStar.v("clickBtn - {} times", mAtomicInteger.getAndIncrement(), "level-v");
            mStar.e("clickBtn - {} times", mAtomicInteger.getAndIncrement(), "level-e");
        }
    }

}
