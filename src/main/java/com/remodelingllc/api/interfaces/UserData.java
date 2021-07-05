package com.remodelingllc.api.interfaces;

import com.remodelingllc.api.entity.enums.Status;

public interface UserData {

    int getId();
    String getUsername();
    String getEmail();
    String getFirstname();
    String getLastname();
    Status getStatus();

}
