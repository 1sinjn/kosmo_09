package com.address;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgr;

public class AddressBookDao implements AddressBookInterface{
	java.sql.Connection 	con = null; //보라색은 인터페이스
	java.sql.PreparedStatement pstmt =null;
	 java.sql.ResultSet         rs      = null;
	   com.util.DBConnectionMgr    dbMgr = null;
	   
	@Override
	public AddressVO getAddressDetail(AddressVO paVO) { 
		System.out.println("DAO addressDetail 호출 성공");
		
		dbMgr = DBConnectionMgr.getInstance();
	    StringBuilder sql = new StringBuilder();	
        sql.append("SELECT id,name,address,gender,hp");
        sql.append("  ,birthday,comments,regdate    ");
        sql.append("  FROM mkaddrtb                 ");
        sql.append(" WHERE id=?" );
        AddressVO raVO = null;//널 : 우선 기다려.
        try {
			con = dbMgr.getConnection();//오라클 서버에 접속.
			pstmt = con.prepareStatement(sql.toString());//오라클 서버에게 셀렉트문으로 ..?
			pstmt.setString(1, paVO.getId());
			rs = pstmt.executeQuery();
			//rs.previous()를 쓰지 않는 이유는 오라클은 커서가 항상 (디폴트) top에 있으니까. list.
			//VO는 XXX장애를 가지고 있다. 한 행만 담을 수 있는 장애를 가지고 있음.
			if(rs.next()) {
				raVO = new AddressVO();
				raVO.setId(rs.getString("id"));
				raVO.setName(rs.getString("Name"));
				raVO.setAddress(rs.getString("Address"));
				raVO.setGender(rs.getString("Gender"));
				raVO.setBirthday(rs.getString("Birthday"));
				raVO.setHp(rs.getString("Hp"));
				raVO.setRegdate(rs.getString("Regdate"));
				raVO.setComments(rs.getString("Comments"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {//사용한 자원 반납하기. con,pstmt,rs
			dbMgr.freeConnection(con,pstmt,rs);
		}
   		return raVO;
	}

	@Override
	public AddressVO addressInsert(AddressVO paVO) {
		  System.out.println("DAO addressInsert 호출 성공");
		  AddressVO raVO = new AddressVO();
	      dbMgr = com.util.DBConnectionMgr.getInstance();
	      StringBuilder sql = new StringBuilder();//스트링쓰지말고 이거써서 문자열 붙여주세여.
	      int status =0;//0이면 입력 실패, 1이면 성공
	      try {

		sql.append(" INSERT INTO mkaddrtb(id,name,hp,gender,birthday ");
		sql.append("  ,comments,address,regdate) ");
		sql.append(" VALUES(?,?,?,?,?,?,?,TO_CHAR(sysdate,'YYYY-MM-DD')) ");
		  con = dbMgr.getConnection();
		  System.out.println("1");
		  pstmt = con.prepareStatement(sql.toString());
		  int i=0;
		  /*
		   * java.sql.SQLException : 인덱스에서 누락된 IN 또는 OUT 매개변수:: 1
		   * 원인 : PreparedStatement사용시 인데스 값 치환 누락
		   * 해결방법 : ? 자리에 대응되는 값을 설정할 것.
		   */
		  pstmt.setString(++i, paVO.getId());
		  pstmt.setString(++i, paVO.getName());
		  pstmt.setString(++i, paVO.getHp());
		  pstmt.setString(++i, paVO.getGender());
		  pstmt.setString(++i, paVO.getBirthday());
		  pstmt.setString(++i, paVO.getComments());
		  pstmt.setString(++i, paVO.getAddress());
		  //입력된 후에 오라클 서버로부터 응답 받은 값-int
		  System.out.println("2");
		  status = pstmt.executeUpdate();
		  //Dao 계층에서 처리된 결과를 리턴타입인 raVO(AddressVO)에 담자
		  
	         System.out.println("3");
	         System.out.println(status);
	         raVO.setStatus(status);//AddressVO status변수에 1저장
	      }catch(SQLException se) {//ORA-XXXXX
	         System.out.println(se.toString());
	         System.out.println(sql.toString());
	      }catch(Exception e){
	         System.out.println(e.toString());
	      }finally {
	         // TODO: handle finally clause
	      }   dbMgr.freeConnection(con,pstmt);
	      return raVO;
	   }

	@Override
	public AddressVO addressUpdate(AddressVO paVO) {
		System.out.println("DAO addressUpdate 호출 성공");
		return null;
	}

	@Override
	public AddressVO addressDelete(AddressVO paVO) {
		System.out.println("DAO addressDelete 호출 성공");
		return null;
	}

	@Override
	public List<AddressVO> address(AddressVO paVO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 public static void main(String args[]) { new
	 AddressBookDao().addressInsert(null); }

	

	@Override
	public List<AddressVO> getAddress() {
		//조회한 결과  n건을 담기 위한 객체 생성
		//테이블의 정보는 계속 변한다.-배열은 변하지 않기 때문에 사용x
		List<AddressVO> list = new ArrayList<AddressVO>();
		dbMgr = DBConnectionMgr.getInstance();
	    StringBuilder sql = new StringBuilder();	
        sql.append("SELECT id,name,address,gender,hp");
        sql.append("  ,birthday,comments,regdate    ");
        sql.append("  FROM mkaddrtb                 ");
        try {
			con = dbMgr.getConnection();//오라클 서버에 접속.
			pstmt = con.prepareStatement(sql.toString());//오라클 서버에게 셀렉트문으로 ..?
			rs = pstmt.executeQuery();
			//rs.previous()를 쓰지 않는 이유는 오라클은 커서가 항상 (디폴트) top에 있으니까. list.
			//VO는 XXX장애를 가지고 있다. 한 행만 담을 수 있는 장애를 가지고 있음.
			AddressVO raVO = null;//널 : 우선 기다려.
			while(rs.next()) {
				raVO = new AddressVO();
				raVO.setId(rs.getString("id"));
				raVO.setName(rs.getString("Name"));
				raVO.setAddress(rs.getString("Address"));
				raVO.setGender(rs.getString("Gender"));
				raVO.setBirthday(rs.getString("Birthday"));
				raVO.setHp(rs.getString("Hp"));
				raVO.setRegdate(rs.getString("Regdate"));
				raVO.setComments(rs.getString("Comments"));
				list.add(raVO);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {//사용한 자원 반납하기. con,pstmt,rs
			dbMgr.freeConnection(con,pstmt,rs);
		}
   		return list;
	}
	
}
