package com.tars.moneytracker.ui.wallet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.CategoryDataModel;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.adapters.GoalsAdapter;
import com.tars.moneytracker.ui.wallet.adapters.CategoriesAdapter;
import com.tars.moneytracker.ui.wallet.adapters.CategoryIconsAdapter;
import com.tars.moneytracker.ui.wallet.adapters.WalletAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletFragment extends Fragment implements RecyclerItemClickInterface {

    private WalletViewModel walletViewModel;
    private RecyclerView myWalletsRecyclerView, myGoalsRecyclerView,categoryRecyclerView;
    private ImageView addWalletBtn,addGoalsBtn,addCategoriesBtn;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));
        setExitTransition(transitionInflater.inflateTransition(R.transition.fade));


        walletViewModel =
                new ViewModelProvider(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        addWalletBtn=root.findViewById(R.id.wallet_add_wallet_btn);
        addGoalsBtn=root.findViewById(R.id.wallet_add_goals_btn);
        addCategoriesBtn=root.findViewById(R.id.wallets_add_categories_btn);


        myWalletsRecyclerView = root.findViewById(R.id.wallet_mywallets_recycler);
        myGoalsRecyclerView = root.findViewById(R.id.wallet_mygoals_recycler);
        categoryRecyclerView=root.findViewById(R.id.wallets_categories_recycler);

        myWalletsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        myGoalsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        fetchWalletData();
        fetchGoalData();
        fetchCategoryData();

        StaticData.getUpdate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("yes")) {
                    fetchWalletData();
                    fetchGoalData();
                    fetchCategoryData();
                    StaticData.setUpdate("no");
                }

            }
        });



        walletViewModel.getWallets().observe(getViewLifecycleOwner(), new Observer<ArrayList<WalletDataModel>>() {
            @Override
            public void onChanged(ArrayList<WalletDataModel> walletDataModels) {
                myWalletsRecyclerView.setAdapter(new WalletAdapter(getActivity(),walletDataModels));
            }
        });

        walletViewModel.getGoalLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<GoalDataModel>>() {
            @Override
            public void onChanged(ArrayList<GoalDataModel> goalDataModels) {
                myGoalsRecyclerView.setAdapter(new GoalsAdapter(getContext(),goalDataModels));
            }
        } );

        walletViewModel.getCategoryLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<CategoryDataModel>>() {
            @Override
            public void onChanged(ArrayList<CategoryDataModel> categoryDataModels) {
                categoryRecyclerView.setAdapter(new CategoriesAdapter(getContext(),categoryDataModels));
            }
        } );

        addCategoriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoriesAlertDialog(getContext());
            }
        });

        addGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoalsAlertDialog(getContext());
            }
        });

        addWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddWalletAlertDialog(getContext());

            }
        });



        return root;
    }



    public void fetchWalletData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<WalletDataModel>> call = retroInterface.getWalletData(new WalletDataModel(StaticData.LoggedInUserEmail));

        call.enqueue(new Callback<ArrayList<WalletDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WalletDataModel>> call, Response<ArrayList<WalletDataModel>> response) {
                walletViewModel.setWalletLiveData(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<WalletDataModel>> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fetchGoalData(){

        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<GoalDataModel>> call = retroInterface.getGoalData();
        call.enqueue(new Callback<ArrayList<GoalDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GoalDataModel>> call, Response<ArrayList<GoalDataModel>> response) {
                walletViewModel.setGoalLiveData(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<GoalDataModel>> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
    public void fetchCategoryData(){

        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<CategoryDataModel>> call = retroInterface.getCategoryData();
        call.enqueue(new Callback<ArrayList<CategoryDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CategoryDataModel>> call, Response<ArrayList<CategoryDataModel>> response) {
                walletViewModel.setCategoryLiveData(response.body());

            }

            @Override
            public void onFailure(Call<ArrayList<CategoryDataModel>> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }
        });



    }



    private void showCategoriesAlertDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(getContext()).inflate(R.layout.new_category_alert,null);

        RecyclerView icons=dialog.findViewById(R.id.new_category_choose_icon_recycler);

        EditText categoryName = dialog.findViewById(R.id.category_alert_category_name);
        Button saveBtn = dialog.findViewById(R.id.category_alert_save_button);
        Button deleteBtn = dialog.findViewById(R.id.category_alert_delete_button);

        icons.setAdapter(new CategoryIconsAdapter());
        icons.setLayoutManager(new GridLayoutManager(getActivity(),3));


        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String name = categoryName.getText().toString();

                CategoryDataModel categoryDataModel = new CategoryDataModel(name);
                RestClient.insertCategory(context,categoryDataModel);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
/*
                String name = categoryName.getText().toString();

                CategoryDataModel categoryDataModel = new CategoryDataModel(name);
                RestClient.deleteCategory(context,categoryDataModel);
*/
                alertDialog.dismiss();
            }
        });
    }

    private void showGoalsAlertDialog(Context context) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(getContext()).inflate(R.layout.new_goal_alert,null);

        Spinner currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);

        String[] currencyItems=getResources().getStringArray(R.array.currencies);

        EditText titleText = dialog.findViewById(R.id.goal_alert_title_editText);
        EditText amountText = dialog.findViewById(R.id.goal_alert_amount_editText);
        TextView dateText = dialog.findViewById(R.id.goal_alert_dateTextView);
        Button saveBtn = dialog.findViewById(R.id.goal_alert_save_button);
        Button deleteBtn = dialog.findViewById(R.id.goal_alert_delete_button);


        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, currencyItems);

        currencies.setAdapter(currencyAdapter);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);


        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        dateText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseDate(context,dateText);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String title = titleText.getText().toString();
                String amount = amountText.getText().toString();
                String currency = currencies.getSelectedItem().toString();
                String date = dateText.getText().toString();


                GoalDataModel goalDataModel = new GoalDataModel(title, amount, currency, date);
                RestClient.insertGoal(context,goalDataModel);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });
    }

    private void showAddWalletAlertDialog(Context context) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(getContext()).inflate(R.layout.new_wallet_alert,null);

        Spinner walletTypes=dialog.findViewById(R.id.wallet_alert_type_spinner);
        Spinner currencies=dialog.findViewById(R.id.wallet_alert_currency_spinner);

        String[] walletItems=getResources().getStringArray(R.array.wallet_types);
        String[] currencyItems=getResources().getStringArray(R.array.currencies);


        EditText titleText = dialog.findViewById(R.id.wallet_alert_title_editText);
        Button saveBtn = dialog.findViewById(R.id.wallet_alert_save_button);
        Button deleteBtn = dialog.findViewById(R.id.wallet_alert_delete_button);
        deleteBtn.setText("Cancel");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, walletItems);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, currencyItems);

        currencies.setAdapter(currencyAdapter);
        walletTypes.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);


        builder.setView(dialog);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String title = titleText.getText().toString();
                String type = walletTypes.getSelectedItem().toString();
                String currency = currencies.getSelectedItem().toString();

                WalletDataModel walletDataModel = new WalletDataModel(title,type,currency, StaticData.LoggedInUserEmail);
