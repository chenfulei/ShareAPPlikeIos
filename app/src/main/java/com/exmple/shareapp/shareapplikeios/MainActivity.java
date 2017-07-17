package com.exmple.shareapp.shareapplikeios;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.exmple.shareapp.shareapplikeios.bottomSheet.RecyclerViewAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btnAndroid;
    private Button btnIos;
    private Button btnBottomSheet;
    private HorizontalListView h_list_view;
    private ListView listView;
    private GridView gridView;
    HorizontalListViewAdapter h_adapter;
    ArrayList<AppInfo> list;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView mRecyclerView;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    BottomSheetDialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();

        list = getShareAppList();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        btnBottomSheet = (Button) findViewById(R.id.bottom_btn);
        btnAndroid = (Button) findViewById(R.id.android_btn);
        btnIos = (Button) findViewById(R.id.ios_btn);
        btnAndroid.setOnClickListener(this);
        btnIos.setOnClickListener(this);
        btnBottomSheet.setOnClickListener(this);
        View btn = findViewById(R.id.btn_justify_text_view);
        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.android_btn:
                Intent data = new Intent(Intent.ACTION_SEND);
                data.setData(Uri.parse("mailto:"));
                data.setType("text/plain");
                data.putExtra(Intent.EXTRA_TEXT, "www.baidu.com");
                startActivity(data);
                break;

            case R.id.ios_btn:
                showDialog();
                break;

            case R.id.bottom_btn:
                showBottomDialog();
                break;

            case R.id.btn_justify_text_view:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, JustifyTextViewActivity.class);
                startActivity(intent);
                break;


        }

    }

    private void showDialog() {

        final Dialog d = Utility.getDialog(this, R.layout.dialog_share_app);
        Window dialogWindow = d.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setAttributes(lp);

        initDialog(d);

        d.show();

    }

    private void showBottomDialog() {
         d = Utility.getBottomDialog(this, R.layout.dialog_bottom_sheet);

//        Window dialogWindow = d.getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setAttributes(lp);

        initBottomDialog(d);
        setBehaviorCallback();
        d.show();

    }

    private void setBehaviorCallback() {
        View view = d.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    d.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    private void initBottomDialog(final Dialog d) {
        recyclerViewAdapter = new RecyclerViewAdapter(this,list);
        //listView
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        //瀑布流
        mRecyclerView = (RecyclerView)d.findViewById(R.id.list_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setAdapter(recyclerViewAdapter);


        // cyl
//        gridView = (GridView) d.findViewById(R.id.list_view);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
////            gridView.setNestedScrollingEnabled(true);
////        }
//        h_adapter = new HorizontalListViewAdapter(this, R.layout.cell_share_horizontal_list_item, list);
//        gridView.setAdapter(h_adapter);

        recyclerViewAdapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(AppInfo parent,View view, int position) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                AppInfo appInfo = parent;
                shareIntent.setComponent(new ComponentName(appInfo.getPkgName(), appInfo.getLaunchClassName()));
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.baidu.com");
                startActivity(shareIntent);
            }

            @Override
            public void onItemLongClick(AppInfo parent,View view, int position) {

            }
        });


//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                AppInfo appInfo = (AppInfo) parent.getItemAtPosition(position);
//                shareIntent.setComponent(new ComponentName(appInfo.getPkgName(), appInfo.getLaunchClassName()));
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.baidu.com");
//                startActivity(shareIntent);
//            }
//        });

//        Button btnDismiss = (Button) d.findViewById(R.id.btn_dismiss);
//        btnDismiss.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                d.dismiss();
//            }
//        });
    }


    private void initDialog(final Dialog d) {
        // cyl
        h_list_view = (HorizontalListView) d.findViewById(R.id.h_list_view);
        h_adapter = new HorizontalListViewAdapter(this, R.layout.cell_share_horizontal_list_item, list);
        h_list_view.setAdapter(h_adapter);
        h_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                AppInfo appInfo = (AppInfo) parent.getItemAtPosition(position);
                shareIntent.setComponent(new ComponentName(appInfo.getPkgName(), appInfo.getLaunchClassName()));
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "www.baidu.com");
                startActivity(shareIntent);
            }
        });

        Button btnDismiss = (Button) d.findViewById(R.id.btn_dismiss);
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }


    private ArrayList<AppInfo> getShareAppList() {
        ArrayList<AppInfo> shareAppInfos = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfos = getShareApps(MainActivity.this);
        if (null == resolveInfos) {
            return null;
        } else {
            for (ResolveInfo resolveInfo : resolveInfos) {
                AppInfo appInfo = new AppInfo();
                appInfo.setPkgName(resolveInfo.activityInfo.packageName);
                appInfo.setLaunchClassName(resolveInfo.activityInfo.name);
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));
                shareAppInfos.add(appInfo);
            }
        }

        return shareAppInfos;
    }

    public List<ResolveInfo> getShareApps(Context context) {
        List<ResolveInfo> mApps;
        Intent intent = new Intent(Intent.ACTION_SEND, null);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("text/plain");
        PackageManager pManager = context.getPackageManager();
        mApps = pManager.queryIntentActivities(intent,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        return mApps;
    }



}
