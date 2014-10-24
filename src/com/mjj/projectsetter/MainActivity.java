package com.mjj.projectsetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mjj.dao.Project;
import com.mjj.dao.ProjectSetterDAO;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {

	private ProjectSetterDAO pjDAO;
	private ListView listView;
	private Button button;
	
	private static final String msgTag="MAINACTIVITY";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pjDAO=new ProjectSetterDAO(this);
        setContentView(R.layout.activity_main);
        listView=(ListView) findViewById(R.id.list_pj);
        PjAdapter adapter=new PjAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.v(msgTag, "您点击了："+arg2);
			}
        	
		});
        
        button=(Button) findViewById(R.id.add_btn);
        button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent();
				intent.setClass(MainActivity.this, Config1.class);
				startActivity(intent);
			}
		});
        
    }
    
    public ArrayList<Map<String,String>>  query(){
    	List<Project> pjs=pjDAO.query();
    	ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
    	for (Project pj : pjs) {
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("NAME", "标题:"+pj.name);
			map.put("ENDTIME","结束时间:"+ pj.endTime.toString().replace(" 11:59:59.0",""));
			map.put("REMIND_STARTTIME","提醒开始时间:"+ pj.remindStartTime.toString());
			map.put("REMIND_ENDTIME","提醒结束时间:"+ pj.remindEndTime.toString());
			list.add(map);
		}
    	return list;
    }
    
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	pjDAO.closeDB();
    }

    //定义
    private class PjAdapter extends BaseAdapter{

    	private static final String tagName="ADAPTER";
    	
    	private LayoutInflater mInflater; //用于导入布局
    	
    	public PjAdapter(Context context){
    		this.mInflater=LayoutInflater.from(context);
    	}
    	
		@Override
		public int getCount() {
			return query().size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;	//创建ListView每一行的显示对象
			Log.w(tagName, "getView,position:"+position+",convertView:"+convertView);
			if(convertView==null){
				convertView=mInflater.inflate(R.layout.item, null);	//读取item.xml布局
				holder=new ViewHolder();
				holder.name=(TextView) convertView.findViewById(R.id.name);
				holder.endTime=(TextView) convertView.findViewById(R.id.endTime);
				holder.remindStartTime=(TextView) convertView.findViewById(R.id.remindStartTime);
				holder.remindEndTime=(TextView) convertView.findViewById(R.id.remindEndTime);
				convertView.setTag(holder); //绑定ViewHolder对象
			}else{
				holder=(ViewHolder) convertView.getTag();	//获取ViewHolder 里的缓存对象
			}
			holder.name.setText(query().get(position).get("NAME").toString());
			holder.endTime.setText(query().get(position).get("ENDTIME").toString());
			holder.remindStartTime.setText(query().get(position).get("REMIND_STARTTIME").toString());
			holder.remindEndTime.setText(query().get(position).get("REMIND_ENDTIME").toString());
			return convertView;
		}
    	
    }

    
    private class ViewHolder{
    	
    	public TextView name;
    	public TextView endTime;
    	public TextView remindStartTime;
    	public TextView remindEndTime;
    }
    
}
