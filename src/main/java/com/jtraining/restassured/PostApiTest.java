package com.jtraining.restassured;

/**
 * Hello world!
 *
 */
public class PostApiTest 
{
    public static void main( String[] args )
    {
       PostApiTest app = new PostApiTest();
//       app.checkGetApi();
       app.checkPostApi();
    }
    
    //This is test1
    public void checkGetApi() {
    	
    	throw new RuntimeException("Application is broken");
//    	 System.out.println( "Tested Get API" );
    }
    
    //This is test2
    public void checkPostApi() {
    	
   	 System.out.println( "Tested Post API" );
   }
}
