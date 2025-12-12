// Carter Rudd
// Advanced Java
// OCCC fall 2025
// PersonGUI Final Project

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

// Main GUI application for managing Person hierarchy
public class PersonGUI3 extends JFrame {
    private ArrayList<Person3> people = new ArrayList<>();
    private JComboBox<Person3> personComboBox = new JComboBox<>();
    private File currentFile = null;

    // Form fields
    private JTextField firstNameField = new JTextField(10);
    private JTextField lastNameField = new JTextField(10);
    private JTextField govIDField = new JTextField(10);
    private JTextField studentIDField = new JTextField(10);
    private JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Person", "RegisteredPerson", "OCCCPerson"});

    // Status bar
    private JLabel statusLabel = new JLabel("Ready.");

    public PersonGUI3() {
        setTitle("OCCC Person Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        setupMenu();
        setupMainPanel();
        setupStatusBar();

        setVisible(true);
    }

    // Creates File and Help menus with required options
    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open...");
        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem saveAsItem = new JMenuItem("Save As...");
        JMenuItem exitItem = new JMenuItem("Exit");

        newItem.addActionListener(e -> {
            people.clear();
            refreshComboBox();
            statusLabel.setText("New container created.");
        });

        openItem.addActionListener(e -> loadFromFile());
        saveItem.addActionListener(e -> saveToFile(currentFile));
        saveAsItem.addActionListener(e -> saveAsNewFile());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(exitItem);

        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem("About");
        helpItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "OCCC Person Manager\nFinal Project – Advanced Java Fall 2025\nCreated by Carter Rudd (CR)\nSupports Person, RegisteredPerson, OCCCPerson"));

        helpMenu.add(helpItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void setupMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top: drop-down list
        personComboBox.setPreferredSize(new Dimension(400, 30));
        panel.add(personComboBox, BorderLayout.NORTH);

        // Center: form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.add(new JLabel("Type:"));
        formPanel.add(typeCombo);
        formPanel.add(new JLabel("First Name:"));
        formPanel.add(firstNameField);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(lastNameField);
        formPanel.add(new JLabel("Government ID:"));
        formPanel.add(govIDField);
        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(studentIDField);

        panel.add(formPanel, BorderLayout.CENTER);

        // Bottom: buttons
        JPanel buttonPanel = new JPanel();
        JButton createButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton actionButton = new JButton("Perform Action");
        JButton clearButton = new JButton("Clear Form"); // NEW BUTTON

        createButton.addActionListener(e -> addPerson());
        updateButton.addActionListener(e -> editPerson());
        deleteButton.addActionListener(e -> deletePerson());
        actionButton.addActionListener(e -> performAction());
        clearButton.addActionListener(e -> clearForm()); // HOOK TO METHOD

        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(actionButton);
        buttonPanel.add(clearButton); // ADD TO PANEL

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel, BorderLayout.CENTER);
    }

    private void setupStatusBar() {
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.add(statusLabel, BorderLayout.WEST);
        add(statusPanel, BorderLayout.SOUTH);
    }

    // Refreshes drop down list after changes
    private void refreshComboBox() {
        personComboBox.removeAllItems();
        for (Person3 p : people) {
            personComboBox.addItem(p);
        }
    }

    // Clears all form fields
    private void clearForm() {
        firstNameField.setText("");
        lastNameField.setText("");
        govIDField.setText("");
        studentIDField.setText("");
        typeCombo.setSelectedIndex(0); // reset to "Person"
        statusLabel.setText("Form cleared.");
    }

    private void addPerson() {
        String type = (String) typeCombo.getSelectedItem();
        String fn = firstNameField.getText().trim();
        String ln = lastNameField.getText().trim();

        if (fn.isEmpty() || ln.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First and Last name are required.");
            return;
        }

        if (type.equals("Person")) {
            people.add(new Person3(fn, ln));
        } else if (type.equals("RegisteredPerson")) {
            String govID = govIDField.getText().trim();
            if (govID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Government ID is required.");
                return;
            }
            people.add(new RegisteredPerson3(fn, ln, govID));
        } else if (type.equals("OCCCPerson")) {
            String govID = govIDField.getText().trim();
            String studentID = studentIDField.getText().trim();
            if (govID.isEmpty() || studentID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Government ID and Student ID are required.");
                return;
            }
            people.add(new OCCCPerson3(new RegisteredPerson3(fn, ln, govID), studentID));
        }

        refreshComboBox();
        statusLabel.setText("Created " + type + ": " + ln + ", " + fn);
    }

    private void editPerson() {
        Person3 selected = (Person3) personComboBox.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "No person selected to update.");
            return;
        }

        String fn = firstNameField.getText().trim();
        String ln = lastNameField.getText().trim();
        String govID = govIDField.getText().trim();
        String studentID = studentIDField.getText().trim();
        String type = (String) typeCombo.getSelectedItem();

        // Validate required fields based on type
        if (fn.isEmpty() || ln.isEmpty()) {
            JOptionPane.showMessageDialog(this, "First and Last name are required.");
            return;
        }

        if (type.equals("Person")) {
            selected = new Person3(fn, ln);
        } else if (type.equals("RegisteredPerson")) {
            if (govID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Government ID is required.");
                return;
            }
            selected = new RegisteredPerson3(fn, ln, govID);
        } else if (type.equals("OCCCPerson")) {
            if (govID.isEmpty() || studentID.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Government ID and Student ID are required.");
                return;
            }
            selected = new OCCCPerson3(new RegisteredPerson3(fn, ln, govID), studentID);
        }

        // Replace the old object in the list with the new one
        int index = personComboBox.getSelectedIndex();
        people.set(index, selected);

        refreshComboBox();
        personComboBox.setSelectedIndex(index); // keep selection on updated person
        statusLabel.setText("Updated " + type + ": " + ln + ", " + fn);
    }

    private void deletePerson() {
        Person3 selected = (Person3) personComboBox.getSelectedItem();
        if (selected != null) {
            people.remove(selected);
            refreshComboBox();
            statusLabel.setText("Deleted " + selected);
        }
    }

    private void performAction() {
        Person3 selected = (Person3) personComboBox.getSelectedItem();
        if (selected != null) {
            selected.eat();
            selected.sleep();
            selected.play();
            selected.run();
            JOptionPane.showMessageDialog(this, "Performed actions for " + selected);
        }
    }

    // Saves current list to file
    private void saveToFile(File file) {
        if (file == null) {
            saveAsNewFile();
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(people);
            statusLabel.setText("Saved " + people.size() + " person(s).");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage());
        }
    }

    private void saveAsNewFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            saveToFile(currentFile);
        }
    }

    // Loads list from file using serialization
    private void loadFromFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentFile = chooser.getSelectedFile();
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(currentFile))) {
                people = (ArrayList<Person3>) ois.readObject();
                refreshComboBox();
                statusLabel.setText("Loaded " + people.size() + " person(s).");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Load failed: " + e.getMessage());
            }
        }
    }

    // Launches the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PersonGUI3::new);
    }
}