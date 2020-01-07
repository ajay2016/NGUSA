package com.nationalguard.ddf.utility;

import java.util.Hashtable;

public class DataUtil {
	
	public static  Object[][] getTestData(Xls_Reader xls, String testCaseName){
		
		String sheetName="Data";
		//String testCaseName="TestB";
		// reads data for only testCaseName
		
		int testStartRowNum=1;
		while(!xls.getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)){
			testStartRowNum++;
		}
		System.out.println("Test starts from row - "+ testStartRowNum);
		int colStartRowNum=testStartRowNum+1;
		int dataStartRowNum=testStartRowNum+2;
		
		// calculate rows of data
		int rows=0;
		while(!xls.getCellData(sheetName, 0, dataStartRowNum+rows).equals("")){
			rows++;
		}
		System.out.println("Total rows are  - "+rows );
		
		//calculate total cols
		int cols=0;
		while(!xls.getCellData(sheetName, cols, colStartRowNum).equals("")){
			cols++;
		}
		System.out.println("Total cols are  - "+cols );
		//in the hashtable there is only one column
		Object[][] data = new Object[rows][1];
		//initialize datarow and hashtable
		int dataRow=0;
		Hashtable<String,String> table=null;
		//read the data\
		//iterate over row
		for(int rNum=dataStartRowNum;rNum<dataStartRowNum+rows;rNum++){
			//Constructs a new, empty hashtable with a default initial capacity 
			//every row making a new hashtable
			table = new Hashtable<String,String>();
			//iterate over column
			for(int cNum=0;cNum<cols;cNum++){
				//every hashtable has same key
				//key is the name of the column and value is the row
				//row number starts form column starts row
				String key=xls.getCellData(sheetName,cNum,colStartRowNum);
				String value= xls.getCellData(sheetName, cNum, rNum);
				//put he data in the table first then to object[][] array
				table.put(key, value);
				// 0,0 0,1 0,2
				//1,0 1,1
			}
			//after inner loop is finished entire data is in the table to we
			//need to put that data in the 2 dim objects
			//this 2 dim object array has only one column row is incresing
			data[dataRow][0] =table;
			dataRow++;
		}
		return data;
		
	}

	
	public static boolean isRunnable(String testName, Xls_Reader xls){
		String sheet="Testcases";
		int rows = xls.getRowCount(sheet);		
		for(int r=2;r<=rows;r++){
			String tName=xls.getCellData(sheet, "TCID", r);
			if(tName.equals(testName)){
				String runmode=xls.getCellData(sheet, "Runmode", r);
				if(runmode.equals("Y"))
					return true;
				else
					return false;

			}
		}
		return false;
	}

	
}
