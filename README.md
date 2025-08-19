# Project Description
A Command Line Program in Java that outputs the British spoken form of a time given as input. 
For example, the program will get 12:00 as input and will give noon as output

## How to RUN
- A packaged jar is available in src/main/resources directory
- You can run with java command below while pointing to the jar
  - java -jar src/main/resources/british-time-converter-1.0-SNAPSHOT.jar 

## Design Patterns Used
- Facade Pattern: Simplifying complex subsystems
- Strategy Pattern: For both parsing and speech conversion
- Chain of Responsibility: Rule processing chain
- Factory Pattern: Rule creation and assembly
- Dependency Injection: For testability

## Application Structure
- BritishTimeConverterApplication.java is the application entry point
  - found in the base package: com.britishtime.converter
- The BritishTimeConverter in core package
  - Main facade orchestrating the conversion process
- We have parsers and speech converters
  - FlexibleTimeParser found in parser package validates time string and parses it as LocalDate
    - Other time parsers can be provided by implementing TimeParser Interface
  - BritishSpeechConverter found in speech package implementation provides speech conversion using Chain of Responsibility for rules.
    - We can support other languages by implementing the SpeechConverter.
- NumberToWordConverter found in util package for extracting hour and minute names
- ConsoleInterface provides an interface for interacting with program.
  - We can provide other UI interfaces by implementing the UserInterface