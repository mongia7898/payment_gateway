package com.mongia.razorpay.common.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Money {
    private Integer amountUnits;
    private String currency;

    public Money add(Money money) throws IllegalAccessException {
        if(!money.currency.equals(this.currency)){
            throw new IllegalAccessException("Cannot add money with different currencies");

        }
        return new Money(this.amountUnits+money.amountUnits,this.currency);

    }

    public Money of(){
        return new Money();
    }


}
