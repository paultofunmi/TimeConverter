package com.britishtime.converter.ui.impl;

import com.britishtime.converter.core.BritishTimeConverter;
import com.britishtime.converter.ui.UserInterface;

import java.util.Scanner;

/**
 * Console-based implementation of the UserInterface.
 * Provides a command-line interface for interacting with the British Time Converter.
 */
public class ConsoleInterface implements UserInterface {

    private final BritishTimeConverter converter;
    private final Scanner scanner;
    private boolean running;

    public ConsoleInterface(BritishTimeConverter converter) {
        this.converter = converter;
        this.scanner = new Scanner(System.in);
        this.running = false;
    }

    @Override
    public void start() {
        if (running) {
            return; // Already running
        }

        running = true;
        printWelcomeMessage();

        while (running && scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();

            if (isQuitCommand(input)) {
                stop();
                break;
            }

            processInput(input);

            if (running) { // Only prompt if still running
                promptForNextInput();
            }
        }

        cleanup();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    /**
     * Prints the welcome message and initial instructions.
     */
    protected void printWelcomeMessage() {
        displayMessage("British Spoken Time Converter");
        displayMessage("Enter time in format HH:mm (e.g., 14:30) or type 'quit' to exit:");
    }

    /**
     * Processes user input and displays the result.
     *
     * @param input the user's input string
     */
    protected void processInput(String input) {
        try {
            displayConversionResult(input, converter.convert(input));
        } catch (IllegalArgumentException e) {
            displayError(e.getMessage());
        }
    }

    /**
     * Prompts the user for the next input.
     */
    protected void promptForNextInput() {
        displayMessage("Enter another time or 'quit' or 'q' or 'exit' to exit:");
    }

    /**
     * Checks if the input is a quit command.
     *
     * @param input the user input to check
     * @return true if the input is a quit command, false otherwise
     */
    protected boolean isQuitCommand(String input) {
        return "quit".equalsIgnoreCase(input) || "exit".equalsIgnoreCase(input) || "q".equalsIgnoreCase(input);
    }

    /**
     * Displays a general message to the user.
     *
     * @param message the message to display
     */
    protected void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays a conversion result to the user.
     *
     * @param input the original input
     * @param result the conversion result
     */
    protected void displayConversionResult(String input, String result) {
        System.out.printf("Input: %s -> Output: %s%n", input, result);
    }

    /**
     * Displays an error message to the user.
     *
     * @param errorMessage the error message to display
     */
    protected void displayError(String errorMessage) {
        System.out.printf("Error: %s%n", errorMessage);
    }

    /**
     * Performs cleanup operations when shutting down.
     */
    protected void cleanup() {
        scanner.close();
        displayMessage("Goodbye!");
        running = false;
    }
}
