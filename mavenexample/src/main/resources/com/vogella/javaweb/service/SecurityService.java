// for login hedera
package com.vogella.javaweb.service;

public interface SecurityService{
	 String findLoggedInUsername();
	 void autoLogin(String username, String password);
}