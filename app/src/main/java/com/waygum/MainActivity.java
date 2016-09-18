package com.waygum;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import DataType.OrgDetail;
import DataType.SiteDetail;
import services.APIClass;
import services.MyService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,MyService.Receiver
{
    private Toolbar toolbar;
    private Handler handler;
    private MyService Receiver;
    private Activity activity;
    private LinearLayout parent;
    private int deptOrg=0;
    private  String parentId="";
    private OrgDetail root;
    private ArrayList<SiteDetail> siteDetails;
    private int marginLeft =195;
    private ArrayList<Integer> viewsId= new ArrayList<>();
    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private int clickedDepth;
    String orgList = "http://www.mocky.io/v2/56fe4479110000761ca03626";
    String siteList ="http://www.mocky.io/v2/56fe4cac110000501da03638";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBarToolbar();
        activity = this;
        parent= (LinearLayout) findViewById(R.id.parent);
        handler = new Handler();
        Receiver= new MyService(handler);
        Receiver.setReceiver(MainActivity.this);
        callApi(orgList, 101);
        callApi(siteList, 102);  // calling alltogether to combine orglist and site list
    }

    private void callApi(final String url, final int type) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                new APIClass(type, Receiver).execute(url);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        String data = resultData.getString("apiresult");
        switch (resultCode)
        {
            case 101:
                processJsonOrgList(data);
                break;
            case 102:
                processJsonSiteList(data);
                createDashBoardOrgSite(0, "-1", -1,"-1");
                break;
            case 103:
                break;
            case 104:
                break;
        }
    }

    private void createDashBoardOrgSite(int depth, String parentId, int linid, String orgId)
    {
        Log.e("Abhay","in fnction call depth "+ depth + "lin "+ linid);
        for(int i=0;i<root.children.size();i++)
        {
            if (root.children.get(i).depth == depth && root.children.get(i).parent ==parentId) {
                Log.e("Abhay","find lin id "+ linid);
                LinearLayout l = (LinearLayout)findViewById(linid);
                TextView tv;
                String tvId="", linlytId="";
                if (l == null) {
                    l = new LinearLayout(activity);
                    l.setOrientation(LinearLayout.VERTICAL);
                    l.setLayoutParams(param);
                    linlytId=Integer.toString(depth)+root.children.get(i).id+0;
                    l.setId(Integer.parseInt(linlytId));
                    tv = new TextView(activity);
                    tvId=Integer.toString(depth)+root.children.get(i).id+1;
                    tv.setId(Integer.parseInt(tvId));
                    tv.setLayoutParams(param);
                    tv.setText(root.children.get(i).orgName);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                    tv.setPadding(marginLeft, 25, 100, 25);
                    tv.setBackgroundColor(getResources().getColor(R.color.tv));
                    tv.setTextColor(getResources().getColor(R.color.blue));
                    l.addView(tv);
                    View v = new View(this);
                    v.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            3
                    ));
                    v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                    l.addView(v);
                    parent.addView(l);
                }
                else
                {
                    tv = new TextView(activity);
                    tvId=Integer.toString(depth)+root.children.get(i).id+1;
                    tv.setId(Integer.parseInt(tvId));
                    tv.setLayoutParams(param);
                    tv.setText(root.children.get(i).orgName);
                    tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                    tv.setPadding(marginLeft, 25, 100, 25);
                    tv.setBackgroundColor(getResources().getColor(R.color.tv));
                    tv.setTextColor(getResources().getColor(R.color.blue));
                    l.addView(tv);
                    View v = new View(this);
                    v.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            3
                    ));
                    v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                    l.addView(v);
                }
                Log.e("Abhay", "set vid " + tvId + " lin " + linlytId);

                if(linlytId.equalsIgnoreCase(""))
                    linlytId="-11";
                tv.setOnClickListener(new OnViewClick(root.children.get(i).id, root.children.get(i).parent, depth, Integer.parseInt(tvId), Integer.parseInt(linlytId)));
            }
        }
        marginLeft += 30;
        if(depth>=1) {
            marginLeft-=30;
            combineSiteOrgDashboad(depth, orgId);
        }
    }

    private void combineSiteOrgDashboad(int depth, String orgId)
    {
        //combining site with orglist
        if(depth>=1) {
            for (int i = 0; i < siteDetails.size(); i++) {
                    if (siteDetails.get(i).organizationId == Integer.parseInt(orgId)) {
                        String linlytid = Integer.toString(depth-1) + orgId + 0;
                        Log.e("Abhay","id linlyt site "+ linlytid);
                        LinearLayout l = (LinearLayout) findViewById(Integer.parseInt(linlytid));
                        if(l!=null)
                        {
                            TextView tv = new TextView(activity);
                            String tvId = Integer.toString(depth) + orgId + siteDetails.get(i).id + 1;
                            tv.setId(Integer.parseInt(tvId));
                            tv.setLayoutParams(param);
                            tv.setText(siteDetails.get(i).siteName);
                            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                            tv.setPadding(marginLeft, 25, 100, 25);
                            tv.setBackgroundColor(getResources().getColor(R.color.tv));
                            tv.setTextColor(getResources().getColor(R.color.blue));
                            l.addView(tv);
                            View v = new View(this);
                            v.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    3
                            ));
                            v.setBackgroundColor(Color.parseColor("#B3B3B3"));
                            l.addView(v);
                        }

                }
            }
        }
    }
    public class OnViewClick implements View.OnClickListener{
        String id,parent;
        int depth;
        int tvId;
        int linlytid;
        public OnViewClick(String id, String parent,  int depth, int viewId,int linlytid)
        {
            this.parent = parent;
            this.id =  id;
            this.depth = depth;
            this.tvId = viewId;
            this.linlytid= linlytid;
        }
        @Override
        public void onClick(View v) {
            Log.e("Abhay", "Clicked vid " + tvId + " lin " + linlytid);
            if(findViewById(tvId).getVisibility()== View.VISIBLE)
            ((TextView)findViewById(tvId)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_up_black_24dp, 0);
            Log.e("Abhay", "efore fnction call depth " + depth + "lin " + linlytid);
            clickedDepth = depth;
            createDashBoardOrgSite(depth + 1, id, linlytid,id);
        }
    }
    private int findMaxDeptHierachy(OrgDetail root)
    {
        int max = root.children.get(0).depth;
        for(int i=0;i<root.children.size();i++)
        {
            if(max<root.children.get(i).depth)
                max = root.children.get(i).depth;
        }
        return max;

    }
    private void recurrJsonOrgList(JSONArray jsonArray, OrgDetail root,String parent)
    {
        try {
            if(jsonArray.length()==0)
                return;
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
                Iterator<String> iterator = jsonObject.keys();
                while (iterator.hasNext())
                {
                    String key = iterator.next();

                    if(key.equals("childOrgs"))
                    {
                        JSONArray jsonArray1 = jsonObject.getJSONArray(key);
                        deptOrg++;
                        recurrJsonOrgList(jsonArray1, root, parentId);
                        deptOrg--;
                    }
                    else
                    {
                        String id ="",orgName="";
                        JSONObject orgDet = jsonObject.getJSONObject(key);
                        Iterator<String> iterator1 = orgDet.keys();
                        while(iterator1.hasNext())
                        {
                            String key1 = iterator1.next();
                            if(key1.equalsIgnoreCase("id"))
                                id = orgDet.getString(key1);
                            else
                                orgName = orgDet.getString(key1);
                        }
                        OrgDetail node = new OrgDetail(orgName, id, parent, deptOrg);
                        root.add(node);
                        parentId= id;
                    }
                }

            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    private void processJsonOrgList(String data)
    {
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> iterator = jsonObject.keys();
            String key = iterator.next();
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            root = new OrgDetail("Wayggum","-1","-1",0);
            recurrJsonOrgList(jsonArray, root,root.parent);
            Log.e("Abhay","root"+root.id +" "+root.orgName+" "+ root.children.toString() );
            for(int i=0;i<root.children.size();i++)
            {
                Log.e("Abhay", "id "+ root.children.get(i).id +" ornname "+ root.children.get(i).orgName +"parent "+root.children.get(i).parent +" depth"+root.children.get(i).depth);
            }
        }
        catch(JSONException jsonException){
            jsonException.printStackTrace();
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    private void processJsonSiteList(String data)
    {
        try {
            JSONObject jsonObject = new JSONObject(data);
            Iterator<String> iterator = jsonObject.keys();
            String key = iterator.next();
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            siteDetails = new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject1 = new JSONObject(jsonArray.get(i).toString());
                Iterator<String> iterator1 = jsonObject1.keys();
                String id="",siteName="";
                int organizationid=-1;
                while (iterator1.hasNext()) {
                    String key1 = iterator1.next();
                    switch (key1)
                    {
                        case "id":
                            id = jsonObject1.getString(key1);
                            break;
                        case "organizationId":
                            organizationid = jsonObject1.getInt(key1);
                            break;
                        case "siteName":
                            siteName = jsonObject1.getString(key1);
                            break;
                    }
                }
                siteDetails.add(new SiteDetail(id,siteName,organizationid));
            }
            for(int i=0;i<siteDetails.size();i++)
            Log.e("Abhay", siteDetails.get(i).id +" "+ siteDetails.get(i).siteName + " "+ siteDetails.get(i).organizationId);

        }
        catch(JSONException jsonException){

            jsonException.printStackTrace();
        }
        catch(Exception exception){

            exception.printStackTrace();
        }
    }

    public Toolbar getActionBarToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.main_toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }
        return toolbar;
    }
}