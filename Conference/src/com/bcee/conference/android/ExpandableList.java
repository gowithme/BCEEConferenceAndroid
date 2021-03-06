package com.bcee.conference.android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.bcee.conference.android.models.ConferenceModel;
import com.bcee.conference.android.models.DataCentre;

public class ExpandableList extends Activity {

	private ExpandableListView elv;
	private TimeExpandableListAdapter adapter;
	private DataCentre dc = DataCentre.createDefaultInstance();
	private List<String> categories;
	private LinkedHashMap<String,List<String>> sessions;
	private List<String> original;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expandable_list);
		initvars();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/** @author http://androidforbegineers.blogspot.ca/2013/08/android-slide-animation-effect-between.html*/
		this.overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
	}

	private void initvars() {
		categories = new ArrayList<String>();
		original = new ArrayList<String>();
		sessions = new LinkedHashMap<String,List<String>>();
		for(String s:dc.getStartTimes()){	
			try {
				Date d = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CANADA).parse(s);
				String date = new SimpleDateFormat("EEEE HH:mm",Locale.CANADA).format(d);
				sessions.put(date,DataCentre.getDataByTime(s));
				categories.add(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			original.add(s);
		}
		
		elv = (ExpandableListView) findViewById(R.id.expandableELV1);
		adapter = new TimeExpandableListAdapter(this, categories, sessions);
		elv.setAdapter(adapter);
		
		elv.setOnChildClickListener(new OnChildClickListener(){
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
                    int groupPosition, int childPosition, long id) {
				ConferenceModel c = dc.findConference(original.get(groupPosition), sessions.get(categories.get(groupPosition)).get(childPosition));
				Intent i = new Intent(v.getContext(),Conferences.class);
				i.putExtra("conference", c);
				startActivityForResult(i,0);
                return false;
            }
		});
	}

	/**
	 * Class to handle the ExpandableListAdapter
	 * 
	 * @author http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
	 *
	 */
	class TimeExpandableListAdapter extends BaseExpandableListAdapter {
		 
	    private Context _context;
	    private List<String> _listDataHeader; // header titles
	    // child data in format of header title, child title
	    private LinkedHashMap<String, List<String>> _listDataChild;
	 
	    public TimeExpandableListAdapter(Context context, List<String> listDataHeader,
	            LinkedHashMap<String, List<String>> listChildData) {
	        this._context = context;
	        this._listDataHeader = listDataHeader;
	        this._listDataChild = listChildData;
	    }
	 
	    @Override
	    public Object getChild(int groupPosition, int childPosititon) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .get(childPosititon);
	    }
	 
	    @Override
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	 
	    @Override
	    public View getChildView(int groupPosition, final int childPosition,
	            boolean isLastChild, View convertView, ViewGroup parent) {
	 
	        final String childText = (String) getChild(groupPosition, childPosition);
	 
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.list_item, null);
	        }
	 
	        TextView txtListChild = (TextView) convertView
	                .findViewById(R.id.lblListItem);
	 
	        txtListChild.setText(childText);
	        return convertView;
	    }
	 
	    @Override
	    public int getChildrenCount(int groupPosition) {
	        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
	                .size();
	    }
	 
	    @Override
	    public Object getGroup(int groupPosition) {
	        return this._listDataHeader.get(groupPosition);
	    }
	 
	    @Override
	    public int getGroupCount() {
	        return this._listDataHeader.size();
	    }
	 
	    @Override
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }
	 
	    @Override
	    public View getGroupView(int groupPosition, boolean isExpanded,
	            View convertView, ViewGroup parent) {
	        String headerTitle = (String) getGroup(groupPosition);
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this._context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.list_group, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView
	                .findViewById(R.id.lblListHeader);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
	    }
	 
	    @Override
	    public boolean hasStableIds() {
	        return false;
	    }
	 
	    @Override
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	}
}
