package com.tars.moneytracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInstance;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.ui.notes.NotesFragment;
import com.tars.moneytracker.ui.notification.NotificationFragment;
import com.tars.moneytracker.ui.profile.ProfileFragment;
import com.tars.moneytracker.ui.wallet.adapters.CategoriesAdapter;
import com.tars.moneytracker.ui.wallet.adapters.CategoryIconsAdapter;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    public static boolean isPopupExpense=false;
    public static boolean isCardOn=false,isTypeCardOn=false,isPopupWalletOn=false;
    public static boolean isNavOn=false;
    private View addBtn ;
    private View outsideCard;

    private CardView wallet_typeContainer;
    private Button incomeBtn,expenseBtn;
    private TextView navHeaderProfileBtn, navHeaderTitle;
    private ImageView menuBtn, notificationBtn, navHeaderProfileIcon, popupTypeIcon,popupWalletIcon;
    private EditText popupTypeEditText;

    private DrawerLayout drawerLayout;
    private ConstraintLayout container;

    private BottomNavigationView bottomNavigationView;
    private NavigationView drawerNavigationView;
    private RecyclerView popupTypeRecyclerView;


    SharedPreferences langPrefs;
    String lang="not set";
    public static final String Language_pref="Language";
    public static final String Selected_language="Selected Language";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container=findViewById(R.id.income_expense_card_container);
        outsideCard=findViewById(R.id.outside_card);

        drawerNavigationView = findViewById(R.id.nav_drawer_view);
        bottomNavigationView = findViewById(R.id.nav_view);
        View headerView = drawerNavigationView.getHeaderView(0);

        drawerLayout = findViewById(R.id.drawer_layout);
        popupTypeIcon =findViewById(R.id.transPopupTypeIcon);
        popupTypeEditText=findViewById(R.id.transPopupIncomeExpeseType);
        popupTypeRecyclerView=findViewById(R.id.transPopupTypeRecycler);
        popupWalletIcon=findViewById(R.id.popup_wallet_icon);

        addBtn = findViewById(R.id.income_expense_btn);
        menuBtn = findViewById(R.id.actionbar_menu);
        submitBtn=findViewById(R.id.home_trans_popup_submit_btn);
        notificationBtn = findViewById(R.id.actionbar_notifications);
        incomeBtn=findViewById(R.id.home_trans_popup_income_btn);
        expenseBtn=findViewById(R.id.home_trans_popup_expense);
        navHeaderTitle=headerView.findViewById(R.id.nav_header_title);
        navHeaderProfileIcon=headerView.findViewById(R.id.nav_header_image);
        navHeaderProfileBtn = headerView.findViewById(R.id.view_profile_button);
        wallet_typeContainer=findViewById(R.id.transaction_popup_type_card);



        incomeBtn.setOnClickListener(this);
        expenseBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        outsideCard.setOnClickListener(this);
        navHeaderProfileBtn.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        notificationBtn.setOnClickListener(this);
        navHeaderTitle.setOnClickListener(this);
        navHeaderProfileIcon.setOnClickListener(this);
        container.setOnClickListener(this);
        popupWalletIcon.setOnClickListener(this);
        popupTypeIcon.setOnClickListener(this);

        drawerNavigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setItemIconTintList(null);



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.setGraph(R.navigation.mobile_navigation);

        NavigationUI.setupWithNavController(bottomNavigationView,navController);



        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {

            }
        });



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                int nv = navController.getCurrentDestination().getId();



                if(nv==R.id.navigation_home) {
                    if (id == R.id.navigation_graphs) {

                        navController.navigate(R.id.action_navigation_home_to_navigation_graphs);


                    }
                    else if(id==R.id.navigation_transactions){
                        navController.navigate(R.id.action_navigation_home_to_navigation_transactions);


                    }
                    else if(id==R.id.navigation_wallet){
                        navController.navigate(R.id.action_navigation_home_to_navigation_wallet);

                    }
                }
                else if(nv==R.id.navigation_graphs) {
                    if (id == R.id.navigation_home) {

                        navController.navigate(R.id.action_navigation_graphs_to_navigation_home);



                    }
                    else if(id==R.id.navigation_transactions){
                        navController.navigate(R.id.action_navigation_graphs_to_navigation_transactions);

                    }
                    else if(id==R.id.navigation_wallet){
                        navController.navigate(R.id.action_navigation_graphs_to_navigation_wallet);

                    }
                }
               else if(nv==R.id.navigation_transactions) {
                    if (id == R.id.navigation_graphs) {

                        navController.navigate(R.id.action_navigation_transactions_to_navigation_graphs);

                    }
                    else if(id==R.id.navigation_home){
                        navController.navigate(R.id.action_navigation_transactions_to_navigation_home);

                    }
                    else if(id==R.id.navigation_wallet){
                        navController.navigate(R.id.action_navigation_transactions_to_navigation_wallet);

                    }
                }
                else if(nv==R.id.navigation_wallet) {
                    if (id == R.id.navigation_graphs) {

                        navController.navigate(R.id.action_navigation_wallet_to_navigation_graphs);

                    }
                    else if(id==R.id.navigation_transactions){
                        navController.navigate(R.id.action_navigation_wallet_to_navigation_transactions);

                    }
                    else if(id==R.id.navigation_home){
                        navController.navigate(R.id.action_navigation_wallet_to_navigation_home);

                    }
                }
                return false;
            }
        });

        popupTypeIcon.setOnClickListener(this);
        popupWalletIcon.setOnClickListener(this);





