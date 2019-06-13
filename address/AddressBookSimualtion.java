package com.address;

public class AddressBookSimualtion {

	public static void main(String[] args) {
		AddressBookCtrl bookCtrl = new AddressBookCtrl();
		AddressVO paVO = new AddressVO();
		AddressVO raVO = null;
		paVO.setCommand("insert");
		paVO.setId("test");
		paVO.setName("김유신");
		paVO.setHp("01032323323");
		try {
			raVO = bookCtrl.send(paVO);
			if(raVO.getStatus()==1) {
				System.out.println("입력 성공");
			} else {
				System.out.println("입력 실패");				
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
