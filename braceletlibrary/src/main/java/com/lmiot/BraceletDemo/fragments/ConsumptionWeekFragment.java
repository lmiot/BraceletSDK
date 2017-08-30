package com.lmiot.BraceletDemo.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.List;

/**
 * Created by ming on 2016/1/19.
 * 热量消耗（周）
 */
public class ConsumptionWeekFragment extends Fragment {
    private Context context;
    private LinearLayout columnar_chart;
    GraphicalView graphicalView;
    private BraceletDBManager manager;
    private double yMax;
    private  int sum=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        initDB();

    }
    private int adding(int week) {
        List<SportDataInfo> sportDataInfos= manager.findsportInweek(week);
        sum = 0;
        if(sportDataInfos!=null) {
            for (SportDataInfo s : sportDataInfos) {
                sum = sum + Integer.parseInt(s.getDay_heat());
            }
            return sum;
        }
        return  0;
    }
    private void initDB() {
        manager=new BraceletDBManager(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_consumption_week, null);
        initView(view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void initView(View view) {


        columnar_chart =(LinearLayout )view.findViewById(R.id.ConsumptionWeekFragment_columnar_chart  ) ;
        graphicalView= ChartFactory.getLineChartView(context, getDataset(), getRenderer()) ;

        columnar_chart .removeAllViews();
        columnar_chart .addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }
    private XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

        XYSeries series = new XYSeries("");
        series .add(1,adding(-6));
        series .add(2,adding(-5));
        series .add(3,adding(-4));
        series .add(4,adding(-3));
        series .add(5,adding(-2));
        series .add(6,adding(-1));
        series .add(7,adding(0));
        // 把添加了点的折线放入dataset
        yMax= math.getMax(adding(-6), adding(-5), adding(-4), adding(-3)
                , adding(-2), adding(-1), adding(0));
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

        /*renderer.setYLabels(0) ;  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addYTextLabel(0, "0");
        renderer.addYTextLabel(2000, "2K");
        renderer.addYTextLabel(4000, "4K");
        renderer.addYTextLabel(6000, "6K");*/

        renderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addXTextLabel(1, TimeUtils.getAweek(-6)+"周");
        renderer.addXTextLabel(2,TimeUtils.getAweek(-5)+"周");
        renderer.addXTextLabel(3,TimeUtils.getAweek(-4)+"周");
        renderer.addXTextLabel(4,TimeUtils.getAweek(-3)+"周");
        renderer.addXTextLabel(5,TimeUtils.getAweek(-2)+"周");
        renderer.addXTextLabel(6,TimeUtils.getAweek(-1)+"周");
        renderer.addXTextLabel(7,TimeUtils.getAweek(0)+"周");
    }
}
