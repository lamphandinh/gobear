package com.example.gobear.ui.base;

import android.content.Context;

public interface IBaseView {
    Context getContext();
    void showLoading();
    void hideLoading();
}
