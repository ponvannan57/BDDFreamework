package main;

import java.time.LocalDate;

public class datechecker {
	
public static int Gracemonth;
public static int totalmonths;
	public static void main(String[] args) {
		LocalDate currentdate = LocalDate.now();
    	int Year = currentdate.getYear();
    	int Month = 12-currentdate.getMonthValue();
    	int wagesubmissionyear = 2018;
    	String quarter = "January, February, March (Q1)";    	
    	int wageyear = Year - wagesubmissionyear;
    	totalmonths = (wageyear*12)-Month;

    	double Wage2 = (double) (170.0/4);
    	
    	if((Wage2*100)%10==0){
    		
    		System.out.println(String.format("%.2f", Wage2));
    		
    	}
//    	double roundoff = (double) Math.round(Wage2*100.00)/100.00;
//    	double roundOff = Math.round(Wage2 * 100.0) / 100.0;
    	
    	
    	System.out.println(Wage2);
    	switch(quarter) {
    	
    	case"January, February, March (Q1)":
    		 Gracemonth = 8;
    		break;
    	case"April, May, June (Q2)":
    		 Gracemonth = 5;
    		break;
    	case"July, August, September (Q3)":
    		 Gracemonth = 2;
    		break;
    	case"October, November, December (Q4)":
    		 Gracemonth = -1;
    		break;
    	
    	}
    	
    	if(quarter.equals("October, November, December (Q4)") && totalmonths ==0)
    	{
    		
    		int wagetotalmonths = totalmonths+0;
        	System.out.println(wagetotalmonths);
    	}
    	else {
    		int wagetotalmonths = totalmonths+Gracemonth;
        	System.out.println(wagetotalmonths);
    	}
    	
    	
    }

	

}
