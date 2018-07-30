package in.programmeraki.hbt.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import in.programmeraki.hbt.R;
import in.programmeraki.hbt.utils.Constant;

public class UserProfileFragment extends Fragment {
    Context activity_context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TextView title_tv, full_name_tv;
    EditText fname_et, lname_et, email_et, dob_et, height_et, weight_et, pulse_min_et,
            pulse_max_et, temp_min_et, temp_max_et;
    ViewGroup top_head_rl;
    Button submit_btn;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity_context = context;
        Log.d("tmp2", "onAttach: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.selected_frag_id = 1;
        Log.d("tmp2", "onResume: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        top_head_rl = view.findViewById(R.id.top_head_rl);
        title_tv = view.findViewById(R.id.title_tv);
        full_name_tv = view.findViewById(R.id.full_name_tv);
        fname_et = view.findViewById(R.id.fname_et);
        lname_et = view.findViewById(R.id.lname_et);
        email_et = view.findViewById(R.id.email_et);
        dob_et = view.findViewById(R.id.dob_et);
        height_et = view.findViewById(R.id.height_et);
        weight_et = view.findViewById(R.id.weight_et);
        pulse_min_et = view.findViewById(R.id.pulse_min_et);
        pulse_max_et = view.findViewById(R.id.pulse_max_et);
        temp_max_et = view.findViewById(R.id.temp_max_et);
        temp_min_et = view.findViewById(R.id.temp_min_et);
        submit_btn = view.findViewById(R.id.submit_btn);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity_context);
        editor = sharedPreferences.edit();

        fname_et.setText(sharedPreferences.getString(Constant.fname, "John"));
        lname_et.setText(sharedPreferences.getString(Constant.lname, "Doe"));
        email_et.setText(sharedPreferences.getString(Constant.email, "john.doe123@yourmail.co"));
        dob_et.setText(sharedPreferences.getString(Constant.dob, "09/07/1992"));

        height_et.setText(sharedPreferences.getString(Constant.weight, ""));
        weight_et.setText(sharedPreferences.getString(Constant.height, ""));
        pulse_min_et.setText(sharedPreferences.getString(Constant.p_min, Constant.p_min_default));
        pulse_max_et.setText(sharedPreferences.getString(Constant.p_max, Constant.p_max_default));
        temp_min_et.setText(sharedPreferences.getString(Constant.t_min, Constant.t_min_default));
        temp_max_et.setText(sharedPreferences.getString(Constant.t_max, Constant.t_max_default));

        full_name_tv.setText(fname_et.getText().toString() + " " + lname_et.getText().toString());

        submit_btn.setOnClickListener(btnView -> {
            editor.putString(Constant.fname, fname_et.getText().toString());
            editor.putString(Constant.lname, lname_et.getText().toString());
            editor.putString(Constant.email, email_et.getText().toString());
            editor.putString(Constant.dob, dob_et.getText().toString());
            editor.putString(Constant.height, height_et.getText().toString());
            editor.putString(Constant.weight, weight_et.getText().toString());
            editor.putString(Constant.p_min, pulse_min_et.getText().toString());
            editor.putString(Constant.p_max, pulse_max_et.getText().toString());
            editor.putString(Constant.t_min, temp_min_et.getText().toString());
            editor.putString(Constant.t_max, temp_max_et.getText().toString());
            editor.apply();
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
