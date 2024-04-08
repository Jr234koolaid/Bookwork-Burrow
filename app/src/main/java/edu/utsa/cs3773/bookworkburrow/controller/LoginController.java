package edu.utsa.cs3773.bookworkburrow.controller;

import static edu.utsa.cs3773.bookworkburrow.FirebaseUtil.loginWithUsernamePassword;


import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.CompletableFuture;

import edu.utsa.cs3773.bookworkburrow.model.Account;

public class LoginController
{
    AppCompatActivity context;
    public LoginController(AppCompatActivity context){
        this.context = context;
    }
    public CompletableFuture<Account> logIn(String email, String password){
        return loginWithUsernamePassword(email, password, context);
    }
} // public class LoginController
