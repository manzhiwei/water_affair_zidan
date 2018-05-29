package com.welltech.security.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails extends WtUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private List<UserRole> roles;

    public MyUserDetails(WtUser user){
        //this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        /**
    	if(roles == null || roles.size() <1){
            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
        }
        StringBuilder commaBuilder = new StringBuilder();
        for(UserRole role : roles){
            commaBuilder.append(role.getRole()).append(",");
        }
        String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
         */
    }

    @Override
    public String getPassword() {
        return null;
        //super.getPassword();
    }

    @Override
    public String getUsername() {
        return null;
        //super.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}