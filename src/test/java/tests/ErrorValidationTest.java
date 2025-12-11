package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import testComponents.BaseTest;

public class ErrorValidationTest extends BaseTest {
    String email = "ceren1481@gmail.com";
    String invalidPassword = "wrongPassword";
    String invalidPasswordMessage = "Incorrect email or password.";

    @Test
    public void loginErrorValidation() {
        landingPage.loginToApp(email, invalidPassword);
        String errorMessage = landingPage.getErrorMessageForInvalidPassword();
        Assert.assertEquals(errorMessage, invalidPasswordMessage);
    }
}