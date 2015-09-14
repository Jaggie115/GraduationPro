package com.example.jaggie.graduationproject.adapters;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaggie.graduationproject.R;
import com.example.jaggie.graduationproject.timetasks.TestTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaggie on 2015/1/23.
 */
public class AppInfoAdapter extends BaseAdapter {

    private Context mContext;
    private TestTask timerTask;
    private List<PackageInfo> packageInfos;
    private List<Drawable> appIcons;
    private List<String> appLabels;
    private PackageManager myPackageManager;
    private List<Boolean> checked;
    private List<PackageInfo> selected;

    public AppInfoAdapter(Context mContext, TestTask timerTask) {
        this.timerTask = timerTask;
        myPackageManager = mContext.getPackageManager();
        this.packageInfos = timerTask.getOtherAppPackageInfos();
        this.selected = timerTask.getSelectAppPackageInfos();
        this.appIcons = new ArrayList<Drawable>();
        this.appLabels = new ArrayList<String>();
        this.checked = new ArrayList<Boolean>();
        if (selected == null) {
            this.selected = new ArrayList<PackageInfo>();
        }
        for (PackageInfo pi : packageInfos) {
            appIcons.add(pi.applicationInfo.loadIcon(myPackageManager));
            appLabels.add(pi.applicationInfo.loadLabel(myPackageManager).toString());
            checked.add(false);
        }
        this.mContext = mContext;

        if (selected != null) {
            for (PackageInfo p : selected) {
                int i = packageInfos.indexOf(p);
                if (!checked.get(i)) {
                    checked.set(i, true);
                } else {
                    checked.set(i, false);
                }
            }
        }
    }


    public void setChecked(int position) {
        if (!checked.get(position)) {
            checked.set(position, true);
            selected.add(packageInfos.get(position));
        } else {
            checked.set(position, false);
            selected.remove(packageInfos.get(position));
        }
    }

    public void setCurrentBlockApps() {
        timerTask.setSelectApps(selected);
    }

    @Override
    public int getCount() {
        return packageInfos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return packageInfos.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder bufferViewHolder;
        if (convertView == null) {
            bufferViewHolder = new ViewHolder();
            View view = LayoutInflater.from(mContext).inflate(R.layout.appitem_layout, null);
            bufferViewHolder.appIcon_Iv = (ImageView) view.findViewById(R.id.appIcon_iv);
            bufferViewHolder.appLabel_Tv = (TextView) view.findViewById(R.id.appLabel_tv);
            bufferViewHolder.selectCheck_Iv = (ImageView) view.findViewById(R.id.appSelectCheck_iv);
            view.setTag(bufferViewHolder);
            convertView = view;
        } else {
            bufferViewHolder = (ViewHolder) convertView.getTag();
        }
        bufferViewHolder.appIcon_Iv.setImageDrawable(appIcons.get(position));
        bufferViewHolder.appLabel_Tv.setText(appLabels.get(position));
        if (checked.get(position)) {
            bufferViewHolder.selectCheck_Iv.setVisibility(View.VISIBLE);
        } else {
            bufferViewHolder.selectCheck_Iv.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView appIcon_Iv;
        ImageView selectCheck_Iv;
        TextView appLabel_Tv;
    }
}
