package com.wrudt.library;

import com.wrudt.beta.R;

public class User {

	public String city;
	public String name;
	public String email;
	public String password;
	private Sex sex;
	
	public void setSex(int id){
		sex = (id == R.id.radio_btn_f)?  Sex.f:Sex.m;
	}
	
	public String getSex(Void v){
		return (sex == Sex.f)?"f":"m";
	}
		

}

enum Sex{
	m,
	f
}