//        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }


    @Override
    public void onBackPressed() {

        if (isCardOn) {
            hideFAB(container);
            isCardOn=false;
        } else {
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals("nt")) {

                    fm.popBackStack("nt", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().remove(new NotificationFragment()).commit();
                }

                else if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals("notes")) {
                    fm.popBackStack("notes", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().remove(new NotesFragment()).commit();
                }

            } else {

                super.onBackPressed();
            }
        }
    }



    @Override
    public void onClick(View v){
        switch(v.getId()){

            case R.id.home_trans_popup_submit_btn:
                HomeDataModel homeDataModel = new HomeDataModel("Food","bKash","14.01.2021","500 Tk","income");
                RestClient.insertTransactionData(getApplicationContext(),homeDataModel);
                break;

            case R.id.home_trans_popup_income_btn:
                if(isPopupExpense){
                    incomeBtn.setBackgroundTintList(getColorStateList(R.color.lightTeal));
                    expenseBtn.setBackgroundTintList(getColorStateList(R.color.transparent));
                    isPopupExpense=false;
                }
                break;

            case R.id.home_trans_popup_expense:
                if(!isPopupExpense){
                    incomeBtn.setBackgroundTintList(getColorStateList(R.color.transparent));
                    expenseBtn.setBackgroundTintList(getColorStateList(R.color.lightTeal));
                    isPopupExpense=true;
                }
                break;

            case R.id.income_expense_btn:
                if(!isCardOn) {
                    revealFAB(container);
                    isCardOn=true;
                }
                else {
                    hideFAB(container);
                    isCardOn=false;
                }
                break;

            case R.id.outside_card:
                if(isCardOn) {
                    hideFAB(container);
                    isCardOn=false;

                }
                break;

            case R.id.nav_header_image:
            case R.id.nav_header_title:
            case R.id.view_profile_button:
                getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,new ProfileFragment()).addToBackStack("tars").commit();
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;

            case R.id.actionbar_menu:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;

            case R.id.actionbar_notifications:
                    getSupportFragmentManager().beginTransaction().add(R.id.drawer_layout, new NotificationFragment()).addToBackStack("nt").commit();

                break;
            case R.id.popup_wallet_icon:
                if(!isPopupWalletOn){
                    revealFAB(wallet_typeContainer);
                    wallet_typeContainer.setVisibility(View.VISIBLE);
                    popupTypeRecyclerView.setAdapter(new CategoriesAdapter(this,this));
                    popupTypeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
                    isPopupWalletOn=true;
                }
                else {
                    hideFABSmall(wallet_typeContainer);
                    isPopupWalletOn=false;
                }
                break;
            case R.id.transPopupTypeIcon:
                if(!isPopupWalletOn){
                    revealFAB(wallet_typeContainer);
                    wallet_typeContainer.setVisibility(View.VISIBLE);
                    popupTypeRecyclerView.setAdapter(new CategoryIconsAdapter());
                    popupTypeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
                    isPopupWalletOn=true;
                }
                else {
                    hideFABSmall(wallet_typeContainer);
                    isPopupWalletOn=false;
                }

                break;
            case R.id.income_expense_card_container:

            default:
                break;
        }
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.notes){
            getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout, new NotesFragment()).addToBackStack("notes").commit();
        }

        else if(id == R.id.export){
            Toast.makeText(getApplicationContext(),"Export Listening",Toast.LENGTH_SHORT).show();

        }
        else if(id == R.id.backup) {
            Toast.makeText(getApplicationContext(), "Backup Listening", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.english) {
            languageAlertDialog("en");
        }
        else if (id == R.id.bangla) {
            languageAlertDialog("bn");
        }
        else if (id == R.id.shareApp){
            Intent shareIntent=new Intent((Intent.ACTION_SEND));
            shareIntent.setType("text/plain");
            String shareBody="Blood and Plasma Banking solution app: Plasma+\n http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            String shareSub="Plasma+";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(shareIntent,"Share Using"));

        }
        else if (id == R.id.emailUs){

            String email = "ece.lab.ruet@gmail.com";
            String subject = "Contact Us";
            String body = "Please share your valuable thoughts with us.";
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, body);
            startActivityForResult(intent,200);
        }

        else if(id == R.id.sendReview){
            Uri uri = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSd_PgXtE8sYdaxCIp4pPXM6IqU7ZvoA963iBksFejGIOUYH6g/viewform?usp=sf_link");
            Intent review = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(review);
        }

        else if(id == R.id.rateApp) {

            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            startActivity(goToMarket);


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }













    private void revealFAB(View view) {

        int cx = view.getWidth() ;
        int cy = view.getHeight() ;
        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(500);
        view.setVisibility(View.VISIBLE);


        anim.start();
    }
    private void hideFAB(View view) {

        int cx = view.getWidth();
        int cy = view.getHeight() ;
        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.setDuration(250);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });



        anim.start();
    }
    private void hideFABSmall(View view) {

        int cx = view.getWidth();
        int cy = view.getHeight() ;
        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);
        anim.setDuration(250);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setVisibility(View.GONE);
            }
        });



        anim.start();
    }

    private void languageAlertDialog(String lang){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.change_language), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                setLocale(lang);
            }
        })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        SharedPreferences langPrefs=getSharedPreferences(Language_pref,MODE_PRIVATE);
        SharedPreferences.Editor langPrefsEditor = langPrefs.edit();
        langPrefsEditor.putString(Selected_language, lang);
        langPrefsEditor.apply();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
    }
}
