package com.address;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AddressBook extends JFrame implements ActionListener {
	SubBook subBook = null;
	//싱글톤 선언
	static AddressBook aBook = null;
	JPanel  jp_north = new JPanel();
	JButton jbtn_ins = new JButton("입력");
	JButton jbtn_upd = new JButton("수정");
	JButton jbtn_del = new JButton("삭제");
	JButton jbtn_det = new JButton("상세조회");
	//헤더 정보를 담을 객체 추가
	String cols[] = {"아이디","이름","주소","HP"};
	String data[][] = new String[0][4];
	//DataSet
	//데이터를 담을 수 있는 클래스가 필요함
	DefaultTableModel dtm_address = new DefaultTableModel(data,cols);
	JTable jt_address = new JTable(dtm_address);//화면만 제공함. 그리드만 제공. 데이터는 없다.
	JScrollPane jsp_address = new JScrollPane(jt_address);
	JTableHeader jth_address = jt_address.getTableHeader();
	//List list = new ArrayList();//선언부와 생성부가 달라야함. 왜? 구현체 클래스가 있어야하기 때문.
	//List list2 = new Vector();
	//List list3= new LinkedList();
	
	public void initDisplay() {
		
		jbtn_ins.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				insertActionPerformed(e);
			}
			
		});
		jbtn_upd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateActionPerformed(e);
			}
		});
		jbtn_det.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				detailActionPerformed(e);
			}
			
		});
		jbtn_del.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteActionPerformed(e);
				
			}
		});
		
		jp_north.setLayout(new FlowLayout());
		jp_north.add(jbtn_ins);
		jp_north.add(jbtn_upd);
		jp_north.add(jbtn_del);
		jp_north.add(jbtn_det);
		this.add("North",jp_north);
		this.add("Center",jsp_address);
		this.setSize(700,500);
		this.setVisible(true);
		jth_address.setFont(new Font("맑은고딕",Font.BOLD,18));
		jth_address.setBackground(new Color(22,22,100));
		jth_address.setForeground(Color.white);
		jth_address.setReorderingAllowed(false);
		jth_address.setResizingAllowed(false);
		jt_address.setGridColor(Color.blue);
		jt_address.getColumnModel().getColumn(0).setPreferredWidth(80);
		jt_address.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt_address.getColumnModel().getColumn(2).setPreferredWidth(390);
		jt_address.getColumnModel().getColumn(3).setPreferredWidth(130);
		jt_address.repaint();
		refreshData();
	}
	protected void deleteActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	protected void detailActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String label = e.getActionCommand();
		//이벤트 어디다 걸지? - JTable(폼,이벤트),DefaultTableModel(값을 저장,값을 입력)
		int index = jt_address.getSelectedRow();
		//로그를 출력할 때 - 주의사항
		//main메소드를 가진 클래스는 system.out.println()
		//main메소드가 없는 클래스는 JOptionPane.showMessageDialog(this,"데이터가 없습니다.")
		//System.out.println("index:"+index);
		if(index<0) {
			JOptionPane.showMessageDialog(this,"조회할 데이터를 한 건만 선택하세요.","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			try {
				jt_address.clearSelection();
				AddressVO paVO = new AddressVO();
				String u_id = (String)dtm_address.getValueAt(index, 0);
				paVO.setId(u_id);
				paVO.setCommand("detail");
				AddressBookCtrl aCtrl = new AddressBookCtrl();
				AddressVO raVO = aCtrl.send(paVO);
				//선택한 후에 상세조회 화면이 열리면 기존에 선택한 로우는 clear 처리
				jt_address.clearSelection();
				subBook = null;
				subBook = new SubBook();
				subBook.set(raVO,label,aBook,true);//null->raVO
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
				//Exception에 대한 history정보까지 출력해줌
				
			}
		}
	}
	protected void updateActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String label = e.getActionCommand();
		subBook = null;
		subBook = new SubBook();
		subBook.set(null,label,aBook,true);	
		
	}
	protected void insertActionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		subBook = null;
		subBook = new SubBook();
		subBook.set(null,label,aBook,true);	
	}
	//새로고침 처리 메소드 구현
	public void refreshData() {
		System.out.println("새로고침 처리");
		//이미 테이블에 있던 데이터는 삭제한다.
		while(dtm_address.getRowCount()>0) {
			dtm_address.removeRow(0);
		}
		AddressBookCtrl aCtrl = new AddressBookCtrl();
		List<AddressVO> list = aCtrl.send("select");
		if((list==null)||(list.size()==0)) {
			JOptionPane.showMessageDialog(this, "데이터가 없습니다.");			
		}
		else {
			for(int i=0;i<list.size();i++) {
				AddressVO raVO = list.get(i);
				//Vector를 생성한 이유는 DB에서 꺼낸값을 행 단위로 dtm_address에
				//추가할 수 있는 addRow(Vector|Object[])라는 메소드에 파라미터로 넣기 위함이다.
				Vector rowData = new Vector();
				rowData.add(0, raVO.getId());
				rowData.add(1, raVO.getName());
				rowData.add(2, raVO.getAddress());
				rowData.add(3, raVO.getHp());
				dtm_address.addRow(rowData);
			}
		}
	}
	public static void main(String[] args) {
		if(aBook==null) {
			aBook = new AddressBook();
		}
		aBook.initDisplay();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	


}
