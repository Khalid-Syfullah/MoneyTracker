package com.tars.moneytracker.ui.notes;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.NoteDataModel;
import com.tars.moneytracker.datamodel.OverviewDataModel;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.ui.home.HomeViewModel;
import com.tars.moneytracker.ui.notification.NotificationFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesFragment extends Fragment {

    private NotesViewModel notesViewModel;
    private View view;
    private ImageView closeBtn,noteSave;
    private boolean animationDone = false;
    String note="";
    private EditText noteEditText;


    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_notes, container, false);
        closeBtn = view.findViewById(R.id.notes_close);
        noteEditText=view.findViewById(R.id.notes_editText);
        fetchNoteData();
        NotesViewModel notesViewModel= new ViewModelProvider(this).get(NotesViewModel.class);

        notesViewModel.getNoteData().observe(getViewLifecycleOwner(), new Observer<NoteDataModel>() {
            @Override
            public void onChanged(NoteDataModel noteDataModel) {
                noteEditText.setText(noteDataModel.getNote());
            }
        });

        noteSave=view.findViewById(R.id.notes_save);



        noteSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                note=noteEditText.getText().toString();
                RetroInterface retroInterface = RestClient.createRestClient();
                NoteDataModel noteDataModel=new NoteDataModel(StaticData.LoggedInUserEmail,note);
                Call<NoteDataModel> call = retroInterface.updateNoteData(noteDataModel);

                call.enqueue(new Callback<NoteDataModel>() {
                    @Override
                    public void onResponse(Call<NoteDataModel> call, Response<NoteDataModel> response) {
                        if(response.isSuccessful()){
                            notesViewModel.setNoteLiveData(noteDataModel);
                        }

                    }

                    @Override
                    public void onFailure(Call<NoteDataModel> call, Throwable t) {


                    }
                });
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                hideFAB(view);
            }
        });

        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(!animationDone){
                    revealFAB(view);
                    animationDone = true;
                }
            }


        });


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notesViewModel = new ViewModelProvider(this).get(NotesViewModel.class);
        revealFAB(view);

    }



    @Override
    public void onDetach() {

        super.onDetach();
    }






    private void revealFAB(View view) {

        int cx = view.getWidth() ;
        int cy = view.getHeight() ;
        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, 0, 0, 0, finalRadius);
        anim.setDuration(500);

        anim.start();
    }
    private void hideFAB(View view) {

        int cx = view.getWidth();
        int cy = view.getHeight() ;
        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim = ViewAnimationUtils.createCircularReveal(view, 0, 0, initialRadius, 0);
        anim.setDuration(500);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                FragmentManager fm= getActivity().getSupportFragmentManager();

                fm.popBackStack("notes", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fm.beginTransaction().remove(NotesFragment.this).commit();
            }
        });
        anim.start();
    }

    private void fetchNoteData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        NoteDataModel noteDataModel = new NoteDataModel(StaticData.LoggedInUserEmail);
        Call<NoteDataModel> call = retroInterface.getNoteData(noteDataModel);

        call.enqueue(new Callback<NoteDataModel>() {
            @Override
            public void onResponse(Call<NoteDataModel> call, Response<NoteDataModel> response) {
                if(response.isSuccessful()){
                    notesViewModel.setNoteLiveData(response.body());
                    
                }

            }

            @Override
            public void onFailure(Call<NoteDataModel> call, Throwable t) {



            }
        });
    }

}