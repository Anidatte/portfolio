package com.app.merger;

import java.awt.Desktop;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.json.allegro.AllegroPlatform;
import com.platform.baselinker.BLTestApi;
import com.platform.tokenutil.TokenReceiverServer;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;

public class WindowLapMerger {

	private JFrame MainJFrame;
	static ArrayList<String> dat;
	static ArrayList<LaptopSeparateData> s_dat;
	static ArrayList<LaptopSeparateData> cview;
	static DefaultListModel listModel;
	static DefaultComboBoxModel filterSetModel;
	
	JComboBox comboBox;
	JComboBox comboBox_procesor;
	JComboBox comboBox_dysk;
	JComboBox comboBox_matryca;
	JComboBox comboBox_klasa;
	JComboBox comboBox_system;
	JComboBox comboBox_gpu;
	JComboBox comboBox_sort;
	JComboBox comboBox_Allegro;
	JCheckBox chckbxPomijajKlasMatrycy;
	JCheckBox chckbxPomijajKlasObudowy;
	JList list1;
	public JProgressBar progressBar;
	JComboBox comboBox_disktype;
	
	JButton btnStart;
	JButton btnResetujFiltry;
	public JLabel lblStatus;
	
	JFileChooser fc;
	File filex;
	private JTextField textField;
	
