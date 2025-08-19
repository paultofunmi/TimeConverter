package com.britishtime.converter.ui;

public interface UserInterface {

  /**
   * Starts the user interface and begins accepting user input. This method should handle the
   * complete user interaction lifecycle until the user chooses to exit.
   */
  void start();

  /**
   * Stops the user interface and performs any necessary cleanup. This method should be called when
   * the application needs to shut down gracefully.
   */
  void stop();

  /**
   * Checks if the user interface is currently running.
   *
   * @return true if the interface is active and accepting input, false otherwise
   */
  boolean isRunning();
}
