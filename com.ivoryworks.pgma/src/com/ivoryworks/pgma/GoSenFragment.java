package com.ivoryworks.pgma;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.java.sen.SenFactory;
import net.java.sen.StringTagger;
import net.java.sen.dictionary.Token;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GoSenFragment extends Fragment implements OnClickListener {
    private EditText mEditText;
    private TextView mTextView;
    private Button mSubmit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gosen, container, false);
        mEditText = (EditText) v.findViewById(R.id.input);
        mTextView = (TextView) v.findViewById(R.id.output);
        mSubmit = (Button) v.findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.submit:
            execGosen(mEditText.getText());
            break;
        }
    }

    private void execGosen(CharSequence text) {
        StringTagger tagger = SenFactory.getStringTagger(null);
        List<Token> tokens = new ArrayList<Token>();
        try {
            String buf = "";
            tagger.analyze(text.toString(), tokens);
            for (Token token : tokens) {
                buf += "=====\n";
                buf += "surface : " + token.getSurface() + "\n";
                buf += "cost : " + token.getCost() + "\n";
                buf += "length : " + token.getLength() + "\n";
                buf += "start : " + token.getStart() + "\n";
                buf += "basicForm : " + token.getMorpheme().getBasicForm() + "\n";
                buf += "conjugationalForm : " + token.getMorpheme().getConjugationalForm() + "\n";
                buf += "conjugationalType : " + token.getMorpheme().getConjugationalType() + "\n";
                buf += "partOfSpeech : " + token.getMorpheme().getPartOfSpeech() + "\n";
                buf += "pronunciations : " + token.getMorpheme().getPronunciations() + "\n";
                buf += "readings : " + token.getMorpheme().getReadings() + "\n";
            }
            mTextView.setText(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