	AllegroPlatform ap;
	BLTestApi bl;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					listModel = new DefaultListModel();
					filterSetModel = new DefaultComboBoxModel();
					WindowLapMerger window = new WindowLapMerger();
					window.MainJFrame.setVisible(true);
					window.initData();
					window.refreshAllegroData();
					window.baselinkerTest();
					TokenReceiverServer.Run(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int ReFilterAll(String fModel, String fProc, String fDisk, String fDisplay, String fSystem, String fClass, String fGraphics, String TextFilter, String fAllegro)
	{
		cview = new ArrayList<LaptopSeparateData>(s_dat);
		if(comboBox_sort.getSelectedIndex()==0)
		{
			cview.sort(LaptopSeparateData::compareToStr);
		}
		else if(comboBox_sort.getSelectedIndex()==1)
		{
			cview.sort(LaptopSeparateData::compareTo);
		}
		else if(comboBox_sort.getSelectedIndex()==2)
		{
			cview.sort(LaptopSeparateData::compareToRev);
		}
		else if(comboBox_sort.getSelectedIndex()==3)
		{
			cview.sort(LaptopSeparateData::compareToId);
		}
		
		DefaultListModel listModelnew = new DefaultListModel();
		for(int i=0; i<cview.size(); i++)
		{
				String x = cview.get(i).FullDescription;
				listModelnew.addElement(x);
		}
		list1.setModel(listModelnew);
		
		// Filter by custom search field
		
		if(!(TextFilter.trim().equals("")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = TextFilter.trim().toUpperCase();
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{		
				if(cview.get(i).Symbol.contains(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		
		// Filter list by model
		
		if(!(fModel.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fModel;
			DefaultListModel listModel1 = new DefaultListModel();
			String [] selectedModelSplitted = selectedModel.split(" ");
			String [] secondFilter;
			
			for(int i=0; i<cview.size(); i++)
			{
				int j=0;
				boolean found = true;
				while(j < selectedModelSplitted.length)
				{
					if (!(cview.get(i).Model.contains(selectedModelSplitted[j])))
					{
						found = false;
					}
					j++;
				}
				
				secondFilter = cview.get(i).Model.split(" ");
				
				if(!(selectedModelSplitted[selectedModelSplitted.length-1].equals(secondFilter[secondFilter.length-1])))
				{
					found = false;
				}
					
				if(found)
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by cpu
		
		if(!(fProc.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fProc;
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{
				if(cview.get(i).ProcessorSeries.contains(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by disk
		
		if(!(fDisk.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fDisk;
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{
				if(cview.get(i).Disk.contains(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by display
		
		if(!(fDisplay.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fDisplay;
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{
				if(cview.get(i).Display.equals(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by graphics card
		
		if(!(fGraphics.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fGraphics;
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{
				if(cview.get(i).GraphicsProcessor.contains(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by class
		
		if(!(fClass.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fClass;
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{
				if(cview.get(i).Class.contains(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
				else
				{
					// Dodatkowe warunki dla checkboxów pomijaj¹cych matrycê i obudowê
					if(chckbxPomijajKlasObudowy.isSelected() && chckbxPomijajKlasMatrycy.isSelected())
					{
						String x = cview.get(i).FullDescription;
						listModel1.addElement(x);
						cview_new.add(cview.get(i));
					}
					else if(chckbxPomijajKlasMatrycy.isSelected())
					{
						String objClass = cview.get(i).Class.substring(0, 1);
						String selClass = fClass.substring(0, 1);
						if(objClass.equals(selClass))
						{
							String x = cview.get(i).FullDescription;
							listModel1.addElement(x);
							cview_new.add(cview.get(i));
						}
					}
					else if(chckbxPomijajKlasObudowy.isSelected())
					{
						String objClassT =  cview.get(i).Class;
						String objClass = cview.get(i).Class.substring(objClassT.length()-1, objClassT.length());
						String selClass = fClass.substring(fClass.length()-1, fClass.length());
						if(objClass.equals(selClass))
						{
							String x = cview.get(i).FullDescription;
							listModel1.addElement(x);
							cview_new.add(cview.get(i));
						}
					}
					
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by system
		
		if(!(fSystem.equals("(no filter)")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			String selectedModel = fSystem;
			DefaultListModel listModel1 = new DefaultListModel();
			
			for(int i=0; i<cview.size(); i++)
			{
				if(cview.get(i).OperatingSystem.equals(selectedModel))
				{
					String x = cview.get(i).FullDescription;
					listModel1.addElement(x);
					cview_new.add(cview.get(i));
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by disk type
		
		if(comboBox_disktype.getSelectedIndex()!=0)
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			DefaultListModel listModel1 = new DefaultListModel();
			
			if(comboBox_disktype.getSelectedIndex()==1)
			{
				for(int i=0; i<cview.size(); i++)
				{
					if(cview.get(i).Disk.contains("SSD"))
					{
						String x = cview.get(i).FullDescription;
						listModel1.addElement(x);
						cview_new.add(cview.get(i));
					}
				}
			}
			else
			{
				for(int i=0; i<cview.size(); i++)
				{
					if(!(cview.get(i).Disk.contains("SSD")))
					{
						String x = cview.get(i).FullDescription;
						listModel1.addElement(x);
						cview_new.add(cview.get(i));
					}
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		// filter by allgero status
		
		if(!(fAllegro.equals("wszystkie")))
		{
			ArrayList<LaptopSeparateData> cview_new = new ArrayList<LaptopSeparateData>();
			DefaultListModel listModel1 = new DefaultListModel();
			
			if(comboBox_Allegro.getSelectedIndex()==1)
			{
				for(int i=0; i<cview.size(); i++)
				{
					if(cview.get(i).inAllegro())
					{
						String x = cview.get(i).FullDescription;
						listModel1.addElement(x);
						cview_new.add(cview.get(i));
					}
				}
			}
			else
			{
				for(int i=0; i<cview.size(); i++)
				{
					if(!(cview.get(i).inAllegro()))
					{
						String x = cview.get(i).FullDescription;
						listModel1.addElement(x);
						cview_new.add(cview.get(i));
					}
				}
			}
			list1.setModel(listModel1);
			cview = cview_new;
		}
		
		RefreshFilters(false);
		return 0;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int RefreshFilters(boolean force)
	{
		// prepare hash sets for every filter
		
		ArrayList<String> hsa = new ArrayList<String>();
		Set<String> hs_models = new HashSet<>();
		Set<String> hs_cpus = new HashSet<>();
		Set<String> hs_systems = new HashSet<>();
		Set<String> hs_classes = new HashSet<>();
		Set<String> hs_displays = new HashSet<>();
		Set<String> hs_disks = new HashSet<>();
		Set<String> hs_gpus = new HashSet<>();
		hs_models.add("(no filter)");
		hs_cpus.add("(no filter)");
		hs_systems.add("(no filter)");
		hs_classes.add("(no filter)");
		hs_displays.add("(no filter)");
		hs_disks.add("(no filter)");
		hs_gpus.add("(no filter)");
		
		// fill hash sets
		
		for(int i=0; i<cview.size(); i++)
		{
			hs_models.add(cview.get(i).Model);
			hs_cpus.add(cview.get(i).ProcessorSeries);
			hs_systems.add(cview.get(i).OperatingSystem);
			hs_classes.add(cview.get(i).Class);
			hs_displays.add(cview.get(i).Display);
			hs_disks.add(cview.get(i).Disk);
			hs_gpus.add(cview.get(i).GraphicsProcessor);
		}
		
		// Prepare models list
		
		hsa.addAll(hs_models);
		hsa.sort(String::compareToIgnoreCase);
		
		DefaultComboBoxModel cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox.getSelectedIndex()==0)
			comboBox.setModel(cmbBoxModel1);
		
		// Prepare processor series list
		
		hsa = new ArrayList<String>();
		hsa.addAll(hs_cpus);
		hsa.sort(String::compareToIgnoreCase);
		
		cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox_procesor.getSelectedIndex()==0)
			comboBox_procesor.setModel(cmbBoxModel1);
		
		// Prepare disks list
		
		hsa = new ArrayList<String>();
		hsa.addAll(hs_disks);
		hsa.sort(String::compareToIgnoreCase);
		
		cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox_dysk.getSelectedIndex()==0)
			comboBox_dysk.setModel(cmbBoxModel1);
		
		// Prepare displays list
		
		hsa = new ArrayList<String>();
		hsa.addAll(hs_displays);
		hsa.sort(String::compareToIgnoreCase);
		
		cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox_matryca.getSelectedIndex()==0)
			comboBox_matryca.setModel(cmbBoxModel1);
		
		// Prepare systems list
		
		hsa = new ArrayList<String>();
		hsa.addAll(hs_systems);
		hsa.sort(String::compareToIgnoreCase);
		
		cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox_system.getSelectedIndex()==0)
			comboBox_system.setModel(cmbBoxModel1);
		
		// Prepare classes list
		
		hsa = new ArrayList<String>();
		hsa.addAll(hs_classes);
		hsa.sort(String::compareToIgnoreCase);
		
		cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox_klasa.getSelectedIndex()==0)
			comboBox_klasa.setModel(cmbBoxModel1);
		
		// Prepare gpu list
		
		hsa = new ArrayList<String>();
		hsa.addAll(hs_gpus);
		hsa.sort(String::compareToIgnoreCase);
		
		cmbBoxModel1 = new DefaultComboBoxModel();
		for(int i=0; i<hsa.size(); i++)
		{
			cmbBoxModel1.addElement(hsa.get(i));
		}
		if(comboBox_gpu.getSelectedIndex()==0)
			comboBox_gpu.setModel(cmbBoxModel1);
		
		return 0;
	}

	/**
	 * Create the application.
	 */
	public WindowLapMerger() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		
		// Some init things
		
		MainJFrame = new JFrame();
		
		MainJFrame.setResizable(false);
		MainJFrame.setTitle("Laptop Manager");
		MainJFrame.setBounds(100, 100, 1000, 635);
		MainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainJFrame.getContentPane().setLayout(null);
		
		// Model Filter Selector
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox.setBounds(10, 15, 160, 21);
		MainJFrame.getContentPane().add(comboBox);
		
		// Processor Filter Selector

		comboBox_procesor = new JComboBox();
		comboBox_procesor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_procesor.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox_procesor.setBounds(10, 55, 160, 21);
		MainJFrame.getContentPane().add(comboBox_procesor);
		
		// Disk Filter Selector
		
		comboBox_dysk = new JComboBox();
		comboBox_dysk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_dysk.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox_dysk.setBounds(180, 15, 160, 21);
		MainJFrame.getContentPane().add(comboBox_dysk);
		
		// Class Filter Selector
		
		comboBox_klasa = new JComboBox();
		comboBox_klasa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_klasa.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox_klasa.setBounds(350, 15, 160, 21);
		MainJFrame.getContentPane().add(comboBox_klasa);
		
		// Displays Filter Selector
		
		comboBox_matryca = new JComboBox();
		comboBox_matryca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_matryca.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox_matryca.setBounds(180, 55, 160, 21);
		MainJFrame.getContentPane().add(comboBox_matryca);
		
		// System Filter Selector
		
		comboBox_system = new JComboBox();
		comboBox_system.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_system.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox_system.setBounds(350, 55, 160, 21);
		MainJFrame.getContentPane().add(comboBox_system);
		
		// System Filter Selector
		
		comboBox_gpu = new JComboBox();
		comboBox_gpu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_gpu.setModel(new DefaultComboBoxModel(new String[] {"Filter Not Set"}));
		comboBox_gpu.setBounds(520, 15, 146, 21);
		MainJFrame.getContentPane().add(comboBox_gpu);
		
		// Start Button to load data action
		
		btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				initData();
			}
		});
		btnStart.setBounds(860, 10, 114, 21);
		MainJFrame.getContentPane().add(btnStart);
		
		
		// About Button
		
		JButton btnOProgramie = new JButton("O programie");
		btnOProgramie.setBounds(860, 36, 114, 21);
		btnOProgramie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Laptop Manager Extended\n\nAutor: Micha³ Fu³at\nWersja: b180914");
			}
		});
		MainJFrame.getContentPane().add(btnOProgramie);
		
		// Exit button
		
		JButton btnWyjcie = new JButton("Wyj\u015Bcie");
		btnWyjcie.setBounds(860, 62, 114, 21);
		btnWyjcie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				MainJFrame.dispose();
				System.exit(0);
				
			}
		});
		MainJFrame.getContentPane().add(btnWyjcie);
		
		// Scroll area
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 124, 964, 427);
		MainJFrame.getContentPane().add(scrollPane);
		
		list1 = new JList();
		list1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==73)
				{
					String new_loc = JOptionPane.showInputDialog("Wpisz now¹ lokalizacjê: ");
					if(new_loc != null)
					{
						int idx = list1.getSelectedIndex();
						String sym = cview.get(idx).Symbol;
						System.out.println(sym+" / "+new_loc);
						for(int i=0; i<s_dat.size(); i++)
						{
							if(s_dat.get(i).Symbol.equals(sym))
							{
								LaptopSeparateData tmp = s_dat.get(i);
								tmp.Location = new_loc;
								tmp.RefreshDescription();
								s_dat.remove(i);
								s_dat.add(tmp);
								ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
								try {
									CloudPlatformWriter.WriteInventToGoogle(sym, new_loc);
								} catch (IOException | GeneralSecurityException e1) {
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null, "Error while logging in to Google Services.");
								}
								break;
							}
						}
					}
				}
				else if(e.getKeyCode()==10)
				{
					try {
						int idx = list1.getSelectedIndex();
						String urlAllegro = cview.get(idx).AllegroLink;
						
						if(urlAllegro.substring(urlAllegro.length()-1, urlAllegro.length()).equals("="))
						{
							JOptionPane.showMessageDialog(null, "Nie znaleziono aukcji dla tego symbolu.");
						}
						else
						{
							Desktop.getDesktop().browse(new URI(urlAllegro));
						}
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println("(Debug) Keycode: "+e.getKeyCode());
			}
		});
		list1.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] values = new String[] {"Data not loaded. Click Start to load."};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list1);
		
		// All labels in main window
		
		JLabel lblGrupa = new JLabel("Model:");
		lblGrupa.setBounds(10, 0, 45, 13);
		MainJFrame.getContentPane().add(lblGrupa);
		
		JLabel lblStan = new JLabel("Seria procesora:");
		lblStan.setBounds(10, 40, 120, 13);
		MainJFrame.getContentPane().add(lblStan);
		
		JLabel lblDyskTwardy = new JLabel("Dysk twardy:");
		lblDyskTwardy.setBounds(180, 0, 126, 13);
		MainJFrame.getContentPane().add(lblDyskTwardy);
		
		JLabel lblMatryca = new JLabel("Matryca:");
		lblMatryca.setBounds(180, 40, 126, 13);
		MainJFrame.getContentPane().add(lblMatryca);
		
		JLabel lblKlasa = new JLabel("Klasa:");
		lblKlasa.setBounds(350, 0, 45, 13);
		MainJFrame.getContentPane().add(lblKlasa);
		
		JLabel lblSystem = new JLabel("System:");
		lblSystem.setBounds(350, 40, 45, 13);
		MainJFrame.getContentPane().add(lblSystem);
		
		JLabel lblKartaGraficzna = new JLabel("Karta graficzna:");
		lblKartaGraficzna.setBounds(520, 0, 100, 13);
		MainJFrame.getContentPane().add(lblKartaGraficzna);
		
		btnResetujFiltry = new JButton("Resetuj filtry");
		btnResetujFiltry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBox.setSelectedIndex(0);
				comboBox_dysk.setSelectedIndex(0);
				comboBox_gpu.setSelectedIndex(0);
				comboBox_klasa.setSelectedIndex(0);
				comboBox_matryca.setSelectedIndex(0);
				comboBox_procesor.setSelectedIndex(0);
				comboBox_system.setSelectedIndex(0);
				comboBox_Allegro.setSelectedIndex(0);
				comboBox_sort.setSelectedIndex(0);
				chckbxPomijajKlasMatrycy.setSelected(false);
				chckbxPomijajKlasObudowy.setSelected(false);
				comboBox_disktype.setSelectedIndex(0);
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		btnResetujFiltry.setBounds(742, 10, 114, 21);
		btnResetujFiltry.setEnabled(false);
		MainJFrame.getContentPane().add(btnResetujFiltry);
		
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "Plik tekstowy", "txt", "TXT");
		fc.setFileFilter(filter);
		
		JButton btnZapiszRaport = new JButton("Zapisz raport");
		btnZapiszRaport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.showSaveDialog(MainJFrame);
				filex = fc.getSelectedFile();
				
				if(filex != null)
				{
					System.out.println(filex.getAbsolutePath());
					try {
						String report = new String();
						
						for (int i=0; i<cview.size(); i++)
						{
							report+=cview.get(i).FullDescription+System.lineSeparator();
						}
						
						File f = new File(filex.getAbsolutePath()+".txt");
						f.delete();
						try (PrintWriter out = new PrintWriter(new OutputStreamWriter( new FileOutputStream(filex.getAbsolutePath()+".txt", true ), "UTF-8" ))) {
						    out.println(report);
						}
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnZapiszRaport.setBounds(742, 36, 114, 21);
		MainJFrame.getContentPane().add(btnZapiszRaport);
		
		comboBox_sort = new JComboBox();
		comboBox_sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_sort.setModel(new DefaultComboBoxModel(new String[] {"po symbolu", "po cenie (rosn\u0105co)", "po cenie (malej\u0105co)", "po ID"}));
		comboBox_sort.setBounds(520, 55, 146, 21);
		MainJFrame.getContentPane().add(comboBox_sort);
		
		JLabel lblTrybSortowania = new JLabel("Tryb sortowania:");
		lblTrybSortowania.setBounds(520, 40, 114, 13);
		MainJFrame.getContentPane().add(lblTrybSortowania);
		
		JButton btnLoadLocations = new JButton("Login GDrive");
		btnLoadLocations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File testFile = new File("./credentials/StoredCredential");
				if(!(testFile.exists()))
				{
					try {
						CloudPlatformLoader.LoadInventFromGoogle();
					} catch (IOException | GeneralSecurityException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error while logging in to Google Services.");
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Google Services already logged on.");
				}
			}
		});
		btnLoadLocations.setBounds(742, 62, 114, 21);
		MainJFrame.getContentPane().add(btnLoadLocations);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("(Debug) Textfield action triggered.");
				textField = (JTextField) e.getSource();
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("(Debug) Textfield action triggered.");
			}
		});
		textField.setBounds(10, 95, 160, 21);
		MainJFrame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblSzukajSymbolu = new JLabel("Szukaj symbolu:");
		lblSzukajSymbolu.setBounds(10, 80, 105, 13);
		MainJFrame.getContentPane().add(lblSzukajSymbolu);
		
		JLabel lblNacinijEnterAby = new JLabel("[ENTER] Otw\u00F3rz aukcj\u0119 | [I] Zmie\u0144 inwentur\u0119");
		lblNacinijEnterAby.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNacinijEnterAby.setBounds(10, 554, 570, 21);
		MainJFrame.getContentPane().add(lblNacinijEnterAby);
		
		progressBar = new JProgressBar();
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				progressBar.repaint();
			}
		});
		progressBar.setBounds(668, 556, 306, 28);
		MainJFrame.getContentPane().add(progressBar);
		
		lblStatus = new JLabel("Status: gotowy");
		lblStatus.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblStatus.setBounds(10, 572, 648, 21);
		MainJFrame.getContentPane().add(lblStatus);
		
		comboBox_Allegro = new JComboBox();
		comboBox_Allegro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_Allegro.setModel(new DefaultComboBoxModel(new String[] {"wszystkie", "wystawiony", "niewystawiony"}));
		comboBox_Allegro.setBounds(180, 95, 160, 21);
		MainJFrame.getContentPane().add(comboBox_Allegro);
		
		JLabel lblStatusWystawienia = new JLabel("Status wystawienia:");
		lblStatusWystawienia.setBounds(180, 80, 160, 13);
		MainJFrame.getContentPane().add(lblStatusWystawienia);
		
		comboBox_disktype = new JComboBox();
		comboBox_disktype.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		comboBox_disktype.setModel(new DefaultComboBoxModel(new String[] {"wszystkie", "z dyskiem SSD", "bez dysku SSD"}));
		comboBox_disktype.setBounds(350, 95, 160, 21);
		MainJFrame.getContentPane().add(comboBox_disktype);
		
		JLabel lblTypDysku = new JLabel("Typ dysku:");
		lblTypDysku.setBounds(350, 80, 160, 13);
		MainJFrame.getContentPane().add(lblTypDysku);
		
		chckbxPomijajKlasMatrycy = new JCheckBox("pomijaj klas\u0119 matrycy");
		chckbxPomijajKlasMatrycy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		chckbxPomijajKlasMatrycy.setBounds(520, 82, 146, 21);
		MainJFrame.getContentPane().add(chckbxPomijajKlasMatrycy);
		
		chckbxPomijajKlasObudowy = new JCheckBox("pomijaj klas\u0119 obudowy");
		chckbxPomijajKlasObudowy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
			}
		});
		chckbxPomijajKlasObudowy.setBounds(520, 100, 146, 21);
		MainJFrame.getContentPane().add(chckbxPomijajKlasObudowy);
	}
	
	public void initData() {
		dat = LoadDataFromDB.process();
		DefaultListModel listModel1 = new DefaultListModel();
		ArrayList <InventData> loc = null;
		File testFile = new File("./credentials/StoredCredential");
		if(testFile.exists())
		{
			try {
				loc = CloudPlatformLoader.LoadInventFromGoogle();
				s_dat = DataSeparator.process(dat, loc);
			} catch (IOException | GeneralSecurityException e1) {
				JOptionPane.showMessageDialog(null, "Error while connecting to Google Services.");
				e1.printStackTrace();
			}
		}
		else
		{
			s_dat = DataSeparator.process(dat, new ArrayList <InventData>());
		}
		
		for(int i=0; i<s_dat.size(); i++)
		{
			String x = s_dat.get(i).FullDescription;
			listModel1.addElement(x);
		}
		list1.setModel(listModel1);
		loadCacheAllegro();
		cview = new ArrayList<LaptopSeparateData>(s_dat);
		RefreshFilters(true);
		ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
		
		btnResetujFiltry.setEnabled(true);
		btnStart.setText("Odœwie¿");
	}
	
	public void refreshAllegroData()
	{
		SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
			 
            @Override
            protected Integer doInBackground() throws Exception {
                progressBar.setIndeterminate(true);
                lblStatus.setText("Status: wyszukiwanie list ofert (API)...");
                ap = new AllegroPlatform(MainJFrame, progressBar);
				ap.ReadAuctionsListAll();
				lblStatus.setText("Status: przetwarzanie aukcji...");
				ap.ParseHTML(progressBar);
				lblStatus.setText("Status: odœwie¿anie linków...");
				refreshLinks();
				ReFilterAll((String)comboBox.getSelectedItem(), (String)comboBox_procesor.getSelectedItem(), (String)comboBox_dysk.getSelectedItem(), (String)comboBox_matryca.getSelectedItem(), (String)comboBox_system.getSelectedItem(), (String)comboBox_klasa.getSelectedItem(), (String)comboBox_gpu.getSelectedItem(), textField.getText(), (String)comboBox_Allegro.getSelectedItem());
				lblStatus.setText("Status: zapisywanie pamiêci podrêcznej...");
				saveCacheAllegro();
				lblStatus.setText("Status: gotowy");
                return 0;
            }

            @Override
            protected void done() {
                try {
                    //TODO
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

       };
       worker.execute();
	}
	
	public void baselinkerTest()
	{
		SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>(){
			 
            @Override
            protected Integer doInBackground() throws Exception {
                progressBar.setIndeterminate(true);
                lblStatus.setText("Status: wyszukiwanie list ofert (API)...");
                bl = new BLTestApi();
				bl.RunTest();
				lblStatus.setText("Status: gotowy");
                return 0;
            }

            @Override
            protected void done() {
                try {
                    //TODO
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

       };
       worker.execute();
	}
	
	public void refreshLinks()
	{
		progressBar.setMaximum(s_dat.size());
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(false);
		
		for(int i=0; i<s_dat.size(); i++) {
			
			boolean found = false;
			String currentSymbol = s_dat.get(i).Symbol;
			System.out.println("Sprawdzanie aukcji dla symbolu: "+currentSymbol);
			
			for(int j=0; j<ap.auctionsListing.size(); j++)
			{
				ArrayList<String> currentSymbolsList = ap.auctionsListing.get(j).symbolsList;
				
				for(int k=0; k<currentSymbolsList.size(); k++)
				{
					if(currentSymbol.equals(currentSymbolsList.get(k)))
					{
						found = true;
						
						// Naprawa systemu cache allegro (b180914)
						if(!(s_dat.get(i).inAllegro())) {
							s_dat.get(i).updateLink(ap.auctionsListing.get(j).nrAukcji);
						} else {
							// Nawet jeœli przedmiot jest wystawiony, to i tak aktualizujemy link, bo nie wiemy czy nadal jest na tej samej aukcji
							s_dat.get(i).removeLink();
							s_dat.get(i).updateLink(ap.auctionsListing.get(j).nrAukcji);
						}
						
						ap.auctionsListing.get(j).symbolsList.remove(k);
						
						break;
					}
				}
				if(found) { break; }
			}
			
			// TODO: fix allegro cache system!
			// Jeœli przedmiot nie zosta³ odnaleziony, usuwamy mu link! Poprawka w b180914
			if(!found) {
				s_dat.get(i).removeLink();
			}
			
			s_dat.get(i).RefreshDescription();
			progressBar.setValue(i);
		}
	}
	
	public void saveCacheAllegro() throws FileNotFoundException
	{
		String cache = "";
		File f = new File("cacheAllegro.txt");
		f.delete();
		
		for(int i=0; i<s_dat.size(); i++)
		{
			cache += s_dat.get(i).Symbol+" "+s_dat.get(i).AllegroLink+"\n";
		}
		
		try (PrintWriter out = new PrintWriter("cacheAllegro.txt")) {
		    out.println(cache);
		}
	}
	
	public void loadCacheAllegro()
	{
		String fileName = "cacheAllegro.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			String[] splitted;
			while ((line = br.readLine()) != null) {
				splitted = line.split(" ");
				for(int i=0; i<s_dat.size(); i++)
				{
					if(s_dat.get(i).Symbol.equals(splitted[0]))
					{
						s_dat.get(i).replaceLink(splitted[splitted.length-1]);
						s_dat.get(i).RefreshDescription();
						break;
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}