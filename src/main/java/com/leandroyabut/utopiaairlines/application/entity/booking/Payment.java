package com.leandroyabut.utopiaairlines.application.entity.booking;

public class Payment {
    private String stripeId;
    private boolean refunded;

    public Payment(String stripeId, boolean refunded) {
        this.stripeId = stripeId;
        this.refunded = refunded;
    }

    public boolean isRefunded() {
        return refunded;
    }

    public void setRefunded(boolean refunded) {
        this.refunded = refunded;
    }

    public String getStripeId() {
        return stripeId;
    }

    public void setStripeId(String stripeId) {
        this.stripeId = stripeId;
    }
}
