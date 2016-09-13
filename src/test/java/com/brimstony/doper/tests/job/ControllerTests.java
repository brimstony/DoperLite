package com.brimstony.doper.tests.job;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import com.brimstony.doper.dto.Sequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by tbrim on 9/8/2016.
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup(){

    }

    @Test
    public void registerJob() throws Exception {
        this.mockMvc.perform(post("/registerJob/").contentType(MediaType.APPLICATION_JSON).content("1554")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        this.mockMvc.perform(post("/registerJob/").contentType(MediaType.APPLICATION_JSON).content("1555")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2));
    }

    @Test
    public void updateJob() throws Exception {
        this.mockMvc.perform(post("/updateJob/").contentType(MediaType.APPLICATION_JSON).content("{\"jobId\": 1, \"progress\":1}")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("1"));

        this.mockMvc.perform(post("/updateJob/").contentType(MediaType.APPLICATION_JSON).content("{\"jobId\": 1, \"progress\":1}")).andDo(print()).andExpect(status().isOk()).andExpect(content().string("2"));

    }

    @Test
    public void viewJob() throws Exception {
        this.mockMvc.perform(get("/job/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.progress").value(2));
        this.mockMvc.perform(get("/job/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2)).andExpect(jsonPath("$.progress").value(0));
    }

    @Test
    public void viewAllJobs() throws Exception {
        this.mockMvc.perform(get("/jobs/")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].progress").value(2));
    }
}
