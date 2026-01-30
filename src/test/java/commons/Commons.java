package commons;

import lombok.Data;

@Data
public class Commons {

	private static int programId  ;
	private static String programName;
	private static float batchId;
	private static String batchName;
	private static String userId;
	private static float batchId1;
	private static String batchName1;
	private static String emailId;
    private static String emailId1;
		
	public static int getProgramId() {
		return programId;
	}
	public static void setProgramId(int programId) {
		Commons.programId = programId;
	}  
	public static String getProgramName() {
		return programName;
	}
	public static void setProgramName(String programName) {
		Commons.programName = programName;
	}
	
	public static float getbatchId() {
		return batchId;
	}
	public static void setbatchId(float batchId) {
		Commons.batchId = batchId;
	}
	
	public static String getbatchName() {
		return batchName;
	}
	public static void setbatchName(String batchName) {
		Commons.batchName = batchName;
	}
    
	public static String getuserId() {
		return userId;
	}
	public static void setuserId(String userId) {
		Commons.userId = userId;
	}

   
    public static String getEmailId() {
        return emailId;
    }
    public static void setEmailId(String userLoginEmail) {
        Commons.emailId = userLoginEmail;  
    }
    
    
    public static String getUserEmailId() {
    	return emailId1;
    }
    
	public static void setUserEmailId(String getUserEmailId) {
		
		 Commons.emailId1 = getUserEmailId;
		 
	}

	public static float getBatchId1() {
        return batchId1;
    }

    public static void setBatchId1(float batchId1) {
        Commons.batchId1 = batchId1;
    }

    public static String getBatchName1() {
        return batchName1;
    }

    public static void setBatchName1(String batchName1) {
        Commons.batchName1 = batchName1;
    }
}