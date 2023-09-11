package jsamples;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.EmptyStackException;
import java.util.Stack;

@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("A stack")
class TestingAStackDemo{
    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew(){
        new Stack<>();
    }

    @Nested
    @DisplayName("when new")
    class WhenNew{
        @BeforeEach
        void createStack(){
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty(){
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throwing EmptyStackException when popped")
        void throwingExceptionWhenPopped(){
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throwing EmptyStackException when peeked")
        void throwingExceptionWhenPeeked(){
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPush{
            String anElement = "an element";
            @BeforeEach
            void pushAnElement(){
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty(){
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped(){
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked(){
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }

    }
}


