import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.protobuf.ResultProto.CourseMarks;
import com.google.protobuf.ResultProto.Result;
import com.google.protobuf.ResultProto.Student;

public class MStudent
{
	public static void main(String[] args) throws  JSONException, IOException ,FileNotFoundException
    {
    	String file1=args[2];
    	if(args[0].equals("-s") && args[1].equals("-j"))
    		serializeJSON(file1);
    	if(args[0].equals("-d") && args[1].equals("-j"))
    		DeserializeJSON(file1);
    	if(args[0].equals("-s") && args[1].equals("-p"))
    		serializePROTO(file1);
    	if(args[0].equals("-d") && args[1].equals("-p"))
    		DeserializePROTO(file1);
    	if(args[0].equals("-t") && args[1].equals("-j"))
    		MetricMeasurementJSON(file1);
    	if(args[0].equals("-t") && args[1].equals("-p"))
    		MetricMeasurementPROTO(file1);
    }
	
    public static void serializeJSON(String fileName) throws IOException,JSONException
    {
    	Vector<String> datavec = new Vector<>();
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter("\n");
        String[] temp;
        FileWriter writer=null;
        try {
			writer = new FileWriter("result.json");
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        JSONArray outerjson=new JSONArray();
       
        while(scanner.hasNext())
        {	
        	 String deliminator=":";
        	 datavec.add(scanner.next());
        	 for(String value : datavec)
        	 {
        		temp=value.split(deliminator);
        		String name;
        		String rollno;
        		String score,cname;
        		JSONObject root= new JSONObject();
        		JSONObject courseObject1=new JSONObject();
        		Vector<String> cstr1=new Vector<>();
        		Vector<String> cstr2=new Vector<>();
        		for(int i =0; i < temp.length ; i++)
        		{	
        			String[] data;
        			String delim=",";
        			data=temp[i].split(delim);	
        			if(i==0)
        			{   
        				for(int l=0;l<data.length;l++)
        				{
        					if(l%2==0)
        					{	name=data[l];
        						root.put("Name",name);
        					}
        					else
        					{
        						rollno=data[l];
        						root.put("RollNo",rollno);	
        					}
        				}
        			}
        			else
        			{   
        				score=data[0];
        				cname=data[1];
        				cstr1.add(score);
        				cstr2.add(cname);
        			}
        		}
        		JSONArray courses=new JSONArray();
        		for(int k=0;k<cstr1.size();k++)
        		{
        			courseObject1=new JSONObject();
        			String s2=cstr1.elementAt(k);
        			courseObject1.put("CourseName",s2);
        			String s1=cstr2.elementAt(k);
        			courseObject1.put("CourseScore", s1);
        			courses.put(courseObject1);
        		}
        		
        		root.put("CourseMarks",courses);
 			
        			outerjson.put(root);
        		cstr1.clear();
         		cstr2.clear();
        	 }
        	 datavec.clear();
        }
        scanner.close();
        //System.out.print(outerjson);
        try{
		writer.write(outerjson.toString());
		writer.close();
		

	} catch (IOException e) {
		e.printStackTrace();
	}
    } 
    
    public static void DeserializeJSON(String filename) throws FileNotFoundException,JSONException,IOException
    {
        Scanner nscanner = new Scanner(new File(filename));   
        String line=nscanner.next();
        nscanner.close();
        JSONArray jsonArray=new JSONArray(line);
        //System.out.print(jsonArray);
        nscanner.close();
        FileWriter wr =null;
        try {
			wr = new FileWriter("output_json.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        for(int i=0;i<jsonArray.length();i++)
          {
        	//System.out.println(jsonArray.get(i));
        	JSONObject obj = (JSONObject) jsonArray.get(i);
        	String name = (String) obj.get("Name");
        	wr.write(name+",");
        	String rollno=(String)obj.get("RollNo");
        	wr.write(rollno+":");
        	JSONArray coursemrks=(JSONArray)obj.get("CourseMarks");
        	int status=0;
        	for(int j=0;j<coursemrks.length();j++)
        	{
        		if(j==coursemrks.length()-1)
        			status=1;
        		JSONObject objD=(JSONObject) coursemrks.get(j);
        		String cname=(String)objD.get("CourseName");
        		wr.write(cname+",");
        		JSONObject objC = (JSONObject) coursemrks.get(j);
        		String cscore=(String)objC.get("CourseScore");
        		wr.write(cscore);
        		if(status==0)
        			wr.write(":");
        	}
        	wr.write("\n");
          }
       
        wr.close();
        
    }
    
    public static void serializePROTO(String fileName) throws IOException,JSONException
    {
    	Vector<String> datavec = new Vector<>();
        Scanner scanner = new Scanner(new File(fileName));
        scanner.useDelimiter("\n");
        String[] temp;
        FileOutputStream output=null;
        try {

       	 output = new FileOutputStream("result_protobuf");
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
        Result.Builder res=Result.newBuilder();
        while(scanner.hasNext())
        {	
        	 String deliminator=":";
        	 datavec.add(scanner.next());
        	 Student.Builder student=Student.newBuilder();
        	 for(String value : datavec)
        	 {	
        		temp=value.split(deliminator);
        		String name;
        		String rollno;
        		String score,cname;
                
        		Vector<String> cstr1=new Vector<>();
        		Vector<String> cstr2=new Vector<>();
        		for(int i =0; i < temp.length ; i++)
        		{	
        			String[] data;
        			String delim=",";
        			data=temp[i].split(delim);	
        			if(i==0)
        			{   
        				for(int l=0;l<data.length;l++)
        				{
        					if(l%2==0)
        					{	name=data[l];
        						student.setName(name);
        					}
        					else
        					{
        						rollno=data[l];
        						int roll=Integer.parseInt(rollno);
        						student.setRollNum(roll);	
        					}
        				}
        			}
        			else
        			{   
        				score=data[0];
        				cname=data[1];
        				cstr1.add(score);
        				cstr2.add(cname);
        			}
        		}
        		
        		for(int k=0;k<cstr1.size();k++)
        		{
        			CourseMarks.Builder coursename= CourseMarks.newBuilder();
        			String s2=cstr1.elementAt(k);
        			coursename.setName(s2);
        			String s1=cstr2.elementAt(k);
        			int scor=Integer.parseInt(s1);
        			coursename.setScore(scor);
            		student.addMarks(coursename);
     			
        		}
        		cstr1.clear();
         		cstr2.clear();
        	 }
        	 datavec.clear();
        	 res.addStudent(student);
        	 
        	 
        }
       // System.out.print(res);
        scanner.close();
        try{
        	 res.build().writeTo(output);
        	 output.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
    }


public static void DeserializePROTO(String filename) throws FileNotFoundException,JSONException,IOException
{	
	 FileWriter wr1 =null;
     try {
			wr1 = new FileWriter("output_protobuf.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
     try {
    	 Result res=Result.parseFrom(new FileInputStream(filename));
    	 //System.out.println(res);
    	 for(Student student :res.getStudentList())
    	 {	
    		 String n1=student.getName();
    		 Integer r=student.getRollNum();
    		 wr1.write(n1+",");
    		 wr1.write(r.toString());
    		 wr1.write(":");
    		 int ind=student.getMarksList().size();
    		 int lne=0;
        	 int flag=0;
    		 for(CourseMarks coursename:student.getMarksList())
    		 {
    			 if(lne==ind-1)
        			 flag=1;
    			 String m2=coursename.getName();
    			 wr1.write(m2);
    			 wr1.write(",");
    			 Integer m1=coursename.getScore();
    			 wr1.write(m1.toString());
    			 if(flag==0)
    				 wr1.write(":");
    			 lne++;
    		 }
    		 wr1.write("\n");
    		 
    	 }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
     wr1.close();
}

public static void MetricMeasurementJSON(String fileName) throws IOException,JSONException
{
	long startTime;
	long stopTime;
	long elapsedTime1=0;
	long elapsedTime2=0;
	long TotalTime=0;
	double rate;
    long filesize = getFileSize(fileName);
    System.out.println("Filesize :"+" "+filesize);
    Vector<String> datavec = new Vector<>();
    Scanner scanner = new Scanner(new File(fileName));
    scanner.useDelimiter("\n");
    String[] temp;
    FileWriter writer=null;
    try {
		writer = new FileWriter("result.json");
	} catch (IOException e1) {
		
		e1.printStackTrace();
	}
    JSONArray outerjson=new JSONArray();
    startTime = System.currentTimeMillis();
    while(scanner.hasNext())
    {	
    	 String deliminator=":";
    	 datavec.add(scanner.next());
    	 for(String value : datavec)
    	 {
    		temp=value.split(deliminator);
    		String name;
    		String rollno;
    		String score,cname;
    		JSONObject root= new JSONObject();
    		JSONObject courseObject1=new JSONObject();
    		Vector<String> cstr1=new Vector<>();
    		Vector<String> cstr2=new Vector<>();
    		for(int i =0; i < temp.length ; i++)
    		{	
    			String[] data;
    			String delim=",";
    			data=temp[i].split(delim);	
    			if(i==0)
    			{   
    				for(int l=0;l<data.length;l++)
    				{
    					if(l%2==0)
    					{	name=data[l];
    						root.put("Name",name);
    					}
    					else
    					{
    						rollno=data[l];
    						root.put("RollNo",rollno);	
    					}
    				}
    			}
    			else
    			{   
    				score=data[0];
    				cname=data[1];
    				cstr1.add(score);
    				cstr2.add(cname);
    			}
    		}
    		JSONArray courses=new JSONArray();
    		for(int k=0;k<cstr1.size();k++)
    		{
    			courseObject1=new JSONObject();
    			String s2=cstr1.elementAt(k);
    			courseObject1.put("CourseName",s2);
    			String s1=cstr2.elementAt(k);
    			courseObject1.put("CourseScore", s1);
    			courses.put(courseObject1);
    		}
    		
    		root.put("CourseMarks",courses);
    		outerjson.put(root);
    		
    		cstr1.clear();
     		cstr2.clear();
    	 }
    	 datavec.clear();
    }
    scanner.close();
    stopTime = System.currentTimeMillis();
	elapsedTime1 = stopTime - startTime;
	// rate=(double)filesize/(double)elapsedTime1;
	 System.out.println("Serialization Time taken :"+" "+elapsedTime1+"msec");
	// System.out.println("Serialization Rate"+" "+rate);
    
    try{
	writer.write(outerjson.toString());
	writer.close();
} catch (IOException e) {
	e.printStackTrace();
}
   
    Scanner nscanner = new Scanner(new File("result.json"));   
    String line=nscanner.next();
    nscanner.close();
    JSONArray jsonArray=new JSONArray(line);
    //System.out.print(jsonArray);
    nscanner.close();
    FileWriter wr =null;
    try {
		wr = new FileWriter("output_json.txt");
	} catch (IOException e1) {
		e1.printStackTrace();
	}    
    startTime = System.currentTimeMillis();
    for(int i=0;i<jsonArray.length();i++)
      {
    	//System.out.println(jsonArray.get(i));
    	JSONObject obj = (JSONObject) jsonArray.get(i);
    	String name = (String) obj.get("Name");
    	wr.write(name+",");
    	String rollno=(String)obj.get("RollNo");
    	wr.write(rollno+":");
    	JSONArray coursemrks=(JSONArray)obj.get("CourseMarks");
    	int status=0;
    	for(int j=0;j<coursemrks.length();j++)
    	{
    		if(j==coursemrks.length()-1)
    			status=1;
    		JSONObject objD=(JSONObject) coursemrks.get(j);
    		String cname=(String)objD.get("CourseName");
    		wr.write(cname+",");
    		JSONObject objC = (JSONObject) coursemrks.get(j);
    		String cscore=(String)objC.get("CourseScore");
    		wr.write(cscore);
    		if(status==0)
    			wr.write(":");
    	}
    	wr.write("\n");
      }
    wr.close();
    //System.out.print(outerjson);
    stopTime = System.currentTimeMillis();
  	elapsedTime2 = stopTime - startTime;
  	rate=(double)filesize/(double)elapsedTime2;
  	System.out.println("Deserialization Time taken :"+" "+elapsedTime2+"msec");
  	//System.out.println("Rate"+" "+rate);
  	TotalTime=elapsedTime1+elapsedTime2;
  	rate=(double)filesize/(double)TotalTime;
  	System.out.println("TotalTime:"+" "+TotalTime+"msec");
  	System.out.println("Rate :"+" "+rate+"kbps");
} 

public static void MetricMeasurementPROTO(String fileName) throws IOException,JSONException
{
	long startTime;
	long stopTime;
	long elapsedTime1=0;
	long elapsedTime2=0;
	long TotalTime=0;
	double rate;
    long filesize = getFileSize(fileName);
    System.out.println("FileSize : "+" "+filesize);
	Vector<String> datavec = new Vector<>();
    Scanner scanner = new Scanner(new File(fileName));
    scanner.useDelimiter("\n");
    String[] temp;
    FileOutputStream output=null;
    try {
   	 output = new FileOutputStream("result_protobuf");
	} catch (IOException e1) {
		e1.printStackTrace();
	}
    Result.Builder res=Result.newBuilder();
    while(scanner.hasNext())
    {	
    	 String deliminator=":";
    	 datavec.add(scanner.next());
    	 Student.Builder student=Student.newBuilder();
    	 for(String value : datavec)
    	 {	
    		temp=value.split(deliminator);
    		String name;
    		String rollno;
    		String score,cname;
            
    		Vector<String> cstr1=new Vector<>();
    		Vector<String> cstr2=new Vector<>();
    		for(int i =0; i < temp.length ; i++)
    		{	
    			String[] data;
    			String delim=",";
    			data=temp[i].split(delim);	
    			if(i==0)
    			{   
    				for(int l=0;l<data.length;l++)
    				{
    					if(l%2==0)
    					{	name=data[l];
    						student.setName(name);
    					}
    					else
    					{
    						rollno=data[l];
    						int roll=Integer.parseInt(rollno);
    						student.setRollNum(roll);	
    					}
    				}
    			}
    			else
    			{   
    				score=data[0];
    				cname=data[1];
    				cstr1.add(score);
    				cstr2.add(cname);
    			}
    		}
    		
    		for(int k=0;k<cstr1.size();k++)
    		{
    			CourseMarks.Builder coursename= CourseMarks.newBuilder();
    			String s2=cstr1.elementAt(k);
    			coursename.setName(s2);
    			String s1=cstr2.elementAt(k);
    			int scor=Integer.parseInt(s1);
    			coursename.setScore(scor);
        		student.addMarks(coursename);
    		}
    		cstr1.clear();
     		cstr2.clear();
    	 }
    	 datavec.clear();
    	 res.addStudent(student); 
    }
   // System.out.print(res);
    scanner.close();
    try{
    	 startTime = System.currentTimeMillis();
    	 res.build().writeTo(output);
    	 output.close();
    	 stopTime = System.currentTimeMillis();
    	 elapsedTime1 = stopTime - startTime;
    	// rate=(double)filesize/(double)elapsedTime1;
    	 System.out.println("Serialization Time taken :"+" "+elapsedTime1+"msec");
    	// System.out.println("Serialization Rate"+" "+rate);
} catch (IOException e) {
	e.printStackTrace();
}
    FileWriter wr1 =null;
    try {
			wr1 = new FileWriter("output_protobuf.txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    try {
     startTime = System.currentTimeMillis();
   	 Result res1=Result.parseFrom(new FileInputStream("result_protobuf"));
   	 //System.out.println(res);
   	 stopTime = System.currentTimeMillis();
   	 elapsedTime2=stopTime-startTime;
	// rate=(double)filesize/(double)elapsedTime2;
	 System.out.println("Deserialization Time taken :"+" "+elapsedTime2+"msec");
   //System.out.println("Deserialize Rate"+" "+rate);
   	 for(Student student :res1.getStudentList())
   	 {	
   		 String n1=student.getName();
   		 Integer r=student.getRollNum();
   		 wr1.write(n1+",");
   		 wr1.write(r.toString());
   		 wr1.write(":");
   		 int ind=student.getMarksList().size();
   		 int lne=0;
       	 int flag=0;
   		 for(CourseMarks coursename:student.getMarksList())
   		 {
   			 if(lne==ind-1)
       			 flag=1;
   			 String m2=coursename.getName();
   			 wr1.write(m2);
   			 wr1.write(",");
   			 Integer m1=coursename.getScore();
   			 wr1.write(m1.toString());
   			 if(flag==0)
   				 wr1.write(":");
   			 lne++;
   		 }
   		 wr1.write("\n");
   	 }
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    wr1.close();
    TotalTime=elapsedTime1+elapsedTime2;
    rate=(double)filesize/(double)TotalTime;
    System.out.println("TotalTime taken :"+" "+TotalTime+"msec");
	System.out.println("Rate :"+" "+rate+"kbps");
}

public static long getFileSize(String filename) {
    File file = new File(filename);
    if (!file.exists() || !file.isFile()) {
       System.out.println("File doesn\'t exist");
       return -1;
    }
    return file.length();
 }
}       