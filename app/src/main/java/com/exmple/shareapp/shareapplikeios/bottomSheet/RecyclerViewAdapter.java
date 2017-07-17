package com.exmple.shareapp.shareapplikeios.bottomSheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.exmple.shareapp.shareapplikeios.AppInfo;
import com.exmple.shareapp.shareapplikeios.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 2016/7/29.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<Integer> mHeight;
    private ArrayList<AppInfo> appList;
    public interface OnItemClickLitener {
        void onItemClick(AppInfo parent, View view, int position);
        void onItemLongClick(AppInfo parent, View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public RecyclerViewAdapter(Context mContext, ArrayList<AppInfo> date) {
        this.appList = date;
        this.mContext = mContext;

        //瀑布流随机高度
//        mHeight = new ArrayList<Integer>();
//        for (int i=0; i<10; i++){
//            mHeight.add((int) (300+Math.random()*300));
//        }
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_share_bottomsheet_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, int position) {

//        final View view = holder.mView;
        holder.tv.setText(appList.get(position).getAppName());
        holder.img.setBackground(appList.get(position).getAppIcon());
        //把随机高度加到每个item
//        ViewGroup.LayoutParams lp= holder.itemView.getLayoutParams();
//        lp.height = mHeight.get(position);
//        holder.itemView.setLayoutParams(lp);

        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(appList.get(pos), holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(appList.get(pos),holder.itemView, pos);
                    return false;
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView img;
        public ViewHolder(View view) {
            super(view);
             tv = (TextView) view.findViewById(R.id.text_list_item);
            img = (ImageView) view.findViewById(R.id.img_list_item);
        }
    }


}
