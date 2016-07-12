
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TesteCaseInterface {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  
  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.firefox.bin",
                    "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
    driver = new FirefoxDriver();
    baseUrl = "http://localhost:8080/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testECaseInterface() throws Exception {
    driver.get(baseUrl + "/ui-repeat/faces/home.xhtml");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("loginForm:username")).clear();
    driver.findElement(By.id("loginForm:username")).sendKeys("username");
    driver.findElement(By.id("loginForm:password")).clear();
    driver.findElement(By.id("loginForm:password")).sendKeys("password");
    driver.findElement(By.name("loginForm:j_idt29")).click();
    driver.findElement(By.linkText("[Criar Adicionar]")).click();
    driver.findElement(By.id("createTask:description")).clear();
    driver.findElement(By.id("createTask:description")).sendKeys("Tarefa");
    driver.findElement(By.id("createTask:duedate")).clear();
    driver.findElement(By.id("createTask:duedate")).sendKeys("2016-07-10");
    driver.findElement(By.id("createTask:tags")).clear();
    driver.findElement(By.id("createTask:tags")).sendKeys("Tag1, Tag2");
    driver.findElement(By.name("createTask:j_idt38")).click();
    driver.findElement(By.id("tarefas")).click();
    driver.findElement(By.id("j_idt20:0:list:menos")).click();
    driver.findElement(By.linkText("Home")).click();
    driver.findElement(By.id("list:j_idt18:0:mais")).click();
    driver.findElement(By.id("tarefas")).click();
    driver.findElement(By.name("j_idt20:0:j_idt55:j_idt56")).click();
    driver.findElement(By.linkText("Home")).click();
    driver.findElement(By.id("pesquisa")).click();
    driver.findElement(By.id("pesquisaForm:pesquisa")).clear();
    driver.findElement(By.id("pesquisaForm:pesquisa")).sendKeys("Pessoal");
    driver.findElement(By.name("pesquisaForm:j_idt24")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
