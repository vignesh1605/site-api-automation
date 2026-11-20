package com.automation.api.data;

import com.automation.api.models.request.UserRequest;
import org.testng.annotations.DataProvider;

public class UserTestData {

    @DataProvider(name = "validCreateUsers")
    public static Object[][] validCreateUsers() {
        return new Object[][] {
                {new UserRequest("Maya", "Raman")},
                {new UserRequest("Anne-Marie", "Smith")}
        };
    }
}