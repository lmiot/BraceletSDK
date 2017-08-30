package com.lmiot.BraceletDemo.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lmiot.BraceletDemo.Bean.SleepDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.Util.math;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.List;

/**
 * Created by ming on 2016/1/19.
 * 深度睡眠、浅度睡眠、清醒（月）
 */
public class SleepMonthFragment extends Fragment {
    private Context context;
    private LinearLayout columnar_chart;
    GraphicalView graphicalView;
    private double Ymax1;
    private double Ymax;
    private double Ymax2;
    private double Ymax3;
    private BraceletDBManager manager;
    private int sum=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        initDB();
    }
    public int addingdeep(int mi){
        List<SleepDataInfo> sportDataInfos = manager.findsleeptInmonth(mi);
        sum = 0;
        if(sportDataInfos!=null) {
            for (SleepDataInfo s : sportDataInfos) {
                sum = sum + s.getDepth_time();
            }
            return sum;
        }
        return 0;
    }
    public int addinglight(int mi){
        List<SleepDataInfo> sportDataInfos = manager.findsleeptInmonth(mi);
        sum = 0;
        if(sportDataInfos!=null) {
            for (SleepDataInfo s : sportDataInfos) {
                sum = sum + s.getLight_time();
            }
            return sum;
        }
        return 0;
    }
    public int addingwake(int mi){
        List<SleepDataInfo> sportDataInfos = manager.findsleeptInmonth(mi);
        sum = 0;
        if(sportDataInfos!=null) {
            for (SleepDataInfo s : sportDataInfos) {
                sum = sum + s.getWake_time();
            }
            return sum;
        }
        return 0;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sleep_month, null);
        initView(view);
        return view;
    }
    private void initDB() {
        manager=new BraceletDBManager(getActivity());
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    private void initView(View view) {
        columnar_chart =(LinearLayout )view.findViewById(R.id.SleepMonthFragment_columnar_chart ) ;
        graphicalView= ChartFactory.getBarChartView(context, getBarDemoDataset(), getBarDemoRenderer(), BarChart.Type.DEFAULT);//柱状图

        columnar_chart .removeAllViews();
        columnar_chart .addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }
    private XYMultipleSeriesDataset getBarDemoDataset() {

        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        CategorySeries seriesDeep_sleep = new CategorySeries("");//深度睡眠
        seriesDeep_sleep.add(addingdeep(-6));
        seriesDeep_sleep.add(addingdeep(-5));
        seriesDeep_sleep.add(addingdeep(-4));
        seriesDeep_sleep.add(addingdeep(-3));
        seriesDeep_sleep.add(addingdeep(-2));
        seriesDeep_sleep.add(addingdeep(-1));
        seriesDeep_sleep.add(addingdeep(0));
        Ymax1= math.getMax(addingdeep(-6),addingdeep(-5),addingdeep(-4),addingdeep(-3)
                ,addingdeep(-2),addingdeep(-1),addingdeep(0));


        dataset.addSeries(seriesDeep_sleep .toXYSeries());

        CategorySeries seriesShallow_sleep = new CategorySeries("");//浅度睡眠
        seriesShallow_sleep .add(addinglight(-6));
        seriesShallow_sleep .add(addinglight(-5));
        seriesShallow_sleep .add(addinglight(-4));
        seriesShallow_sleep .add(addinglight(-3));
        seriesShallow_sleep .add(addinglight(-2));
        seriesShallow_sleep .add(addinglight(-1));
        seriesShallow_sleep .add(addinglight(0));
        Ymax2= math.getMax(addinglight(-6),addinglight(-5),addinglight(-4),addinglight(-3)
                ,addinglight(-2),addinglight(-1),addinglight(0));
        dataset.addSeries(seriesShallow_sleep .toXYSeries());

        CategorySeries seriesWake= new CategorySeries("");//清醒
        seriesWake.add(addingwake(-6));
        seriesWake.add(addingwake(-5));
        seriesWake.add(addingwake(-4));
        seriesWake.add(addingwake(-3));
        seriesWake.add(addingwake(-2));
        seriesWake.add(addingwake(-1));
        seriesWake.add(addingwake(0));
        dataset.addSeries(seriesWake .toXYSeries());
        Ymax3= math.getMax(addingwake(-6),addingwake(-5),addingwake(-4),addingwake(-3)
                ,addingwake(-2),addingwake(-1),addingwake(0));
        Ymax=math.getMax(Ymax1,Ymax2,Ymax3);
        return dataset;
    }

    public XYMultipleSeriesRenderer getBarDemoRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(getResources().getColor(R.color .sleep_deep  ) );
        r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
        r.setChartValuesSpacing(20);//显示的点的值与图的距离
        r.setChartValuesTextSize(28);//设置显示的坐标点值的字体大小
        renderer.addSeriesRenderer(r);

        r = new SimpleSeriesRenderer();
        r.setColor(getResources().getColor(R.color .sleep_light));
        r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
        r.setChartValuesSpacing(20);//显示的点的值与图的距离
        r.setChartValuesTextSize(28);//设置显示的坐标点值的字体大小
        renderer.addSeriesRenderer(r);

        r = new SimpleSeriesRenderer();
        r.setColor(getResources().getColor(R.color .wake ));
        r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
        r.setChartValuesSpacing(20);//显示的点的值与图的距离
        r.setChartValuesTextSize(28);//设置显示的坐标点值的字体大小
        renderer.addSeriesRenderer(r);

        setChartSettings(renderer);

        return renderer;
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(0);// 设置X坐标轴起始点
        renderer.setXAxisMax(8);// 设置X坐标轴最大值
        renderer.setYAxisMin(0);// 设置Y坐标轴起始点
        renderer.setYAxisMax(Ymax);// 设置Y坐标轴最大值
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(getResources().getColor(R.color.gray_light3));//外部颜色
        renderer.setBackgroundColor(getResources().getColor(R.color.gray_light2));//内部颜色

        renderer .setXLabelsColor(getResources().getColor(R.color.black3)) ;// 设置x轴上标签的颜色
        // renderer .setLabelsColor(getResources().getColor(R.color.black2));//设置轴上标签的颜色，只是好像不起作用
        renderer.setYLabelsColor(0, getResources().getColor(R.color.black3)) ;// 设置Y轴上标签的颜色
        if(Ymax==0){

            renderer .setShowCustomTextGrid(false) ;//设置是否需要显示网格

        }else{
            renderer .setShowCustomTextGrid(true) ;//设置是否需要显示网格

        }
        renderer .setShowGridY(true) ;//设置是否需要显示网格
        renderer .setShowGrid(true);
        renderer .setGridColor(getResources().getColor(R.color.gray_total ));
        renderer.setPanEnabled(false);//设置表格不可滑动
        renderer.setZoomEnabled(false, false);//设置允许放大缩小

        renderer.setLabelsTextSize(31); // 设置轴上标签的大小
        renderer.setAxesColor(0xff8e8e8e);//设置坐标轴颜色
        renderer.setBarSpacing(0.5);//设置间距
        renderer.setMargins(new int[]{50, 50, 80, 0});//设置边距
        renderer.setYLabelsAlign(Paint.Align.RIGHT);//y轴 数字表示在坐标还是右边

 /*       renderer.setYLabels(0) ;  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addYTextLabel(0, "0");
        renderer.addYTextLabel(240, "240m");
        renderer.addYTextLabel(480, "480m");
        renderer.addYTextLabel(720, "720m");*/

        renderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addXTextLabel(1, TimeUtils.getAmonth(-6));
        renderer.addXTextLabel(2,TimeUtils.getAmonth(-5));
        renderer.addXTextLabel(3,TimeUtils.getAmonth(-4));
        renderer.addXTextLabel(4,TimeUtils.getAmonth(-3));
        renderer.addXTextLabel(5,TimeUtils.getAmonth(-2));
        renderer.addXTextLabel(6,TimeUtils.getAmonth(-1));
        renderer.addXTextLabel(7,TimeUtils.getAmonth(0));
    }
}
