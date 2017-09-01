package com.lmiot.BraceletDemo.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lmiot.BraceletDemo.Bean.HeartRateData;
import com.lmiot.BraceletDemo.Bean.SportDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.Util.math;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by ming on 2016/1/19.
 * 总步数（月）
 */
public class HeartRateFargment extends Fragment {
    private Context context;
    private LinearLayout columnar_chart;
    GraphicalView graphicalView;
    private BraceletDBManager manager;
    private XYMultipleSeriesRenderer mRenderer;

    public View getViewSave() {
        return mViewSave;
    }

    private View mViewSave;

    public String getCurrentDate() {
        return mCurrentDate;
    }

    public void setCurrentDate(String currentDate) {
        mCurrentDate = currentDate;

    }

    private String mCurrentDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        initDB();
    }


    private void initDB() {
        manager=new BraceletDBManager(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("HeartRateFargment", "onCreateView");

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_heart_rate, null);

        mCurrentDate = TimeUtils.getCurrentDate();
        initView(view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public void initView(View view) {
        mViewSave = view;

        try {
            columnar_chart =(LinearLayout )view.findViewById(R.id.PaceMonthFragment_columnar_chart ) ;
            mRenderer = getRenderer();
            graphicalView= ChartFactory.getLineChartView(context, getDataset(), mRenderer) ;

            Log.d("HeartRateFargment", "mRenderer.getSeriesRendererCount():" + mRenderer.getSeriesRendererCount());


            //设置坐标点上的数据显示
            SimpleSeriesRenderer seriesrenderer =mRenderer.getSeriesRendererAt(0);
            seriesrenderer.setChartValuesTextSize(10);
            seriesrenderer.setDisplayChartValues(false);
            seriesrenderer.setDisplayChartValuesDistance(30);

            columnar_chart .removeAllViews();
            columnar_chart .addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private XYMultipleSeriesDataset getDataset() {

        List<HeartRateData> heartRateDataList = manager.findHeartRateDataByDate(mCurrentDate);

        Log.d("HeartRateFargment", new Gson().toJson(heartRateDataList));

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");


        if(heartRateDataList!=null){
            if(heartRateDataList.size()>0){
                for(HeartRateData heartRateData:heartRateDataList){
                    int heartRate = heartRateData.getHeartRate();
                    String time = heartRateData.getTime();
                    switch (time){
                        case "0":
                        case "00":
                            series .add(0,heartRate);
                            break;
                        case "01":
                            series .add(1,heartRate);
                            break;
                        case "02":
                            series .add(2,heartRate);
                            break;
                        case "03":
                            series .add(3,heartRate);
                            break;
                        case "04":
                            series .add(4,heartRate);
                            break;
                        case "05":
                            series .add(5,heartRate);
                            break;
                        case "06":
                            series .add(6,heartRate);
                            break;
                        case "07":
                            series .add(7,heartRate);
                            break;
                        case "08":
                            series .add(8,heartRate);
                            break;
                        case "09":
                            series .add(9,heartRate);
                            break;
                        case "10":
                            series .add(10,heartRate);
                            break;
                        case "11":
                            series .add(11,heartRate);
                            break;
                        case "12":
                            series .add(12,heartRate);
                            break;
                        case "13":
                            series .add(13,heartRate);
                            break;
                        case "14":
                            series .add(14,heartRate);
                            break;
                        case "15":
                            series .add(15,heartRate);
                            break;
                        case "16":
                            series .add(16,heartRate);
                            break;
                        case "17":
                            series .add(17,heartRate);
                            break;
                        case "18":
                            series .add(18,heartRate);
                            break;
                        case "19":
                            series .add(19,heartRate);
                            break;
                        case "20":
                            series .add(20,heartRate);
                            break;
                        case "21":
                            series .add(21,heartRate);
                            break;
                        case "22":
                            series .add(22,heartRate);
                            break;
                        case "23":
                            series .add(23,heartRate);
                            break;

                    }


                }

            }

        }



        // 把添加了点的折线放入dataset
        dataset.addSeries(series);

        return dataset;
    }

    public XYMultipleSeriesRenderer getRenderer() {
        // 新建一个xymultipleseries
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(); /*描绘器，设置图表整体效果，比如x,y轴效果，缩放比例，颜色设置*/
        setChartSettings(renderer);

        // 设置一个系列的颜色为蓝色
        XYSeriesRenderer r = new XYSeriesRenderer();/*某一组数据的描绘器，描绘该组数据的个性化显示效果，主要是字体跟颜色的效果*/
        // r.setColor(getResources().getColor(R.color.black2));//表示该组数据的图或线的颜色
        r.setColor(getResources().getColor(R.color.colorAccent ) );//表示该组数据的图或线的颜色
        r.setPointStyle(PointStyle.CIRCLE);//坐标点的显示风格
        r.setPointStrokeWidth(0);//坐标点的大小
        r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
        r.setChartValuesSpacing(20);//显示的点的值与图的距离
        r.setChartValuesTextSize(20);//设置显示的坐标点值的字体大小
        r.setFillPoints(true ) ;//设置显示的坐标点是否填充
        r .setLineWidth(2);

        renderer.addSeriesRenderer(r); // 往xymultiplerender中增加一个系列

        return renderer;
    }
    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(0);// 设置X坐标轴起始点
        renderer.setXAxisMax(24);// 设置X坐标轴最大值
        renderer.setYAxisMin(0);// 设置Y坐标轴起始点
        renderer.setYAxisMax(250);// 设置Y坐标轴最大值
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(getResources().getColor(R.color.gray_light3));//外部颜色
        renderer.setBackgroundColor(getResources().getColor(R.color.gray_light2));//内部颜色

        renderer .setXLabelsColor(getResources().getColor(R.color.black3)) ;// 设置x轴上标签的颜色
        // renderer .setLabelsColor(getResources().getColor(R.color.black2));//设置轴上标签的颜色，只是好像不起作用
        renderer.setYLabelsColor(0, getResources().getColor(R.color.black3)) ;// 设置Y轴上标签的颜色
        renderer .setShowCustomTextGrid(true) ;//设置是否需要显示网格
        renderer.setShowGridX(true);
        renderer .setShowGridY(true) ;//设置是否需要显示网格
        renderer .setGridColor(getResources().getColor(R.color.gray_total ));
        renderer.setPanEnabled(false);//设置表格不可滑动
        renderer.setZoomEnabled(false, false);//设置允许放大缩小

        renderer.setLabelsTextSize(31); // 设置轴上标签的大小
        renderer.setAxesColor(0xff8e8e8e);//设置坐标轴颜色
        renderer.setBarSpacing(0.5);//设置间距
        renderer.setMargins(new int[]{50, 50, 80, 0});////设置外边距，顺序为：上左下右
        renderer.setYLabelsAlign(Paint.Align.CENTER);//y轴 数字表示在坐标还是右边
        renderer .setPointSize(3) ;

        renderer.setYLabels(0) ;  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addYTextLabel(0, "0");
        renderer.addYTextLabel(50, "50");
        renderer.addYTextLabel(100, "100");
        renderer.addYTextLabel(150, "150");
        renderer.addYTextLabel(200, "200");
        renderer.addYTextLabel(250, "250");


        renderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addXTextLabel(3, "3:00");
        renderer.addXTextLabel(6, "6:00");
        renderer.addXTextLabel(9, "9:00");
        renderer.addXTextLabel(12, "12:00");
        renderer.addXTextLabel(15, "15:00");
        renderer.addXTextLabel(18, "18:00");
        renderer.addXTextLabel(21, "21:00");



    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        Log.d("HeartRateFargment", "hidden:" + hidden);

    }
}
