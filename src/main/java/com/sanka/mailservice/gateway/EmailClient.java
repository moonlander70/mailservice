package com.sanka.mailservice.gateway;

import com.sanka.mailservice.model.Email;

public interface EmailClient {

    void sendEmail(Email email);


}
