package com.whut.myMap.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.baidu.android.common.logging.Log;
import com.whut.net.loginnet;

public class httpclient{		
		private HttpClient client;		
		private  HttpPost post;
		private HttpGet get;
		private HttpParams httpParams;	
		public static Header[] headers;	
		public static String JSESSIONID  = null;
		static {			
		}	
		public httpclient() {
			client = new DefaultHttpClient();
			httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);
		}
		//采用短链接的http协议进行通讯,提交方式为post和get两种方式，返回的数据类型为JSON	
		/**
		 * 发送post请求
		 * @param uri 服务器地址
		 * @param params 键值对参数
		 * @return 服务器返回的json字符串
		 */
		public String sendPost(String uri,Map<String,String> params) {
			try {
			post = new HttpPost(uri);
			post.setParams(httpParams);
			if(params != null && params.size()>0) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>();
				for(Map.Entry<String, String> item: params.entrySet()) {
					NameValuePair pair = new BasicNameValuePair(item.getKey(),item.getValue());
					pairs.add(pair);
				}
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs,"utf-8");
					post.setEntity(entity);
				} 		
			 if(null != JSESSIONID ){
	                post.setHeader("Cookie", "JSESSIONID=" + JSESSIONID );
	            }   
				HttpResponse httpresponse= client.execute(post);	
				int statusCode = httpresponse.getStatusLine().getStatusCode();
				System.out.println("statusCode:"+statusCode);
				if(statusCode == 200) {
					CookieStore cookieStore = ((AbstractHttpClient) client).getCookieStore();  
		            List<Cookie> cookies = cookieStore.getCookies();  
		            for(int i=0;i<cookies.size();i++){  
		                if("JSESSIONID".equals(cookies.get(i).getName())){  
		                    JSESSIONID = cookies.get(i).getValue();  
		                    break;  
		                }  
		            }  
					return EntityUtils.toString(httpresponse.getEntity(), "utf-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				if(client != null) {
					client.getConnectionManager().shutdown();
				}
			}
			return null;
		}	
		/**
		 * 发送get请求
		 * @param uri 服务器地址
		 * @param params 请求参数
		 * @return 服务器返回的json字符串,失败则返回null
		 */
		public String sendGet(String uri){
			get = new HttpGet(uri);
			get.setParams(httpParams);
			try {
				 if(null != JSESSIONID ){
		                get.setHeader("Cookie", "JSESSIONID=" + JSESSIONID );
		            }            
				HttpResponse httpresponse = client.execute(get);
				int statusCode = httpresponse.getStatusLine().getStatusCode();
				Log.i("statuscodeget", statusCode+"");
				System.out.println("statusCode:"+statusCode);
				if(statusCode == 200) {
					CookieStore mCookieStore = ((AbstractHttpClient) client).getCookieStore();
	                List<Cookie> cookies = mCookieStore.getCookies();
	                for (int i = 0; i < cookies.size(); i++) {
	                    //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
	                    if ("JSESSIONID".equals(cookies.get(i).getName())) {
	                    	JSESSIONID  = cookies.get(i).getValue();
	                        break;
	                    }
	                }
					return  EntityUtils.toString(httpresponse.getEntity(),"utf-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				if(client != null) {
				client.getConnectionManager().shutdown();
				}
			}
			return null;
		}
		//发送个人头像
		public  String uploadSubmit(String url, Map<String, String> param,List<File> listfile) throws Exception {
			try{
			System.out.println("11111");		
			 post = new HttpPost(url);  
//			HttpClient httpClient=new DefaultHttpClient();
			MultipartEntity entity = new MultipartEntity();
			if (param != null && !param.isEmpty()) {
				for (Map.Entry<String, String> entry : param.entrySet()) {     
					if (entry.getValue() != null
							&& entry.getValue().trim().length() > 0) {
						entity.addPart(entry.getKey(),new StringBody(entry.getValue(),
								Charset.forName(org.apache.http.protocol.HTTP.UTF_8)));
					}
				}
			}
			// 添加文件参数
			for(File file:listfile)
			if (file != null && file.exists()) {
				entity.addPart("file", new FileBody(file));
				System.out.print(444);
			}
			post.setEntity(entity);  
			 if(null != JSESSIONID ){
	             post.setHeader("Cookie", "JSESSIONID=" + JSESSIONID );
	            }            
			HttpResponse response = client.execute(post);
			int stateCode = response.getStatusLine().getStatusCode();
			if (stateCode == HttpStatus.SC_OK) {
				HttpEntity result = response.getEntity();
				CookieStore mCookieStore = ((AbstractHttpClient) client).getCookieStore();
                List<Cookie> cookies = mCookieStore.getCookies();
                for (int i = 0; i < cookies.size(); i++) {
                    //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
                    if ("PHPSESSID".equals(cookies.get(i).getName())) {
                    	JSESSIONID  = cookies.get(i).getValue();
                        break;
                    }
                }
				if (result != null) {
					return EntityUtils.toString(result,"utf-8");
				}        
			 }
			}catch(Exception e){
				e.printStackTrace();
			    throw new RuntimeException(e);
			}
			return url;
		}
	}