package com.pingtiao51.armsmodule.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.pingtiao51.armsmodule.di.module.LoginModule;
import com.pingtiao51.armsmodule.mvp.ui.activity.LoginActivity;
import com.pingtiao51.armsmodule.mvp.ui.custom.view.BankPayDialog;

import dagger.Component;

@ActivityScope
@Component( dependencies = AppComponent.class)
public interface BankPayDialogComponent {
    void inject(BankPayDialog dialog);

}
