package com.zigartiq.ledger.utils;

import com.zigartiq.ledger.entity.Category;

import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String[] PUBLIC_URL_PATTERNS = {
            "/api/auth/**",
            "/docs",
            "/docs/swagger-config",
            "/swagger-ui/**",
    };

    public static enum TransactionType {
        INCOME,
        EXPENSE
    }

    public static final List<Category> DEFAULT_CATEGORIES = Arrays.asList(
            new Category("Food", "#FF6B6B", "🍕"),
            new Category("Bills", "#4ECDC4", "💰"),
            new Category("Transport", "#45B7D1", "🚗"),
            new Category("Entertainment", "#96CEB4", "🎥"),
            new Category("Health", "#FF7F50", "⚕️"),
            new Category("Salary", "#98D8AA", "🎊"),
            new Category("Other", "#6C757D", "📦"));
}
