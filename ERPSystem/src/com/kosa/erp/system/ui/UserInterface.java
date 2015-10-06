package com.kosa.erp.system.ui;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.kosa.erp.system.ERPSystemHelper;

public class UserInterface implements ActionListener {

	JFrame frame;
	JRadioButton humanRadio, materialRadio;
	JTextField nameField, valField;
	JTextField nameField2, valField2;
	JLabel nameLabel, valLabel;
	JLabel nameLabel2, valLabel2;

	public UserInterface(String title) {
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				ERPSystemHelper.load();
			}

			@Override
			public void windowIconified(WindowEvent e) {
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {
				ERPSystemHelper.save();
			}

			@Override
			public void windowClosed(WindowEvent e) {
			}

			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		initUI();
	}

	private void initUI() {
		Container con = frame.getContentPane();
		con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

		// ���� ��ư �����̳�
		Container radioCon = new Container();
		radioCon.setLayout(new FlowLayout());

		humanRadio = new JRadioButton("Human");
		humanRadio.setSelected(true);
		humanRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameLabel.setText("�̸�:");
				valLabel.setText("�޿�:");
				nameLabel2.setText("�̸�:");
				valLabel2.setText("�޿�:");
			}
		});
		materialRadio = new JRadioButton("Material");
		materialRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameLabel.setText("ǰ��:");
				valLabel.setText("����:");
				nameLabel2.setText("ǰ��:");
				valLabel2.setText("����:");
			}
		});

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(humanRadio);
		radioGroup.add(materialRadio);

		radioCon.add(humanRadio);
		radioCon.add(materialRadio);

		con.add(radioCon);

		// �ι�° �̸� �Է� â
		{
			Container nameCon = new Container();
			nameCon.setLayout(new FlowLayout());

			nameLabel = new JLabel("�̸�:");
			nameCon.add(nameLabel);

			nameField = new JTextField(20);
			nameCon.add(nameField);

			con.add(nameCon);

			// ����° �޿� �Է� â
			Container valCon = new Container();
			valCon.setLayout(new FlowLayout());

			valLabel = new JLabel("�޿�:");
			valCon.add(valLabel);

			valField = new JTextField(20);
			valCon.add(valField);

			con.add(valCon);
		}
		
		{
			Container nameCon = new Container();
			nameCon.setLayout(new FlowLayout());

			nameLabel2 = new JLabel("�̸�:");
			nameCon.add(nameLabel2);

			nameField2 = new JTextField(20);
			nameCon.add(nameField2);

			con.add(nameCon);

			// ����° �޿� �Է� â
			Container valCon = new Container();
			valCon.setLayout(new FlowLayout());

			valLabel2 = new JLabel("�޿�:");
			valCon.add(valLabel2);

			valField2 = new JTextField(20);
			valCon.add(valField2);

			con.add(valCon);
		}

		// �׹�° ��ư â
		Container buttonCon = new Container();
		buttonCon.setLayout(new FlowLayout());

		JButton addButton = new JButton("Add");
		addButton.addActionListener(this);
		buttonCon.add(addButton);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		buttonCon.add(deleteButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(this);
		buttonCon.add(updateButton);

		JButton reportButton = new JButton("Report");
		reportButton.addActionListener(this);
		buttonCon.add(reportButton);

		con.add(buttonCon);
	}

	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Add")) {
			try {
				// ����̳� �����̳�?
				boolean isHuman = humanRadio.isSelected();
				// �̸� ��������
				String name = nameField.getText();
				if (name.isEmpty())
					throw new Exception();
				// �޿��� ��������
				String valStr = valField.getText();
				// �޿��� double ��ȯ
				double val = Double.parseDouble(valStr);
				// ���۷� �߰�
				if (isHuman)
					ERPSystemHelper.addHuman(name, val);
				else
					ERPSystemHelper.addMaterial(name, val);
				// �����
				nameField.setText("");
				valField.setText("");
			} catch (Exception e2) {
				JDialog dialog = new JDialog(frame);
				dialog.setTitle("����");
				dialog.getContentPane().add(new JLabel("������ �����ϴ�."));
				dialog.pack();
				dialog.setVisible(true);
			}
			
		} else if (cmd.equals("Update")) {
			try {
				// �̸� ��������
				String name = nameField.getText();
				if (name.isEmpty())
					throw new Exception();
				String name2 = nameField2.getText();
				if (name2.isEmpty())
					throw new Exception();
				// �޿��� ��������
				String valStr = valField2.getText();
				// �޿��� double ��ȯ
				double val = Double.parseDouble(valStr);
				// ���۷� ������Ʈ
				ERPSystemHelper.update(name, name2, val);
				// �����
				nameField.setText("");
				valField.setText("");
				nameField2.setText("");
				valField2.setText("");
			} catch (Exception e2) {
				JDialog dialog = new JDialog(frame);
				dialog.setTitle("����");
				dialog.getContentPane().add(new JLabel("������ �����ϴ�."));
				dialog.pack();
				dialog.setVisible(true);
			}

		} else if (cmd.equals("Delete")) {
			try {
				// �̸� ��������
				String name = nameField.getText();
				if (name.isEmpty())
					throw new Exception();
				// ���۷� ����
				ERPSystemHelper.delete(name);
				// �����
				nameField.setText("");
				valField.setText("");
			} catch (Exception e2) {
				JDialog dialog = new JDialog(frame);
				dialog.setTitle("����");
				dialog.getContentPane().add(new JLabel("������ �����ϴ�."));
				dialog.pack();
				dialog.setVisible(true);
			}
		} else if (cmd.equals("Report")) {
			JDialog dialog = new JDialog(frame);
			dialog.setTitle("Report");
			dialog.getContentPane().add(new JTextArea(ERPSystemHelper.getReportText()));
			dialog.pack();
			dialog.setVisible(true);
		}
	}
}
