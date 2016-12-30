package com.whut.net;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.track;
import com.whut.myMap.entites.url;
import com.whut.myMap.utils.gsonutil;
import com.whut.myMap.utils.httpclient;
public class addtracknet {
	public String  addtrackbean(trackbean trackbean,Context context){
		try{
		int trackid=Integer.parseInt(addtrack(trackbean.getTrack()));
		System.out.println(trackid);
		if(trackid==0){
			int a=1/0;
		}else{
		if(trackbean.getRedsbean()!=null){
			addredsnet addredsnet=new addredsnet();
		   for(redsbean redsbean:trackbean.getRedsbean()){
			redsbean.getReds().setTrack_id(trackid);
		 	int result=Integer.parseInt(addredsnet.addreds(redsbean));
		 	if(result==0){
		 		int a=1/0;
		 	}
		  }
		 }
		System.out.println(trackid+"fd");
		addlbs(trackid,trackbean.getLbsbean());
		return 1+"";
		}
		}catch(Exception e){
			e.printStackTrace();
			return 0+"";
		}
		return 0+"";	
	}
    public String addtrack(track track) {
    	try{
    	httpclient http=new httpclient();
		Map<String ,String > parsm=new HashMap<String, String>();	
		List<File> fList=new ArrayList<File>();
		File  f;
		File music;
		String jsonString2=gsonutil.createJsonString(track);
	    parsm.put("track", jsonString2);
	    try {
		 f=new File(track.getBgurl());
		}catch (Exception e) {
			// TODO: handle exception
			String path="file:///android_asset/head.jpg";
			System.out.println(path);
			f=new File(path);
		}
	    try {
			 music=new File(track.getMusicstring());
			 fList.add(music);
			}catch (Exception e) {
				// TODO: handle exception
			}
		fList.add(f);
		return http.uploadSubmit(new url().geturl()+"/servlet/addtrackNEW", parsm, fList);
    }catch(Exception e){
    	e.printStackTrace();
    	throw new RuntimeException(e);
      }
	}
    public void addlbs(int trackid, List<lbs> list) {
    	try {
		httpclient http=new httpclient();
    	Map<String ,String > parsm=new HashMap<String, String>();	
		String jsonString1=gsonutil.createJsonString(list);
		System.out.println(jsonString1+"hlkdshghd");
		parsm.put("listlbs",jsonString1);
		parsm.put("trackid", trackid+"");
		http.sendPost(new url().geturl()+"/servlet/addlbs",parsm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
