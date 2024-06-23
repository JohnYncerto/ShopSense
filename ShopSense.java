import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import javax.swing.*;
import java.io.*;
import java.security.SecureRandom;

public class ShopSense implements ActionListener {
    // frame variables--------------------------------------
    JFrame myFrame;
    JLabel lblCash, lblId, lblPrice, lblProduct, lblQty;
    JLabel background;
    JTextField txtCash, txtId, txtPrice, txtProduct, txtQty;
    JButton btnCancel, btnSubmit, btnView, btnDelete, btnFind, btnUpdate, btnLog;
    JPanel input, receipt;
    //-----------------------------------------------------

    // value variables--------------------------------------
    private String transactionId;
    private double cash;
    private double change;
    private String Id;
    private double price, totalPrice;
    private String product;
    private int quantity;
    //-----------------------------------------------------

    // Frame creation---------------------------------------
    public ShopSense() {
        myFrame = new JFrame("ShopSense");
        myFrame.setSize(600, 400); // Set the initial size
        myFrame.setLayout(null); 
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setResizable(false);
        
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\Admin\\eclipse-workspace\\M3_Project\\src\\Cashier bg.png"); 
        background = new JLabel(backgroundImage);
        background.setBounds(0, 0, 600, 400); 
        myFrame.add(background);
        
        lblProduct = new JLabel("Enter product name:");
        txtProduct = new JTextField(10);
        lblId = new JLabel("Enter product ID:");
        txtId = new JTextField(10);
        lblPrice = new JLabel("Enter product price:");
        txtPrice = new JTextField(10);
        lblCash = new JLabel("Enter payment amount:");
        txtCash = new JTextField(10);
        lblQty = new JLabel("Enter product quantity:");
        txtQty= new JTextField(10);

        btnCancel = new JButton("Cancel");
        btnSubmit = new JButton("Submit");
        
        btnView = new JButton("View");
        btnDelete = new JButton("Delete");
        btnUpdate = new JButton("Update");
        btnFind = new JButton("Find");
        
        input = new JPanel();
        input.setLayout(new GridLayout(6,2,5,5));
        input.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        input.setOpaque(false);
        input.setPreferredSize(new Dimension(300, 200));
        
        receipt = new JPanel();
        receipt.setLayout(new GridLayout(4,1,5,5));
        receipt.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        receipt.setOpaque(false);
        receipt.setPreferredSize(new Dimension(200, 200));

        // Add components to the panel
        input.add(lblProduct);
        input.add(txtProduct);
        input.add(lblId);
        input.add(txtId);
        input.add(lblQty);
        input.add(txtQty);
        input.add(lblPrice);
        input.add(txtPrice);
        input.add(lblCash);
        input.add(txtCash);
        input.add(btnCancel);
        input.add(btnSubmit);
        
        receipt.add(btnView);
        receipt.add(btnUpdate);
        receipt.add(btnFind);
        receipt.add(btnDelete);

        // Register action listener for buttons
        btnCancel.addActionListener(this);
        btnSubmit.addActionListener(this);
        
        btnView.addActionListener(this);
        btnDelete.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnFind.addActionListener(this);

        input.setBounds(30, 150, 300, 200);
        background.add(input); // Add input panel to background
        receipt.setBounds(350, 150, 200, 200);
        background.add(receipt); // Add receipt panel to background
        
        // Make the frame visible
        myFrame.setVisible(true);
        
    }
    //-----------------------------------------------------
    
