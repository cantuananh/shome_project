package com.shopme.admin.service;

public class CategoryNoFoundException extends Exception {
    public CategoryNoFoundException(String message) {
        super(message);
    }
}
