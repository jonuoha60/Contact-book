import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.util.regex.Pattern;

public class FrameClass extends JFrame {
    // declaing arraylist to hold names
    private ArrayList<String> contactsNames = new ArrayList<String>();
    // declaring Labels
    private JLabel selectContLabel,
            numberOfContLabel,
            mobileContLabel,
            workContLabel,
            emailContLabel;

    // declaring text fields
    private JTextField mobileContTxtF,
            workContTxtF,
            emailContTxtF;

    // declaring list and search button
    private JList namesContList = new JList();
    private JButton searchbutton = new JButton("Search");
    private JButton addbutton = new JButton("Add Contact");
    private JButton editbutton = new JButton("Edit Contact");
    private JButton deletebutton = new JButton("Delete Contact");
    private JScrollPane scrollPane = new JScrollPane(namesContList);

    public FrameClass() {
        // setting the layout
        this.setLayout(null);
        // setting a frame
        this.setTitle("Contact Application");
        // calling a method to populate a list with names
        listPopulation();

        // initializing Labels
        selectContLabel = new JLabel("Contact Book (Click to search): ");
        numberOfContLabel = new JLabel("Number of Contact: ");
        mobileContLabel = new JLabel("Name: ");
        workContLabel = new JLabel("Mobile: ");
        emailContLabel = new JLabel("Email: ");

        // declaring text fields
        mobileContTxtF = new JTextField();
        workContTxtF = new JTextField();
        emailContTxtF = new JTextField();

        // positionning the JLabel
        selectContLabel.setBounds(5, 5, 400, 30);
        numberOfContLabel.setBounds(5, 250, 200, 30);
        mobileContLabel.setBounds(240, 50, 100, 30);
        workContLabel.setBounds(240, 100, 100, 30);
        emailContLabel.setBounds(240, 150, 100, 30);
        // positioning and sizing JText Field
        mobileContTxtF.setBounds(290, 50, 150, 30);
        workContTxtF.setBounds(290, 100, 150, 30);
        emailContTxtF.setBounds(290, 150, 150, 30);
        // postionning and sizing the JList
        namesContList.setBounds(5, 50, 200, 200);
        // postionning and sizing the Search Button
        searchbutton.setBounds(5, 300, 200, 30);
        addbutton.setBounds(250, 300, 200, 30);
        editbutton.setBounds(250, 350, 200, 30);
        deletebutton.setBounds(5, 350, 200, 30);

        // adding new text to the labels
        numberOfContLabel.setText("Number of contacts: " + contactsNames.toArray().length);
        // adding actionListener to searchButton
        this.searchbutton.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // if statement to check if the name is selected
                        List<String> fileData = readFromFile();
                        if (fileData != null) {
                            for (String line : fileData) {
                                if (line.startsWith(namesContList.getSelectedValue() + " ")) { // Match full contact
                                                                                               // entry
                                    String[] parts = line.split(" ");
                                    if (parts.length >= 2) {
                                        mobileContTxtF.setText(parts[0]); // Set the mobile number
                                        workContTxtF.setText(parts[1]); // Work field
                                        emailContTxtF.setText(parts[2]); // Email field
                                    }
                                    break; // Found and populated, no need to loop further
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                                    "Please select the name from the list before clicking search");
                        }
                    }

                });
        // adding list names and propertites
        this.addbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                addContact();

            }

        });
        // editing list names and propertites
        this.editbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                editContact();
            }

        });
        // deleting list names and propertites
        this.deletebutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }

        });
        // adding the components to JFrame
        this.add(scrollPane);
        this.add(selectContLabel);
        this.add(numberOfContLabel);
        this.add(mobileContLabel);
        this.add(workContLabel);
        this.add(emailContLabel);

        // adding text fields
        this.add(mobileContTxtF);
        this.add(workContTxtF);
        this.add(emailContTxtF);

        // adding the list and a button on JFrame
        this.add(namesContList);
        this.add(searchbutton);
        this.add(editbutton);
        this.add(addbutton);
        this.add(deletebutton);

    }

    // method to read from file

    public List<String> readFromFile() {
        try {
            return Files.readAllLines(Paths.get("./Contacts.txt"));
        } catch (Exception IO) {
            JOptionPane.showMessageDialog(null, "Error: " + IO);
            return null;
        }
    }

    // method to populate the list with names
    public void listPopulation() {
        List<String> fileData = readFromFile();
        if (fileData != null) {
            for (String line : fileData) { // Iterate over each line from the file
                String name = line.split(" ")[0]; // Split the string by space and take the first part
                contactsNames.add(name);
            }
            namesContList.setListData(contactsNames.toArray());
        } else {
            JOptionPane.showMessageDialog(null, "Error reading contacts.");
        }
    }

    public class Validator {

        // Email regex pattern
        private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Phone number regex pattern (only 10-digit phone numbers)
        private static final String PHONE_REGEX = "^\\d{10}$";

        // Validate email
        public static boolean isValidEmail(String email) {
            return Pattern.matches(EMAIL_REGEX, email);
        }

        // Validate phone number
        public static boolean isValidPhoneNumber(String number) {
            return Pattern.matches(PHONE_REGEX, number);
        }
    }

    public void addContact() {
        // Get the text from the input fields
        String mobile = mobileContTxtF.getText().trim();
        String work = workContTxtF.getText().trim();
        String email = emailContTxtF.getText().trim();

        // Validate that none of the fields are empty
        if (mobile.isEmpty() || work.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields before clicking add.");
            return;
        }

        // Validate mobile number (10-digit)
        if (!Validator.isValidPhoneNumber(work)) {
            JOptionPane.showMessageDialog(null, "Invalid mobile number. Must be exactly 10 digits.");
            return;
        }

        // Validate email
        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return;
        }

        // Attempt to write to the file
        try {
            // Write data into the file, each contact as three separate lines
            String contactEntry = mobile + " " + work + " " + email + "\n";
            Files.write(Paths.get("./Contacts.txt"), contactEntry.getBytes(), java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);

            JOptionPane.showMessageDialog(null, "Contact added successfully!");

            // Clear the text fields
            mobileContTxtF.setText("");
            workContTxtF.setText("");
            emailContTxtF.setText("");

            // Refresh the contact list
            contactsNames.clear();
            listPopulation();
            numberOfContLabel.setText("Number of contacts: " + contactsNames.size());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error saving contact: " + ex.getMessage());
        }
    }

    public void editContact() {
        String mobile = mobileContTxtF.getText().trim();
        String work = workContTxtF.getText().trim();
        String email = emailContTxtF.getText().trim();

        if (mobile.isEmpty() || work.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields before clicking add.");
            return;
        }

        // Validate mobile number (10-digit)
        if (!Validator.isValidPhoneNumber(work)) {
            JOptionPane.showMessageDialog(null, "Invalid mobile number. Must be exactly 10 digits.");
            return;
        }

        // Validate email
        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return;
        }

        // Edit the file
        try {
            // Read all contacts from the file
            List<String> contacts = readFromFile();
            boolean contactFound = false;

            if (contacts != null) {
                for (int i = 0; i < contacts.size(); i++) {
                    String[] contactDetails = contacts.get(i).split(" ");
                    if (contactDetails[0].equals(mobile)) { // Match by mobile number
                        contacts.set(i, mobile + " " + work + " " + email); // Replace old data with new
                        contactFound = true;
                        break;
                    }
                }

                if (!contactFound) {
                    JOptionPane.showMessageDialog(null, "Contact not found.");
                    return;
                }

                // Write updated contacts back to the file
                Files.write(Paths.get("./Contacts.txt"), String.join("\n", contacts).getBytes(),
                        java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);

                JOptionPane.showMessageDialog(null, "Contact updated successfully!");

                // Clear the text fields
                mobileContTxtF.setText("");
                workContTxtF.setText("");
                emailContTxtF.setText("");

                // Refresh the contact list
                contactsNames.clear();
                listPopulation();
                numberOfContLabel.setText("Number of contacts: " + contactsNames.size());
            } else {
                JOptionPane.showMessageDialog(null, "No contacts found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error updating contact: " + ex.getMessage());
        }
    }

    public void deleteContact() {
        String nameToDelete = mobileContTxtF.getText().trim();
        if (nameToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the name to delete.");
            return;
        }

        try {
            List<String> contacts = readFromFile();
            if (contacts != null) {
                // Check if contact with the matching name exists and remove it
                boolean contactFound = false;
                for (int i = contacts.size() - 1; i >= 0; i--) { // Iterate backward
                    if (contacts.get(i).startsWith(nameToDelete + " ")) {
                        contacts.remove(i);
                        contactFound = true;
                    }
                }

                if (!contactFound) {
                    JOptionPane.showMessageDialog(null, "Contact not found.");
                    return;
                }

                // Write the updated contact list back to the file
                Files.write(Paths.get("./Contacts.txt"), String.join("\n", contacts).getBytes(),
                        java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);

                JOptionPane.showMessageDialog(null, "Contact deleted successfully!");

                // Clear fields and refresh the UI
                mobileContTxtF.setText("");
                workContTxtF.setText("");
                emailContTxtF.setText("");
                contactsNames.clear();
                listPopulation();
                numberOfContLabel.setText("Number of contacts: " + contactsNames.size());
            } else {
                JOptionPane.showMessageDialog(null, "No contacts to delete.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error deleting contact: " + ex.getMessage());
        }
    }

}
