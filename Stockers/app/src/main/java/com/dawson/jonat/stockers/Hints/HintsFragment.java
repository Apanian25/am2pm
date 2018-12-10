package com.dawson.jonat.stockers.Hints;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dawson.jonat.stockers.Entity.Hint;
import com.dawson.jonat.stockers.R;

public class HintsFragment extends Fragment {

    private Hint hint;

    public void setHint(Hint h) {
        this.hint = h;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (hint != null && savedInstanceState != null) {
            savedInstanceState.putString("hint", hint.getHint());
            savedInstanceState.putString("link", hint.getUrl());
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null && hint == null){
            this.hint = new Hint(savedInstanceState.getString("hint"), savedInstanceState.getString("link"));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (hint != null && savedInstanceState != null) {
            savedInstanceState.putString("hint", hint.getHint());
            savedInstanceState.putString("link", hint.getUrl());
        }
        return inflater.inflate(R.layout.fragment_hints, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String hintStr = hint == null ? savedInstanceState.getString("hint") : hint.getHint();
        final String linkStr = hint == null ? savedInstanceState.getString("link") : hint.getUrl();

        TextView tv = (TextView) view.findViewById(R.id.hintTV);
        tv.setText(hintStr);

        Button learnBtn = (Button) view.findViewById(R.id.learnMoreBtn);
        learnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(linkStr));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (hint != null && outState != null) {
            outState.putString("hint", hint.getHint());
            outState.putString("link", hint.getUrl());
        }
    }
}
