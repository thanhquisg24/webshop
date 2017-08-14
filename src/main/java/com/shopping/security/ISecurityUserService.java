package com.shopping.security;

public interface ISecurityUserService {

    String validatePasswordResetToken(long id, String token);

}
