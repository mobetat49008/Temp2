package com.sweetvvck.fakezhihu;

import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.graphics.Outline;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {


	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	private CharSequence mTitle;
	
	private Fragment currentFragment;
	private Fragment lastFragment;
	Context context;
	
	private static String[][] picGroups = {{"p1","p1_1", "p1_2", "p1_3"},{"p2","p2_1", "p2_2", "p2_3"},{"p3"},{"p4"},{"p5"},{"p6"},{"p7"}};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  
        //getWindow().setEnterTransition(new Explode().setDuration(1000)); 
		setContentView(R.layout.activity_main);

		this.context = this;  
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(String title) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		currentFragment = fragmentManager.findFragmentByTag(title);
		if(currentFragment == null) {
			currentFragment = ContentFragment.newInstance(title);
			ft.add(R.id.container, currentFragment, title);
		}
		if(lastFragment != null) {
			ft.hide(lastFragment);
		}
		if(currentFragment.isDetached()){
			ft.attach(currentFragment);
		}
		ft.show(currentFragment);
		lastFragment = currentFragment;
		ft.commit();
		onSectionAttached(title);
	}

	public void onSectionAttached(String title) {
		mTitle = title;
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch(item.getItemId()) {  
		
		case R.id.action_new:  
			Log.e("___Dylan___",">>11111111");
			ContentFragment.actors.add(new Actor("ben", "p1","fuck","sex","GOGO",picGroups[0]));  
			ContentFragment.mRecyclerView.scrollToPosition(ContentFragment.myAdapter.getItemCount() - 1);  
			ContentFragment.myAdapter.notifyDataSetChanged(); 
			return true;		
		}
		
		return super.onOptionsItemSelected(item);
	}


	public static class ContentFragment extends Fragment {
		
		private static final String ARG_SECTION_TITLE = "section_title";
		public static List<Actor> actors = new ArrayList<Actor>(); 
		  
		
		private static RecyclerView mRecyclerView; 
		private static MyAdapter myAdapter;
		private View rootView;
		private RecyclerView.LayoutManager mLayoutManager;
		ImageButton button;
		
		static boolean isinited = false;
		
	    private String[] names = { "ben" };

	    private static String[] pics = {"p1"};

	    private static String[] works = {"fucker"};

	    private static String[] role = {"sexer"};
	    
	    private static String[] policy = {"GOGO"};
	    
	    private static boolean isinit=false; 
		
		
		public static ContentFragment newInstance(String title) {
			ContentFragment fragment = new ContentFragment();
			Bundle args = new Bundle();
			args.putString(ARG_SECTION_TITLE, title);
			fragment.setArguments(args);
			return fragment;
		}

		public ContentFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			
			rootView = inflater.inflate(R.layout.activity_recycler, container, false); 
			
			if(isinit)
			{
				actors.remove(myAdapter.getItemCount() - 1); 
				initViews();
			}
			
			if(!isinit){
		    initViews();
		    isinit=true;
			}
	
			return rootView;
		}
		
		private void initViews() {
			Log.e("___Dylan___",">>0000");
			actors.add(new Actor(names[0], pics[0], works[0], role[0],policy[0],picGroups[0]));  
			 
	        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list);   
	        
	        mLayoutManager=new LinearLayoutManager(getActivity());
	          
	        mRecyclerView.setLayoutManager(mLayoutManager);  
	     
	        mRecyclerView.setItemAnimator(new DefaultItemAnimator());  
	    
	        //mRecyclerView.setHasFixedSize(true);  

	        myAdapter = new MyAdapter(this, actors); 
	       
	        mRecyclerView.setAdapter(myAdapter);  
	        
	     // set outline and listener for floating action button
	        button = (ImageButton) rootView.findViewById(R.id.add_button);
	        
	        button.setOutlineProvider(new ViewOutlineProvider() {
	            @Override
	            public void getOutline(View view, Outline outline) {
	                int shapeSize = (int) getResources().getDimension(R.dimen.shape_size);
	                outline.setRoundRect(0, 0, shapeSize, shapeSize, shapeSize / 2);
	            }
	        });
	        
	        button.setClipToOutline(true);
	        
	        button.setOnClickListener(new MyOnClickListener());
	
		}
		public class MyOnClickListener implements View.OnClickListener {  
	        boolean isAdd = true;  
	  
	        @Override  
	        public void onClick(View v) {  
	            // start animation  
	            Animator animator = createAnimation(v);  
	            animator.start();  
	  
	            // add item  
	            if (myAdapter.getItemCount() != names.length && isAdd) {  
	            	Log.e("___Dylan___",">>22222");
	            	ContentFragment.actors.add(new Actor("ben", "p1","fuck","sex","GOGO",picGroups[0]));   
	                mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);  
	                myAdapter.notifyDataSetChanged();  
	            }  
	            // delete item  
	            else {  
	                actors.remove(myAdapter.getItemCount() - 1);  
	                mRecyclerView.scrollToPosition(myAdapter.getItemCount() - 1);  
	                myAdapter.notifyDataSetChanged();  
	            }  
	  /*
	            if (myAdapter.getItemCount() == 0) {  
	                button.setImageDrawable(getDrawable(android.R.drawable.ic_input_add));  
	                isAdd = true;  
	            }  
	            if (myAdapter.getItemCount() == names.length) {  
	                button.setImageDrawable(getDrawable(android.R.drawable.ic_delete));  
	                isAdd = false;  
	            }
	  */  
	        }  
	    }  
	}
	/** 
     * start detail activity 
    */  
    public void startActivity(final View v, final int position) {  
  
        View pic = v.findViewById(R.id.pic);  
        View add_btn = this.findViewById(R.id.add_button);  
        Log.e("___Dylan___",">>startActivity");
        
        // set share element transition animation for current activity  
        Transition ts = new ChangeTransform();  
        ts.setDuration(3000);  
        getWindow().setExitTransition(ts);  
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation((MainActivity) context,  
                Pair.create(pic, position + "pic"),  
                Pair.create(add_btn, "ShareBtn")).toBundle();  
  
        // start activity with share element transition  
        Intent intent = new Intent(context, DetailActivity.class);  
        intent.putExtra("pos", position);  
        startActivity(intent, bundle);  
  
    }  
  
    /** 
     * create CircularReveal animation 
     */  
    public static Animator createAnimation(View v) {  
        // create a CircularReveal animation  
        Animator animator = ViewAnimationUtils.createCircularReveal(  
                v,  
                v.getWidth() / 2,  
                v.getHeight() / 2,  
                0,  
                v.getWidth());  
        animator.setInterpolator(new AccelerateDecelerateInterpolator());  
        animator.setDuration(500);  
        return animator; 
    }
    
    
}
