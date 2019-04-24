package com.pingtiao51.armsmodule.mvp.model.entity.eventbus;

import com.pingtiao51.armsmodule.mvp.model.entity.response.HomeMessageScrollResponse;

import java.util.List;

public class HomeScrollMessageTag {

    private List<HomeMessageScrollResponse> messages;

    public HomeScrollMessageTag(List<HomeMessageScrollResponse> msgList){
        messages = msgList;
    }

    public List<HomeMessageScrollResponse> getMessages() {
        return messages;
    }

    public void setMessages(List<HomeMessageScrollResponse> messages) {
        this.messages = messages;
    }
}
