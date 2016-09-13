package com.brimstony.doper.tests.job;

import static org.junit.Assert.*;

import com.brimstony.doper.dto.Sequence;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by tbrim on 9/8/2016.
 */

public class SequenceTests {

    private Sequence sequence;

    @Before
    public void setUp(){
        sequence = new Sequence();
    }
    @Test
    public void sequentialTest() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(50);
        Collection<SequenceTask> taskList = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            SequenceTask worker = new SequenceTask(sequence);
            taskList.add(worker);
        }
        executor.invokeAll(taskList).forEach((task) -> {
            try {
                task.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        while (!executor.isTerminated()) {}
    }

    @After
    public void validate() throws Exception {
        assertEquals(5001, sequence.nextVal());
    }

    class SequenceTask implements Callable<Integer> {
        public SequenceTask(Sequence sequence) {
            this.sequence = sequence;
        }

        private Sequence sequence;

        public Integer call() {
            return sequence.nextVal();
        }
    }
}
