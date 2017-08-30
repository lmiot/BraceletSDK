package com.lmiot.BraceletDemo.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lmiot.BraceletDemo.Bean.TiredDataInfo;
import com.lmiot.BraceletDemo.Database.BraceletDBManager;
import com.lmiot.BraceletDemo.R;
import com.lmiot.BraceletDemo.Util.LogUtils;
import com.lmiot.BraceletDemo.Util.SPUtil;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class SevenDayfragment extends Fragment {
    private Context context;
    private LinearLayout columnar_chart;
    GraphicalView graphicalView;
    private SimpleDateFormat sdf1,sdf2;
    private Date daynow,day4,day7;
    private BraceletDBManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        initialDateformat();
        initDB();

    }

    private void initDB() {
        manager=new BraceletDBManager(getActivity());
    }

    private void initialDateformat() {
        daynow=new Date(System.currentTimeMillis());
        day4=new Date(System.currentTimeMillis()-(24*4*3600*1000));
        day7=new Date(System.currentTimeMillis()-(24*7*3600*1000));
        sdf1=new SimpleDateFormat("yyyy-M-dd");
        sdf2=new SimpleDateFormat("M/dd");
        LogUtils.d("date4",sdf1.format(day4));
        LogUtils.d("date7",sdf1.format(day7));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.sevenfragment, null);
        initView(view);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    private void initView(View view) {

        columnar_chart =(LinearLayout )view.findViewById(R.id.SevenFragment_columnar_chart ) ;
        graphicalView= ChartFactory.getLineChartView(context, getDataset(), getRenderer()) ;

        columnar_chart .removeAllViews();
        columnar_chart .addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
    }
    private XYMultipleSeriesDataset getDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        XYSeries series = new XYSeries("");
        //描点。
        String nowtime=sdf1.format(daynow);
        TiredDataInfo tdi_now=manager.findTiredDataInfo(SPUtil.getUserName(getContext()),nowtime);
        int goalnowday= Integer.parseInt(tdi_now.getSdnn());
        String before4daytime=sdf1.format(day4);
        TiredDataInfo tdi_b4=manager.findTiredDataInfo(SPUtil.getUserName(getContext()),before4daytime);
        int goalb4day= Integer.parseInt(tdi_b4.getSdnn());
        String before7daytime=sdf1.format(day7);
        TiredDataInfo tdi_b7=manager.findTiredDataInfo(SPUtil.getUserName(getContext()),before7daytime);
        int goalb7day= Integer.parseInt(tdi_b7.getSdnn());
        series .add(1,goalb7day) ;
        series .add(2,goalb4day);
        series .add(3,goalnowday);
        // 把添加了点的折线放入dataset
        dataset.addSeries(series);

        return dataset;
    }

    public XYMultipleSeriesRenderer getRenderer() {
        // 新建一个xymultipleseries
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(); /*描绘器，设置图表整体效果，比如x,y轴效果，缩放比例，颜色设置*/
        setChartSettings(renderer);

        XYSeriesRenderer r = new XYSeriesRenderer();/*某一组数据的描绘器，描绘该组数据的个性化显示效果，主要是字体跟颜色的效果*/
        r.setColor(getResources().getColor(R.color.green ) );//表示该组数据的图或线的颜色
        r.setPointStyle(PointStyle.CIRCLE);//坐标点的显示风格
        r.setPointStrokeWidth(2);//坐标点的大小
        r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
        r.setChartValuesSpacing(30);//显示的点的值与图的距离
        r.setChartValuesTextSize(25);//设置显示的坐标点值的字体大小
        r.setFillPoints(true ) ;//设置显示的坐标点是否填充
        r .setLineWidth(2);

        renderer.addSeriesRenderer(r); // 往xymultiplerender中增加一个系列

        return renderer;
    }
    private void setChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(0);// 设置X坐标轴起始点
        renderer.setXAxisMax(4);// 设置X坐标轴最大值
        renderer.setYAxisMin(0);// 设置Y坐标轴起始点
        renderer.setYAxisMax(120);// 设置Y坐标轴最大值
        renderer.setApplyBackgroundColor(true);
        renderer.setMarginsColor(Color.WHITE );//外部颜色
        renderer.setBackgroundColor(Color .WHITE );//内部颜色

        renderer .setXLabelsColor(getResources().getColor(R.color.black3)) ;// 设置x轴上标签的颜色
        // renderer .setLabelsColor(getResources().getColor(R.color.black2));//设置轴上标签的颜色，只是好像不起作用
        renderer.setYLabelsColor(0, getResources().getColor(R.color.black3)) ;// 设置Y轴上标签的颜色

       /* renderer .setShowGridY(true) ;//设置是否需要显示网格
        renderer.setShowGridX(true) ;
        renderer .setShowGrid(true);*/
        renderer .setGridColor(getResources().getColor(R.color.gray_total2));
        renderer .setShowCustomTextGrid(true) ;//设置是否需要显示网格
        renderer.setPanEnabled(false);//设置表格不可滑动
        renderer.setZoomEnabled(false, false);//设置允许放大缩小

        renderer.setLabelsTextSize(25); // 设置轴上标签的大小
        renderer.setAxesColor(0xff8e8e8e);//设置坐标轴颜色
        renderer.setBarSpacing(0.5);//设置间距
        renderer.setMargins(new int[]{50, 50, 0, 0});////设置外边距，顺序为：上左下右
        renderer.setYLabelsAlign(Paint.Align.RIGHT);//y轴 数字表示在坐标还是右边
        renderer .setPointSize(7) ;

        renderer.setYLabels(0) ;  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
        renderer.addYTextLabel(0, "0");
        renderer.addYTextLabel(25, "25");
        renderer.addYTextLabel(50, "50");
        renderer.addYTextLabel(75, "75");
        renderer.addYTextLabel(100, "100");

        renderer.setXLabels(0); // 设置 X 轴不显示数字（改用我们手动添加的文字标签）
        //根据今天日期来填充X轴:
        //填充X轴的今天时间
        String Xnowtime=sdf2.format(daynow);
        //填充X轴的前四天时间
        String Xbefore4dayname=sdf2.format(day4);
        //填充X轴的前七天时间
        String Xbefore7dayname=sdf2.format(day7);
        renderer.addXTextLabel(1, Xbefore7dayname);
        renderer.addXTextLabel(2,Xbefore4dayname);
        renderer.addXTextLabel(3,Xnowtime);

    }

}
