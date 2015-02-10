package com.sweetvvck.fakezhihu;

import com.sweetvvck.fakezhihu.MainActivity.ContentFragment;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks {


    private NavigationDrawerFragment mNavigationDrawerFragment_1;

    ImageView pic;  
  
    int position;  
  
    int picIndex = 0;  
  
    Actor actor;  
    
    private CharSequence mTitle;
    
    private Fragment currentFragment_1;
	private Fragment lastFragment_1;
	Context context;
	
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        // set Explode enter transition animation for current activity  
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);  
        //getWindow().setEnterTransition(new Explode().setDuration(1000));  
        //getWindow().setExitTransition(null);  
  
        setContentView(R.layout.detail_layout);  
        Log.e("___Dylan___",">>onCreate");
        this.context = this;  
		
        mNavigationDrawerFragment_1 = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_1);
		mTitle = getTitle();
		mNavigationDrawerFragment_1.setUp(R.id.navigation_drawer_1, (DrawerLayout) findViewById(R.id.detail_layout));
		Log.e("___Dylan___",">>onCreate1");
		
        position = getIntent().getIntExtra("pos", 0);  
        actor = ContentFragment.actors.get(position);  
        pic = (ImageView) findViewById(R.id.detail_pic);  
          
        TextView name = (TextView) findViewById(R.id.detail_name);  
        TextView works = (TextView) findViewById(R.id.detail_works);  
        TextView role = (TextView) findViewById(R.id.detail_role);  
        TextView policy = (TextView) findViewById(R.id.policy); 
        ImageButton btn = (ImageButton) findViewById(R.id.detail_btn);  
  
        // set detail info  
        pic.setTransitionName(position + "pic");  
        pic.setImageDrawable(getDrawable(actor.getImageResourceId(this)));  
        name.setText("簡介：" + actor.name);  
        works.setText("詳細說明：" + actor.works);  
        role.setText("網友評論：" + actor.role);
        policy.setText("取消政策：" + actor.policy);  
        // set action bar title  
        getActionBar().setTitle(ContentFragment.actors.get(position).name);  
        Log.e("___Dylan___",">>onCreate2");
      
        /*
        // floating action button  
        btn.setImageDrawable(getDrawable(android.R.drawable.ic_menu_gallery));  
        btn.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                // set first animation  
                Animator animator = createAnimation(pic, true);  
                animator.start();  
                animator.addListener(new Animator.AnimatorListener() {  
                    @Override  
                    public void onAnimationStart(Animator animation) {  
  
                    }  
  
                    @Override  
                    public void onAnimationEnd(Animator animation) {  
                        picIndex++;  
                        if (actor.getPics() != null) {  
                            if (picIndex >= actor.getPics().length) {  
                                picIndex = 0;  
                            }  
                            // set second animation  
                            doSecondAnim();  
                        }  
                    }  
  
                    @Override  
                    public void onAnimationCancel(Animator animation) {  
  
                    }  
  
                    @Override  
                    public void onAnimationRepeat(Animator animation) {  
  
                    }  
                });  
            }  
        });
         */
    }  
  
    /** 
     * exec second animation for pic view 
     */  
    private void doSecondAnim() {  
        pic.setImageDrawable(getDrawable(actor.getImageResourceId(this, actor.getPics()[picIndex])));  
        Animator animator = createAnimation(pic, false);  
        animator.start();  
    }  
  
    /** 
     * create CircularReveal animation with first and second sequence 
     */  
    public Animator createAnimation(View v, Boolean isFirst) {  
  
        Animator animator;  
  
        if (isFirst) {  
            animator = ViewAnimationUtils.createCircularReveal(  
                    v,  
                    v.getWidth() / 2,  
                    v.getHeight() / 2,  
                    v.getWidth(),  
                    0);  
        } else {  
            animator = ViewAnimationUtils.createCircularReveal(  
                    v,  
                    v.getWidth() / 2,  
                    v.getHeight() / 2,  
                    0,  
                    v.getWidth());  
        }  
  
        animator.setInterpolator(new DecelerateInterpolator());  
        animator.setDuration(500);  
        return animator;  
    }  
  
    @Override  
    public void onBackPressed() {  
        super.onBackPressed();  
        pic.setImageDrawable(getDrawable(actor.getImageResourceId(this)));  
        finishAfterTransition();  
    }  
    @Override
	public void onNavigationDrawerItemSelected(String title) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		currentFragment_1 = fragmentManager.findFragmentByTag(title);
		if(currentFragment_1 == null) {
			currentFragment_1 = ContentFragment_detail.newInstance(title);
			ft.add(R.id.container_1, currentFragment_1, title);
		}
		if(lastFragment_1 != null) {
			ft.hide(lastFragment_1);
		}
		if(currentFragment_1.isDetached()){
			ft.attach(currentFragment_1);
		}
		ft.show(currentFragment_1);
		lastFragment_1 = currentFragment_1;
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
		if (!mNavigationDrawerFragment_1.isDrawerOpen()) {
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		
		return super.onOptionsItemSelected(item);
	}
   public static class ContentFragment_detail extends Fragment {
      
      private static final String ARG_SECTION_TITLE = "section_title";
      private View rootView;
    
      public static ContentFragment_detail newInstance(String title) {
         ContentFragment_detail fragment = new ContentFragment_detail();
         Bundle args = new Bundle();
         args.putString(ARG_SECTION_TITLE, title);
         fragment.setArguments(args);
         return fragment;
      }

      public ContentFragment_detail() {
      }

      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container_1,
            Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.detail_layout, container_1, false); 
			
         return rootView;
      }
   }
}  