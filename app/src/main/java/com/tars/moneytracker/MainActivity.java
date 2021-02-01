package com.tars.moneytracker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.TransactionDataModel;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.adapters.WalletNamesAdapter;
import com.tars.moneytracker.ui.home.adapters.WalletNamesClickListener;
import com.tars.moneytracker.ui.notes.NotesFragment;
import com.tars.moneytracker.ui.notification.NotificationFragment;
import com.tars.moneytracker.ui.profile.ProfileFragment;
import com.tars.moneytracker.ui.wallet.WalletViewModel;
import com.tars.moneytracker.ui.wallet.adapters.CategoriesAdapter;
import com.tars.moneytracker.ui.wallet.adapters.CategoryIconClickInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private Button incomeBtn,expenseBtn,submitBtn;
    private TextView navHeaderProfileBtn, navHeaderUserName, popupDate,popupWalletNameTextView;
    private ImageView menuBtn, notificationBtn, navHeaderProfileIcon, popupTypeIcon,popupWalletIcon;
    private EditText popupTitleEditText,popupAmountEditText;

    private DrawerLayout drawerLayout;
    private ConstraintLayout container;

    private BottomNavigationView bottomNavigationView;
    private NavigationView drawerNavigationView;
    private RecyclerView popupTypeRecyclerView;
    WalletViewModel walletViewModel;
    ArrayList<CategoryDataModel> categoryDataModels;

    SharedPreferences langPrefs;
    String date="not_selected";
    String lang="not set";
    String popUpWalletName="not set";
    int popupIncomeExpenseType =-1;
    public static final String Language_pref="Language";
    public static final String Selected_language="Selected Language";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container=findViewById(R.id.income_expense_card_container);
        outsideCard=findViewById(R.id.outside_card);

        walletViewModel=new ViewModelProvider(this).get(WalletViewModel.class);
        categoryDataModels=new ArrayList<>();
        drawerNavigationView = findViewById(R.id.nav_drawer_view);
        bottomNavigationView = findViewById(R.id.nav_view);
        View headerView = drawerNavigationView.getHeaderView(0);

        drawerLayout = findViewById(R.id.drawer_layout);
        popupTypeIcon =findViewById(R.id.transPopupTypeIcon);
        popupTitleEditText=findViewById(R.id.transPopupIncomeExpeseType);
        popupAmountEditText=findViewById(R.id.transaction_popup_card_amount);
        popupDate=findViewById(R.id.dateText);
        popupTypeRecyclerView=findViewById(R.id.transPopupTypeRecycler);
        popupWalletIcon=findViewById(R.id.popup_wallet_icon);
      

        addBtn = findViewById(R.id.income_expense_btn);
        menuBtn = findViewById(R.id.actionbar_menu);
        submitBtn=findViewById(R.id.home_trans_popup_submit_btn);
        notificationBtn = findViewById(R.id.actionbar_notifications);
        incomeBtn=findViewById(R.id.home_trans_popup_income_btn);
        expenseBtn=findViewById(R.id.home_trans_popup_expense);
        navHeaderUserName =headerView.findViewById(R.id.nav_header_user_name);
        navHeaderProfileIcon=headerView.findViewById(R.id.nav_header_image);
        navHeaderProfileBtn = headerView.findViewById(R.id.view_profile_button);
        wallet_typeContainer=findViewById(R.id.transaction_popup_type_card);

        popupWalletNameTextView=findViewById(R.id.transaction_popup_card_walletName);

        navHeaderUserName.setText(StaticData.LoggedInUserName.toUpperCase());



        incomeBtn.setOnClickListener(this);
        expenseBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        outsideCard.setOnClickListener(this);
        navHeaderProfileBtn.setOnClickListener(this);
        menuBtn.setOnClickListener(this);
        notificationBtn.setOnClickListener(this);
        navHeaderUserName.setOnClickListener(this);
        navHeaderProfileIcon.setOnClickListener(this);
        container.setOnClickListener(this);
        popupWalletIcon.setOnClickListener(this);
        popupTypeIcon.setOnClickListener(this);
        popupTitleEditText.setOnClickListener(this);
        popupAmountEditText.setOnClickListener(this);
        popupDate.setOnClickListener(this);

        drawerNavigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setItemIconTintList(null);
        fetchCategoryData();
        fetchWalletData();
        StaticData.getUpdate().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("yes")) {

                    fetchCategoryData();
                    StaticData.setUpdate("no");
                }

            }
        });


        walletViewModel.getCategoryLiveData().observe(this, categoryDataModel ->  {

               categoryDataModels=categoryDataModel;

        } );


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
                else if (fm.getBackStackEntryAt(fm.getBackStackEntryCount() - 1).getName().equals("profile")) {
                    fm.popBackStack("profile", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().remove(new ProfileFragment()).commit();
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

                verificationForUpload();
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

                    final Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);

                    calendar.set(year, month, day);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                    date = sdf.format(calendar.getTime());

                    popupDate.setText(date);

                    revealFAB(container);
                    isCardOn=true;
                }
                else {
                    hideFAB(container);
                    isCardOn=false;
                }
                break;

            case R.id.dateText:
                chooseDate();
                break;

            case R.id.outside_card:
                if(isCardOn) {
                    hideFAB(container);
                    isCardOn=false;

                }
                break;

            case R.id.nav_header_image:
            case R.id.nav_header_user_name:
            case R.id.view_profile_button:
                getSupportFragmentManager().beginTransaction().replace(R.id.drawer_layout,new ProfileFragment()).addToBackStack("profile").commit();
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
                    walletViewModel.getWallets().observe(this, new Observer<ArrayList<WalletDataModel>>() {
                        @Override
                        public void onChanged(ArrayList<WalletDataModel> walletDataModels) {

                            popupTypeRecyclerView.setAdapter(new WalletNamesAdapter(MainActivity.this,walletDataModels, new WalletNamesClickListener() {
                                @Override
                                public void onItemClick(String name) {
                                    popupWalletNameTextView.setText(name);
                                    popUpWalletName=name;
                                    hideFABSmall(wallet_typeContainer);
                                    isPopupWalletOn=false;

                                }
                            }));
                        }
                    });




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
                    CategoriesAdapter categoriesAdapter=new CategoriesAdapter(this, categoryDataModels, new CategoryIconClickInterface() {
                        @Override
                        public void onItemClick(int tag) {
                            popupTypeIcon.setImageResource(tag);
                            popupTypeIcon.setTag(tag);
                            popupIncomeExpenseType =tag;
                            hideFABSmall(wallet_typeContainer);
                            isPopupWalletOn=false;
                        }
                    });
                    popupTypeRecyclerView.setAdapter(categoriesAdapter);
                    popupTypeRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
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
            String shareBody="CashTrack app: Plasma+\n http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            String shareSub="CashTrack+";
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(shareIntent,"Share Using"));

        }
        else if (id == R.id.emailUs){

            String email = "tarsbinary@gmail.com";
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
            Uri uri = Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSd3RqJLIIQ5iot2rqdhBJIEogKZQj8h484rMWbAF4spy1fWBQ/viewform?usp=sf_link");
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
        wallet_typeContainer.setVisibility(View.GONE);



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


    private void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                        calendar.set(year, month, dayOfMonth);
                        date = sdf.format(calendar.getTime());
                        popupDate.setText(date);
                    }
                }, year, month, day); // set date picker to current date
        calendar.add(Calendar.DATE, -30);
        datePicker.getDatePicker().setMinDate(calendar.getTime().getTime());
        calendar.add(Calendar.DATE, 60);
        datePicker.getDatePicker().setMaxDate(calendar.getTime().getTime());
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }


    private void verificationForUpload() {

        String title="", amount="", type="", date="";


        String wallet="";

        if(popUpWalletName.equals("not set")){
            popupWalletNameTextView.setError("Choose wallet");
        }
        else {
            wallet=popUpWalletName;
        }
        if(popupIncomeExpenseType ==-1){
            popupTitleEditText.setError("select a category icon");
        }

        if(isPopupExpense){
            type="Expense";
        }
        else{
            type="Income";
        }

        if(popupTitleEditText.getText().toString().isEmpty()){
            popupTitleEditText.setError("Enter Title");
            popupTitleEditText.requestFocus();
        }
        else{
            title = popupTitleEditText.getText().toString();
        }

        if(popupAmountEditText.getText().toString().isEmpty()){
            popupAmountEditText.setError("Amount Invalid");
            popupAmountEditText.requestFocus();
        }

        else{
            amount = popupAmountEditText.getText().toString();

        }


        if(popupDate.getText().toString().isEmpty()){
            popupDate.setError("Date Invalid");
            popupDate.requestFocus();
        }

        else{
            date = popupDate.getText().toString();

        }

        if(!title.isEmpty() && !amount.isEmpty() && !type.isEmpty() && !(popupIncomeExpenseType==-1) && !wallet.isEmpty() && !date.isEmpty()){
            submitAlertDialog(StaticData.LoggedInUserEmail,title,amount,type,popupIncomeExpenseType,wallet,date);
        }



    }

    private void submitAlertDialog(String email, String title, String amount, String type, int category, String wallet, String date) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getResources().getString(R.string.are_you_sure));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                TransactionDataModel transactionDataModel = new TransactionDataModel(email,title,amount,type,category,wallet,date);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<TransactionDataModel> call = retroInterface.insertTransactionData(transactionDataModel);

                call.enqueue(new Callback<TransactionDataModel>() {
                    @Override
                    public void onResponse(Call<TransactionDataModel> call, Response<TransactionDataModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Response received!",Toast.LENGTH_SHORT).show();
                            StaticData.setUpdate("yes");
                            dialog.dismiss();
                            hideFAB(container);
                            isCardOn=false;
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"No response from server!",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<TransactionDataModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"No Retrofit connection!",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog=builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
    public void fetchCategoryData(){

        RetroInterface retroInterface = RestClient.createRestClient();

        Call<ArrayList<CategoryDataModel>> call = retroInterface.getCategoryData(new CategoryDataModel(StaticData.LoggedInUserEmail));
        call.enqueue(new Callback<ArrayList<CategoryDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDataModel>> call, Response<ArrayList<CategoryDataModel>> response) {
                walletViewModel.setCategoryLiveData(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDataModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void fetchWalletData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<WalletDataModel>> call = retroInterface.getWalletData(new WalletDataModel(StaticData.LoggedInUserEmail));

        call.enqueue(new Callback<ArrayList<WalletDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WalletDataModel>> call, Response<ArrayList<WalletDataModel>> response) {
                if(response.isSuccessful()) {
                    walletViewModel.setWalletLiveData(response.body());
                }
                else {
                    Toast.makeText(MainActivity.this, "response failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<WalletDataModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }





}
