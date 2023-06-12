package com.example.geektrust;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.UncheckedIOException;
import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest {
    @ParameterizedTest
    @NullSource
    void givenArgsIsNull_whenCallingMainMethod_thenThrowsException(String args) {
        assertThrows(NullPointerException.class, () -> Main.main(new String[]{args}));
    }

    @ParameterizedTest
    @EmptySource
    void givenArgsIsEmpty_whenCallingMainMethod_thenThrowsException(String args) {
        assertThrows(UncheckedIOException.class, () -> Main.main(new String[]{args}));
    }

    @Test
    void givenArgsIsMoreThanOne_whenCallingMainMethod_thenThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> Main.main(new String[]{"sample_input/input1.txt", "sample_input/input2.txt"}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sample_input/input99.txt"})
    void givenArgsDoesNotExist_whenCallingMainMethod_thenThrowsException(String args) {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{args}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sample_input/missing_entries.txt",
            "sample_input/missing_parameters.txt",
            "sample_input/invalid_bedroom_value.txt",
            "sample_input/invalid_ratio_value.txt",
            "sample_input/invalid_command.txt",
            "sample_input/invalid_bedroom_type.txt"})
    void givenRecordsHaveInvalidValues_whenCallingMainMethod_thenThrowsException(String args) {
        assertThrows(IllegalArgumentException.class, () -> Main.main(new String[]{args}));
    }

    @ParameterizedTest
    @ValueSource(strings = {"sample_input/input1.txt",
            "sample_input/input2.txt",
            "sample_input/input3.txt"})
    void givenFilesAreValid_whenCallingMainMethod_thenSuccessful(String args) {
        Main.main(new String[]{args});
    }
}