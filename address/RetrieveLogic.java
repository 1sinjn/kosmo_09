package com.address;

import java.util.List;

/*
 * NullpointerExcveption- 인스턴스화 하지 않고 선언만 되어 있는 경우
 * 그 주소번지를 사용하여 메소드를 호출하면 바로 그때 널 포인트 익셉션 발생함
 * A a = new A();//다형성 기대할수 없다.
 * A a = new B();//다형성 기대 가능
 * A a = B.getXXX();//다형성 기대 가능
 * 1),2) 차이점은 싱글톤 기대 가능.
 * if(a=null){
 *  a = new A();
 * }
 * 
 */
public class RetrieveLogic {
	//선언부에 인터페이스 생성부에 구현체 클래스 사용 - 인스턴스화
	//스프링 프레임워크를 이용해서 MVC패턴을 적용할 때 -> 추상클래스,인터페이스 중심의 코딩 전개
	
	AddressBookInterface aDao = new AddressBookDao();
	public AddressVO addressDetail(AddressVO paVO) {
		System.out.println("리트리브로직 어드레스인서트 호출 성공");
		AddressVO raVO = null;
		raVO=aDao.getAddressDetail(paVO);
		//raVO.setStatus(1);
		return raVO;
	}
	public List<AddressVO> getAddressList() {
		// TODO Auto-generated method stub
		System.out.println("리트리브로직 어드레스인서트 호출 성공");
		List<AddressVO> list = null;
		list = aDao.getAddress();
		return list;
	}

}
