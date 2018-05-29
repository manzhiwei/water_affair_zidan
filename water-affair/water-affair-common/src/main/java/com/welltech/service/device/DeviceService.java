package com.welltech.service.device;


import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class DeviceService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${device.server.url}")
	private String url;
	
	@Value("${device.app.id}")
	private String appId;
	
	@Value("${device.app.secret}")
	private String appSecret;
	
	public String applyJob(String jobCode, String jobBizNo, String jobCreateBy,
			JSONObject data){
		String result = "";
		String timestamp = new Date().getTime() + "";
		JSONObject content = new JSONObject();
		content.put("jobCode", jobCode);
		content.put("jobBizNo", jobBizNo);
		content.put("jobCreateBy", jobCreateBy);
		content.put("jobData", data.toString());
		try {
			OkHttpClient client = new OkHttpClient.Builder()
					.connectTimeout(30, TimeUnit.SECONDS)
					.readTimeout(30, TimeUnit.SECONDS)
					.build();
			RequestBody body = RequestBody.create(
					MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE), 
					content.toString());
			Request request = new Request.Builder()
					.url(url + "/rest/job.json")
					.header("Authorization", appId)
					.header("Timestamp", timestamp)
					.header("Sign", DigestUtils.md5DigestAsHex((timestamp + appSecret).getBytes()))
					.post(body)
					.build();
			
			logger.info("请求{}，数据：{}", url, content.toString());
			
			Call call = client.newCall(request);
			Response response = call.execute();
			String respStr = response.body().string();
			
			logger.info("返回数据：{}", respStr);
			
			JSONObject respJson = JSON.parseObject(respStr);
			result = respJson.getString("message");
			
			if(StringUtils.isBlank(result)){
				if(respJson.getInteger("status") == HttpStatus.OK.value()){
					result = "操作成功";					
				} else {
					result = "操作失败";
				}
			}
			
		} catch(Exception e){
			logger.error("发生异常", e);
			result = "操作失败";
		}
		return result;
	}
	
	public String applySetConfig(Map<String, Object> map, String mcuId, String username){
		JSONObject data = JSON.parseObject(JSON.toJSONString(map));
		return applyJob("WT04101", mcuId, username, data);
	}
	
}
