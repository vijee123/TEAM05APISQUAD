package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor; 
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramBatchPojo {

	
	@JsonProperty("batchDescription")
    private String batchDescription;
    @JsonProperty("batchName")
    private String batchName;
    @JsonProperty("batchNoOfClasses")
    private int batchNoOfClasses;
    @JsonProperty("batchStatus")
    private String batchStatus;
    @JsonProperty("programId")
    private int programId;
    @JsonProperty("batchId")
    private int batchId;
 

//	    public String getbatchDescriptions() {
//			return batchDescription;
//		}
//		// Setter Methods
//		public void setbatchDescription( String batchDescription ) {
//			this.batchDescription = batchDescription;
//		}
//	
//	 public String getbatchName() {
//			return batchName;
//		}
//		// Setter Methods
//		public void setbatchName( String batchName ) {
//			this.batchName = batchName;
//		}
//		
//		public int getbatchNoOfClasses() {
//			return batchNoOfClasses;
//		}
//		// Setter Methods
//		public void setbatchNoOfClasses( int batchNoOfClasses ) {
//			this.batchNoOfClasses = batchNoOfClasses;
//		}
//		
//		public int getprogramId() {
//			return programId;
//		}
//		// Setter Methods
//		public void setprogramId( int programId ) {
//			this.programId = programId;
//		}
//		
//		 public String getbatchStatus() {
//				return batchStatus;
//			}
//			// Setter Methods
//			public void setbatchStatus( String batchStatus ) {
//				this.batchStatus = batchStatus;
//			}

}





    
   


