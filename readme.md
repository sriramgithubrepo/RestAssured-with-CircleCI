# API call Automation

- Pre-requiste JRE1.8, maven 3.6.0 and IDE used IntelliJ
- Description: Backend API call automation using RestAssured library with Java,Junit,Maven and integrated with CircleCI. 

1. Testcases are located under src\test\java\org.example.test\testcase
2. Utility and endpoints classes are located under src\test\java\org.example.test\setup
3. CircleCI configuration file(config.yml) is located under rootdirectory\.circleci\config.yml
4. pom.xml is located under rootdirectory.
5. Testcases are sequenced such that first user is validated,and then posts for the user is identified and then comments email format is validated.
