package com.hdpack.app.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hdpack.app.model.enums.CoreSize;
import com.hdpack.app.model.enums.JobStatus;
import com.hdpack.app.model.enums.WindingDirection;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "jobs")
public class Job extends BaseEntity {

    
    @Indexed(unique = true)
    @NotBlank(message = "Job Sheet Number is required")
    private String jobSheetNo; 

    @NotBlank(message = "Job Name is required")
    private String jobName;

    @NotBlank(message = "Company Name (Client) is required")
    @Indexed 
    private String companyName;

    @NotNull(message = "Job Date is required")
    private LocalDate jobDate;

    private CoreSize coreSize;

    private WindingDirection windingDirection;

    private String adhesiveType;

    @NotNull
    @Min(1)
    private Integer totalQuantity; // Total labels to be printed.

    @NotNull
    @Min(1)
    private Integer labelsPerRoll; // Total labels will be printed on one paper sheet.

    @DBRef 
    private Paper paper;

    @DBRef
    private Punch punch;
    
    private List<JobInkDetail> inksUsed;

    private JobStatus status = JobStatus.PENDING;

    private String imagePath;

    private String notes;

	public void setImagePath(String imagePath) {
		this.imagePath=imagePath;		
	}
}