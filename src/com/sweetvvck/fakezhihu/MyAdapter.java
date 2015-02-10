package com.sweetvvck.fakezhihu;

import java.util.List;

import com.sweetvvck.fakezhihu.MainActivity.ContentFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter  
extends RecyclerView.Adapter<MyAdapter.ViewHolder>  
{  

private List<Actor> actors;  

private static Context mContext; 


public MyAdapter( ContentFragment contentFragment , List<Actor> actors)  
{  
	this.mContext = contentFragment.getActivity(); 
    this.actors = actors;  
}  

@Override  
public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )  
{  
	
	Log.e("___Dylan___",">>onCreateViewHolder : "  + "i="+i);
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_cardview, viewGroup, false);  
    return new ViewHolder(v);  
}  

@Override  
public void onBindViewHolder( ViewHolder viewHolder, int i )  
{  

    Actor p = actors.get(i); 
    viewHolder.mContext = mContext;
    viewHolder.mTextView.setText(p.name);  
    viewHolder.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
}  

@Override  
public int getItemCount()  
{  

    return actors == null ? 0 : actors.size();  
}  


public static class ViewHolder  
    extends RecyclerView.ViewHolder  
{  
    public Context mContext;

	public TextView mTextView;  

    public ImageView mImageView;  

    public ViewHolder( View v )  
    {  
        super(v);  
        mTextView = (TextView) v.findViewById(R.id.info_text);  
        mImageView = (ImageView) v.findViewById(R.id.pic);  
        Log.e("___Dylan___",">>ViewHolder0");
        v.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	Log.e("___Dylan___",">>ViewHolder1");
             ((MainActivity)mContext).startActivity(v, getPosition()); 

            }  
        }); 
    }  
}  
}  