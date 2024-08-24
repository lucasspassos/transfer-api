package com.example.transfer_api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    @InjectMocks
    private Util util;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ObjectWriter objectWriter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testParseToTypeSuccess() throws Exception {
        String json = "{\"name\":\"John\",\"age\":30}";
        Person person = new Person("John", 30);

        when(objectWriter.writeValueAsString(person)).thenReturn(json);
        when(objectMapper.readValue(json, Person.class)).thenReturn(person);

        Person result = util.parseToType(person, Person.class);

        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals(30, result.getAge());
    }

    @Test
    void testParseToTypeNullObject() {
        Person result = util.parseToType(null, Person.class);

        assertNull(result);
    }

    @Test
    void testParseToTypeException() throws Exception {
        String json = "{\"name\":\"John\",\"age\":30}";

        when(objectWriter.writeValueAsString(any())).thenThrow(new RuntimeException("Serialization error"));

        Person result = util.parseToType(new Person("John", 30), Person.class);

        assertNull(result);
    }

    private static class Person {
        private String name;
        private int age;

        public Person() { }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
