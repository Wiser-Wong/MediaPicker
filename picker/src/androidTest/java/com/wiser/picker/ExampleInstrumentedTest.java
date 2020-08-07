package com.wiser.picker;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.lib.MediaHelper;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.wiser.picker.test", appContext.getPackageName());
    }

    @Test
    public void loadAllPhoto(){
        List<MediaData> mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemPhotos(InstrumentationRegistry.getInstrumentation().getTargetContext());
        if (mediaDataList != null) {

        }
        for (int i = 0; i < mediaDataList.size(); i++) {

        }

    }
}