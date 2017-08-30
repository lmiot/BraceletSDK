package com.lmiot.BraceletDemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.zhy.android.percent.support.PercentRelativeLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;


public class TiredTestDetailActivity extends Activity {






    private Context context;
    private RelativeLayout columnar_chart;
    GraphicalView graphicalView;
    List<int[]> x = new ArrayList<int[]>();
    List<int[]> y = new ArrayList<int[]>();
    int[] colors;
    private BraceletDBManager manager;
    private ImageView mIdBack;
    private TextView mIdTitle;
    private TextView mIdTvShowTac;
    private TextView mIdAverageRateTired;
    private TextView mIdTvShowSdnn;
    private TextView mIdTvShowSdnnText;
    private LinearLayout mIdTestdetaelACLinlayout;
    private LinearLayout mLine1Shwo;
    private LinearLayout mMLine1Shwo;
    private LinearLayout mLine1w;
    private LinearLayout mLine2e;
    private TextView mCeshi2;
    private PercentRelativeLayout mSevenFragmentColumnarChart;
    private SeekBar mSeekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiredtestdetail);
        initView();
        x.add(new int[]{10, 65, 70, 75, 80});
        x.add(new int[]{10, 65, 70, 75, 80});
        x.add(new int[]{10, 65, 70, 75, 80});
        y.add(new int[]{60, 30, 30, 30, 30});
        y.add(new int[]{50, 20, 20, 20, 20});
        y.add(new int[]{40, 10, 10, 10, 10});
        colors = new int[]{getResources().getColor(R.color.a), getResources().getColor(R.color.b), getResources().getColor(R.color.c)};

        Intent intent = getIntent();
        String averageRate = intent.getStringExtra("average_rate01");
        String sdnn = intent.getStringExtra("sdnn");
        mIdAverageRateTired.setText(averageRate);
        mIdTvShowSdnnText.setText(sdnn);
        int sd = Integer.parseInt(sdnn);
        int SDNN = 100 - sd;
        if (SDNN < 0) {
            SDNN = 0;
        }
        mSeekBar.setMax(100);
        mSeekBar.setProgress(SDNN);
        mSeekBar.setEnabled(false);

        Log.e("疲劳值:", averageRate + ":" + sdnn);

        columnar_chart = (RelativeLayout) findViewById(R.id.SevenFragment_columnar_chart);
        initChartView();
    }


    /**
     * 设置页头
     */
    private void initView() {


        mIdBack = findViewById(R.id.id_back);
        mIdTitle = findViewById(R.id.id_title);
        mIdTvShowTac = findViewById(R.id.id_tv_showTac);
        mIdAverageRateTired = findViewById(R.id.id_average_rate_tired);
        mIdTvShowSdnn = findViewById(R.id.id_tv_showSdnn);
        mIdTvShowSdnnText = findViewById(R.id.id_tv_showSdnn_text);
        mIdTestdetaelACLinlayout = findViewById(R.id.id_testdetaelAC_linlayout);
        mMLine1Shwo = findViewById(R.id.line1_shwo);
        mLine1w = findViewById(R.id.line1w);
        mLine2e = findViewById(R.id.line2e);
        mCeshi2 = findViewById(R.id.ceshi2);
        mSevenFragmentColumnarChart = findViewById(R.id.SevenFragment_columnar_chart);
        mSeekBar = findViewById(R.id.seekBar);

        mIdTitle.setText("疲劳测试结果");

        mIdBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void initChartView() {

        graphicalView = ChartFactory.getLineChartView(getApplication(), getDataset(), getRenderer());
        /*columnar_chart.removeAllViews();*/
        columnar_chart.addView(graphicalView, new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
    }

    private XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        for (int i = 0; i < 3; i++) {
            XYSeries series = new XYSeries("");
            int[] xV = x.get(i);                     /* 获取该条曲线的x轴坐标数组 */
            int[] yV = y.get(i);                     /* 获取该条曲线的y轴坐标数组 */
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series); /* 将该条曲线的 x,y 轴数组存放到 单条曲线数据中 */
        }
        return dataset;
    }

    public XYMultipleSeriesRenderer getRenderer() {

        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(); /*描绘器，设置图表整体效果，比如x,y轴效果，缩放比例，颜色设置*/
        setChartSettings(renderer);
        for (int i = 0; i < 3; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();      /* 单个曲线的渲染器 */
            r.setColor(colors[i]);                            /* 为单个曲线渲染器设置曲线颜色 */
            r.setPointStrokeWidth(100);//坐标点的大小
            r.setChartValuesSpacing(30);//显示的点的值与图的距离
            r.setFillPoints(true);//设置显示的坐标点是否填充
            r.setLineWidth(2);
            renderer.addSeriesRenderer(r); // 往xymultiplerender中增加一个系列/* 为单个曲线渲染器设置曲线风格 */
                                     /* 将单个曲线渲染器设置到渲染器集合中 */
        }
                             /*某一组数据的描绘器，描绘该组数据的个性化显示效果，主要是字体跟颜色的效果*/
        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(10);// 设置X坐标轴起始点
        renderer.setXAxisMax(80);// 设置X坐标轴最大值
        renderer.setYAxisMin(0);// 设置Y坐标轴起始点
        renderer.setYAxisMax(100);// 设置Y坐标轴最大值
        renderer.setApplyBackgroundColor(true);
        renderer.setXRoundedLabels(false);
        renderer.setYLabelsPadding(-100f);
        renderer.setYLabelsVerticalPadding(-8f);
        renderer.setMarginsColor(getResources().getColor(R.color.gray_light3));//外部颜色
        renderer.setBackgroundColor(getResources().getColor(R.color.gray_light2));//内部颜色gray_light2
        renderer.setXLabelsColor(getResources().getColor(R.color.black3));// 设置x轴上标签的颜色
        renderer.setYLabelsColor(0, getResources().getColor(R.color.black3));// 设置Y轴上标签的颜色
        //renderer.setLabelsColor(getResources().getColor(R.color.a));//设置轴上标签的颜色，只是好像不起作用
        //renderer.setShowGridY(false);//设置是否需要显示网格
        //renderer.setShowGridX(false);
        renderer.setGridColor(getResources().getColor(R.color.grid));/**/
        //renderer.setShowGrid(false);
        renderer.setPanEnabled(false);//设置表格不可滑动
        renderer.setZoomEnabled(false, false);//设置允许放大缩小

        renderer.setBarSpacing(100D);//设置间距
        renderer.setBarWidth(1000f);
        renderer.setShowCustomTextGrid(true);//设置是否需要显示网格


        renderer.setShowLegend(true);

        renderer.setLabelsTextSize(20); // 设置轴上标签的大小
        renderer.setAxesColor(0xff8e8e8e);//设置坐标轴颜色
        //renderer.setAxesColor(getResources().getColor(R.color.a));
        renderer.setMargins(new int[]{20, 25, 0, 20});////设置外边距，顺序为：上左下右
        renderer.setYLabelsAlign(Paint.Align.RIGHT);//y轴 数字表示在坐标还是右边
    /*	renderer.setXTitle("年龄");*/
        renderer.setAxisTitleTextSize(13);
        /*renderer.setYTitle("svn");*/
        renderer.setLegendHeight(30);
        renderer.setLegendTextSize(30);
        renderer.setFitLegend(true);

        renderer.setPointSize(100f);

        renderer.setYLabels(0);  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addYTextLabel(0, "00");
        renderer.addYTextLabel(10, "10");
        renderer.addYTextLabel(20, "20");
        renderer.addYTextLabel(30, "30");
        renderer.addYTextLabel(40, "40");
        renderer.addYTextLabel(50, "50");
        renderer.addYTextLabel(60, "60");
        renderer.addYTextLabel(70, "70");
        renderer.addYTextLabel(80, "80");
        renderer.addYTextLabel(90, "90");
        renderer.addYTextLabel(100, "100");
        renderer.setXLabels(0);
        renderer.addXTextLabel(0, "10");
        renderer.addXTextLabel(10, "10");
        renderer.addXTextLabel(15, "15");
        renderer.addXTextLabel(20, "20");
        renderer.addXTextLabel(25, "25");
        renderer.addXTextLabel(30, "30");
        renderer.addXTextLabel(35, "35");
        renderer.addXTextLabel(40, "40");
        renderer.addXTextLabel(45, "45");
        renderer.addXTextLabel(50, "50");
        renderer.addXTextLabel(55, "55");
        renderer.addXTextLabel(60, "60");
        renderer.addXTextLabel(65, "65");
        renderer.addXTextLabel(70, "70");
        renderer.addXTextLabel(75, "75");
        renderer.addXTextLabel(80, "80");
    }

}