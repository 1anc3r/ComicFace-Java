package me.lancer.comicface_java.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.lancer.comicface_java.R;
import me.lancer.comicface_java.mvp.base.activity.BaseActivity;
import me.lancer.comicface_java.mvp.book.fragment.BookFragment;
import me.lancer.comicface_java.ui.application.App;
import me.lancer.comicface_java.ui.fragment.BlankFragment;

public class MainActivity extends BaseActivity {

    private App app = new App();
    private Fragment currentFragment;

    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (App) this.getApplication();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        currentFragment = new BookFragment();
        bundle.putInt("index", 0);
        currentFragment.setArguments(bundle);
        switchContent(currentFragment);
    }

    public void switchContent(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_main, fragment).commit();
        invalidateOptionsMenu();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast(this, "再按一次退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
