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

		// 라디오 버튼 컨테이너
		Container radioCon = new Container();
		radioCon.setLayout(new FlowLayout());

		humanRadio = new JRadioButton("Human");
		humanRadio.setSelected(true);
		humanRadio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameLabel.setText("이름:");
				valLabel.setText("급여:");
				nameLabel2.setText("이름:");
				valLabel2.setText("급여:");
			}
		});
		materialRadio = new JRadioButton("Material");
		materialRadio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nameLabel.setText("품명:");
				valLabel.setText("가격:");
				nameLabel2.setText("품명:");
				valLabel2.setText("가격:");
			}
		});

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(humanRadio);
		radioGroup.add(materialRadio);

		radioCon.add(humanRadio);
		radioCon.add(materialRadio);

		con.add(radioCon);

		// 두번째 이름 입력 창
		{
			Container nameCon = new Container();
			nameCon.setLayout(new FlowLayout());

			nameLabel = new JLabel("이름:");
			nameCon.add(nameLabel);

			nameField = new JTextField(20);
			nameCon.add(nameField);

			con.add(nameCon);

			// 세번째 급여 입력 창
			Container valCon = new Container();
			valCon.setLayout(new FlowLayout());

			valLabel = new JLabel("급여:");
			valCon.add(valLabel);

			valField = new JTextField(20);
			valCon.add(valField);

			con.add(valCon);
		}
		
		{
			Container nameCon = new Container();
			nameCon.setLayout(new FlowLayout());

			nameLabel2 = new JLabel("이름:");
			nameCon.add(nameLabel2);

			nameField2 = new JTextField(20);
			nameCon.add(nameField2);

			con.add(nameCon);

			// 세번째 급여 입력 창
			Container valCon = new Container();
			valCon.setLayout(new FlowLayout());

			valLabel2 = new JLabel("급여:");
			valCon.add(valLabel2);

			valField2 = new JTextField(20);
			valCon.add(valField2);

			con.add(valCon);
		}

		// 네번째 버튼 창
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
				// 사람이냐 물건이냐?
				boolean isHuman = humanRadio.isSelected();
				// 이름 가져오기
				String name = nameField.getText();
				if (name.isEmpty())
					throw new Exception();
				// 급여를 가져오기
				String valStr = valField.getText();
				// 급여를 double 변환
				double val = Double.parseDouble(valStr);
				// 헬퍼로 추가
				if (isHuman)
					ERPSystemHelper.addHuman(name, val);
				else
					ERPSystemHelper.addMaterial(name, val);
				// 지우기
				nameField.setText("");
				valField.setText("");
			} catch (Exception e2) {
				JDialog dialog = new JDialog(frame);
				dialog.setTitle("오류");
				dialog.getContentPane().add(new JLabel("에러가 났습니다."));
				dialog.pack();
				dialog.setVisible(true);
			}
			
		} else if (cmd.equals("Update")) {
			try {
				// 이름 가져오기
				String name = nameField.getText();
				if (name.isEmpty())
					throw new Exception();
				String name2 = nameField2.getText();
				if (name2.isEmpty())
					throw new Exception();
				// 급여를 가져오기
				String valStr = valField2.getText();
				// 급여를 double 변환
				double val = Double.parseDouble(valStr);
				// 헬퍼로 업데이트
				ERPSystemHelper.update(name, name2, val);
				// 지우기
				nameField.setText("");
				valField.setText("");
				nameField2.setText("");
				valField2.setText("");
			} catch (Exception e2) {
				JDialog dialog = new JDialog(frame);
				dialog.setTitle("오류");
				dialog.getContentPane().add(new JLabel("에러가 났습니다."));
				dialog.pack();
				dialog.setVisible(true);
			}

		} else if (cmd.equals("Delete")) {
			try {
				// 이름 가져오기
				String name = nameField.getText();
				if (name.isEmpty())
					throw new Exception();
				// 헬퍼로 삭제
				ERPSystemHelper.delete(name);
				// 지우기
				nameField.setText("");
				valField.setText("");
			} catch (Exception e2) {
				JDialog dialog = new JDialog(frame);
				dialog.setTitle("오류");
				dialog.getContentPane().add(new JLabel("에러가 났습니다."));
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
