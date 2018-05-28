package EShop;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Kasse extends JFrame
{
	private double summe;
	
	private JLabel lblSumme;
	private JLabel lblKunde;
	private JLabel lblVorname;
	private JLabel lblNachname;
	private Kunde kunde;
	
	private JButton kaufen;
	private JButton abbrechen;
	
	public Kasse(Kunde kunde,double summe)
	{
		this.kunde = kunde;
		this.summe = summe;
		setVisible(true);
		setTitle("Kasse");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		lblSumme = new JLabel();
		lblSumme.setText("Summe: "+summe);
		add(createControlPanel(), BorderLayout.NORTH);
		add(createData(), BorderLayout.CENTER);
		add(createButtons(), BorderLayout.SOUTH);
	}

	private JComponent createButtons() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		
		abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		c.gridx = 0;
		c.gridy = 0;
		panel.add(abbrechen,c);
		
		kaufen = new JButton("Kaufen");
		kaufen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(kunde.getKontostand()>=summe) {
				JOptionPane.showMessageDialog(Kasse.this,"Ihre Bestellung ist erfolgreich", "Bestätigung",JOptionPane.INFORMATION_MESSAGE);
				dispose();}
				else {
					JOptionPane.showMessageDialog(Kasse.this,"Sie haben nicht genug Geld", "Hinweis",JOptionPane.ERROR_MESSAGE);
					dispose();
				}
			}
		});
		c.gridx = 1;
		c.gridy = 0;
		panel.add(kaufen,c);
		return panel;
	}

	private JComponent createData() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		lblKunde = new JLabel("Kunde: ");
		c.gridx = 0;
		c.gridy = 0;
		panel.add(lblKunde,c);
		
		lblVorname = new JLabel();
		lblVorname.setText(((Kunde) kunde).getVorname());
		c.gridx = 1;
		c.gridy = 0;
		panel.add(lblVorname,c);
		
		lblNachname = new JLabel();
		lblNachname.setText(((Kunde) kunde).getNachname());
		c.gridx = 2;
		c.gridy = 0;
		panel.add(lblNachname,c);
		return panel;
	}

	private JComponent createControlPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		lblSumme = new JLabel();
		lblSumme.setText("Summe: "+summe+ " €");
		panel.add(lblSumme,c);
		return panel;
	}
}
