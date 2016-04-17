package cn.com.xplora.xploraapp;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.com.xplora.xploraapp.fancyFlow.FancyCoverFlow;
import cn.com.xplora.xploraapp.fancyFlow.demo.FilmInfo;
import cn.com.xplora.xploraapp.fancyFlow.demo.FilmInfoTest;
import cn.com.xplora.xploraapp.fancyFlow.demo.ImageAdapter;

public class FancyFlowDemoActivity extends AppCompatActivity {

    DisplayImageOptions options;

    private ImageLoader imageLoader;

    private FancyCoverFlow fancyCoverFlow;

    private List<FilmInfo> filmList;

    private ImageAdapter adapter;


    private int cur_index = 0;
    private int count_drawble;
    private static int MSG_UPDATE = 1;
    // ��ʱ����
    private ScheduledExecutorService scheduledExecutorService;

    // ͨ��handler������������
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == MSG_UPDATE) {
                fancyCoverFlow.setSelection(cur_index);
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fancy_flow_demo);
        filmList = FilmInfoTest.getfilmInfo();
        // ����option
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.logo)
                .showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        imageLoader = ImageLoader.getInstance();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                FancyFlowDemoActivity.this).threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        adapter = new ImageAdapter(this, filmList, options, imageLoader);

        fancyCoverFlow = (FancyCoverFlow) findViewById(R.id.fancyCoverFlow);

        // item֮��ļ�϶���Խ�����Ϊ��imageview�Ŀ�������ű����ĳ˻���һ��
        fancyCoverFlow.setSpacing(-72);
        fancyCoverFlow.setAdapter(adapter);
        fancyCoverFlow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                cur_index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // ����¼�
        fancyCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(FancyFlowDemoActivity.this,
                        filmList.get(position % filmList.size()).getFilmName(),
                        Toast.LENGTH_LONG).show();
            }
        });

        // �����Զ��ֲ�
        count_drawble = adapter.getCount();
        startPlay();
    }

    /**
     * ��ʼ�ֲ�ͼ�л�
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new AutoPlayTask(), 1, 4,
                TimeUnit.SECONDS);
    }

    /**
     * ֹͣ�ֲ�ͼ�л�
     */
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * ִ���ֲ�ͼ�л�����
     *
     */
    private class AutoPlayTask implements Runnable {

        @Override
        public void run() {

            cur_index = cur_index % count_drawble; // ͼƬ����[0,count_drawable)
            Message msg = handler.obtainMessage(MSG_UPDATE);
            handler.sendMessage(msg);
            cur_index++;
        }

    }

    @Override
    protected void onStop() {
        imageLoader.stop();
        super.onStop();
    }
}
