package com.cst438;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.cst438.controller.ScheduleController;
import com.cst438.controller.StudentController;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@ContextConfiguration(classes = { StudentController.class })
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest
public class JunitTestStudent {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Test
    public void createNewStudentTest() throws Exception {
        String name = "Makoto Edamura";
        String email = "medamura@csumb.edu";
        Student student = new Student();
        student.setName(name);
        student.setEmail(email);

        when(studentRepository.findByEmail(email)).thenReturn(null);
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Makoto Edamura\", \"email\":\"medamura@csumb.edu\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void holdTest() throws Exception {
        String email = "medamura@csumb.edu";
        Student student = new Student();
        student.setName("Makoto Edamura");
        student.setEmail(email);
        student.setStatusCode(0);
        student.setStatus(null);

        when(studentRepository.findByEmail(email)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/student/hold")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Makoto Edamura\", \"email\":\"medamura@csumb.edu\"}")
                .accept(MediaType.APPLICATION_JSON))
//                .param("email", email))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteHoldTest() throws Exception {
        String email = "medamura@csumb.edu";
        Student student = new Student();
        student.setName("Makoto Edamura");
        student.setEmail(email);
        student.setStatusCode(1);
        student.setStatus("Hold");

        when(studentRepository.findByEmail(email)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(post("/student/deleteHold")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Makoto Edamura\", \"email\":\"medamura@csumb.edu\"}")
                .accept(MediaType.APPLICATION_JSON))
//                .param("email", email))
                .andExpect(status().isOk());
    }
}