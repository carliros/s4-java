package s4;

/**
 * Created by carlos on 12/18/17.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import s4.model.Student;
import s4.services.StudentService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void addStudentTest() throws Exception {
        mvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Chesher\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.studentId", is(0)));
    }

    @Test
    public void getStudentList() throws Exception {
        Student pedro = new Student();
        pedro.setId(1);
        pedro.setFirstName("Pedro");
        pedro.setLastName("Gonzales");

        List studentList = Arrays.asList(pedro);

        given(studentService.getStudents(Optional.empty(), Optional.empty(), Optional.empty())).willReturn(studentList);

        mvc.perform(get("/students").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results.students", hasSize(1)))
                .andExpect(jsonPath("$.results.students[0].firstName", is(pedro.getFirstName())));
    }
}
