/**
 * 
 */
package com.welltech.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Zhujia at 2017年8月16日 下午5:50:49
 */
public class WtParamQueryDto {
	
	/**
	 * 页面为首次加载
	 */
	private boolean firstLoad=true;
	/**
	 * 测点序列号
	 */
	private String pointId;
	
	/**
	 * 测点名称
	 */
	private String pointName;
	
	/**
	 * 序列号
	 */
	private String type;
	
	/**
	 * 报表类型
	 * 1:日报表
	 * 2:周报表
	 * 3:月报表
	 * 4:季报表
	 * 5:年报表
	 */
	private String reportType;
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH") 
	private Date startTime; 
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH") 
	private Date endTime;
	
	@DateTimeFormat(pattern="yyyy-MM-dd") 
	private Date date; 
	
	@DateTimeFormat(pattern="yyyy-MM") 
	private Date month;
	
	@DateTimeFormat(pattern="yyyy") 
	private Date year;
	
	/**
	 * 是否显示最大值
	 */
	private boolean showMax;
	
	/**
	 * 是否显示最小值
	 */
	private boolean showMin;
	
	/**
	 * 是否显示平均值
	 */
	private boolean showAvg;
	
	/**
	 * 是否显示黑臭三分
	 */
	private boolean showWaterBlack;
	
	/**
	 * 是否显示地表水质
	 */
	private boolean showWaterSurface;
	
	/**
	 * 是否显示主要污染物
	 */
	private boolean showMain;

	/**
	 * 是否显示32路参数
	 */
	private boolean p1;
	private boolean p2;
	private boolean p3;
	private boolean p4;
	private boolean p5;
	private boolean p6;
	private boolean p7;
	private boolean p8;
	private boolean p9;
	private boolean p10;
	private boolean p11;
	private boolean p12;
	private boolean p13;
	private boolean p14;
	private boolean p15;
	private boolean p16;
	private boolean p17;
	private boolean p18;
	private boolean p19;
	private boolean p20;
	private boolean p21;
	private boolean p22;
	private boolean p23;
	private boolean p24;
	private boolean p25;
	private boolean p26;
	private boolean p27;
	private boolean p28;
	private boolean p29;
	private boolean p30;
	private boolean p31;
	private boolean p32;
	
	public boolean isFirstLoad() {
		return firstLoad;
	}
	public void setFirstLoad(boolean firstLoad) {
		this.firstLoad = firstLoad;
	}
	public String getPointId() {
		return pointId;
	}
	public void setPointId(String pointId) {
		this.pointId = pointId;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getMonth() {
		return month;
	}
	public void setMonth(Date month) {
		this.month = month;
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	public boolean isShowMax() {
		return showMax;
	}
	public void setShowMax(boolean showMax) {
		this.showMax = showMax;
	}
	public boolean isShowMin() {
		return showMin;
	}
	public void setShowMin(boolean showMin) {
		this.showMin = showMin;
	}
	public boolean isShowAvg() {
		return showAvg;
	}
	public void setShowAvg(boolean showAvg) {
		this.showAvg = showAvg;
	}
	public boolean isShowWaterBlack() {
		return showWaterBlack;
	}
	public void setShowWaterBlack(boolean showWaterBlack) {
		this.showWaterBlack = showWaterBlack;
	}
	public boolean isShowWaterSurface() {
		return showWaterSurface;
	}
	public void setShowWaterSurface(boolean showWaterSurface) {
		this.showWaterSurface = showWaterSurface;
	}
	public boolean isShowMain() {
		return showMain;
	}
	public void setShowMain(boolean showMain) {
		this.showMain = showMain;
	}
	public boolean isP1() {
		return p1;
	}
	public void setP1(boolean p1) {
		this.p1 = p1;
	}
	public boolean isP2() {
		return p2;
	}
	public void setP2(boolean p2) {
		this.p2 = p2;
	}
	public boolean isP3() {
		return p3;
	}
	public void setP3(boolean p3) {
		this.p3 = p3;
	}
	public boolean isP4() {
		return p4;
	}
	public void setP4(boolean p4) {
		this.p4 = p4;
	}
	public boolean isP5() {
		return p5;
	}
	public void setP5(boolean p5) {
		this.p5 = p5;
	}
	public boolean isP6() {
		return p6;
	}
	public void setP6(boolean p6) {
		this.p6 = p6;
	}
	public boolean isP7() {
		return p7;
	}
	public void setP7(boolean p7) {
		this.p7 = p7;
	}
	public boolean isP8() {
		return p8;
	}
	public void setP8(boolean p8) {
		this.p8 = p8;
	}
	public boolean isP9() {
		return p9;
	}
	public void setP9(boolean p9) {
		this.p9 = p9;
	}
	public boolean isP10() {
		return p10;
	}
	public void setP10(boolean p10) {
		this.p10 = p10;
	}
	public boolean isP11() {
		return p11;
	}
	public void setP11(boolean p11) {
		this.p11 = p11;
	}
	public boolean isP12() {
		return p12;
	}
	public void setP12(boolean p12) {
		this.p12 = p12;
	}
	public boolean isP13() {
		return p13;
	}
	public void setP13(boolean p13) {
		this.p13 = p13;
	}
	public boolean isP14() {
		return p14;
	}
	public void setP14(boolean p14) {
		this.p14 = p14;
	}
	public boolean isP15() {
		return p15;
	}
	public void setP15(boolean p15) {
		this.p15 = p15;
	}
	public boolean isP16() {
		return p16;
	}
	public void setP16(boolean p16) {
		this.p16 = p16;
	}
	public boolean isP17() {
		return p17;
	}
	public void setP17(boolean p17) {
		this.p17 = p17;
	}
	public boolean isP18() {
		return p18;
	}
	public void setP18(boolean p18) {
		this.p18 = p18;
	}
	public boolean isP19() {
		return p19;
	}
	public void setP19(boolean p19) {
		this.p19 = p19;
	}
	public boolean isP20() {
		return p20;
	}
	public void setP20(boolean p20) {
		this.p20 = p20;
	}
	public boolean isP21() {
		return p21;
	}
	public void setP21(boolean p21) {
		this.p21 = p21;
	}
	public boolean isP22() {
		return p22;
	}
	public void setP22(boolean p22) {
		this.p22 = p22;
	}
	public boolean isP23() {
		return p23;
	}
	public void setP23(boolean p23) {
		this.p23 = p23;
	}
	public boolean isP24() {
		return p24;
	}
	public void setP24(boolean p24) {
		this.p24 = p24;
	}
	public boolean isP25() {
		return p25;
	}
	public void setP25(boolean p25) {
		this.p25 = p25;
	}
	public boolean isP26() {
		return p26;
	}
	public void setP26(boolean p26) {
		this.p26 = p26;
	}
	public boolean isP27() {
		return p27;
	}
	public void setP27(boolean p27) {
		this.p27 = p27;
	}
	public boolean isP28() {
		return p28;
	}
	public void setP28(boolean p28) {
		this.p28 = p28;
	}
	public boolean isP29() {
		return p29;
	}
	public void setP29(boolean p29) {
		this.p29 = p29;
	}
	public boolean isP30() {
		return p30;
	}
	public void setP30(boolean p30) {
		this.p30 = p30;
	}
	public boolean isP31() {
		return p31;
	}
	public void setP31(boolean p31) {
		this.p31 = p31;
	}
	public boolean isP32() {
		return p32;
	}
	public void setP32(boolean p32) {
		this.p32 = p32;
	}
	
}
