Feature: Wage Submission

  Background: 
    Given I Configure the Browser as "Chrome" with URL "New"

  @tag1
  Scenario Outline: Employer Wage Submission
    #Then I Register an Employer As per Deatils from Below DataTable
      #| ServiceBeginDate | Yearwages | ServiceEndDate | EMPAddress        | EMPCity  | EMPState   | EMPZipCode | EMPEmail           | EMPPhone   |
      #| 1/1/2018         |      2018 | 4/10/2026      | 45 Raymond Street | Rosewell | New Mexico |      87017 | rosewell@gmail.com | 9548741325 |
    Then Login with the username <Username>
    And I navigate to Wage Submission
    #And I Login With Newly Created Employer and navigate to wage submission
    When I select year <Year> and quarter <Quarter> for wage submission
    Then I select wage submission method as <Method>
    And I select the File type as <FileType>
    Then I Upload the file <FileName>
    And I selct Overwrite or merge option <Option> if it gets displayed
    And I Select ignore errors and proceed to next screen
    When I click on button <button> and proceed to next screen
    Then I calculate the Interest for Month <Year> and Quarter <Quarter>

    Examples: 
      | Username | Year | Quarter                       | Method | FileType | FileName           | button | option |
      | mali21   | 2018 | January, February, March (Q1) | File   | CSV      | CSVSAMPLE_DATA.csv | Next   | Merge  |
