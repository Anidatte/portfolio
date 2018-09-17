package com.json.allegro;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class progressWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JProgressBar progressBar;
	/**
	 * Launch the application.
	 */
	public static void start(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					progressWindow frame = new progressWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public progressWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblParsowanieHtml = new JLabel("Parsowanie HTML...");
		contentPane.add(lblParsowanieHtml, BorderLayout.NORTH);
		
		progressBar = new JProgressBar();
		contentPane.add(progressBar, BorderLayout.CENTER);
	}
	
	public void setProgress(int c, int m)
	{
		progressBar.setValue(c);
		progressBar.setMaximum(m);
	}

}
