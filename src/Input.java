/* _im36 (GoldGrab app intermediate 36)

(No change for this class since _im28)

*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Input extends JFrame
{ 
    private JLabel inputJLabel; // label for...
    private JTextField inputJTextField; // text area to recieve user choice of howMany bars to supply
    private JButton submitJButton; // button to submit bar number choice    
     
    public Input()
    {
        setLayout(null);

        inputJLabel = new JLabel("Enter the number of gold bars to display (4 to 11): ");
        inputJLabel.setBounds(20, 20, 280, 20);
        add(inputJLabel); 
        
        SubmissionHandler submissionHandler = new SubmissionHandler();
        
        inputJTextField = new JTextField();
        inputJTextField.setBounds(310, 20, 30, 20);
        add(inputJTextField);     
        inputJTextField.addActionListener(submissionHandler);
        
        submitJButton = new JButton("Submit");
        submitJButton.setBounds(360, 20, 100, 20);
        add(submitJButton);         
        submitJButton.addActionListener(submissionHandler);
        
    }
    
    
    private class SubmissionHandler implements ActionListener
    {
       public void actionPerformed(ActionEvent e) 
        {                                    
            try
            {
                if(!inputJTextField.getText().equals(""))
                {    
                    int value = Integer.parseInt(inputJTextField.getText());

                    if(value >= 4 && value <= 11)
                    {
                        Launch.howMany = value;  
                        Launch.setup();
                        dispose();
                    }    
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Only numbers 4-11 are valid",
                                "Invalid Input...", JOptionPane.INFORMATION_MESSAGE);                                  
                    }    
                }
            }
            catch(NumberFormatException numberFormatException)
            {
                JOptionPane.showMessageDialog(null, "Please enter only numbers",
                        "Invalid Input...", JOptionPane.INFORMATION_MESSAGE);  
            }                                           
        } // end method actionPerformed  
    } // end inner class SubmissionHandler
    
    
    public static void main(String[] args) 
    {
        Input inputFrame = new Input();     
        inputFrame.setSize(500, 100);
        inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inputFrame.setLocationRelativeTo(null); // put frame in middle of screen (optional)
        inputFrame.setVisible(true);   
        inputFrame.setTitle("GoldGrab");
    }
    
}

