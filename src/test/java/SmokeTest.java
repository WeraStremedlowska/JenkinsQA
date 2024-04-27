import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import src.runner.BaseTest;

public class SmokeTest extends BaseTest {

    @Test
    public void testWelcome() {
        WebElement welcomeText = getDriver().findElement(By.cssSelector(".empty-state-block > h1"));

        Assert.assertEquals(welcomeText.getText(), " to Jenkins!");
    }
}
