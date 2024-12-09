# Contact-book
A java Contact book that uses Swing UI

## Getting Started
Welcome to the Contact Management Application project! This guideline will help you set up and understand how to run and work with the application.

This application uses Java Swing to provide a graphical user interface (GUI) for managing contact information stored in a local file called Contacts.txt.

The main application is powered by Contacts.java as the entry point, with FrameClass.java managing the user interface logic. Together, these classes ensure seamless interaction with contact data stored in Contacts.txt.

## Requirements
Java Development Kit (JDK) 8 or later
Java IDE like Visual Studio Code, IntelliJ IDEA, or NetBeans

## File structure
/src
  - FrameClass.java
  - Contacts.java
  - Contacts.txt
README.md

## Folders & Their Functionality
src/:
Contacts.java: Acts as the main entry point to initialize the application.
FrameClass.java: Manages the GUI logic and user interaction with buttons and text fields.
Contacts.txt:
A file used to persist user contacts (saved information).
README.md:
This documentation file.

## Features
This application is divided into five parts and supports the following features:

## View Contacts:
A dynamic list displays saved contacts from Contacts.txt.
## Add Contacts:
Enter and save a new contact with their mobile number, work number, and email.
## Edit Contacts:
Modify an existing contact's information by searching and selecting it.
## Delete Contacts:
Remove any contact from the system by searching and clicking delete.
## Search Contacts:
Users can search for and view a contact's specific information.

## Dependencies
This application uses only Java's built-in libraries:

Swing (javax.swing) for UI components.
NIO (java.nio.file) for file reading and writing.
Regex (java.util.regex) for validating email and phone number formats.

