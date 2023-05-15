package com.pim.projects.besttravel.infrastructure.helper;


import com.pim.projects.besttravel.util.exception.ForbiddenCustomerException;
import org.springframework.stereotype.Component;

@Component
public class BlackListHelper {

    public void isBlackListCustomer(String customerId){
        if(customerId.equals("GOTW771012HMRGR087")){
            throw new ForbiddenCustomerException();
        }
    }


}
