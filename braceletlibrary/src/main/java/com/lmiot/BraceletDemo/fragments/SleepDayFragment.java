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
import org.achartengine.chart.BarChart;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import java.util.Random;

/**
 * Created by ming on 2016/1/19.
 * 深度睡眠、浅度睡眠、清醒（天）
 */
public class SleepDayFragment extends Fragment {
	private Context context;
	private LinearLayout columnar_chart;
	GraphicalView graphicalView;
	private double Ymax;
	private double Ymax1;
	private double Ymax2;
	private double Ymax3;
	private BraceletDBManager manager;

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
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_sleep_day, null);
		initView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	private void initView(View view) {


		columnar_chart = (LinearLayout) view.findViewById(R.id.columnar_chart);
		graphicalView = ChartFactory.getBarChartView(context, getBarDemoDataset(), getBarDemoRenderer(), BarChart.Type.DEFAULT);//柱状图

		columnar_chart.removeAllViews();
		columnar_chart.addView(graphicalView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
	}

	private XYMultipleSeriesDataset getBarDemoDataset() {

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		final int nr = 7;
		Random r = new Random();
		CategorySeries seriesDeep_sleep = new CategorySeries("");//深度睡眠
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-6))).getDepth_time());
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-5))).getDepth_time());
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-4))).getDepth_time());
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-3))).getDepth_time());
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-2))).getDepth_time());
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-1))).getDepth_time());
		seriesDeep_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(0))).getDepth_time());
		Ymax1= math.getMax((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-6))).getDepth_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-5))).getDepth_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-4))).getDepth_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-3))).getDepth_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-2))).getDepth_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-1))).getDepth_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(0))).getDepth_time());
		dataset.addSeries(seriesDeep_sleep.toXYSeries());

		CategorySeries seriesShallow_sleep = new CategorySeries("");//浅度睡眠
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-6))).getLight_time());
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-5))).getLight_time());
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-4))).getLight_time());
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-3))).getLight_time());
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-2))).getLight_time());
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-1))).getLight_time());
		seriesShallow_sleep.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(0))).getLight_time());
		Ymax2=math.getMax((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-6))).getLight_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-5))).getLight_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-4))).getLight_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-3))).getLight_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-2))).getLight_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-1))).getLight_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(0))).getLight_time());
		dataset.addSeries(seriesShallow_sleep.toXYSeries());

		CategorySeries seriesWake = new CategorySeries("");//清醒
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-6))).getWake_time());
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-5))).getWake_time());
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-4))).getWake_time());
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-3))).getWake_time());
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-2))).getWake_time());
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-1))).getWake_time());
		seriesWake.add((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(0))).getWake_time());
		Ymax3=math.getMax((manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-6))).getWake_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-5))).getWake_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-4))).getWake_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-3))).getWake_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-2))).getWake_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(-1))).getWake_time(),(manager.findSleepDataInfo(SPUtil.getUserName(getContext()),
				TimeUtils.getdayDate_otherFormat(0))).getWake_time());
		dataset.addSeries(seriesWake.toXYSeries());
		Ymax=math.getMax(Ymax1,Ymax2,Ymax3);


		return dataset;
	}

	public XYMultipleSeriesRenderer getBarDemoRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(getResources().getColor(R.color.sleep_deep));
		r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
		r.setChartValuesSpacing(30);//显示的点的值与图的距离
		r.setChartValuesTextSize(25);//设置显示的坐标点值的字体大小
		renderer.addSeriesRenderer(r);

		r = new SimpleSeriesRenderer();
		r.setColor(getResources().getColor(R.color.sleep_light));
		r.setDisplayChartValues(true);//设置是否显示坐标点的y轴坐标值
		r.setChartValuesSpacing(30);//显示的点的值与图的距离
		r.setChartValuesTextSize(25);//设置显示的坐标点值的字体大小
		renderer.addSeriesRenderer(r);

		r = new SimpleSeriesRenderer();
		r.setColor(getResources().getColor(R.color.wake));
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

		renderer.setXLabelsColor(getResources().getColor(R.color.black3));// 设置x轴上标签的颜色
		// renderer .setLabelsColor(getResources().getColor(R.color.black2));//设置轴上标签的颜色，只是好像不起作用
		renderer.setYLabelsColor(0, getResources().getColor(R.color.black3));// 设置Y轴上标签的颜色

		renderer.setGridColor(getResources().getColor(R.color.gray_total));
		renderer.setPanEnabled(false);//设置表格不可滑动
		renderer.setZoomEnabled(false, false);//设置允许放大缩小
		if(Ymax==0){

			renderer .setShowCustomTextGrid(false) ;//设置是否需要显示网格

		}else{
			renderer .setShowCustomTextGrid(true) ;//设置是否需要显示网格

		}

		renderer .setShowGridY(true) ;//设置是否需要显示网格
		renderer .setShowGrid(true);

		renderer.setLabelsTextSize(40); // 设置轴上标签的大小
		renderer.setAxesColor(0xff8e8e8e);//设置坐标轴颜色
		renderer.setBarSpacing(0.5);//设置间距
		renderer.setMargins(new int[]{50, 50, 80, 0});//设置外边距，顺序为：上左下右
		renderer.setYLabelsAlign(Paint.Align.RIGHT);//y轴 数字表示在坐标还是右边

		/*renderer.setYLabels(0);  // 设置 Y 轴不显示数字（改用我们手动添加的文字标签）
		renderer.addYTextLabel(0, "0");
		renderer.addYTextLabel(240, "240m");
		renderer.addYTextLabel(480, "480m");
		renderer.addYTextLabel(720, "720m");*/

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
