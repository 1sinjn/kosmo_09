package com.address;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SubBook extends JDialog implements ActionListener{
	//////////////입력 화면 추가하기 시작///////////////////
	private JLabel labelId;//
	private JTextField txtId;//이름을 입력하는 컴포넌트
	private JLabel labelName;//이름라벨
	private JTextField txtName;//이름을 입력하는 컴포넌트
	private JLabel labelAddr;
	private JTextField txtAddress;
	private JLabel labelHp;
	private JTextField txtHp;
	private JLabel labelGender;
	private JComboBox comboGender;
	private JLabel labelBirth;
	private JTextField txtBirthDay;
	private JLabel labelComment;
	private JTextArea txtComment;
	private JLabel labelRegDate;
	private JTextField txtRegDate;
	private JScrollPane scrollPane;
	private JScrollPane scrollComment;
	//////////////입력 화면 추가하기 끝///////////////////
	Font font = new Font("돋움체",Font.PLAIN,16);
	JPanel jp_center = new JPanel();//입력에 필요한 화면을 배치(좌표값 배치)
	JPanel jp_south = new JPanel();
	JButton jbtn_save = new JButton("저장");
	JButton jbtn_cancel = new JButton("취소");
	
	AddressBook aBook = null; 
	String title = null;
	AddressVO aVO = null;
	public SubBook() {	
	  initDisplay();	
	}
	

/**********************************************
 * 부모창에서 결정된 값(입력, aVO)
 * @param aVO 입력버튼을 부모창에서 눌렀을 땐 null,수정일땐 aVO는 db에서 읽어온 값을
 *        담고있다. 
 * @param title - 부모창에서 선택한 버튼의 라벨담기
 * @param aBook - 부모창(AddressBook)의 원본 주소번지를 담는 변수
 * @param isEdit 자식창(SubBook)에서 입력콤포넌트를 활성화 시키기 위한 값
 * true : 수정가능 fulse : 수정불가. 라고 써줄수 있다..
 */
	
	public void set(AddressVO aVO, String title, AddressBook aBook, boolean isEdit) {
		this.aVO = aVO;
		this.title = title;
		this.aBook = aBook;
		this.setValue();
		this.setTitle(title);
		this.setVisible(true);
		this.setEditable(isEdit);
	}
	public void initDisplay() {
		//jp_center속지에 레이아웃을 초기화하자.
		jp_center.setLayout(null);//FlowLayout 레이아웃 뭉개 놓고
		////////////////////화면 객체 생성하기 시작/////////////
		labelName = new JLabel("이름(필수입력) ");
		labelAddr = new JLabel("주소 ");
		labelHp = new JLabel("전화번호 ");
		labelId = new JLabel("관계 ");
		labelGender = new JLabel("성별 ");
		labelBirth = new JLabel("생일(YYYYMMDD) ");
		labelComment = new JLabel("비고 ");
		labelRegDate = new JLabel("수정일 ");

		labelName.setFont(font);
		labelAddr.setFont(font);
		labelHp.setFont(font);
		labelId.setFont(font);
		labelGender.setFont(font);
		labelBirth.setFont(font);
		labelComment.setFont(font);
		labelRegDate.setFont(font);

		// 데이터를 보여줄 텍스트 필드등을 정의합니다.
		txtName = new JTextField(20);
		txtName.setEditable(false);
		txtAddress = new JTextField(20);
		txtHp = new JTextField(20);
		//id
		txtId = new JTextField(20);
		txtBirthDay = new JTextField(20);
		txtComment = new JTextArea(3, 20);
		scrollComment = new JScrollPane(txtComment);
		txtRegDate = new JTextField(20);

		String [] genderList= {"남자", "여자"};
		comboGender = new JComboBox(genderList);		
		////////////////////화면 객체 생성하기 끝/////////////
		////////////////////화면 객체 배치하기 시작/////////////
		labelName.setBounds(20,20, 100,20);
		txtName.setBounds(120,20, 150,20);

		labelAddr.setBounds(20, 45, 100,20);
		txtAddress.setBounds(120,45, 150,20);

		labelHp.setBounds(20,70, 100,20);
		txtHp.setBounds(120,70, 150, 20);

		labelId.setBounds(20,95, 100,20);
		txtId.setBounds(120,95, 150,20);

		labelGender.setBounds(20,120, 100,20);
		comboGender.setBounds(120, 120, 150,20);
  		comboGender.setFont(new java.awt.Font("굴림", 0, 12));

		labelBirth.setBounds(20,145, 100,20);
		txtBirthDay.setBounds(120,145, 150,20);

		labelComment.setBounds(20, 170, 100,20);
		scrollComment.setBounds(120,170, 250,60);

		labelRegDate.setBounds(20, 235, 100,20);
		txtRegDate.setBounds(120,235, 150,20);
		txtRegDate.setEditable(false);

		// 컴포넌트들을 패널에 붙입니다.
		jp_center.add(labelName);
		jp_center.add(txtName);
		jp_center.add(labelAddr);
		jp_center.add(txtAddress);
		jp_center.add(labelHp);
		jp_center.add(txtHp);
		jp_center.add(labelId);
		jp_center.add(txtId);
		jp_center.add(labelGender);
		jp_center.add(comboGender);
		jp_center.add(labelBirth);
		jp_center.add(txtBirthDay);
		jp_center.add(labelComment);
		jp_center.add(scrollComment);
		jp_center.add(labelRegDate);
		jp_center.add(txtRegDate);
		////////////////////화면 객체 배치하기 끝/////////////
		jbtn_save.addActionListener(this);
		jbtn_cancel.addActionListener(this);
		this.setLayout(new BorderLayout());
		jp_south.add(jbtn_save);
		jp_south.add(jbtn_cancel);
		this.add("South",jp_south);
		this.add("Center",jp_center);
		//자식창의 제목은 세가지 중 한가지가 되어야 함.
		//하나의 화면을 가지고 세가지 기능을 어떻게 처리하지?
		this.setTitle("입력|수정|상세조회");
		this.setSize(500,500);
		this.setVisible(false);//false메모리상 상주는 일어나고 있지만, 화면상에만 보여지지 않은 상태false, 입력 눌렀을때 true로..
	}///////////////////////end of initDisplay
	public void setValue() {
		JOptionPane.showMessageDialog(aBook, "aVO:"+aVO);
		//입력일 때
		if(aVO==null) {
			setId("");
			setName("");
			setHp("");
			setGender("1");
			setComment("");
			setAddr("");
			setBirth("");
		}
		//상세조회나 수정시는 aVO에 있는 값으로 각 콤포넌트(txtId, txtName..)를 초기화한다.
		else {
			//setId는 화면에 값을 출력, aVO.getId()-DB에서 가져온 값
			JOptionPane.showMessageDialog(aBook, "aVO:"+aVO.getId());
			setId(aVO.getId());
			setName(aVO.getName());
			setHp(aVO.getHp());
			setGender(aVO.getGender());
			setAddr(aVO.getAddress());
			setComment(aVO.getComments());
			setBirth(aVO.getBirthday());
			setRegDate(aVO.getRegdate());
		}
	}
	//입력받는 컴포넌트의 활성화 혹은 비활성화에 대한 처리
		public void setEditable(boolean isEdit) {
			txtName.setEditable(isEdit);
			txtAddress.setEditable(isEdit);
			txtHp.setEditable(isEdit);
			txtId.setEditable(isEdit);
			txtBirthDay.setEditable(isEdit);
			txtComment.setEditable(isEdit);
		}
//↓↓↓↓↓↓화면에서 입력받은 값 혹은 화면에 출력한 값 처리하는 getter/setter 정의해보기.////////
		/* 각 컬럼의 값들을 설정하거나 읽어오는 메소드 구현하기 */
		public String getId() {
			return txtId.getText();
		}
		
		public void setId(String id) {
			txtId.setText(id);
		}
		
		public String getName() {
			return txtName.getText();
		}
		
		public void setName(String name) {
			txtName.setText(name);
		}
		public String getAddr() {
			return txtAddress.getText();
		}
		public void setAddr(String addr) { 
			txtAddress.setText(addr);
		}
		public String getHp() {
			return txtHp.getText();
		}
		public void setHp(String hp) { 
			txtHp.setText(hp);
		}
		public String getBirth() {
			return txtBirthDay.getText();
		}
		public void setBirth(String birth) { 
			txtBirthDay.setText(birth);
		}
		public String getComment() {
			return txtComment.getText();
		}
		public void setComment(String comment) { 
			txtComment.setText(comment);
		}
		public String getRegDate() {
			return txtRegDate.getText();
		}
		public void setRegDate(String regdate) { 
			txtName.setText(regdate);
		}
		public String getGender() {
			if(comboGender.getSelectedItem().equals("남자")) return "1";
			else return "0";
		}
		public void setGender(String gender) {
		if(gender.equals("1")) comboGender.setSelectedItem("남자");
		else comboGender.setSelectedItem("여자");
	}
	/*
	public static void main(String[] args) {
		SubBook sBook = new SubBook();
		sBook.initDisplay();
	}
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		if("저장".equals(label)) {
			//dispose는 창만 닫게 해줄뿐 메모리에 대해서까지 영향력이 없다.
			//aVO에는 select한 정보가 담겨 있고
			//paVO에는 화면에서 새로 입력한 정보가 담겨 있음
			if(aVO !=null) {//수정인 경우
			  try {
				  AddressVO paVO = new AddressVO();
				  paVO.setCommand("update");
				  paVO.setId(aVO.getId());
				  paVO.setName(aVO.getName());
				  paVO.setAddress(aVO.getAddress());
				  paVO.setHp(aVO.getHp());
				  paVO.setBirthday(aVO.getBirthday());
				  paVO.setComments(aVO.getComments());
				  paVO.setGender(aVO.getGender());
				  AddressBookCtrl aCtrl = new AddressBookCtrl();
				  aCtrl.send(paVO);
				
			} catch (Exception e2) {
				// TODO: handle exception
			}	
				
			}else {//입력인 경우
				JOptionPane.showMessageDialog(aBook, "입력요청");
				try {
					AddressVO paVO = new AddressVO();
					paVO.setCommand("insert");
					//다이얼로그 창으로부터 입력된 값을 읽어서 paVO담기
					paVO.setId(getId());
					paVO.setName(getName());
					paVO.setHp(getHp());
					paVO.setAddress(getAddr());
					paVO.setGender(getGender());
					paVO.setBirthday(getBirth());
					paVO.setComments(getComment());
					//컨트롤 계층에 데이터 입력을 의뢰하고 입력이 성공되면
					//자식창은 닫고 부모창은 새로고침 처리한다.
					AddressBookCtrl aCtrl = new AddressBookCtrl();
					AddressVO raVO = aCtrl.send(paVO);
					//raVO는 디비에서 꺼내온 값. paVO는 사용자가 입력한 값.
					if(raVO!=null) {
						if(raVO.getStatus()==1) {//입력 성공
							JOptionPane.showMessageDialog(aBook, "입력성공");
							this.dispose();
							aBook.refreshData();
						}else {
							JOptionPane.showMessageDialog(aBook, "입력실패");
							return;
						}
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
		}
		if("취소".equals(label)) {
			//dispose는 창만 닫게 해줄뿐 메모리에 대해서까지 영향력이 없다.
			this.dispose();
			aBook.refreshData();
			
		}
		
	}
	/*
	 * public static void main(String args[]) { SubBook sBook = new SubBook();
	 * sBook.initDisplay(); }
	 */
}
