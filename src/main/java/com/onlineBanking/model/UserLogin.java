package com.onlineBanking.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserLogin implements UserDetails {

   private User user;
    
   public UserLogin(User user) {
       this.user = user;
   }

  
   @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
	  return null;
 }
   
//   @Override
//   public Collection<? extends GrantedAuthority> getAuthorities() {
//      Set<Role> roles = user.getRoles();
//      List<SimpleGrantedAuthority> authorities= new ArrayList<>();
//      for(Role role:roles) {
//    	authorities.add(new SimpleGrantedAuthority(role.getName())); 
//      }
//	   return authorities;
//   }

   @Override
   public String getPassword() {
       return user.getPassword();
   }

   @Override
   public String getUsername() {
       return user.getUserName();
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
       return user.isEnabled();
   }
    
 

}
