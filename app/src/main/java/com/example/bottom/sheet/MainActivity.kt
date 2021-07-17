package com.example.bottom.sheet

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.bottom.sheet.utils.BottomSheetUtils
import com.example.bottom.sheet.utils.ViewPagerBottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        //
        val banner = findViewById<ViewPager>(R.id.banner)
        banner.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = View(this@MainActivity)
                val color = if (position % 2 == 0) {
                    Color.RED
                } else {
                    Color.BLUE
                }
                view.setBackgroundColor(color)
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                container.addView(view, params)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
                container.removeView(any as View?)
            }

            override fun getCount(): Int {
                return 10
            }

            override fun isViewFromObject(view: View, any: Any): Boolean {
                return view == any
            }

        }
        //
        val scrollView = findViewById<View>(R.id.linearLayout)
        //
        val page = findViewById<ViewPager>(R.id.pager)
        page.adapter = object : PagerAdapter() {

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val view = View(this@MainActivity)
                val color = if (position % 2 == 0) {
                    Color.RED
                } else {
                    Color.BLUE
                }
                view.setBackgroundColor(color)
                val params = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                container.addView(view, params)
                return view
            }

            override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
                container.removeView(any as View?)
            }

            override fun getCount(): Int {
                return 10
            }

            override fun isViewFromObject(view: View, any: Any): Boolean {
                return view == any
            }

        }
        // TabLayout
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.app_name))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.app_name))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.app_name))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.app_name))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.app_name))
        //
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        val fm = supportFragmentManager
        val list = arrayListOf("全部", "汽车", "美女", "宠物", "风景", "动漫", "游戏", "体育", "明星")
        viewPager.adapter =
            object : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
                override fun getCount(): Int {
                    return list.size
                }

                override fun getItem(position: Int): Fragment {
                    return BlankFragment.newInstance(list[position], list[position])
                }

                override fun getPageTitle(position: Int): CharSequence {
                    return list[position]
                }
            }
        //
        tabLayout.setupWithViewPager(viewPager)
        //
        BottomSheetUtils.setupViewPager(viewPager)
        //
        val behavior = ViewPagerBottomSheetBehavior.from(scrollView)
        behavior.isHideable = false
        behavior.addBottomSheetCallback(object :
            ViewPagerBottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (ViewPagerBottomSheetBehavior.STATE_EXPANDED == newState) {
                    page.visibility = View.GONE
                } else if (ViewPagerBottomSheetBehavior.STATE_COLLAPSED == newState) {
                    tabLayout.visibility = View.GONE
                    viewPager.visibility = View.GONE
                } else {
                    page.visibility = View.VISIBLE
                    tabLayout.visibility = View.VISIBLE
                    viewPager.visibility = View.VISIBLE
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                page.alpha = 1 - slideOffset
                tabLayout.alpha = slideOffset
                viewPager.alpha = slideOffset
            }

        })
        tabLayout.alpha = 0.0f
        viewPager.alpha = 0.0f

        findViewById<View>(R.id.button).setOnClickListener {
            if (ViewPagerBottomSheetBehavior.STATE_EXPANDED == behavior.state) {
                behavior.state = ViewPagerBottomSheetBehavior.STATE_COLLAPSED
            } else {
                behavior.state = ViewPagerBottomSheetBehavior.STATE_EXPANDED
            }
        }
    }
}