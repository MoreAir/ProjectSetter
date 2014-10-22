package com.mjj.projectsetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mjj.dao.Project;
import com.mjj.dao.ProjectSetterDAO;

import android.app.Activity;
import android.content.Context;
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
				
			}
		});
        
    }
    
    public ArrayList<Map<String,String>>  query(){
    	List<Project> pjs=pjDAO.query();
    	ArrayList<Map<String,String>> list=new ArrayList<Map<String,String>>();
    	for (Project pj : pjs) {
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("NAME", pj.name);
			map.put("ENDTIME", pj.endTime.toString());
			list.add(map);
		}
    	return list;
    }
    
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	pjDAO.closeDB();
    }

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
			ViewHolder holder;
			Log.w(tagName, "getView,position:"+position+",convertView:"+convertView);
			if(convertView==null){
				convertView=mInflater.inflate(R.layout.item, null);
				holder=new ViewHolder();
				holder.name=(TextView) convertView.findViewById(R.id.name);
				holder.comment=(TextView) convertView.findViewById(R.id.endTime);
				convertView.setTag(holder); //绑定ViewHolder对象
			}else{
				holder=(ViewHolder) convertView.getTag();
			}
			holder.name.setText(query().get(position).get("NAME").toString());
			holder.comment.setText(query().get(position).get("ENDTIME").toString());
			
			return convertView;
		}
    	
    }

    
    private class ViewHolder{
    	
    	public TextView name;
    	public TextView comment;
    	
    }
    
}
