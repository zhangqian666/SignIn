package com.iptv.signin.model;

import com.iptv.signin.bean.SignInTime;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by ZhangQian on 2017/3/12 0012.
 */

public class SignInModel extends BaseModel<SignInTime> {

    private List<SignInTime> signInTimes;
    private SignInModelListener signInTimeBaseModelListener;

    public SignInModel(SignInModelListener signInTimeBaseModelListener) {
        super();
        this.signInTimeBaseModelListener = signInTimeBaseModelListener;
    }

    public void saveSignInMessage(SignInTime signInTime) {
        signInTime.save();
        signInTimeBaseModelListener.setSaveResult(true);
    }

    public void getSignInMessage(String message) {
        signInTimes = DataSupport.findAll(SignInTime.class);
        signInTimeBaseModelListener.setResulList(signInTimes);
    }

    public void deleteSignInMessage(SignInTime signInTime,String time){
       DataSupport.deleteAll(SignInTime.class,"content = ?",time);
    }
    public interface SignInModelListener extends BaseModelListener<SignInTime>{
        void setSaveResult(boolean isSuccess);
    }
}
