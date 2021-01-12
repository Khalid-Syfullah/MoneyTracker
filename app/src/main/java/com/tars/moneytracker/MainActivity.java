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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private View addBtn ;
    private ConstraintLayout container;
    private  boolean isPopupExpense=false,isCardOn=false;
    private View outsideCard;
    private Button incomeBtn,expenseBtn;

    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView drawerNavigationView;

    SharedPreferences langPrefs;
    String lang="not set";
    public static final String Language_pref="Language";
    public static final String Selected_language="Selected Language";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.



        addBtn = findViewById(R.id.income_expense_btn);

        outsideCard=findViewById(R.id.outside_card);
        incomeBtn=findViewById(R.id.home_trans_popup_income_btn);
        expenseBtn=findViewById(R.id.home_trans_popup_expense);

        drawerNavigationView = findViewById(R.id.nav_drawer_view);
        bottomNavigationView = findViewById(R.id.nav_view);
        drawerLayout = findViewById(R.id.drawer_layout);

        addBtn = findViewById(R.id.income_expense_btn);
        container=findViewById(R.id.income_expense_card_container);



        incomeBtn.setOnClickListener(this);
        expenseBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        outsideCard.setOnClickListener(this);


        drawerNavigationView.setNavigationItemSelectedListener(this);


        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_transactions, R.id.navigation_wallet, R.id.navigation_graphs)
                .build();
        navView.setItemIconTintList(null);



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public void onClick(View v){
        switch(v.getId()){

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

            case R.id.actionbar_profileIcon:
                Toast.makeText(getApplicationContext(),"Profile Listening",Toast.LENGTH_SHORT).show();
                break;

            case R.id.actionbar_notifications:
                Toast.makeText(getApplicationContext(),"Notification Listening",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.profile){

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

    public void clickEvent(View v) {
        if (v.getId() == R.id.actionbar_menu) {

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else{
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

        if (v.getId() == R.id.actionbar_notifications) {
            Toast.makeText(MainActivity.this, "Notifications",
                    Toast.LENGTH_SHORT).show();
        }

        if (v.getId() == R.id.actionbar_profileIcon) {
            Toast.makeText(MainActivity.this, "Profile",
                    Toast.LENGTH_SHORT).show();
        }
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
        anim.setDuration(500);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
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

}