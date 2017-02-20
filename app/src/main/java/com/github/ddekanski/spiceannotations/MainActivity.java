package com.github.ddekanski.spiceannotations;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ddekanski.spiceannotations.controller.MySpiceManager;
import com.github.ddekanski.spiceannotations.model.User;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.springframework.util.StringUtils;

@EActivity(R.layout.activity_main)
@WindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
public class MainActivity extends Activity implements RequestListener<User> {

    @ViewById
    EditText pageNameInput;

    @ViewById
    TableLayout detailsSection;

    @ViewById
    TextView name;

    @ViewById
    TextView category;

    @ViewById
    TextView about;

    @ViewById
    TextView website;

    private MySpiceManager spiceManager = new MySpiceManager();

    @Override
    public void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    @Click
    void getPageDetails() {
        String pageName = pageNameInput.getText().toString();
        if (!StringUtils.hasText(pageName)) {
            showMsg("Please specify the username");
            return;
        }

        if (isPageInCache(pageName)) {
            showMsg("The page is already cached");
        }

        detailsSection.setVisibility(View.GONE);
        setProgressBarIndeterminateVisibility(true);
        spiceManager.executeRequest(User.class, r -> r.getUser(pageName),
                pageName, DurationInMillis.ALWAYS_RETURNED, this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private boolean isPageInCache(CharSequence pageName) {
        try {
            return spiceManager.isDataInCache(User.class, pageName, DurationInMillis.ALWAYS_RETURNED).get();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void onRequestSuccess(User user) {
        setProgressBarIndeterminateVisibility(false);
        detailsSection.setVisibility(View.VISIBLE);
        name.setText(user.getName());
        category.setText(user.getType());
        about.setText(user.getBio());
        website.setText(user.getBlog());
    }

    @Override
    public void onRequestFailure(SpiceException spiceException) {
        setProgressBarIndeterminateVisibility(false);
        final String msg = spiceException.getCause().getMessage();
        showMsg("Error: " + msg);
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
