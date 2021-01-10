package com.emp.model;

import java.util.*;


public interface EmpDAO_interface {
	public void insert(EmpVO empVO);
    public void update(EmpVO empVO);
    public void delete(String emp_id);
    public EmpVO findByPrimaryKey(String emp_id);
    public List<EmpVO> getAll();
    public EmpVO findByAccount(String empMail);
    
//    �U�νƦX�d��(�ǤJ�Ѽƫ��AMap)(�^�� List)
//    public List<FavoritesVO> getAll(Map<String, String[]> map); 

}