    //Functionality----------------------------------------
    public boolean valid() {
    	String cashText = txtCash.getText().trim();
        String priceText = txtPrice.getText().trim();
        String productText = txtProduct.getText().trim();
        String idText = txtId.getText().trim();
        String QtyText = txtQty.getText().trim();
        
        if (cashText.isEmpty() || priceText.isEmpty() || productText.isEmpty() || idText.isEmpty() || QtyText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Inputs must not be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            cash = Double.parseDouble(cashText);
            price = Double.parseDouble(priceText);
            quantity = Integer.parseInt(QtyText); 
            totalPrice = price * quantity;
            
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(null, "Quantity must be greater than zero!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            } 
            if (cash > totalPrice) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Payment amount must be greater than the price!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input for cash or price!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    public double getChange() {
    	 boolean valid = false;
    	 cash = Double.parseDouble(txtCash.getText());
    	 price = Double.parseDouble(txtPrice.getText());
    	 quantity = Integer.parseInt(txtQty.getText()); 
         totalPrice = price * quantity;
    	 change = cash - totalPrice;
    	 return change;
    }  
    
    private String TransactionId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
    
    public void reciept() {
    	StringBuilder receipt = new StringBuilder();
    	product= txtProduct.getText();
    	quantity = Integer.parseInt(txtQty.getText()); 
        totalPrice = price * quantity;
    	Id = txtId.getText();
    	LocalDate currentDate = LocalDate.now();
    	transactionId = TransactionId();
    	receipt.append("=======================================================\n");
    	receipt.append("Receipt ---" + transactionId+"\n");
    	receipt.append("Date of transaction: ").append(currentDate).append("\n");
        receipt.append("Product Name: ").append(product).append("\n");
        receipt.append("Product ID: ").append(Id).append("\n");
        receipt.append("Product quantity: ").append(quantity).append("\n");
        receipt.append("Price: ").append(String.format("%.2f₱", totalPrice)).append("\n");
        receipt.append("Given Money: ").append(String.format("%.2f₱", cash)).append("\n");
        receipt.append("Change: ").append(String.format("%.2f₱", change)).append("\n");
        receipt.append("=======================================================\n");
        
        JOptionPane.showMessageDialog(null, receipt.toString(), "Receipt", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void save() {
    	 try {
 	        FileWriter writer = new FileWriter("C:\\Users\\Admin\\eclipse-workspace\\M3_Project\\src\\Log", true); // true to append to the file
 	        String product = txtProduct.getText();
 	        String Id = txtId.getText();	        
 	        LocalDate currentDate = LocalDate.now();
 	        StringBuilder tag = new StringBuilder();
 	        tag.append(transactionId).append("\t");
 	        tag.append(currentDate).append("\t");
 	        tag.append(product).append("\t");
 	        tag.append(Id).append("\t");
 	        tag.append(quantity).append("\t");
 	        tag.append(String.format("%.2f₱", totalPrice)).append("\t");
 	        tag.append(String.format("%.2f₱", cash)).append("\t");
 	        tag.append(String.format("%.2f₱", change)).append("\n");
 	        writer.write(tag.toString());
 	        writer.close(); // Close the writer after writing
 	        System.out.println("Data saved successfully.");
 	    } catch (IOException e) {
 	        System.out.println("An error occurred while saving data: " + e.getMessage());
 	        e.printStackTrace();
 	    }
    }
    
    public void view() {
    	try {
            FileReader reader = new FileReader("C:\\Users\\Admin\\eclipse-workspace\\M3_Project\\src\\Log");
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder logs = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                logs.append(line).append("\n"); 
            }          
            bufferedReader.close();
            JTextArea textArea = new JTextArea(logs.toString());
            textArea.setEditable(false); 
            textArea.setRows(20); 
            textArea.setColumns(70); 
            JScrollPane scrollPane = new JScrollPane(textArea); 
            JOptionPane.showMessageDialog(null, scrollPane, "Logs", JOptionPane.PLAIN_MESSAGE);           
        } catch (IOException e) {
            System.out.println("An error occurred while gathering data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete() {
    	try {
            String userInput = JOptionPane.showInputDialog(null, "Please enter transaction ID:");
            File file = new File("C:\\Users\\Admin\\eclipse-workspace\\M3_Project\\src\\Log");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder modifiedContent = new StringBuilder();            
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.contains(userInput)) {
                    found = true; 
                    continue; 
                }
                modifiedContent.append(line).append(System.lineSeparator());
            }
            reader.close();
            FileWriter writer = new FileWriter(file);
            writer.write(modifiedContent.toString());
            writer.close();         
            if (found) {
                JOptionPane.showMessageDialog(null, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } 	
    }
    
    public void find() {
    	try {
            String userInput = JOptionPane.showInputDialog(null, "Please enter transaction ID:");
            File file = new File("C:\\Users\\Admin\\eclipse-workspace\\M3_Project\\src\\Log");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder findResult = new StringBuilder();
            String line;
            boolean found = false;           
            while ((line = reader.readLine()) != null) {
                if (line.contains(userInput)) {
                    found = true;
                    findResult.append(line).append("\n");
                }
            }   
            reader.close();
            
            if (found) {
                JTextArea textArea = new JTextArea(findResult.toString());
                textArea.setEditable(false);
                textArea.setRows(7);
                textArea.setColumns(70);
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(null, scrollPane, "Found", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Product not found", "Found", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } 	
    }
    
    public void update() {
    	try {
            String userInput = JOptionPane.showInputDialog(null, "Please enter transaction ID:");
            File file = new File("C:\\Users\\Admin\\eclipse-workspace\\M3_Project\\src\\Log");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder modifiedContent = new StringBuilder();
            boolean found = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(userInput)) {
                    found = true;
                    String newName = JOptionPane.showInputDialog(null, "Please enter new Product name:");
                    String[] parts = line.split("\\s+"); // Split by whitespace (assuming \t or spaces)
                    String transactionId = parts[0];
                    String date = parts[1];
                    String currentProductName = parts[2]; // Assuming this is where product name is
                    String id = parts[3];
                    String quantity = parts[4];
                    String price = parts[5];
                    String payment = parts[6];
                    String change = parts[7];
                    String updatedLine = String.format("%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s\t\t%s", transactionId, date, newName, id, quantity, price, payment, change);               
                    modifiedContent.append(updatedLine).append("\n");
                } else {
                    modifiedContent.append(line).append("\n");
                }
            }
            reader.close();

            if (!found) {
                JOptionPane.showMessageDialog(null, "Transaction ID not found", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                FileWriter writer = new FileWriter(file);
                writer.write(modifiedContent.toString());
                writer.close();
                JOptionPane.showMessageDialog(null, "Product updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void clearFields() {
        txtProduct.setText(""); 
        txtId.setText(""); 
        txtPrice.setText(""); 
        txtCash.setText(""); 
        txtQty.setText("");
    }
    	
    //-----------------------------------------------------  
    
    //Main-------------------------------------------------
    public static void main(String[] args) {
        new ShopSense();
    }
    //-----------------------------------------------------  
    
    //Buttons----------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == btnCancel) {
            clearFields();
        } else if (e.getSource() == btnSubmit) {
        	if (valid() == true) {
        		getChange();
            	reciept();
            	save();
        	} 
        }else if (e.getSource()== btnView) {
        	view();
        }else if (e.getSource()== btnDelete) {
        	delete();
        }else if(e.getSource()== btnFind) {
        	find();
        }else if (e.getSource()== btnUpdate) {
        	update();
        }
        
    }
    //-----------------------------------------------------  
}
