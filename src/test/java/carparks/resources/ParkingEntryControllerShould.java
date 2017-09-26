package carparks.resources;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import carparks.parking.DailySummaryDto;
import carparks.parking.FeeType;
import carparks.parking.ParkingEntryDto;
import carparks.parking.ParkingService;
import carparks.parking.SessionStatus;



@RunWith(SpringRunner.class)
@WebMvcTest(ParkingEntryController.class)
public class ParkingEntryControllerShould {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ParkingService service;
	
	@Test
	public void return_parking_entry_with_get_by_plate_number() throws Exception {
		
		// given
		ParkingEntryDto entry = new ParkingEntryDto("WF12345", new BigDecimal(12), SessionStatus.FINISHED);
		when(service.getEntry(anyString(), anyString())).thenReturn(entry);
		
		// when & then
		this.mockMvc.perform(get("/platenumber/WF12345")).andExpect(status().isOk())
        .andExpect(content().json("{'plateNumber':'WF12345','fee':12.00,'status':'FINISHED'}"));
		
	}
	
	@Test
	public void return_parking_entry_with_get_by_entry_id() throws Exception {
		
		// given
		ParkingEntryDto entry = new ParkingEntryDto("WF12345", new BigDecimal(12), SessionStatus.FINISHED);
		when(service.getEntry(anyInt(), anyString())).thenReturn(entry);
		
		// when & then
		this.mockMvc.perform(get("/id/1")).andExpect(status().isOk())
        .andExpect(content().json("{'plateNumber':'WF12345','fee':12.00,'status':'FINISHED'}"));
		
	}
	
	@Test
	public void post_parking_entry_with_given_plate_number() throws Exception {
		
		// given
		ParkingEntryDto entry = new ParkingEntryDto("WF12345", new BigDecimal(12), SessionStatus.FINISHED);
		when(service.tryToCreateParkingEntry(anyString(), anyObject())).thenReturn(entry);
		
		Gson gson = new Gson();
	    String json = gson.toJson(FeeType.REGULAR);
		
		
		// when & then
		this.mockMvc.perform(post("/WF12345/parking").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json("{'plateNumber':'WF12345','fee':12.00,'status':'FINISHED'}"));
		
	}
	
	@Test
	public void finish_and_modify_parking_entry_with_given_plate_number() throws Exception {
		
		// given
		ParkingEntryDto entry = new ParkingEntryDto("WF12345", new BigDecimal(12), SessionStatus.FINISHED);
		when(service.tryToFinishParkingSession(anyString())).thenReturn(entry);
		
		Gson gson = new Gson();
	    String json = gson.toJson(FeeType.REGULAR);
		
		
		// when & then
		this.mockMvc.perform(put("/WF12345").contentType(MediaType.APPLICATION_JSON)
		.content(json)).andDo(print()).andExpect(status().isCreated())
        .andExpect(content().json("{'plateNumber':'WF12345','fee':12.00,'status':'FINISHED'}"));
		
	}

	@Test
	public void return_fee_summary_in_a_given_day() throws Exception {
		
		// given
		DailySummaryDto summary = new DailySummaryDto(new BigDecimal(12), new BigDecimal(50));
		when(service.getDailySummary(any(), anyString())).thenReturn(summary);
		
		// when & then
		this.mockMvc.perform(get("/summary/2017-09-20")).andExpect(status().isOk())
        .andExpect(content().json("{'sumRegular':12.00,'sumVip':50.00,'sum':62.00}"));
		
	}
	
	@Test
	public void return_fee_summary_today() throws Exception {
		
		// given
		DailySummaryDto summary = new DailySummaryDto(new BigDecimal(12), new BigDecimal(50));
		when(service.getDailySummary(any(), anyString())).thenReturn(summary);
		
		// when & then
		this.mockMvc.perform(get("/summary")).andExpect(status().isOk())
        .andExpect(content().json("{'sumRegular':12.00,'sumVip':50.00,'sum':62.00}"));
		
	}
}
