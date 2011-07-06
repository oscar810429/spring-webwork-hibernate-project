package com.painiu.util;

import java.util.*;

public class SummaryCase {

	//~ Static fields/initializers =============================================

	//~ Instance fields ===================================================

	//~ Constructors ====================================================

	//~ Methods =======================================================

	//~ Accessors ======================================================
	
	public static void main(String[] args) throws Exception{
		Random random=new Random();
	      CaseObject object=new CaseObject();
	      boolean result=true;
	      while(result){
	         result=object.execute(random.nextInt(1000));
	         Thread.sleep(1000);
	      }
		}

}

class CaseObject{
	 
	   private static int sleepTotalTime=0; 
	 
	   public boolean execute(int sleepTime) throws Exception{
	       System.out.println("sleep: "+sleepTime);
	       sleepTotalTime+=sleepTime;
	       Thread.sleep(sleepTime);
	       return true;
	   }
	 
	}
