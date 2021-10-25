Feature: Wage Submission

  Background: 
    Given I Configure the Browser as "Chrome" with URL "New"

  @tag1
  Scenario Outline: Employer Wage Submission
    #Then Login with the username <Username>
    Then I Register an Employer As per Deatils from Below DataTable
      | ServiceBeginDate | Yearwages | ServiceEndDate | EMPAddress        | EMPCity  | EMPState   | EMPZipCode | EMPEmail           | EMPPhone   |
      | 1/1/2018         |   2018   | 4/10/2026      | 45 Raymond Street | Rosewell | New Mexico |      87017 | rosewell@gmail.com | 9548741325 |

    Examples: 
      | Username |
      | sadmin   |
