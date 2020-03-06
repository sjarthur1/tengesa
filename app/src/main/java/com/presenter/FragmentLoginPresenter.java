package com.presenter;

import android.view.View;

public class FragmentLoginPresenter {
    
    private LoginView view;
    public FragmentLoginPresenter(LoginView view){
        this.view = view;
    }
    
    public interface LoginView{
        void loginError();
    }
}
