package com.lmiot.BraceletDemo.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.SPUtil;
import com.lmiot.BraceletDemo.Util.TimeUtils;
import com.lmiot.BraceletDemo.Util.math;
import com.lmiot.BraceletDemo.Util.StrUtils;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.Random;

/**
 * Created by ming on 2016/1/19.
 * 热量消耗（天）
 */
public class ConsumptionDayFragment extends Fragment {
    private Context context;
    private LinearLayout columnar_chart;
    GraphicalView graphicalView;
    private BraceletDBManager manager;
    private double yMax;

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
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_consumption_day, null);
        initView(view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    private void initView(View view) {

        columnar_chart =(LinearLayout )view.findViewById(R.id.ConsumptionDayFragment_columnar_chart ) ;
        graphicalView= ChartFactory.getLineChartView(context, getDataset(), getRenderer()) ;

        columnar_chart .removeAllViews();
        columnar_chart .addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }
    private XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        final int nr = 7;// 每个系列种包含10个随机数
        Random r = new Random();
        // 新建一个系列(线条)
        XYSeries series = new XYSeries("");
        series .add(1, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-6)).getDay_heat())) ;
        series .add(2, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-5)).getDay_heat())) ;
        series .add(3, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-4)).getDay_heat())) ;
        series .add(4, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-3)).getDay_heat())) ;
        series .add(5, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-2)).getDay_heat())) ;
        series .add(6, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-1)).getDay_heat())) ;
        series .add(7, Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(0)).getDay_heat())) ;
        yMax= math.getMax(Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-6)).getDay_heat()),Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-5)).getDay_heat()),Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-4)).getDay_heat()),Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-3)).getDay_heat()),Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-2)).getDay_heat()),Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(-1)).getDay_heat()),Double.parseDouble(manager.findSportDataInfo(SPUtil.getUserName(getContext()),
                TimeUtils.getdayDate_otherFormat(0)).getDay_heat()));



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
        r.setColor(getResources().getColor(R.color.green ) );//表示该组数据的图或线的颜色
        r.setPointStyle(PointStyle.CIRCLE);//坐标点的显示风格
        r.setPointStrokeWidth(2);//坐标点的大小
        r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
        r.setChartValuesSpacing(20);//显示的点的值与图的距离
        r.setChartValuesTextSize(28);//设置显示的坐标点值的字体大小
        r.setFillPoints(true ) ;//设置显示的坐标点是否填充
        r .setLineWidth(2);

        renderer.addSeriesRenderer(r); // 往xymultiplerender中增加一个系列

        return renderer;
    }
    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(0);// 设置X坐标轴起始点
        renderer.setXAxisMax(8);// 设置X坐标轴最大值
        renderer.setYAxisMin(0);// 设置Y坐标轴起始点
        renderer.setYAxisMax(yMax);// 设置Y坐标轴最大值
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(getResources().getColor(R.color.gray_light3));//外部颜色
        renderer.setBackgroundColor(getResources().getColor(R.color.gray_light2));//内部颜色

        renderer .setXLabelsColor(getResources().getColor(R.color.black3)) ;// 设置x轴上标签的颜色
        // renderer .setLabelsColor(getResources().getColor(R.color.black2));//设置轴上标签的颜色，只是好像不起作用
        renderer.setYLabelsColor(0, getResources().getColor(R.color.black3)) ;// 设置Y轴上标签的颜色
        if(yMax==0){

            renderer .setShowCustomTextGrid(false) ;//设置是否需要显示网格

        }else{
            renderer .setShowCustomTextGrid(true) ;//设置是否需要显示网格

        }
        renderer .setShowGridY(true) ;//设置是否需要显示网格
        renderer .setShowGrid(true);
        renderer .setGridColor(getResources().getColor(R.color.gray_total ));
        renderer.setPanEnabled(false);//设置表格不可滑动
        renderer.setZoomEnabled(false, false);//设置允许放大缩小

        renderer.setLabelsTextSize(40); // 设置轴上标签的大小
        renderer.setAxesColor(0xff8e8e8e);//设置坐标轴颜色
        renderer.setBarSpacing(0.5);//设置间距
        renderer.setMargins(new int[]{50, 50, 80, 0});////设置外边距，顺序为：上左下右
        renderer.setYLabelsAlign(Paint.Align.RIGHT);//y轴 数字表示在坐标还是右边
        renderer .setPointSize(7) ;

      /*  renderer.setYLabels(0) ;  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addYTextLabel(0, "0");
        renderer.addYTextLabel(2000, "2K");
        renderer.addYTextLabel(4000, "4K");
        renderer.addYTextLabel(6000, "6K");*/

        renderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addXTextLabel(1, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(-6)));
        renderer.addXTextLabel(2, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(-5)));
        renderer.addXTextLabel(3, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(-4)));
        renderer.addXTextLabel(4, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(-3)));
        renderer.addXTextLabel(5, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(-2)));
        renderer.addXTextLabel(6, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(-1)));
        renderer.addXTextLabel(7, StrUtils.RemoveYearOfDate(TimeUtils.getAdayDate(0)));
    }
}
