package com.beingdeveloper.jivanseva

import ViewPagerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.beingdeveloper.jivanseva.activities.EPin
import com.beingdeveloper.jivanseva.activities.EWallet
import com.beingdeveloper.jivanseva.activities.EditProfile
import com.beingdeveloper.jivanseva.activities.Notification
import com.beingdeveloper.jivanseva.activities.Payout
import com.beingdeveloper.jivanseva.drawerActivities.BinaryTree
import com.beingdeveloper.jivanseva.drawerActivities.BusinessSummary
import com.beingdeveloper.jivanseva.drawerActivities.DirectReferrals
import com.beingdeveloper.jivanseva.drawerActivities.JoiningReport
import com.beingdeveloper.jivanseva.drawerActivities.LegView
import com.beingdeveloper.jivanseva.drawerActivities.LevelCount
import com.beingdeveloper.jivanseva.drawerActivities.LevelWiseMembers
import com.beingdeveloper.jivanseva.drawerActivities.Members
import com.beingdeveloper.jivanseva.drawerActivities.PurchaseReport
import com.beingdeveloper.jivanseva.drawerActivities.Share
import com.beingdeveloper.jivanseva.drawerActivities.TdsAndAdmin
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar : Toolbar   // toolbar
    private lateinit var drawerLayout: DrawerLayout    // drawer_layout
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    private lateinit var navigationView:NavigationView
    private lateinit var bottomNavigationView : BottomNavigationView


    // Toggle variables
    private var isTeamManagementExpanded = false
    private var isBusinessManagementExpanded = false
    private var isReportExpanded = false


    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar = findViewById(R.id.toolbar)        // finding toolbar
        setSupportActionBar(toolbar)                   // setting toolbar

        drawerLayout = findViewById(R.id.drawer_layout)    // finding correct view for drawer layout
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)   // 3 horizontal line  icon


        navigationView = findViewById(R.id.navigation_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        menuIcon.setOnClickListener{
            drawerLayout.openDrawer((GravityCompat.END))
        }

        //  finding image view of profile picture and calling its respective activity
        val profileImage : ImageView = findViewById(R.id.profile_picture)

        profileImage.setOnClickListener{
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener{
            item -> when(item.itemId){
                R.id.home ->{
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.wallet ->{
                    val intent = Intent(this , EWallet::class.java)
                    startActivity(intent)
                    true
                }
                R.id.payout ->{
                    val intent = Intent(this , Payout::class.java)
                    startActivity(intent)
                    true
                }
                R.id.notification ->{
                    val intent = Intent(this , Notification ::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



        // ViewPager and Tabs Layout

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // Connect TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Business"
                    tab.contentDescription = "Business Tab"
                }
                1 -> {
                    tab.text = "Income"
                    tab.contentDescription = "Income Tab"
                }
                else -> null
            }
        }.attach()



        onBackPressedCallback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //check is drawer is open
                if(drawerLayout.isDrawerOpen(GravityCompat.END)){
                    //close the drawer if open
                    drawerLayout.closeDrawer(GravityCompat.END)
                }
                else{
                    //Allow back press to proceed if drawer is closed
                    isEnabled = false //disable callback temporarily
                    onBackPressedDispatcher.onBackPressed()   //call back action
                    isEnabled = true // re-enable callback
                }
            }
        }

        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)



        // Set a DrawerListener to reset menu state on close
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                resetMenuState() // Reset menu to base state when drawer closes
            }
            override fun onDrawerStateChanged(newState: Int) {}
        })





        // end of onCreate Function
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_profile ->{
                val intent = Intent(this , EditProfile ::class.java)
                startActivity(intent)
                true
            }


            R.id.team_management -> toggleTeamManagementMenu()

            R.id.my_binary_tree ->{
                val intent = Intent(this , BinaryTree ::class.java)
                startActivity(intent)
                true
            }

            R.id.all_members ->{
                val intent = Intent(this , Members ::class.java)
                startActivity(intent)
                true
            }
            R.id.direct_referrals ->{
                val intent = Intent(this , DirectReferrals ::class.java)
                startActivity(intent)
                true
            }
            R.id.leg_view ->{
                val intent = Intent(this , LegView ::class.java)
                startActivity(intent)
                true
            }
            R.id.level_wise_members ->{
                val intent = Intent(this , LevelWiseMembers ::class.java)
                startActivity(intent)
                true
            }
            R.id.level_count ->{
                val intent = Intent(this , LevelCount ::class.java)
                startActivity(intent)
                true
            }


            R.id.e_pin_management ->{
                val intent = Intent(this , EPin ::class.java)
                startActivity(intent)
                true
            }

            R.id.e_wallet ->{
                val intent = Intent(this , EWallet ::class.java)
                startActivity(intent)
                true
            }

            R.id.payout ->{
                val intent = Intent(this , Payout ::class.java)
                startActivity(intent)
                true
            }

            R.id.business_management -> toggleBusinessManagementMenu()

            R.id.business_summary ->{
                val intent = Intent(this , BusinessSummary ::class.java)
                startActivity(intent)
                true
            }

            R.id.report -> toggleReportMenu()

            R.id.joining_report ->{
                val intent = Intent(this , JoiningReport ::class.java)
                startActivity(intent)
                true
            }
            R.id.tds ->{
                val intent = Intent(this , TdsAndAdmin ::class.java)
                startActivity(intent)
                true
            }
            R.id.purchase_report ->{
                val intent = Intent(this , PurchaseReport ::class.java)
                startActivity(intent)
                true
            }

            R.id.share ->{
                val intent = Intent(this , Share ::class.java)
                startActivity(intent)
                true
            }

            else -> drawerLayout.closeDrawer(GravityCompat.END)
        }

        return true
    }

    private fun toggleTeamManagementMenu() {
        // Toggle visibility of Sub Team and Full Team items
        isTeamManagementExpanded = !isTeamManagementExpanded
        navigationView.menu.findItem(R.id.my_binary_tree).isVisible = isTeamManagementExpanded
        navigationView.menu.findItem(R.id.all_members).isVisible = isTeamManagementExpanded
        navigationView.menu.findItem(R.id.direct_referrals).isVisible = isTeamManagementExpanded
        navigationView.menu.findItem(R.id.leg_view).isVisible = isTeamManagementExpanded
        navigationView.menu.findItem(R.id.level_wise_members).isVisible = isTeamManagementExpanded
        navigationView.menu.findItem(R.id.level_count).isVisible = isTeamManagementExpanded

    }

    private fun toggleBusinessManagementMenu(){
        isBusinessManagementExpanded = !isBusinessManagementExpanded
        navigationView.menu.findItem(R.id.business_summary).isVisible = isBusinessManagementExpanded

    }

    private fun toggleReportMenu() {
        // Toggle visibility of Sub Team and Full Team items
        isReportExpanded = !isReportExpanded
        navigationView.menu.findItem(R.id.joining_report).isVisible = isReportExpanded
        navigationView.menu.findItem(R.id.tds).isVisible = isReportExpanded
        navigationView.menu.findItem(R.id.purchase_report).isVisible = isReportExpanded
    }

    private fun resetMenuState() {
        if (isTeamManagementExpanded) toggleTeamManagementMenu()
        if (isBusinessManagementExpanded) toggleBusinessManagementMenu()
        if (isReportExpanded) toggleReportMenu()
    }





}