
Feature: Wage Submission

  Background: 
    Given I Configure the Browser as "Chrome" with URL "New"

  @T1
  Scenario Outline: Employer Registration
    Then I Register an Employer As per Deatils from Below DataTable for Iteration <Iteration>
      | ServiceBeginDate | Yearwages | ServiceEndDate | EMPAddress        | EMPCity  | EMPState   | EMPZipCode | EMPEmail           | EMPPhone   |
      | 1/1/2018         |      2018 | 4/10/2026      | 45 Raymond Street | Rosewell | New Mexico |      87017 | rosewell@gmail.com | 9548741325 |

    Examples: 
      | Iteration |
      |         1 |
      

  @T2
  Scenario Outline: Employer Wage Submission For FileType <FileType>
    Then I Register an Employer As per Deatils from Below DataTable
      | ServiceBeginDate | Yearwages | ServiceEndDate | EMPAddress        | EMPCity  | EMPState   | EMPZipCode | EMPEmail           | EMPPhone   |
      | 1/1/2018         |      2018 | 4/10/2026      | 45 Raymond Street | Rosewell | New Mexico |      87017 | rosewell@gmail.com | 9548741325 |
    And I Login With Newly Created Employer and navigate to wage submission
    When I select year <Year> and quarter <Quarter> for wage submission
    Then I select wage submission method as <Method>
    And I select the File type as <FileType>
    Then I replace the data for File Type as <FileType> File Name <FileName> for Year <Year> and Quarter <Quarter>
    Then I Upload the file <FileName> and <FileType>
    And I selct Overwrite or merge option <Option> if it gets displayed
    And I Select ignore errors and proceed to next screen
    When I click on button <button> and proceed
    Then I calculate the Interest for Month <Year> and Quarter <Quarter>

    Examples: 
      | Year | Quarter                          | Method | FileType | FileName   | button | option |
      | 2018 | January, February, March (Q1)    | File   | CSV      | CSV_DATA   | Next   | Merge  |
      | 2018 | April, May, June (Q2)            | File   | CSV      | CSVTXT     | Next   | Merge  |
      | 2018 | October, November, December (Q4) | File   | XML      | XML_Sample | Next   | Merge  |
      | 2018 | July, August, September (Q3)     | File   | EFW2     | EFW2       | Next   | Merge  |
      | 2018 | January, February, March (Q1)    | File   | ICESA    | ICESA      | Next   | Merge  |
      
      
  

  @T3
  Scenario Outline: Employer Wage Submission
    Then Login with the username <Username>
    And I navigate to Wage Submission
    #When I select year <Year> and quarter <Quarter> for wage submission
    Then I google with pen
    #Then I select wage submission method as <Method>
    #And I select the File type as <FileType>
    #And I selct Overwrite or merge option <Option> if it gets displayed
    #And I Select ignore errors and proceed to next screen
    #When I click on button <button> and proceed to next screen
    #Then I calculate the Interest for Month <Year> and Quarter <Quarter>

    Examples: 
      | Username | #Year | Quarter                      | Method | FileType | FileName           | button | Option |
      | mali54   |# 2019 | July, August, September (Q3) | File   | CSV      | CSVSAMPLE_DATA.csv | Next   | Merge  |
