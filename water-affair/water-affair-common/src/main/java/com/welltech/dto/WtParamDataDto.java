/**
 * 
 */
package com.welltech.dto;

import java.util.Date;

/**
 * Created by Zhujia at 2017年8月16日 下午5:26:12
 */
public class WtParamDataDto {

	private Date startDate;

	private Date endDate;

	/**
	 * 格式化日期 仅作显示 无逻辑
	 */
	private String dateFormatStr;

	/**
	 * 用户有效参数拼接，例：pollutant=“p1,p2,p5,p9”；一般是指有评级结果的数据参数
	 */
	private String pollutant;

	/**
	 * 数据类型: 
	 * 0:按日期统计数据 
	 * 1:最小值 
	 * 2:最大值 
	 * 3:平均值 
	 * 4:分项类别/黑臭三分 
	 * 5:分项类别/地表水质
	 */
	private String dataType;

	private String p1;
	private String p2;
	private String p3;
	private String p4;
	private String p5;
	private String p6;
	private String p7;
	private String p8;
	private String p9;
	private String p10;
	private String p11;
	private String p12;
	private String p13;
	private String p14;
	private String p15;
	private String p16;
	private String p17;
	private String p18;
	private String p19;
	private String p20;
	private String p21;
	private String p22;
	private String p23;
	private String p24;
	private String p25;
	private String p26;
	private String p27;
	private String p28;
	private String p29;
	private String p30;
	private String p31;
	private String p32;

	//标准名称
	private String tarP1 = "p1";
	private String tarP2 = "p2";
	private String tarP3 = "p3";
	private String tarP4 = "p4";
	private String tarP5 = "p5";
	private String tarP6 = "p6";
	private String tarP7 = "p7";
	private String tarP8 = "p8";
	private String tarP9 = "p9";
	private String tarP10 = "p10";
	private String tarP11 = "p11";
	private String tarP12 = "p12";
	private String tarP13 = "p13";
	private String tarP14 = "p14";
	private String tarP15 = "p15";
	private String tarP16 = "p16";
	private String tarP17 = "p17";
	private String tarP18 = "p18";
	private String tarP19 = "p19";
	private String tarP20 = "p20";
	private String tarP21 = "p21";
	private String tarP22 = "p22";
	private String tarP23 = "p23";
	private String tarP24 = "p24";
	private String tarP25 = "p25";
	private String tarP26 = "p26";
	private String tarP27 = "p27";
	private String tarP28 = "p28";
	private String tarP29 = "p29";
	private String tarP30 = "p30";
	private String tarP31 = "p31";
	private String tarP32 = "p32";

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDateFormatStr() {
		return dateFormatStr;
	}

