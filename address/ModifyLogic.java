package com.address;

public class ModifyLogic {
	AddressBookInterface aBookInterface = new AddressBookDao();
	public AddressVO addressUpdate(AddressVO paVO) {
		AddressVO raVO = null;
		System.out.println("ModifyLogic addressUpdate()호출");
		raVO = aBookInterface.addressUpdate(paVO);
		return raVO;
	}

}
