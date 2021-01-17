package com.tars.moneytracker.ui.wallet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;

import com.tars.moneytracker.R;
import com.tars.moneytracker.ui.home.adapters.GoalsAdapter;
import com.tars.moneytracker.ui.wallet.adapters.CategoriesAdapter;
import com.tars.moneytracker.ui.wallet.adapters.WalletAdapter;

public class WalletFragment extends Fragment {

    private WalletViewModel walletViewModel;
    private RecyclerView myWalletsRecyclerView, myGoalsRecyclerView,categoryRecyclerView;
    private ImageView addWalletBtn,addGoalsBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TransitionInflater transitionInflater = TransitionInflater.from(requireContext());
        setEnterTransition(transitionInflater.inflateTransition(R.transition.fade));
        setExitTransition(transitionInflater.inflateTransition(R.transition.fade));
        walletViewModel =
                new ViewModelProvider(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        walletViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        addWalletBtn=root.findViewById(R.id.wallet_add_wallet_btn);
        addGoalsBtn=root.findViewById(R.id.wallet_add_goals_btn);


        myWalletsRecyclerView = root.findViewById(R.id.wallet_mywallets_recycler);
        myGoalsRecyclerView = root.findViewById(R.id.wallet_mygoals_recycler);
        categoryRecyclerView=root.findViewById(R.id.wallets_categories_recycler);

        myWalletsRecyclerView.setAdapter(new WalletAdapter());
        myGoalsRecyclerView.setAdapter(new GoalsAdapter());
        categoryRecyclerView.setAdapter(new CategoriesAdapter());


        myWalletsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        myGoalsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

        addGoalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoalsAlertDialog();
            }
        });

        addWalletBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddWalletAlertDialog();

            }
        });



        return root;
    }

    private void showGoalsAlertDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(getContext()).inflate(R.layout.new_goal_alert,null);

        Spinner currencies=dialog.findViewById(R.id.goal_alert_currency_spinner);

        String[] currencyItems=getResources().getStringArray(R.array.currencies);

        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, currencyItems);

        currencies.setAdapter(currencyAdapter);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        builder.setView(dialog);

        builder.show();
    }

    private void showAddWalletAlertDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        View dialog=LayoutInflater.from(getContext()).inflate(R.layout.new_wallet_alert,null);
        Spinner walletTypes=dialog.findViewById(R.id.wallet_alert_type_spinner);
        Spinner currencies=dialog.findViewById(R.id.wallet_alert_currency_spinner);

        String[] walletItems=getResources().getStringArray(R.array.wallet_types);
        String[] currencyItems=getResources().getStringArray(R.array.currencies);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, walletItems);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, currencyItems);

        currencies.setAdapter(currencyAdapter);
        walletTypes.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);
        currencyAdapter.setDropDownViewResource(R.layout.custom_spinner_dropdown);

        builder.setView(dialog);

        builder.show();


//                Dialog dialog=new Dialog(getContext());
//        dialog.setContentView(R.layout.new_wallet_alert);

//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.show();

    }
}