package com.emp.model;

import java.util.List;



public class EmpService {

		private EmpDAO_interface dao;

		public EmpService() {
//			dao = new EmpJDBCDAO();
			dao = new EmpDAO();
		}
		
		public EmpVO addEmp(String emp_password, java.sql.Timestamp emp_add_time, String emp_mail, String emp_phone,
				Integer emp_status, Integer emp_auth, java.sql.Timestamp emp_last_edit_time, String emp_last_editor) {

			EmpVO empVO = new EmpVO();

			empVO.setEmp_password(emp_password);
			empVO.setEmp_add_time(emp_add_time);
			empVO.setEmp_mail(emp_mail);
			empVO.setEmp_phone(emp_phone);
			empVO.setEmp_status(emp_status);
			empVO.setEmp_auth(emp_auth);
			empVO.setEmp_last_edit_time(emp_last_edit_time);
			empVO.setEmp_last_editor(emp_last_editor);
					
			dao.insert(empVO);

			return empVO;
		}
		public EmpVO updateEmp(String emp_id, String emp_password, java.sql.Timestamp emp_add_time, String emp_mail, String emp_phone,
				Integer emp_status, Integer emp_auth, java.sql.Timestamp emp_last_edit_time, String emp_last_editor) {


			EmpVO empVO = new EmpVO();

			empVO.setEmp_id(emp_id);
			empVO.setEmp_password(emp_password);
			empVO.setEmp_add_time(emp_add_time);
			empVO.setEmp_mail(emp_mail);
			empVO.setEmp_phone(emp_phone);
			empVO.setEmp_status(emp_status);
			empVO.setEmp_auth(emp_auth);
			empVO.setEmp_last_edit_time(emp_last_edit_time);
			empVO.setEmp_last_editor(emp_last_editor);
			dao.update(empVO);

			return dao.findByPrimaryKey(emp_id);
		}
		
		public void updateEmp(EmpVO emp_id) {
			dao.update(emp_id);
		}

		public void deleteEmp(String emp_id) {
			dao.delete(emp_id);
		}

		public EmpVO getOneEmp(String emp_id) {
			return dao.findByPrimaryKey(emp_id);
		}

		public List<EmpVO> getAll() {
			return dao.getAll();
		}
		//登入
		public EmpVO login(String empEmail, String empPassword) {
			EmpVO empVO = dao.findByAccount(empEmail);
			if(empVO!=null) {
				if(empPassword!=null && empPassword.length()!=0) {
					String pass = empVO.getEmp_password();
					System.out.println(pass);
					if(pass.equals(empPassword)) {
						return empVO;
					}
				}
			}
			return null;
		}
		
		public static void main(String[] args) {
			
			EmpService empService = new EmpService();
			// �s�W
//			empService.addEmp(new byte[10], java.sql.Timestamp.valueOf("2020-12-11 21:37:13"), "asdf@gmail.com", "0900-00061", 1,
//					2, java.sql.Timestamp.valueOf("2020-11-11 21:37:13"), "123214");
			// �ק�
//			empService.updateEmp("EMP00150", new byte[11], java.sql.Timestamp.valueOf("2020-12-11 21:37:13"), "XXXXX@gmail.com", "0900-06060", 1,
//					2, java.sql.Timestamp.valueOf("2020-11-11 21:37:13"), "1");
			// �R��
//			empService.deleteEmp("EMP00200");
			// �d�� (�浧)
//			empService.getOneEmp("EMP00150");
			// �d�� (����)
			empService.getAll();
		}
}
