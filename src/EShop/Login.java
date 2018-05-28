package EShop;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Login extends JPanel {

	private JTextField userField;
	private JPasswordField passField;


	public Login() {
		super(new GridLayout(2, 2));
		add(new JLabel("Benutzername: "));
		add(userField = new JTextField());
		add(new JLabel("Passwort: "));
		add(passField = new JPasswordField(""));
	}

	public String getUserName() {
		assert(userField.getText()!=null);
		if(userField.getText()==null)
		{
			JOptionPane.showMessageDialog(Login.this, "Bitte richtigen Benutzernamen eingeben");
		}
		return userField.getText();
	}

	@SuppressWarnings("deprecation")
	public String getPassword() 
	{
		String password = passField.getText();
		if(password==null)
		{
			JOptionPane.showMessageDialog(Login.this, "Bitte richtiges Passwort eingeben");
		}
		return password;
	}
}