//                RestClient.insertWallet(context,walletDataModel);
                RetroInterface retroInterface = RestClient.createRestClient();
                Call<WalletDataModel> call = retroInterface.insertWalletData(walletDataModel);

                call.enqueue(new Callback<WalletDataModel>() {
                    @Override
                    public void onResponse(Call<WalletDataModel> call, Response<WalletDataModel> response) {
                        Toast.makeText(context,response.body().getServerMsg(),Toast.LENGTH_SHORT).show();
                        fetchWalletData();

                    }

                    @Override
                    public void onFailure(Call<WalletDataModel> call, Throwable t) {
                        Toast.makeText(context,"No Retrofit connection!",Toast.LENGTH_SHORT).show();


                    }

                });
                alertDialog.dismiss();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                alertDialog.dismiss();
            }
        });




    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_SHORT).show();
    }



    private void chooseDate(Context context, TextView dateText) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePicker =
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                        calendar.set(year, month, dayOfMonth);
                        String date = sdf.format(calendar.getTime());
                        dateText.setText(date);
                    }
                }, year, month, day); // set date picker to current date
        datePicker.getDatePicker().setMinDate(calendar.getTime().getTime());
        calendar.add(Calendar.DATE, 30);
        datePicker.getDatePicker().setMaxDate(calendar.getTime().getTime());
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void onItemClick(Drawable position, String name) {

    }

    @Override
    public void onItemClick(String name) {

    }
}