	public void setDateFormatStr(String dateFormatStr) {
		this.dateFormatStr = dateFormatStr;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public String getP3() {
		return p3;
	}

	public void setP3(String p3) {
		this.p3 = p3;
	}

	public String getP4() {
		return p4;
	}

	public void setP4(String p4) {
		this.p4 = p4;
	}

	public String getP5() {
		return p5;
	}

	public void setP5(String p5) {
		this.p5 = p5;
	}

	public String getP6() {
		return p6;
	}

	public void setP6(String p6) {
		this.p6 = p6;
	}

	public String getP7() {
		return p7;
	}

	public void setP7(String p7) {
		this.p7 = p7;
	}

	public String getP8() {
		return p8;
	}

	public void setP8(String p8) {
		this.p8 = p8;
	}

	public String getP9() {
		return p9;
	}

	public void setP9(String p9) {
		this.p9 = p9;
	}

	public String getP10() {
		return p10;
	}

	public void setP10(String p10) {
		this.p10 = p10;
	}

	public String getP11() {
		return p11;
	}

	public void setP11(String p11) {
		this.p11 = p11;
	}

	public String getP12() {
		return p12;
	}

	public void setP12(String p12) {
		this.p12 = p12;
	}

	public String getP13() {
		return p13;
	}

	public void setP13(String p13) {
		this.p13 = p13;
	}

	public String getP14() {
		return p14;
	}

	public void setP14(String p14) {
		this.p14 = p14;
	}

	public String getP15() {
		return p15;
	}

	public void setP15(String p15) {
		this.p15 = p15;
	}

	public String getP16() {
		return p16;
	}

	public void setP16(String p16) {
		this.p16 = p16;
	}

	public String getP17() {
		return p17;
	}

	public void setP17(String p17) {
		this.p17 = p17;
	}

	public String getP18() {
		return p18;
	}

	public void setP18(String p18) {
		this.p18 = p18;
	}

	public String getP19() {
		return p19;
	}

	public void setP19(String p19) {
		this.p19 = p19;
	}

	public String getP20() {
		return p20;
	}

	public void setP20(String p20) {
		this.p20 = p20;
	}

	public String getP21() {
		return p21;
	}

	public void setP21(String p21) {
		this.p21 = p21;
	}

	public String getP22() {
		return p22;
	}

	public void setP22(String p22) {
		this.p22 = p22;
	}

	public String getP23() {
		return p23;
	}

	public void setP23(String p23) {
		this.p23 = p23;
	}

	public String getP24() {
		return p24;
	}

	public void setP24(String p24) {
		this.p24 = p24;
	}

	public String getP25() {
		return p25;
	}

	public void setP25(String p25) {
		this.p25 = p25;
	}

	public String getP26() {
		return p26;
	}

	public void setP26(String p26) {
		this.p26 = p26;
	}

	public String getP27() {
		return p27;
	}

	public void setP27(String p27) {
		this.p27 = p27;
	}

	public String getP28() {
		return p28;
	}

	public void setP28(String p28) {
		this.p28 = p28;
	}

	public String getP29() {
		return p29;
	}

	public void setP29(String p29) {
		this.p29 = p29;
	}

	public String getP30() {
		return p30;
	}

	public void setP30(String p30) {
		this.p30 = p30;
	}

	public String getP31() {
		return p31;
	}

	public void setP31(String p31) {
		this.p31 = p31;
	}

	public String getP32() {
		return p32;
	}

	public void setP32(String p32) {
		this.p32 = p32;
	}

	public String getTarP1() {
		return tarP1;
	}

	public void setTarP1(String tarP1) {
		this.tarP1 = tarP1;
	}

	public String getTarP2() {
		return tarP2;
	}

	public void setTarP2(String tarP2) {
		this.tarP2 = tarP2;
	}

	public String getTarP3() {
		return tarP3;
	}

	public void setTarP3(String tarP3) {
		this.tarP3 = tarP3;
	}

	public String getTarP4() {
		return tarP4;
	}

	public void setTarP4(String tarP4) {
		this.tarP4 = tarP4;
	}

	public String getTarP5() {
		return tarP5;
	}

	public void setTarP5(String tarP5) {
		this.tarP5 = tarP5;
	}

	public String getTarP6() {
		return tarP6;
	}

	public void setTarP6(String tarP6) {
		this.tarP6 = tarP6;
	}

	public String getTarP7() {
		return tarP7;
	}

	public void setTarP7(String tarP7) {
		this.tarP7 = tarP7;
	}

	public String getTarP8() {
		return tarP8;
	}

	public void setTarP8(String tarP8) {
		this.tarP8 = tarP8;
	}

	public String getTarP9() {
		return tarP9;
	}

	public void setTarP9(String tarP9) {
		this.tarP9 = tarP9;
	}

	public String getTarP10() {
		return tarP10;
	}

	public void setTarP10(String tarP10) {
		this.tarP10 = tarP10;
	}

	public String getTarP11() {
		return tarP11;
	}

	public void setTarP11(String tarP11) {
		this.tarP11 = tarP11;
	}

	public String getTarP12() {
		return tarP12;
	}

	public void setTarP12(String tarP12) {
		this.tarP12 = tarP12;
	}

	public String getTarP13() {
		return tarP13;
	}

	public void setTarP13(String tarP13) {
		this.tarP13 = tarP13;
	}

	public String getTarP14() {
		return tarP14;
	}

	public void setTarP14(String tarP14) {
		this.tarP14 = tarP14;
	}

	public String getTarP15() {
		return tarP15;
	}

	public void setTarP15(String tarP15) {
		this.tarP15 = tarP15;
	}

	public String getTarP16() {
		return tarP16;
	}

	public void setTarP16(String tarP16) {
		this.tarP16 = tarP16;
	}

	public String getTarP17() {
		return tarP17;
	}

	public void setTarP17(String tarP17) {
		this.tarP17 = tarP17;
	}

	public String getTarP18() {
		return tarP18;
	}

	public void setTarP18(String tarP18) {
		this.tarP18 = tarP18;
	}

	public String getTarP19() {
		return tarP19;
	}

	public void setTarP19(String tarP19) {
		this.tarP19 = tarP19;
	}

	public String getTarP20() {
		return tarP20;
	}

	public void setTarP20(String tarP20) {
		this.tarP20 = tarP20;
	}

	public String getTarP21() {
		return tarP21;
	}

	public void setTarP21(String tarP21) {
		this.tarP21 = tarP21;
	}

	public String getTarP22() {
		return tarP22;
	}

	public void setTarP22(String tarP22) {
		this.tarP22 = tarP22;
	}

	public String getTarP23() {
		return tarP23;
	}

	public void setTarP23(String tarP23) {
		this.tarP23 = tarP23;
	}

	public String getTarP24() {
		return tarP24;
	}

	public void setTarP24(String tarP24) {
		this.tarP24 = tarP24;
	}

	public String getTarP25() {
		return tarP25;
	}

	public void setTarP25(String tarP25) {
		this.tarP25 = tarP25;
	}

	public String getTarP26() {
		return tarP26;
	}

	public void setTarP26(String tarP26) {
		this.tarP26 = tarP26;
	}

	public String getTarP27() {
		return tarP27;
	}

	public void setTarP27(String tarP27) {
		this.tarP27 = tarP27;
	}

	public String getTarP28() {
		return tarP28;
	}

	public void setTarP28(String tarP28) {
		this.tarP28 = tarP28;
	}

	public String getTarP29() {
		return tarP29;
	}

	public void setTarP29(String tarP29) {
		this.tarP29 = tarP29;
	}

	public String getTarP30() {
		return tarP30;
	}

	public void setTarP30(String tarP30) {
		this.tarP30 = tarP30;
	}

	public String getTarP31() {
		return tarP31;
	}

	public void setTarP31(String tarP31) {
		this.tarP31 = tarP31;
	}

	public String getTarP32() {
		return tarP32;
	}

	public void setTarP32(String tarP32) {
		this.tarP32 = tarP32;
	}

	public String getPollutant() {
		return pollutant;
	}

	public void setPollutant(String pollutant) {
		this.pollutant = pollutant;
	}
	
}
