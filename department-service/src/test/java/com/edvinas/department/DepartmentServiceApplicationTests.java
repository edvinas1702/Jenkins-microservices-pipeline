package com.edvinas.department;

import com.edvinas.department.controller.DepartmentController;
import com.edvinas.department.entity.Department;
import com.edvinas.department.service.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DepartmentServiceApplicationTests {

//	@Test
//	void contextLoads() {
//	}

	private MockMvc mockMvc;

	@Mock
	private DepartmentService departmentService;

	@InjectMocks
	private DepartmentController departmentController;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
	}

//	private Long departmentId;
//	private String departmentName;
//	private String departmentAddress;
//	private String departmentCode;

	@Test
	public void testCreateDepartment() throws Exception {
		Department department = new Department(1L, "CS", "Vilnius", "003");
		given(departmentService.saveDepartment(department)).willReturn(department);

		mockMvc.perform(post("/departments/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(department)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentName").value("CS"));
	}


	@Test
	public void testGetDepartment() throws Exception {

		Long departmentId = 1L;
		Department newDepartment = new Department(departmentId, "CS", "Vilnius", "003");

		// Posts a new department
		mockMvc.perform(post("/departments/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(newDepartment)))
				.andExpect(status().isOk());

		// Gets the new user
		mockMvc.perform(get("/departments/{id}", departmentId)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testDeleteDepartment() throws Exception {
		Long departmentId = 1L;
		doNothing().when(departmentService).deleteDepartment(departmentId);

		mockMvc.perform(delete("/departments/{id}", departmentId))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateDepartment() throws Exception {
		Long departmentId = 1L;
		Department department = new Department(departmentId, "CS", "Vilnius", "003");

		given(departmentService.updateDepartment(departmentId, department)).willReturn(department);

		mockMvc.perform(put("/departments/{id}", departmentId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(department)))
				.andExpect(status().isOk())
				.andExpect(content().string("Department with ID " + departmentId + " updated successfully."));
	}

}
